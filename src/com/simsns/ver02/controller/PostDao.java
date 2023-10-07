package com.simsns.ver02.controller;
import com.simsns.ver02.model.Post;
import com.simsns.ver02.model.User;

import java.util.TreeMap;
import java.util.UUID;

public interface PostDao {

    /**
     * 전체 게시글 반환하는 메서드
     *
     * @return 전체 게시글 TreeMap<id값, Post>
     */
    TreeMap<Integer, Post> get ();

    /**
     * 조회할 게시글 index를 넘기면, 해당 게시글 반환하는 메서드
     *
     * @param index 조회할 게시글 index
     * @return index에 해당하는 게시글 반환
     */
    Post get (int index);

    /**
     * 해당 유저가 게시글 작성자인지 확인하는 메서드
     *
     * @param userIndex 유저의 index
     * @param postIndex 게시글 index
     * @return 해당 게시글의 유저의 index가 일치하면 true 반환, 다른 사람의 index면 false 바환
     */
    boolean checkOwner (UUID userIndex, int postIndex);

    /**
     * 게시글의 index를 받아 해당 게시글에 like를 1 올려주는 메서드
     *
     * @param index like를 누를 게시글의 index
     */
    void addLike (int index);

    /**
     * 게시글을 저장하는 메서드
     *
     * @param post 저장할 게시글
     * @return 저장이 완료되면 1 반환, 실패되면 0 반환
     */
    int save(Post post);

    /**
     * 게시글 수정 메서드. 수정 사항 제외 null을 전달하면 수정 사항만 변경됨.
     *
     * @param index 수정할 게시글의 index
     * @param title 수정할 타이틀, 수정하지 않을 시 null 전달.
     * @param content 수정할 내용, 수정하지 않을 시 null 전달.
     * @return 수정에 성공하면 1 반환, 수정에 실패하면 0 반환. 수정 사항이 아무것도 없을 시 수정 실패.
     */
    int modify (int index, String title, String content);

    /**
     * postIndex를 받아 해당 게시글을 삭제하는 메서드
     *
     * @param postIndex 삭제할 게시글의 index
     * @return 삭제에 성공하면 1 반환, 실패하면 0 반환.
     */
    int remove(int postIndex);

    /**
     * 전달받은 유저의 Index로 작성자를 확인하여 해당 유저의 게시글 목록 반환.
     *
     * @param userIndex 게시글을 조회할 유저의 index
     * @return 해당 유저의 게시글 목록
     */
    TreeMap<Integer, Post> getCurUserPost (UUID userIndex);

}
