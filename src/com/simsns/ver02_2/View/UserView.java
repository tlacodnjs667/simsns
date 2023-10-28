package com.simsns.ver02_2.View;

import com.simsns.ver02_2.controller.PostDaoImpl;
import com.simsns.ver02_2.controller.UserDaoImpl;
import com.simsns.ver02_2.model.Post;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

public class UserView {
    private static UserView instance;

    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private final PostDaoImpl postDao = PostDaoImpl.getInstance();

    Scanner sc = new Scanner(System.in);
    Util util = Util.getInstance();

    UUID curUserIndex = null;

    boolean isAdmin = false;

    private UserView() {
    }

    public static UserView getInstance (){
        if (instance == null) {
            instance = new UserView();
        }
        return instance;
    }

//    -----------------------

    public void signStream () {
        System.out.println("\n유저만 이용가능합니다!");
        System.out.println("[0]로그인 | [1]회원가입 | [2]아이디/비밀번호 분실");

        int select = util.scan();

        switch (select) {
            case 0 -> signIn();
            case 1 -> signUp();
            case 2 -> findIdOrPassword ();
            default -> System.out.println("해당하는 서비스가 없습니다.");
        }

    }

    private void signIn () {
        System.out.println("\n********* 로그인 *********");
        System.out.print("\n아이디 입력 >> ");
        String id = sc.nextLine();
        System.out.print("비밀번호 입력 >> ");
        String password = sc.nextLine();

        UUID userIdx = userDao.getUser(id, null);

        if (userIdx == null) {
            System.out.println("일치하는 계정이 없습니다.\n다시 시도해주세요.");
            return;
        }

        if (!userDao.checkUserPassword(userIdx, password)) {
            System.out.println("비밀번호가 일치하지 않습니다.\n다시 시도해주세요.");
            return;
        }

        curUserIndex = userIdx;

        System.out.println( userDao.findUser(curUserIndex, null) + "님 반가워요!");
    }

    private void signUp () {
        System.out.println("\n********* 회원가입 *********");
        System.out.println("# 아이디와 비밀번호, 이메일은 필수 정보 입니다.");
        System.out.print("\n아이디 입력 >> ");
        String id = sc.nextLine();
        System.out.print("비밀번호 입력 >> ");
        String password = sc.nextLine();
        System.out.print("이메일 입력 >> ");
        String email = sc.nextLine();

        if (id.isEmpty() || password.isEmpty() || email.isEmpty()) {
            System.out.println("필수 정보가 누락되었습니다.\n다시 시도해주세요.");
            signUp();
            return;
        }

        if (userDao.getUser(id, null) != null) {
            System.out.println("중복된 아이디입니다.\n다시 시도해주세요.");
            signUp();
            return;
        }

        if (userDao.getUser(null, email) != null) {
            System.out.println("중복된 이메일입니다. 다시 시도해주세요.");
            signUp();
            return;
        }

        boolean result = userDao.save(id, email, password);

        if (!result) {
            System.out.println("회원가입에 실패하였습니다.");
        }

        System.out.println("회원가입이 완료되었습니다.");
    }

    private void findIdOrPassword () {
        System.out.println("****** 아이디/비밀번호를 잃어버렸어요 ******");
        System.out.println("[0] 아이디 분실\t| [1] 비밀번호 분실");

        System.out.print("입력 >> ");
        int selectNum = util.scan();

        switch (selectNum) {
            case 0 -> findUserById();
            case 1 -> modifyPassword();
            default -> System.out.println("해당 번호에 서비스가 없습니다.");
        }

    }

    private void findUserById () {
        System.out.print("\n이메일을 입력해주세요.\n입력 >> ");
        String email = sc.nextLine();

        System.out.println("아이디는 '" + userDao.findUser(null, email) + "'입니다.");
    }

    private void modifyPassword () {

        System.out.print("\n아이디를 입력해주세요.\n입력 >> ");
        String id = sc.nextLine();

        UUID index = userDao.getUser(id, null);
        if( index == null) {
            System.out.println("해당하는 유저 정보가 없습니다..");
            return;
        }

        System.out.println("\n****** 본인인증 ******");
        System.out.println("해당 아이디로 등록한 이메일을 입력해주세요..");
        System.out.print("입력 >> ");
        String emailToAuth = sc.nextLine();

        boolean isAccountOwner = (userDao.getUser(id, emailToAuth) != null);

        if (!isAccountOwner) {
            System.out.println("\n회원정보가 일치하지 않습니다..");
            System.out.println("다시 시도해주세요..");
            return;
        }


        System.out.println("비밀번호를 변경합니다. 변경할 비밀번호를 입력해주세요.");
        System.out.print("입력 >> ");
        String password = sc.nextLine();

        int resultOfUpdate = userDao.update(index, password);

        if (resultOfUpdate == 0) {
            System.out.println("비밀번호 수정에 실패했습니다.\n비밀번호 찾기를 다시 수행해주세요..");
        }

        System.out.println("비밀번호가 수정되었습니다. 다시 로그인을 시도하세요.");
    }

    public void profileStream () {
        System.out.println("\n****** 프로필 보기 ******");
        System.out.println("아이디 >> " + userDao.findUser(curUserIndex, null));
        System.out.println();

        TreeMap<Integer, Post> posts = postDao.getCurUserPost(curUserIndex);

        if (posts.isEmpty()) {
            System.out.println("작성한 글이 없어요!\n게시글을 작성해주세요.");
        }

        Set<Integer> kset = posts.keySet();

        for (Integer key : kset) {
            System.out.println("[" +key+ "]" + posts.get(key));
        }

        System.out.println("\n[0] 로그아웃 | [1] 계정 수정하기 | [2] 아이디 삭제 | [3] 목록으로 돌아가기");

        int profileMenu = util.scan();

        switch (profileMenu) {
            case 0 -> {
                curUserIndex = null;
                System.out.println("로그아웃됩니다.");
            }
            case 1 -> modifyUserInfo();
            case 2 -> eraseUser();
            case 3 -> System.out.println("목록으로 돌아갑니다.");
            default -> System.out.println("\n현재 준비되지 않은 서비스입니다.. \n 메뉴로 돌아갑니다.");
        }

    }

    private void modifyUserInfo () {
        System.out.println("\n*** 회원정보 수정 ***");

        System.out.println("[본인인증] 현재 비밀번호를 확인합니다..");
        System.out.print("현재 비밀번호 입력 >>");
        String passwordToCheck = sc.nextLine();

        if (!userDao.checkUserPassword(curUserIndex, passwordToCheck)) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        }

        System.out.println("\n수정할 정보만 입력해주세요!\n# 아이디 수정은 불가능합니다!");

        System.out.print("비밀번호 입력 >> ");
        String password = sc.nextLine();
        System.out.print("이메일 입력 >> ");
        String email = sc.nextLine();

        int result = userDao.update(curUserIndex, password, email);

        if (result == 0) {
            System.out.println("수정할 정보가 없습니다!");
        }

        System.out.println("회원정보 수정이 완료되었습니다!");

    }

    private void eraseUser(){
        System.out.println("정말 삭제하실 건가요? [0]Y | [1]N");
        System.out.print(">> ");
        int firstChoice = util.scan();

        if (firstChoice == 1) {
            System.out.println("다행이네요...");
        }

        System.out.println("정말 정말 삭제하실 건가요?");
        System.out.print(">> ");
        int secondChoice = util.scan();

        if (secondChoice == 1) {
            System.out.println("다행이네요...");
            return;
        }

        boolean result = userDao.erase(curUserIndex);

        if (!result) {
            System.out.println("계정 삭제에 실패했습니다.\n다시 시도해주세요.");
            profileStream();
            return;
        }

        curUserIndex = null;
        System.out.println("계정이 삭제되었어요.\n로그인 페이지로 돌아갑니다.");
    };


    public UUID getCurUser () {
        return curUserIndex;
    }
}
