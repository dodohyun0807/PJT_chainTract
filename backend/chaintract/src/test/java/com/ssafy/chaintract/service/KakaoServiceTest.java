package com.ssafy.chaintract.service;

import com.ssafy.chaintract.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoServiceTest {

    @Autowired KakaoService kakaoService;

    @Test
    void 카카오로그인테스트() throws Exception{

        String accessToken = "nUvw1cpL_F1vJIBf99St-Vhs2YypRG8oo371sAopb1UAAAF_zWaEIQ";

        User user = kakaoService.getUserInfoByToken(accessToken);
        System.out.println("socialId : "+user.getSocialId());
        System.out.println("email : "+ user.getEmail());
        System.out.println("name : "+user.getName());

    }

}