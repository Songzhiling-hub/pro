package com.sram.pro.user.service;

import com.sram.pro.user.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;

    public Map<String,Object> getUser(String principal) {
        return userDao.getUser(principal);
    }
}
