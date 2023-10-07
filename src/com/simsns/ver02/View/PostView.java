package com.simsns.ver02.View;

import com.simsns.ver01.Comment;
import com.simsns.ver02.controller.CommentDaoImpl;
import com.simsns.ver02.controller.PostDaoImpl;
import com.simsns.ver02.controller.UserDaoImpl;
import com.simsns.ver02.model.Post;

import java.util.*;

public class PostView {

    private static PostView instance;

    Scanner scanner = new Scanner(System.in);
    private Util util = Util.getInstance();

    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final PostDaoImpl postDao = PostDaoImpl.getInstance();
    private static final CommentDaoImpl commentDao = CommentDaoImpl.getInstance();

    private static UserView userView = UserView.getInstance();


    private PostView () {}

    public static PostView getInstance () {
        if (instance == null) {
            instance = new PostView();
        }

        return instance;
    };

    public void read() {
        TreeMap<Integer, Post> posts = postDao.get();

        if (posts.isEmpty()) {
            System.out.println("저장된 포스트가 없습니다.");
            return;
        }

        System.out.printf("\n전체 포스트는 %d개입니다.\n", posts.size());

        Set<Integer> set = posts.keySet();

        for (Integer index : set) {
            System.out.println("["+index+"]"  + posts.get(index));
        }

        System.out.print("\n내용을 보고싶은 게시글의 번호를 입력하세요." +
                "\n공백 입력 시 메인으로 돌아갑니다." +
                "\n입력 >> ");

        int index = util.scanCanEmpty();

        if (index == -1) {
            return;
        }

        read(index);
    };

    public void read(int index) {

        Post postToRead = postDao.get(index);

        if (postToRead == null) {
            System.out.println("해당 번호에 저장된 게시글이 없습니다.");
            return;
        }

        System.out.println("---- [" + index +"] 번 게시물 ----");
        System.out.println(postToRead.getTitle() + " | ♥\uFE0F" + postToRead.getLikes());
        System.out.println(userDao.findUser(postToRead.getOwner(), null) + "님의 게시글입니다.");
        System.out.println(postToRead.getContent());

        if(!postToRead.getComments().isEmpty()) {
            System.out.printf("\n--------------- 댓글보기[%d 개] ---------------\n", postToRead.getComments().size());
            for (int i = 0; i< postToRead.getComments().size(); i++){
                System.out.println( userDao.findUser(postToRead.getOwner(), null) + " | "+ postToRead.getComments().get(i));
            }
            System.out.println("-------------------------------------");
        } else {
            System.out.println("\n-------------------------------------");
            System.out.println("등록된 댓글이 없습니다.");
        }

        System.out.println("[0] 게시글 목록\t| [1] 좋아요 누르기\t| [2] 댓글 작성");

        int selectedNum = util.scan();

        switch (selectedNum) {
            case 0 -> read();
            case 1 -> putLike(index);
            case 2 -> addComment(index);
        }

    }

    private void putLike (int index) {
        boolean isUserAuthor = postDao.checkOwner(userView.curUserIndex, index);

        if (isUserAuthor) {
            System.out.println("자신의 게시글에 좋아요를 누를 수 없습니다!");
            read(index);
        }

        postDao.addLike(index);
        read(index);
    }

    private void addComment(int index) {

        System.out.println("등록된 댓글은 수정/삭제 불가");
        System.out.print("\n댓글 내용 입력 >> ");
        String comment = scanner.nextLine();

        System.out.println("다음 댓글을 등록할까요? [0] Y | [1] N");
        System.out.println(comment);

        System.out.print("입력 >> ");
        boolean isForUpload = util.scan() == 0;

        if (!isForUpload) {
            System.out.println("해당 게시글로 돌아갑니다..");
            read(index);
            return;
        }

        int result = commentDao.save(userView.getCurUser(), index, comment);

        if (result == 0) {
            System.out.println("댓글 저장에 실패했습니다.");
        }

    }

    public void createPost() {
        System.out.println("\n****** 게시글 작성 ******");

        System.out.print("\n제목 입력 >>");
        String title=scanner.nextLine();
        System.out.print("내용 입력 >>");
        String content = scanner.nextLine();

        Post postToSave = new Post(title, content, userView.curUserIndex);

        System.out.println("\n# 게시글의 내용을 확인하세요!\n");
        System.out.println("---------------------------");
        System.out.println("제목 | " + title);
        System.out.println("내용 | " + content);
        System.out.println("---------------------------");
        System.out.println("\n저장하시겠습니까?  [0]Y | [1]N");
        System.out.println("# 주의 | 취소 시 모든 내용이 삭제됩니다.");

        System.out.print("입력 >> ");

        int selected = util.scan();

        if (selected == 1) {
            System.out.println("입력 사항을 저장하지 않습니다.");
            return;
        }

        int result = postDao.save(postToSave);

        if (result == 0) {
            System.out.println("게시글 저장에 실패하였습니다.\n메인으로 돌아갑니다.");
            return;
        }

        System.out.println("게시글이 저장되었습니다..");
    };

    public void modifyOrDeletePost () {
        System.out.println("**** 게시글 수정 / 삭제 ****");
        System.out.println("\n회원님의 게시글을 보여드릴게요!");

        TreeMap<Integer, Post> postsByUser = postDao.getCurUserPost(userView.curUserIndex);

        if (postsByUser.isEmpty()) {
            System.out.println("현재 회원님이 작성한 게시물이 없습니다. 게시물을 작성해주세요!");
            return;
        }

        for (Map.Entry<Integer, Post> entry : postsByUser.entrySet()) {
            System.out.println( "["+entry.getKey() + "] " + entry.getValue());
        }

        System.out.println("[] 안에 적힌 숫자가 해당 게시글의 번호입니다.");
        System.out.println("수정/삭제할 게시글의 번호를 입력하세요.");
        System.out.print("입력 >> ");

        int postIdx = util.scan();

        if (!postDao.checkOwner(userView.curUserIndex, postIdx)) {
            System.out.println("해당 게시글의 수정/삭제 권한이 없습니다..");
            return;
        }

        System.out.println("다음 게시글이 맞나요?\n" + postDao.get(postIdx));
        System.out.println("실행을 선택하세요.\t\t [0] 수정\t| [1] 삭제\t | [2] 메인메뉴로..");

        int process = util.scan();

        switch (process) {
            case 0->
                modifyPost(postIdx);
            case 1 ->
                deletePost(postIdx);
            case 2 -> {
                return;
            }
            default -> System.out.println("실행할 수 없는 번호입니다.");
        }

    }

    private void modifyPost (int postIdx) {
        System.out.println(postIdx + "번 게시글을 수정합니다.");

        boolean canModify = postDao.checkOwner(userView.curUserIndex, postIdx);

        if (!canModify) {
            System.out.println("해당 게시글의 수정권한이 없습니다.");
            return;
        }

        System.out.println("*****************");
        System.out.println("수정 사항만 입력해주세요!\n공백으로 놔두면 이전 사항이 저장됩니다.");

        System.out.print("수정할 제목 >>");
        String title = scanner.nextLine();

        System.out.print("수정할 내용 >>");
        String content = scanner.nextLine();

        System.out.println("수정 사항을 확인하세요.");

        Post postToModify = postDao.get(postIdx);

        System.out.println("* 제목 : " + (title.isEmpty() ? postToModify.getTitle() : title));
        System.out.println("* 내용 : " + (content.isEmpty() ? postToModify.getContent() : content ));

        int result = postDao.modify (postIdx, title, content);

        if (result == 0) {
            System.out.println("수정할 내용이 없습니다.\n메뉴로 돌아갑니다.");
            return;
        }

        System.out.println("수정사항이 반영되었습니다..");
    }

    private void deletePost (int postIdx) {
        System.out.println(postIdx + "번 게시글을 삭제합니다.");

        boolean canDelete = postDao.checkOwner(userView.curUserIndex, postIdx);

        if (!canDelete) {
            System.out.println("해당 게시글의 삭제권한이 없습니다.\n메뉴로 돌아갑니다.");
            return;
        }

        int result = postDao.remove(postIdx);

        if (result == 0) {
            System.out.println("게시글 삭제에 실패하였습니다.\n메뉴로 돌아갑니다.");
        }

        System.out.println("삭제되었습니다.");
    }


}
