package com.soft1851.devoted.service.impl;

import com.soft1851.devoted.domain.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class TUserServiceImplTest {
    @Resource
    private TUserServiceImpl tUserServiceImpl;

    @Test
    void userLogin() {
        LoginDto loginDto = new LoginDto();
        loginDto.setAccount("111");
        loginDto.setPassword("123456");
        System.out.println(tUserServiceImpl.userLogin(loginDto));

    }
}