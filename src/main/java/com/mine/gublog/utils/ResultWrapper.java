package com.mine.gublog.utils;

import com.mine.gublog.constants.ResultConstants;
import lombok.*;

import java.io.Serializable;

/**
 * 结果包装器
 *
 * @date 2019-11-21
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultWrapper<T> implements Serializable{

    private static final long serialVersionUID=-886039040513829129L;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private T data;

    @Getter
    @Setter
    private String message;

    public static ResultWrapper success(Object data){
        return new ResultWrapper(ResultConstants.SUCCESS, data,null);
    }

    public static ResultWrapper fail(int status, String msg){
        return new ResultWrapper(status, null,msg);
    }
}
