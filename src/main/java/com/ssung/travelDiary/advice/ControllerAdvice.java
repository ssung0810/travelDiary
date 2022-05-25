package com.ssung.travelDiary.advice;

import com.ssung.travelDiary.exception.BoardNotFountException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String MemberNotFoundException(MemberNotFoundException e,
                                                      HttpServletRequest request) {
        return "error/MemberNotFound";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String BoardNotFountException(BoardNotFountException e,
                                         HttpServletRequest request) {

        String referer = request.getHeader("referer");
        log.info("referer = {}", referer);
        return "error/BoardNotFound";
    }
}
