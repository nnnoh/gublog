package com.mine.gublog.controller.api;

import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.utils.ResultWrapper;
import com.mine.gublog.entity.UmsUser;
import com.mine.gublog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultWrapper<Integer> register(@Validated UmsUser user) {
        ResultWrapper<Integer> resultWrapper = new ResultWrapper<Integer>();
        UmsUser userDb = userService.queryUserByCondition(new UmsUser(user.getUsername()));
        if (userDb == null) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(userService.addUser(user));
        } else {
            // 注册失败
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage("用户名已存在");
        }
        return resultWrapper;
    }
}
