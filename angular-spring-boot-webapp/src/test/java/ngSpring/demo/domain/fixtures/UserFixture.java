package ngSpring.demo.domain.fixtures;

import ngSpring.demo.domain.entities.User;

public class UserFixture {
    public static User validData() {
        return User.builder()
                .enabled(true)
                .username("user")
                .password("password")
                .build();
    }

    public static User invalidData() {
        return User.builder().build();
    }
}
