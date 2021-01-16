package com.kiprenko.springfield.restcontroller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserManageControllerTest {


    @LocalServerPort
    private int port;
    @Autowired
    private UserManageController controller;

    @Test
    void createUser() {
    }

    @Test
    void getUsersList() {
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsersCount() {

    }
}