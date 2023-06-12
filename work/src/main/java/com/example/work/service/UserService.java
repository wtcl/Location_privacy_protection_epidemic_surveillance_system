package com.example.work.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.work.entity.User;


public interface UserService extends IService<User> {

    /**
     * @param user
     * @param current
     * @param size
     * @return
     */
    public Page<User> listData(User user, long current, long size);

    /**
     * @param username
     * @return
     */
    public Boolean checkUserName(String username);

    public User selectByUserName(String username);

    public Page<User> listFunOne(User user, long current, long size, String uuids);
}
