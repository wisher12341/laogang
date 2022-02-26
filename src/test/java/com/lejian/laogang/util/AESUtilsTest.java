package com.lejian.laogang.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESUtilsTest {


    @Test
    void encrypt() {
        String encode = AESUtils.encrypt("测试");
        System.out.println(encode);
        System.out.println(AESUtils.decrypt(encode));
    }

}