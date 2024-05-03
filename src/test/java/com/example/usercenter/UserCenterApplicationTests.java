package com.example.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {


    @Test
    void testDigest() {
        String result = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(result);
    }

    @Test
    void contextLoads() {

    }

}
