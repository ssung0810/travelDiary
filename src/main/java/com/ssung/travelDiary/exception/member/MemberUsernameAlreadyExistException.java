package com.ssung.travelDiary.exception.member;

public class MemberUsernameAlreadyExistException extends RuntimeException {

    public MemberUsernameAlreadyExistException(String message) {
        super(message);
    }
}
