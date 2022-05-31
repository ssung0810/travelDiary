package com.ssung.travelDiary.password;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

public class PasswordEncoderTest {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void bcrypt로_인코딩되는지_확인() throws Exception {
        // given
        String password = "password";

        // when
        String encodePassword = passwordEncoder.encode(password);

        // then
        assertThat(encodePassword).contains("bcrypt");
    }

    @Test
    public void 인코딩전의_문자와_인코딩후의_문자가_같은지_확인() throws Exception {
        // given
        String password = "password";
        String encodePassword = passwordEncoder.encode(password);

        // when
        boolean matches = passwordEncoder.matches(password, encodePassword);

        // then
        assertThat(matches).isTrue();
    }
}
