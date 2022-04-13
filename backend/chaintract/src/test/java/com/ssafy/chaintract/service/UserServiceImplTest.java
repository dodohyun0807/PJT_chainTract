package com.ssafy.chaintract.service;

import com.ssafy.chaintract.domain.User;
import com.ssafy.chaintract.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired KakaoService kakaoService;

    private static final String accessToken = "nUvw1cpL_F1vJIBf99St-Vhs2YypRG8oo371sAopb1UAAAF_zWaEIQ";
    protected MockHttpServletRequest request;
    protected MockHttpSession session;

    @BeforeEach
    public void setUp() throws Exception{

        User user = new User();

        session = new MockHttpSession();

        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @After
    public void clear(){

    }

    @Test
    @Transactional
    public void 로그인() throws Exception{

        // given
        User user = kakaoService.getUserInfoByToken(accessToken);
        userService.login(user);

        // when
        User findUser = userRepository.findUserByEmail(user.getEmail()).get(0);

        // then
        Assertions.assertSame(user, findUser);
    }

    @Test
    @Transactional
    public void 로그아웃() throws Exception{

        // given
        User user = kakaoService.getUserInfoByToken(accessToken);
        userService.login(user);

        // when
        // 로그인
        HttpSession session_bofore = request.getSession();
        session_bofore.setAttribute("loginUser",user);

        // 로그아웃
        HttpSession session_after = request.getSession(false);
        session_after.invalidate();

        // then : 로그아웃 할 때 세션을 만료시키고, 로그인 할 때 만들었던 이전의 세션도 만료되었는지 테스트
        Assertions.assertThrows(IllegalStateException.class, () -> {
            session_bofore.getAttribute("loginUser");
        });
    }

    @Test
    @Transactional
    public void 회원탈퇴() throws Exception{

        // given
        User user = kakaoService.getUserInfoByToken(accessToken);
        userService.login(user);

        // when
        List<User> userList_before = userRepository.findUserByEmail(user.getEmail());
        userService.deleteUser(user.getEmail());
        List<User> userList_after = userRepository.findUserByEmail(user.getEmail());

        // then
        Assertions.assertEquals(userList_before.size(), 1);
        Assertions.assertEquals(userList_after.size(), 0);
    }


}