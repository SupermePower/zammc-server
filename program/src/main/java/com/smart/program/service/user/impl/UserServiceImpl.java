package com.smart.program.service.user.impl;

import com.smart.program.domain.user.UserInfoEntity;
import com.smart.program.repository.user.UserRepository;
import com.smart.program.request.user.AddUserRequest;
import com.smart.program.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 添加用户信息
     *
     * @param request
     * @throws Exception
     */
    public void addUser(AddUserRequest request) throws Exception {
        UserInfoEntity byId = userRepository.queryUserById(request);
        if (byId != null) {
            return;
        }
        UserInfoEntity userInfoEntity = getUserInfoEntity(request);
        userRepository.saveAndFlush(userInfoEntity);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    private UserInfoEntity getUserInfoEntity(AddUserRequest request) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(request.getUserId());
        userInfoEntity.setNickName(null == request.getNickName() ? "" : request.getNickName());
        userInfoEntity.setAvatarUrl(null == request.getAvatarUrl() ? "" : request.getAvatarUrl());
        userInfoEntity.setGender(null == request.getGender() ? 1 : request.getGender());
        userInfoEntity.setLanguage(null == request.getLanguage() ? "" : request.getLanguage());
        userInfoEntity.setCountry(null == request.getCountry() ? "" : request.getCountry());
        userInfoEntity.setProvince(null == request.getProvince() ? "" : request.getProvince());
        userInfoEntity.setCity(null == request.getCity() ? "" : request.getCity());
        return userInfoEntity;
    }
}
