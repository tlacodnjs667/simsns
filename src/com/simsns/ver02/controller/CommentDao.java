package com.simsns.ver02.controller;

import com.simsns.ver02.model.Comment;

import java.util.UUID;
import java.util.Vector;

public interface CommentDao {
    /**
     * idx에 해당하는 게시글에 인자로 받은 댓글 저장하는 메서드
     *
     * @param userIndex 댓글 작성자
     * @param postIndex 댓글을 저장할 게시글의 인덱스
     * @param content 저장할 댓글
     * @return 저장 성공 시 1, 실패 시 0
     */
    int save(UUID userIndex, int postIndex, String content);
}
