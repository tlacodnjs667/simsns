package com.simsns.ver02_1.controller;

import java.util.UUID;

public interface UserDao {
    /**
     * 유저 생성 메서드, id, email, password를 받아 생성 결과를 (true : 성공, false : 실패)로 반환
     * 중복한 email과 id가 있을 경우 false를 반환.
     *
     * @param id 생성할 유저의 id
     * @param email 생성할 유저의 email
     * @param password 생성할 유저의 password
     * @return 유저 생성에 성공 시 true, 중복한 email과 id가 있을 경우 false를 반환.
     */
    boolean save (String id, String email, String password);

    /**
     * 유저의 id나 email을 받아 해당하는 유저의 id값 반환하는 메서드.
     * todo password 값을 받아야 id값을 조회할 수 있는 거 아닐까. 이 함수의 존재 의미에 관해 다시 생각해보아야 할듯.
     *
     * @param id 유저의 id값.
     * @param email 유저의 email
     * @return 해당하는 유저의 id
     */
    UUID getUser (String id, String email);

    /**
     * 해당 유저의 비밀 번호가 맞는 지 확인하는 메서드
     *
     * @param index 체크할 유저의 index
     * @param password 해당 유저의 비밀번호
     * @return index와 비밀번호가 일치하면 true, 일치하지 않으면 false 반환
     */
    boolean checkUserPassword (UUID index, String password);

    /**
     * user의 비밀번호를 수정하는 메서드
     *
     * @param index 비밀번호를 변경할 유저의 index
     * @param password 변경할 비밀번호
     * @return 변경에 성공 시 1, 실패 시 0 반환.
     */
    int update (UUID index, String password);

    /**
     * user 정보를 수정하는 메서드 (비밀번호와 이메일)
     * index를 제외하고 수정할 정보만 인자로 넘기면, 해당 정보를 수정할 수 있음.
     * 만약 (1)index가 null이거나 (2)해당 Index에 저장된 user 정보가 없거나,
     * (3)수정할 정보가 어떤 것도 없을 때 (두 정보 모두 이전과 동일할 때)는 수정에 실패.
     *
     * @param index 수정할 계정의 id값.
     * @param password 수정할 비밀 번호 (인자로 넘기지 않으면 이전과 동일)
     * @param email 수정할 이메일 (인자로 넘기지 않으면 이전과 동일)
     * @return 수정 성공 시 1, 실패 시 0을 반환.
     */
    int update (UUID index, String password, String email);

    /**
     * 유저 id 반환 메서드. index 또는 email을 이용하여 값을 얻을 수 있음.
     *
     * @param index user의 index
     * @param email user의 email
     * @return 유저의 id값 반환
     */
    String findUser (UUID index, String email);


    /**
     * 유저 데이터를 삭제하는 메서드.
     * 인자로 전달받은 index 값에 해당하는 유저를 데이터에서 삭제.
     *
     * @param index 삭제할 유저의 index
     * @return 삭제 성공 시 true, 실패 시 false 반환.
     */
    boolean erase (UUID index);

}
