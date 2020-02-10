package com.mine.gublog.service;

import com.mine.gublog.entity.UmsUser;

public interface UserService {
    /**
     * 条件查询用户表
     * 仅查询启用数据
     * @param user
     * @return
     */
    UmsUser queryUserByCondition(UmsUser user);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int addUser(UmsUser user);
}
