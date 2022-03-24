package com.ssung.travelDiary.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtHandlerTest {

    JwtHandler jwtHandler = new JwtHandler();
//    @Autowired private JwtHandler jwtHandler;

    @Test
    public void Jwt토큰_생성() throws Exception {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());

        // when
        String token = jwtHandler.createToken(encodedKey, "subject", 60L);

        // then
        assertThat(token).contains("Bearer");
    }

    @Test
    public void Jwt토큰_유효성검사_성공() throws Exception {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.createToken(encodedKey, "subject", 60L);

        // when
        boolean validate = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(validate).isTrue();
    }

    @Test
    public void Jwt토큰_유효성검사_실패() throws Exception {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.createToken(encodedKey, "subject", 60L);

        // when
        boolean validate = jwtHandler.validate("encodedKey", token);

        // then
        assertThat(validate).isFalse();
    }

    @Test
    public void Jwt토큰_subject가져오기() throws Exception {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.createToken(encodedKey, "subject", 60L);

        // when
        String subject = jwtHandler.expectedSubject(encodedKey, token);

        // then
        assertThat(subject).isEqualTo("subject");
    }

    @Test
    public void Jwt토큰_만료() throws Exception {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.createToken(encodedKey, "subject", 0L);

        // when
        boolean validate = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(validate).isFalse();
    }
}