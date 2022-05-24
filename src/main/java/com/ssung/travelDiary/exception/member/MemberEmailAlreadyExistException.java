package com.ssung.travelDiary.exception.member;

public class MemberEmailAlreadyExistException extends RuntimeException {

    public MemberEmailAlreadyExistException(String message) {
        super(message);
    }
}
