package com.ssung.travelDiary.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileControllerTest {

    @Test
    void prod_profile이_조회된다() throws Exception {
        // given
        String expectedProfile = "prod";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile("test");
        env.addActiveProfile(expectedProfile);

        ProfileController controller = new ProfileController(env);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    void prod_profile이_없으면_첫_번째가_조회된다() throws Exception {
        // given
        String expectedProfile = "first";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("second");

        ProfileController controller = new ProfileController(env);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    void active_profile이_없으면_default가_조회된다() throws Exception {
        // given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();

        ProfileController controller = new ProfileController(env);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}