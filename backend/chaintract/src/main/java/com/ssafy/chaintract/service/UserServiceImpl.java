package com.ssafy.chaintract.service;

import com.ssafy.chaintract.domain.User;
import com.ssafy.chaintract.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.sasl.AuthenticationException;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User login(User user) {

        // 기존 USER 유무 확인 후 없으면 저장
        List<User> userList = userRepository.findUserByEmail(user.getEmail());
        if(userList.size() == 0){ // 기존 유저가 없으면
            userRepository.save(user);
            return user;
        }else{ // 기존 유저가 있으면
            return userList.get(0);
        }


    }

    // 수정 하기 : not file -> url
    @Override
    @Transactional
    public void registerSign(User user, String path) {
        List<User> userList = userRepository.findUserByEmail(user.getEmail());
        // 유저 찾아서 정보 수정하기
        userList.get(0).setFile_path(path);

    }


    @Override
    @Transactional
    public boolean deleteUser(String email) {
        List<User> userList = userRepository.findUserByEmail(email);
        if(userList.size() == 0) return false;
        userRepository.deleteUser(userList.get(0));
        return true;
    }

}
