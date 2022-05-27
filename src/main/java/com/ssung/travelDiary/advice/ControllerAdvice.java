package com.ssung.travelDiary.advice;

import com.ssung.travelDiary.exception.board.BoardNotFountException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String MemberNotFoundException(MemberNotFoundException e) {

        return "error/MemberNotFound";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String BoardNotFountException(BoardNotFountException e) {

        return "error/BoardNotFound";
    }
}
