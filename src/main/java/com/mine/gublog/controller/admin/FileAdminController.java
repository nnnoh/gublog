package com.mine.gublog.controller.admin;

import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.utils.ResultWrapper;
import com.mine.gublog.entity.FmsFile;
import com.mine.gublog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/admin/file")
public class FileAdminController {
    @Autowired
    private FileService fileService;

    @Value("${file.url.path}")
    private String fileUrlPath;
    @Value("${file.location.dir}")
    private String fileLocationDir;

    /**
     * 上传文件
     *
     * @param mFile    上传文件
     * @param business 关联的业务名
     * @return 上传成功返回url
     */
    @PostMapping("")
    public ResultWrapper uploadFile(@RequestParam("file") MultipartFile mFile, @RequestParam("business") String business, HttpServletRequest request) {
        ResultWrapper resultWrapper = new ResultWrapper<String>();
        if (mFile != null) {
            FmsFile fmsFile = new FmsFile();
            // 路径 ()/business/yyyyMMddHHmmss-random/filename
            StringBuilder pathname = new StringBuilder(File.separator);
            if (business != null) {
                // 避免文件重名
                SimpleDateFormat fdt = new SimpleDateFormat("yyyyMMddHHmmss");
                pathname.append(business).append(File.separator)
                        .append(fdt.format(new Date())).append("-")
                        .append(Math.round(Math.random() * 1000))
                        .append(File.separator);
            }
            pathname.append(mFile.getOriginalFilename());

            // fms_file
            fmsFile.setBusiness(business);
            fmsFile.setFilename(mFile.getOriginalFilename());
            fmsFile.setLocationPath(fileLocationDir + pathname);
            fmsFile.setUrl(ServletUriComponentsBuilder.fromContextPath(request)
                    .path(fileUrlPath + pathname.toString().replaceAll("\\\\", "/")).build().toString());
            fmsFile.setStatus(true);

            if (fileService.storeFile(fmsFile, mFile)) {
                resultWrapper.setStatus(ResultConstants.SUCCESS);
                resultWrapper.setData(fmsFile);
            } else {
                resultWrapper.setStatus(ResultConstants.FAIL);
                resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
            }
        }
        return resultWrapper;
    }

    @DeleteMapping("")
    public ResultWrapper deleteFileByInfo(FmsFile fmsFile){
        ResultWrapper resultWrapper = new ResultWrapper<String>();
        int rows = fileService.deleteFileByInfo(fmsFile);
        if(rows>0){
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setMessage(rows + " rows affected.");
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }
}
