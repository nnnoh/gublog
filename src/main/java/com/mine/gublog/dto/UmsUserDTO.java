package com.mine.gublog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息dto
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UmsUserDTO implements Serializable {
    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiresTime;
}
