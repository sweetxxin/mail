package com.xxin.controller;

import com.xxin.conf.Constants;
import com.xxin.entity.User;
import com.xxin.service.RequestService;
import com.xxin.service.UserService;
import com.xxin.utils.EncryptUtil;
import com.xxin.utils.JsonUtil;
import com.xxin.utils.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name="user", urlPatterns={"/user/*"})
public class UserController extends HttpServlet {
    private UserService userService = new UserService();
    ResultUtil resultUtil = new ResultUtil();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestService.dispatch(this, request, response);
        //        String s = request.getPathInfo();
//        s = s.replace("/", "");
//        try {
//            response.getWriter().write(this.getClass().getDeclaredMethod(s, HttpServletRequest.class).invoke(this, request).toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public String register (HttpServletRequest request, HttpServletResponse response) {
        ResultUtil resultUtil = new ResultUtil();
        User user = JsonUtil.jsonToPojo( request.getParameter("user"), User.class );
        User u = userService.queryByUsername(user.getUsername());
        if (u==null){
            if (user.getPassword()!=""&&user.getName()!=""&&user.getMail()!=""&&user.getTelephone()!=""){
                user.setUid(UUID.randomUUID().toString());
                user.setPassword(EncryptUtil.SHA1(user.getPassword()));
                userService.save(user);
                resultUtil.setCode(Constants.REGISTER_OK);
                resultUtil.setMsg("注册成功");
            } else {
                resultUtil.setCode(Constants.USER_NAME_OK);
                resultUtil.setMsg("用户名有效");
            }
        }else{
            resultUtil.setCode(Constants.USER_NAME_EXIST);
            resultUtil.setMsg("用户名已存在");
        }
        return JsonUtil.objectToJson(resultUtil);
    }
    public String login(HttpServletRequest request, HttpServletResponse response) {
        User u = userService.queryByUsername(request.getParameter("username"));
        if (u==null){
            resultUtil.setCode(Constants.USER_NO_EXIST);
            resultUtil.setMsg("用户不存在");
        }else {
            if (u.getPassword().equals(EncryptUtil.SHA1(request.getParameter("password")))){
                request.getSession().setAttribute("loginStatus","already");
                request.getSession().setAttribute("loginUser",u.getUid());
                resultUtil.setCode(Constants.RESPONSE_OK);
                resultUtil.setData(u.getUid());
                resultUtil.setMsg("登陆成功");
            }else {
                resultUtil.setCode(Constants.PASSWORD_ERROR);
                resultUtil.setMsg("密码错误");
            }
        }
        return JsonUtil.objectToJson(resultUtil);
    }
    public String bind(HttpServletRequest request, HttpServletResponse response) {
        int res = userService.bind(request.getParameter("uid"),request.getParameter("mail"),request.getParameter("auth"));
        if(res>=1){
            if (res==1){
                resultUtil.setCode(Constants.RESPONSE_OK);
                resultUtil.setMsg("绑定成功");
            }else {
                resultUtil.setCode(100);
                resultUtil.setMsg("修改成功");
            }
        }else {
            resultUtil.setCode(Constants.RESPONSE_FAIL);
            resultUtil.setMsg("绑定出错");
        }
        return  JsonUtil.objectToJson(resultUtil);
    }
    public String info(HttpServletRequest request, HttpServletResponse response) {
        resultUtil.setData(userService.getUserInfo(request.getParameter("uid")));
        resultUtil.setCode(Constants.RESPONSE_OK);
        resultUtil.setMsg("获取成功");
        return  JsonUtil.objectToJson(resultUtil);
    }
    public String update(HttpServletRequest request, HttpServletResponse response) {
        userService.update(JsonUtil.jsonToPojo(request.getParameter("user"),User.class));
        resultUtil.setCode(Constants.RESPONSE_OK);
        resultUtil.setMsg("更新成功");
        return JsonUtil.objectToJson(resultUtil);
    }
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("loginStatus",null);
        resultUtil.setCode(Constants.RESPONSE_OK);
        resultUtil.setMsg("注销成功");
        return JsonUtil.objectToJson(resultUtil);
    }
    public String remove(HttpServletRequest request, HttpServletResponse response) {
        userService.remove(request.getParameter("uid"),request.getParameter("mail"));
        resultUtil.setCode(Constants.RESPONSE_OK);
        resultUtil.setMsg("删除成功");
        return JsonUtil.objectToJson(resultUtil);
    }
}
