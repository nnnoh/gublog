package com.mine.gublog.controller.api;

import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.utils.ResultWrapper;
import com.mine.gublog.dto.UmsUserDTO;
import com.mine.gublog.entity.UmsUser;
import com.mine.gublog.service.UserService;
import com.mine.gublog.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultWrapper login(@RequestParam String username, @RequestParam String password) {
        ResultWrapper resultWrapper = new ResultWrapper<>();

        UmsUser user = userService.queryUserByCondition(new UmsUser(username, password));
        if (user != null) {
            String token = jwtTokenUtil.generateToken(user);
            UmsUserDTO umsUserDto = new UmsUserDTO(user.getId(), user.getUsername(), token, jwtTokenUtil.getExpiredDateFromToken(token));
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(umsUserDto);
        } else {
            // 登录失败
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage("用户名或密码输入错误");
        }
        return resultWrapper;
    }

    @RequestMapping(value = "/logout")
    public ResultWrapper logout(HttpServletRequest req, HttpServletResponse rsp) {
        ResultWrapper resultWrapper = new ResultWrapper<>();
        // 删除所有cookie
        Cookie[] cookies=req.getCookies();
        for(Cookie cookie: cookies){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            rsp.addCookie(cookie);
        }
        resultWrapper.setStatus(ResultConstants.SUCCESS);
        resultWrapper.setMessage("");
        return resultWrapper;
    }
}
