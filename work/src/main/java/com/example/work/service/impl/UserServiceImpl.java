package com.example.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.work.entity.User;
import com.example.work.service.UserService;
import com.example.work.usermapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * @param user
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<User> listData(User user, long current, long size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user.getId() != null){
            queryWrapper.eq("id", user.getId());
        }
        if (StringUtils.isNotBlank(user.getUsername())) {
            queryWrapper.like("username", user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getUuid())) {
            queryWrapper.like("uuid", user.getUuid());
        }
        if (StringUtils.isNotBlank(user.getStatus())) {
            queryWrapper.like("status", user.getStatus());
        }
        return baseMapper.selectPage(new Page<>(current, size), queryWrapper);
    }

    @Override
    public Boolean checkUserName(String username) {
        if (StringUtils.isNotBlank(username)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("username", username);
            Integer count = baseMapper.selectCount(queryWrapper);
            return (count != null && count > 0) ? false : true;
        }
        return false;
    }

    @Override
    public User selectByUserName(String username) {
        if(StringUtils.isNotBlank(username)){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = baseMapper.selectOne(queryWrapper);
            return user;
        }
        return new User();
    }

    @Override
    public Page<User> listFunOne(User user, long current, long size, String uuids) {
        System.out.println(current+"-"+size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("uuid", uuids.split(";"));
        return baseMapper.selectPage(new Page<>(current, size), queryWrapper);
    }
}
