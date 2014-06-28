package com.tasteofdata.web.controller.user;

import com.tasteofdata.land.module.exception.UserExcpetion;
import com.tasteofdata.land.module.support.cache.CacheService;
import com.tasteofdata.land.module.user.entry.User;
import com.tasteofdata.land.module.user.service.UserService;
import com.tasteofdata.land.util.JsonUtil;
import com.tasteofdata.web.util.LoginUtil;
import com.tasteofdata.web.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private CacheService cacheService;

    /**
     * 登录
     * @param request
     * @param response
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request,HttpServletResponse response){
        String json = "{'retcode':'0','retmsg':'失败'}";
        Map<String, String> jsonMap = new HashMap<String, String>(2);
        jsonMap.put("retcode","-1");
        jsonMap.put("retmsg", "失败");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if (StringUtils.isBlank(userName)) {
            jsonMap.put("retcode", "1");
            jsonMap.put("retmsg", "用户名不能为空");
        }else if(StringUtils.isBlank(password)){
            jsonMap.put("retcode", "2");
            jsonMap.put("retmsg", "密码不能为空");
        }

        try {
            User user = userService.queryByPassword(userName, password);
            if(null==user){
                jsonMap.put("retcode", "3");
                jsonMap.put("retmsg","用户名或密码错误");
            }else{
                //用户登陆成功，用户信息放入缓存
                LoginUtil.setCacheLoginUser(request,response,cacheService,user);
                jsonMap.put("retcode", "0");
                jsonMap.put("retmsg","登陆成功");
            }
        } catch (UserExcpetion userExcpetion) {
            logger.error("用户登陆失败,userName="+userName+",password="+password,userExcpetion);
            jsonMap.put("retcode", "3");
            jsonMap.put("retmsg","用户名或密码错误");
        }
        json = JsonUtil.object2Json(jsonMap);
        WebUtils.printWrite(response,json);
    }
    
    @RequestMapping("/add")
    public void addUser(){
    	 User user = new User();
         user.setUserName("test");
         user.setEmail("test@test.com");
         int id = userService.addUser(user);
         logger.error("------------------"+id);
    }

}
