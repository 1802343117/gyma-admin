package com.soft1851.devoted.service.impl;

import com.soft1851.devoted.service.TMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TMenuServiceImplTest {
    @Resource
    private TMenuService tMenuService;

    @Test
    void selectById() {
        System.out.println(tMenuService.selectById(1));
    }
}