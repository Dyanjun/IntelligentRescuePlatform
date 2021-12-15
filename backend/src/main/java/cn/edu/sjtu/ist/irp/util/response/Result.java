package cn.edu.sjtu.ist.irp.util.response;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/15 12:04
 */
@Data
public class Result<T> {

    /**
     * 数据
     */
    T data;

    /**
     * 消息
     */
    String message;

    /**
     * 代码
     */
    int code;
}
