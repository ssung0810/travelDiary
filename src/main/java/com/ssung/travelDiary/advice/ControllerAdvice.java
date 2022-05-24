package com.ssung.travelDiary.advice;

import com.ssung.travelDiary.exception.member.MemberUsernameAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {

//    @ExceptionHandler(MemberUsernameAlreadyExistException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public String MemberUsernameAlreadyExistException(MemberUsernameAlreadyExistException e) {
//
//    }
}
