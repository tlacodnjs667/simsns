package com.simsns.ver01;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    Vector<User> users = new Vector<User>();
    Vector<Post> posts = new Vector<Post>();

    User cur = null;
    Scanner sc = new Scanner( System.in);
    public static void main(String[] args) {
        Main app = new Main();

        boolean run = true;
        System.out.println("*** Simstagram ***");

        while (true) {
            try {
                if (app.cur == null) {
                    app.userStream();
                    continue;
                }

                System.out.println("\n************ Simsns *************");
                System.out.println(app.cur.getId() + "님 반가워요!");
                int menu = app.selectMenu();

                switch (menu) {
                    case 0: // 종료
                        run = false;
                        break;
                    case 1: // 게시글 목록 조회
                        app.readAll();
                        break;
                    case 2: // 게시글 작성
                        app.createPost();
                        break;
                    case 3: // 게시글 수정/삭제
                        app.modifyOrDeletePost();
                        break;
                    case 4:
                        app.profileStream();
                        break;
                    default:
                        System.out.println("\n현재 준비되지 않은 서비스입니다.. \n 메뉴로 돌아갑니다.");
                        break;
                }

                System.out.println("\n메인메뉴로 돌아갑니다..");
            } catch (Exception e) {
                System.err.println(e);
                System.out.println("다시 시도하세요.");
            }
        }


    }

    private int selectMenu () {
        int menu = 0;

        System.out.println("\n무엇을 실행할까요?");
        System.out.println("| [0] 종료\t | [1]게시글 목록 \t| [2] 게시글 작성\t \n| [3] 게시글 수정/삭제 | [4] 프로필\t |");
        System.out.print("입력 >> ");
        menu = Integer.parseInt(sc.nextLine());
        return menu;
    }

    private void userStream() {
        System.out.println("\n유저만 이용가능합니다!");
        System.out.println("[0]로그인 | [1]회원가입 | [2]아이디/비밀번호 분실");
        System.out.print("입력 >> ");

        int select =Integer.parseInt(sc.nextLine());

        switch (select) {
            case 0:
                signIn();
                break;
            case 1:
                signUp();
                break;
            case 2 :
                findIdOrPsword();
                break;
        }


        return;
    }

    private void readAll () {
        if (posts.isEmpty()) {
            System.out.println("저장된 포스트가 없습니다.");
            return;
        }


        System.out.printf("\n전체 포스트는 %d개입니다.\n", posts.size());

        for (int i = 0; i < posts.size(); i++) {
            System.out.println("["+i+"]"  + posts.get(i));
        }


        System.out.print("\n내용을 보고싶은 게시글의 번호를 입력하세요." +
                "\n공백 입력 시 메인으로 돌아갑니다."+
                "\n입력 >> ");

        String input = sc.nextLine();

        if(input.isEmpty()) {
            return;
        }

        readDetail(Integer.parseInt(input));
    }

    private void createPost () {
        System.out.println("\n****** 게시글 작성 ******");

        System.out.print("\n제목 입력 >>");
        String title=sc.nextLine();
        System.out.print("내용 입력 >>");
        String content=sc.nextLine();

        Post postToSave = new Post(title, content, cur);

        System.out.println("\n# 게시글의 내용을 확인하세요!\n");
        System.out.println("---------------------------");
        System.out.println("제목 | " + title);
        System.out.println("내용 | " + content);
        System.out.println("---------------------------");
        System.out.println("\n저장하시겠습니까?  [0]Y | [1]N");
        System.out.println("# 주의 | 취소 시 모든 내용이 삭제됩니다.");

        System.out.print("입력 >> ");
        int selected = Integer.parseInt(sc.nextLine());
        if (selected == 1) {
            return;
        }

        posts.add(postToSave);
        System.out.println("게시글이 저장되었습니다..");
    }

    private void modifyOrDeletePost() {
        System.out.println("**** 게시글 수정 / 삭제 ****");
        System.out.println("\n회원님의 게시글을 보여드릴게요!");

        HashMap <Integer, Post> postsByUser = getCurUserPosts();

        if (postsByUser.isEmpty()) {
            System.out.println("현재 회원님이 작성한 게시물이 없습니다. 게시물을 작성해주세요!");
            return;
        }

        for (HashMap.Entry<Integer, Post> entry : postsByUser.entrySet()) {
            System.out.println( "["+entry.getKey() + "] " + entry.getValue());
        }

        System.out.println("[] 안에 적힌 숫자가 해당 게시글의 번호입니다.");
        System.out.println("수정/삭제할 게시글의 번호를 입력하세요.");
        System.out.print("입력 >> ");

        int postIdx = Integer.parseInt(sc.nextLine());

        if (posts.get(postIdx).getOwner() != cur) {
            System.out.println("해당 게시글의 수정/삭제 권한이 없습니다..");
            return;
        }

        System.out.println("다음 게시글이 맞나요?\n" + posts.get(postIdx));
        System.out.println("실행을 선택하세요.\t\t [0] 수정\t| [1] 삭제\t | [2] 메인메뉴로..");

        int process = Integer.parseInt(sc.nextLine());

        switch (process) {
            case 0:
                modifyPost(postIdx);
                break;
            case 1:
                deletePost(postIdx);
                break;
            case 2:
                break;
            default:
                System.out.println("실행할 수 없는 번호입니다.");
                break;
        }

    }

    private void profileStream () {
        System.out.println("\n****** 프로필 보기 ******");
        System.out.println("아이디 >> " + cur.getId());
        System.out.println();

        HashMap <Integer, Post> postsByUser = getCurUserPosts();

        for (HashMap.Entry<Integer, Post> entry : postsByUser.entrySet()) {
            System.out.println(entry.getValue());
        }

        System.out.println("\n[0] 로그아웃 | [1] 계정 수정하기 | [2] 아이디 삭제 | [3] 목록으로 돌아가기");
        int profileMenu = Integer.parseInt(sc.nextLine());
        switch (profileMenu) {
            case 0 :
                cur = null;
                System.out.println("로그아웃됩니다.");
                break;
            case 1 :
                modifyUserInfo();
                break;
            case 2 :
                eraseUser();
                break;
            case 3:
                System.out.println("목록으로 돌아갑니다.");
                return;
            default:
        }
    }

    private void readDetail (int idx) {
        if (idx >= posts.size() || idx < 0) {
            System.out.println("해당 번호에 저장된 게시글이 없습니다.");
            return;
        }

        Post postToRead = posts.get(idx);

        System.out.println("---- [" + idx +"] 번 게시물 ----");
        System.out.println(postToRead.getTitle() + " | ♥\uFE0F" + postToRead.getLikes());
        System.out.println(postToRead.getOwner().getId() + "님의 게시글입니다.");
        System.out.println(postToRead.getContent());

        if(!postToRead.getComments().isEmpty()) {
            System.out.printf("\n--------------- 댓글보기[%d 개] ---------------\n", postToRead.getComments().size());
        for (int i = 0; i< postToRead.getComments().size(); i++){
            System.out.println(postToRead.getComments().get(i));
        }
            System.out.println("-------------------------------------");
        }

        else {
            System.out.println("\n-------------------------------------");
            System.out.println("등록된 댓글이 없습니다.");
        }

        System.out.println("[0] 게시글 목록\t| [1] 좋아요 누르기\t| [2] 댓글 작성");

        int selectedNum = Integer.parseInt(sc.nextLine());
        switch (selectedNum) {
            case 0:
                readAll();
                break;
            case 1:
                putLike(idx);
                break;
            case 2: // 댓글 달기....
                addComment(idx);
                break;
        }
    }

    /*
    * 여기까지 AppMain에 있어야 할 것
    *
    * 이후, 대게 Control단에 있어야 함.
    * */

    private void findIdOrPsword (){
        System.out.println("****** 아이디/비밀번호를 잃어버렸어요 ******");
        System.out.println("[0] 아이디 분실\t| [1] 비밀번호 분실");

        System.out.print("입력 >> ");
        int selectNum = Integer.parseInt(sc.nextLine());

        if (selectNum == 0) {
            System.out.print("\n이메일을 입력해주세요.\n입력 >> ");

            String email = sc.nextLine();

            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    System.out.println("아이디는 '" + user.getId() + "'입니다.");
                    return;
                }
            }
        } else if (selectNum == 1) {
            System.out.print("\n아이디를 입력해주세요.\n입력 >> ");

            String id = sc.nextLine();

            int userIdx = User.findUserIndexById(users, id);

            if (userIdx == -1) {
                System.out.println("해당하는 유저 정보가 없습니다..");
                return;
            }

            System.out.println("\n****** 본인인증 ******");
            System.out.println("해당 아이디로 등록한 이메일을 입력해주세요..");
            System.out.print("입력 >> ");
            String emailToAuth = sc.nextLine();

            boolean isAccountOwner = users.get(userIdx).getEmail().equals(emailToAuth);

            if (!isAccountOwner) {
                System.out.println("\n회원정보가 일치하지 않습니다..");
                System.out.println("다시 시도해주세요..");
                return;
            }


            System.out.println("비밀번호를 변경합니다. 변경할 비밀번호를 입력해주세요.");
            System.out.print("입력 >> ");
            String psword = sc.nextLine();

            users.get(userIdx).setPassword(psword);

            System.out.println("비밀번호가 수정되었습니다. 다시 로그인을 시도하세요.");
        } else  {
            System.out.println("해당 번호에 서비스가 없습니다.");
        }


    }

    private void putLike (int idx) {
        boolean canLike = !checkOwnerOfPost(idx);

        if (!canLike) {
            System.out.println("\n** 본인 게시글에는 좋아요를 누를 수 없습니다. **\n");
            readDetail(idx);
            return;
        }

        Post postToRead = posts.get(idx);
        postToRead.setLikes(postToRead.getLikes()+1);
    }

    private void addComment (int idx) {

        System.out.println("등록된 댓글은 수정/삭제 불가");
        System.out.print("\n댓글 내용 입력 >> ");
        String comment = sc.nextLine();

        System.out.println("다음 댓글을 등록할까요? [0] Y | [1] N");
        System.out.println(comment);

        System.out.print("입력 >> ");
        boolean isForUpload = Integer.parseInt(sc.nextLine()) == 0;

        if (!isForUpload) {
            System.out.println("해당 게시글로 돌아갑니다..");
            readDetail(idx);
            return;
        }

        Comment commentToAdd = new Comment(comment, cur);

        posts.get(idx).getComments().add(commentToAdd);
        readDetail(idx);
    }

    private void modifyPost(int idx){
        System.out.println(idx + "번 게시글을 수정합니다.");

        boolean canModify = checkOwnerOfPost(idx);
        Post postToModify = posts.get(idx);

        if (!canModify) {
            System.out.println("해당 게시글의 수정권한이 없습니다.");
            return;
        }

        System.out.println("*****************");
        System.out.println("수정 사항만 입력해주세요!\n공백으로 놔두면 이전 사항이 저장됩니다.");

        System.out.print("수정할 제목 >>");
        String title = sc.nextLine();

        System.out.print("수정할 내용 >>");
        String content = sc.nextLine();

        System.out.println("수정 사항을 확인하세요.");

        System.out.println("* 제목 : " + (title.isEmpty() ? postToModify.getTitle() : title));
        System.out.println("* 내용 : " + (content.isEmpty() ? postToModify.getContent() : content ));

        if (!title.isEmpty()) {
            postToModify.setTitle(title);
        }
        if (!content.isEmpty()) {
            postToModify.setContent(content);
        }
        System.out.println("수정사항이 반영되었습니다..");
    }
    private void deletePost(int idx){
        System.out.println(idx + "번 게시글을 삭제합니다.");

        boolean canDelete = checkOwnerOfPost(idx);

        if (!canDelete) {
            System.out.println("해당 게시글의 삭제권한이 없습니다.");
            return;
        }

        posts.remove(idx);
        System.out.println("삭제되었습니다.");
    }

    private void eraseUser(){
        System.out.println("정말 삭제하실 건가요? [0]Y | [1]N");
        System.out.print(">> ");
        int firstChoice = Integer.parseInt(sc.nextLine());
        if (firstChoice == 1) {
            System.out.println("다행이네요...");
        }
        System.out.println("정말 정말 삭제하실 건가요?");
        System.out.print(">> ");
        int secondChoice = Integer.parseInt(sc.nextLine());

        if (secondChoice == 1) {
            System.out.println("다행이네요...");
            return;
        }

        int userIdx = User.findUserIndexById(users, cur.getId());

        if (userIdx == -1){
            System.out.println("앵 우ㅔ 안대");
            return;
        }

        cur = null;
        users.remove(userIdx);

        System.out.println("계정이 삭제되었어요.\n로그인 페이지로 돌아갑니다.");
    };

    private void modifyUserInfo () {
        System.out.println("\n*** 회원정보 수정 ***");


        User userToModify = users.get(User.findUserIndexById(users, cur.getId()));
        System.out.println("[본인인증] 현재 비밀번호를 확인합니다..");
        System.out.print("현재 비밀번호 입력 >>");
        String pswordToCheck = sc.nextLine();

        if (!userToModify.getPassword().equals(pswordToCheck)) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        };

        System.out.println("\n수정할 정보만 입력해주세요!\n# 아이디 수정은 불가능합니다!");

        System.out.print("비밀번호 입력 >> ");
        String password = sc.nextLine();
        System.out.print("이메일 입력 >> ");
        String email = sc.nextLine();


        if (password.isEmpty()) {
            userToModify.setPassword(password);
        }

        if (email.isEmpty()) {
            userToModify.setEmail(email);
        }

        System.out.println("회원정보 수정이 완료되었습니다!");

    }



    private int signUp (){
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
            return 0;
        }

        int idx =User.findUserIndexById(users, id);

        if (idx != -1) {
            System.out.println("이미 사용중인 아이디입니다..");
            return 0;
        }

        User newUser = new User(id, password, email);
        users.add(newUser);

        return 1;
    }

    private int signIn() {
        System.out.println("\n********* 로그인 *********");
        System.out.print("\n아이디 입력 >> ");
        String id = sc.nextLine();
        System.out.print("비밀번호 입력 >> ");
        String password = sc.nextLine();

        int matchedUserIdx = User.findUserIndexById(users, id);

        if (matchedUserIdx == -1) {
            System.out.println("해당 계정정보가 없습니다.. \n다시 시도하세요..");
            return 0;
        }

        User matchedUser= users.get(User.findUserIndexById(users, id));



        if(!matchedUser.getPassword().equals(password) ) {
            System.out.println("비밀번호가 틀렸습니다.. \n다시 시도하세요..");
            return 0;
        }

        this.cur = matchedUser;

        return 1;
    }

    private HashMap<Integer, Post> getCurUserPosts () {
        HashMap<Integer, Post> resultPost = new HashMap<Integer, Post>();


        for (int i = 0 ; i < posts.size(); i ++) {
            if (posts.get(i).getOwner() == cur) {
                resultPost.put(i, posts.get(i));
            }
        }

        return resultPost;
    }

    private boolean checkOwnerOfPost (int postIdx) {
        return posts.get(postIdx).getOwner() == cur;
    }

}