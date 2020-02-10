package com.mine.gublog.task;

import com.mine.gublog.entity.FmsFile;
import com.mine.gublog.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 定时任务
 */
@Slf4j
@Component
public class FileClearTask {
    @Autowired
    private
    FileService fileService;

    /**
     * 定时清除无用文件
     */
    @Scheduled(cron = "0 0 4 * * *")
    private void deleteInvalidFile(){
        List list = fileService.findInvalidFile();
        for (Object o : list) {
            FmsFile fmsFile = (FmsFile) o;
            File file = new File(fmsFile.getLocationPath());
            if (file.exists()) {
                boolean result = file.delete();
                log.debug("delete invalid file %s (success: %b)",fmsFile.getLocationPath(),result);
            }
        }
    }
}
