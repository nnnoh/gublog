package com.mine.gublog.service.impl;

import com.mine.gublog.dao.FmsFileMapper;
import com.mine.gublog.entity.BamsArticleContentExample;
import com.mine.gublog.entity.FmsFile;
import com.mine.gublog.entity.FmsFileExample;
import com.mine.gublog.service.FileService;
import com.mine.gublog.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FmsFileMapper fileMapper;

    @Override
    public boolean storeFile(FmsFile fmsFile, MultipartFile mFile) {
        Path target = Paths.get(fmsFile.getLocationPath());
        File parent = target.getParent().toFile();
        // Files.copy 目标目录不存在将报错
        if (!parent.exists()) {
            // 创建 不存在的所有父路径
            parent.mkdirs();
        }

        // 文件存盘, 文件已存在将报错
        try {
            Files.copy(mFile.getInputStream(), target);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 信息存表
        int rows = fileMapper.insertSelective(fmsFile);
        return rows > 0;
    }

    @Override
    public int deleteFileByInfo(FmsFile fmsFile) {
        FmsFileExample fileExample = new FmsFileExample();
        FmsFileExample.Criteria criteria = fileExample.createCriteria();
        // 反射遍历所有字段
        try {
            Class clazz = fmsFile.getClass();
            Field[] fields = clazz.getDeclaredFields();
            // 设置select条件
            for (Field f : fields) {
                Class fc = f.getType();
                PropertyDescriptor readPd = new PropertyDescriptor(f.getName(), clazz);
                Method readMethod = readPd.getReadMethod();
                Object value = readMethod.invoke(fmsFile);
                if (value != null) {
                    Method method = criteria.getClass().getMethod("and" + StringUtil.capitalize(f.getName()) + "EqualTo", fc);
                    method.invoke(criteria, value);
                }
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 设置status为false
        FmsFile set = new FmsFile();
        set.setStatus(false);
        int rows = fileMapper.updateByExampleSelective(set, fileExample);
        // 定期对无效文件删除本地存储
        return 0;
    }

    @Override
    public List findInvalidFile(){
        FmsFileExample fmsFileExample = new FmsFileExample();
        FmsFileExample.Criteria criteria = fmsFileExample.createCriteria();
        criteria.andStatusEqualTo(false);

        return fileMapper.selectByExample(fmsFileExample);
    }
}
