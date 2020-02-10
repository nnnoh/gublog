package com.mine.gublog.service;

import com.mine.gublog.entity.FmsFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * 保存文件
     * @param fmsFile
     * @param mFile
     * @return
     */
    boolean storeFile(FmsFile fmsFile, MultipartFile mFile);


    /**
     * 通过文件信息字段删除文件
     * @param fmsFile
     * @return
     */
    int deleteFileByInfo(FmsFile fmsFile);

    /**
     * 获取状态为无效的文件
     * @return
     */
    List findInvalidFile();
}
