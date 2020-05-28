package com.sram.pro.user.dao;

import java.util.Map;

public interface IUserDao {

    Map<String,Object> getUser(String principal);
}
