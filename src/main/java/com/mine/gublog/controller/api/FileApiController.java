package com.mine.gublog.controller.api;

import com.mine.gublog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
public class FileApiController {
    @Autowired
    private FileService fileService;

    @Value("${file.url.path}")
    private String fileUrlPath;
    @Value("${file.location.dir}")
    private String fileLocationDir;


}
