package com.ssafy.chaintract.service;

import com.ssafy.chaintract.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
     User login(User user);
     void registerSign(User user, String path);
     boolean deleteUser(String email);
}
