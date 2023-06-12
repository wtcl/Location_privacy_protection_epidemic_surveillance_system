package com.example.work.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;


@Controller
@RequestMapping("/")
public class GetController {

    // 测试是否已经登录
    @GetMapping("/logintest")
    @ResponseBody
    public String logintest(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("user"));
        if(session.getAttribute("user")!=null){
            return "Y";
        }else {
            return "N";
        }
    }

    // 公钥发放，用于安卓端
    @GetMapping("pubkey")
    @ResponseBody
    public String pubkey(){
        SHE she = new SHE();
        String r1 = she.SHEEncryption(new BigInteger("0")).toString(16);
        String r2 = she.SHEEncryption(new BigInteger("0")).toString(16);
        System.out.println("pubKey1: "+r1);
        System.out.println("pubKey2: "+r2);
        return r1+","+r2;
    }

    // 框架主页
    @GetMapping(value = "index")
    public String index(Model model, HttpSession httpSession) {
        model.addAttribute("user", httpSession.getAttribute("user"));
        return "index";
    }

    // 数据展示主页面
    @GetMapping(value = "view")
    public String view() {
        return "pages/view";
    }

    // 网页登录
    @GetMapping(value = {"login", ""})
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            return "index";
        }else {
            return "login";
        }
    }

    // 登出
    @GetMapping(value = "logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }
}
