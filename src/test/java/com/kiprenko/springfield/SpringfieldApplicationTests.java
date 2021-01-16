package com.kiprenko.springfield;

import com.kiprenko.springfield.restcontroller.v1.UserManageController;
import com.kiprenko.springfield.restcontroller.v1.VersionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringfieldApplicationTests {

    @Autowired
    private UserManageController userManageController;
    @Autowired
    private VersionController versionController;

    @Test
    public void contextLoads() {
        assertThat(userManageController).isNotNull();
        assertThat(versionController).isNotNull();
    }
}
