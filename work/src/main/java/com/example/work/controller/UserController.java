package com.example.work.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.common.Response;
import com.example.work.entity.User;
import com.example.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.Random;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;
    private Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

    @RequestMapping(value = "list")
    public String list(){
        return "pages/userList";
    }

    @RequestMapping(value = "funone")
    public String funone() { return "fun/funOneList"; }

    @RequestMapping(value = "funtwo")
    public String funtwo() { return "fun/funTwoList"; }

    @RequestMapping(value = "funthree")
    public String funthree() { return "fun/funThreeList"; }

    /**
     * 查询列表数据
     *
     * @param user
     * @param current
     * @param size
     * @return
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Response<Page<User>> listData(User user, @RequestParam(name="page") long current,
                                         @RequestParam(name = "limit") long size) {
        Page<User> page = userService.listData(user, current, size);

        return Response.success(page);
    }

    /**
     * 功能一二
     *
     * @param user
     * @param current
     * @param size
     * @return
     */
    @RequestMapping(value = "listfun")
    @ResponseBody
    public Response<Page<User>> listfun(User user, @RequestParam(name="page") long current,
                                           @RequestParam(name="limit") long size, @RequestParam(name="uuids") String uuids){
        Page<User> page = userService.listFunOne(user, current, size, uuids);
        return Response.success(page);
    }

    /**
     * 删除
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public Response<Boolean> delete(User user) {
        Assert.notNull(user.getId(), "ID不能为空");
        boolean result = userService.removeById(user.getId());
        return Response.success(result);
    }

    /**
     * 修改
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "edit")
    public String edit(User user, Model model) {
        Assert.notNull(user.getId(), "ID不能为空");
        model.addAttribute("user", userService.getById(user.getId()));
        return "pages/userEdit";
    }

    /**
     * 检验用户名称是否唯一
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "check")
    @ResponseBody
    public Response<Boolean> checkUserName(@NotNull String username){
        Boolean checkResult = userService.checkUserName(username);
        return Response.success(checkResult);
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "update")
    @ResponseBody
    public Response<Boolean> update(User user) {
        Assert.notNull(user.getId(), "ID不能为空");
        boolean result = userService.updateById(user);
        return Response.success(result);
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping(value = "add")
    public String add(){
        return "pages/userAdd";
    }

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public Response<Boolean> save(User user) {
        System.out.println(user.toString());
        Random random = new Random();
        user.setUuid(Long.toHexString((long) Math.pow(2, 63) + random.nextInt((int) Math.pow(2, 63))));
        String pp = pbkdf2PasswordEncoder.encode(user.getPassword());
        user.setPassword(pp);
        boolean result = userService.save(user);
        return Response.success(result);
    }

}
