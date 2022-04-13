package com.ssafy.chaintract.api;

import com.ssafy.chaintract.domain.User;
import com.ssafy.chaintract.file.FileStore;
import com.ssafy.chaintract.file.UploadFile;
import com.ssafy.chaintract.service.KakaoService;
import com.ssafy.chaintract.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Api(tags = {"api"}) // 알아보자
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final FileStore fileStore;
    private static final String LOGIN_USER = "loginUser";

    // 젠킨스
    @GetMapping("/test")
    public String test(){
        return "test_success";
    }

    @ApiOperation("로그인")
    @PostMapping("/auth/login")
    public ApiUtils.ApiResult<?> loginUser(@RequestBody CreateUserRequest userRequest, HttpServletRequest request){

        User user = kakaoService.getUserInfoByToken(userRequest.getAccesstoken());

        if(user == null){
            log.info("토큰 값이 유효하지 않습니다!");
            return ApiUtils.error("토큰값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 트랜잭션
        User loginUser = userService.login(user);

        // 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);

        return ApiUtils.success(HttpStatus.OK);
    }


    @ApiOperation("로그아웃")
    @GetMapping("/auth/logout")
    public ApiUtils.ApiResult<?> logoutUser(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        // 세션 만료
        session.invalidate();

        return ApiUtils.success(HttpStatus.OK);
    }


    @ApiOperation("회원탈퇴")
    @DeleteMapping("/user/delete")
    public ApiUtils.ApiResult<?> deleteUser(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        // 트랜잭션
        User user = (User)session.getAttribute(LOGIN_USER);
        // DB에 이미 그 멤버가 없을때 에러 처리 - 아마 실행될 일 없음
        if(!userService.deleteUser(user.getEmail()))
            return ApiUtils.error("이미 없는 회원입니다.",HttpStatus.NOT_FOUND);

        // 세션 만료
        session.invalidate();

        return ApiUtils.success(HttpStatus.OK);
    }

    @ApiOperation("서명 업로드")
    @PostMapping("/user/signature")
    public ApiUtils.ApiResult<?> uploadSign(@ModelAttribute CreateSignRequest signRequest, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(LOGIN_USER);

        UploadFile uploadFile = null;
        try {
            uploadFile = fileStore.storeFile(signRequest.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("사인 등록 유저 이름 :" + user.getName());
        userService.registerSign(user, uploadFile.getFullPath());

        return ApiUtils.success(HttpStatus.CREATED);
    }

    @Data
    static class CreateUserRequest {
        private String accesstoken;
    }


    @Data
    static class CreateSignRequest{
        private MultipartFile file;
    }

}