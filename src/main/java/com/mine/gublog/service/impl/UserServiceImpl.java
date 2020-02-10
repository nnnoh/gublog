package com.mine.gublog.service.impl;

import com.mine.gublog.dao.UmsUserMapper;
import com.mine.gublog.entity.UmsUser;
import com.mine.gublog.entity.UmsUserExample;
import com.mine.gublog.service.UserService;
import com.mine.gublog.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UmsUserMapper userMapper;

    @Override
    public UmsUser queryUserByCondition(UmsUser user) {
        UmsUserExample userExample = new UmsUserExample();
        UmsUserExample.Criteria criteria = userExample.createCriteria();
        // 反射遍历所有字段
        try {
            Class clazz = user.getClass();
            Field[] fields = clazz.getDeclaredFields();
            // 设置select条件
            for (Field f : fields) {
                Class fc = f.getType();
                PropertyDescriptor readPd = new PropertyDescriptor(f.getName(), clazz);
                Method readMethod = readPd.getReadMethod();
                Object value = readMethod.invoke(user);
                if (value != null) {
                    Method method = criteria.getClass().getMethod("and" + StringUtil.capitalize(f.getName()) + "EqualTo", fc);
                    method.invoke(criteria, value);
                }
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        criteria.andStatusEqualTo(true);
        List list = userMapper.selectByExample(userExample);
        if (list.isEmpty()) {
            return null;
        }
        return (UmsUser) list.get(0);
    }

    @Override
    public int addUser(UmsUser user) {
        // 忽视插入id
        user.setId(null);
        // 状态设为true
        user.setStatus(true);
        return userMapper.insertSelective(user);
    }
}
