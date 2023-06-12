package com.example.work.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class WebExceptionAdvice implements ErrorController {


    public String getErrorPath(){
        return "error";
    }

    /**
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        model.addAttribute("message", throwable != null ? throwable.getMessage() : null);
        switch (statusCode) {
            case 400:
                return "error/400";
            case 403:
                return "error/403";
            case 404:
                return "error/404";
            default:
                return "error/500";
        }
    }
}
