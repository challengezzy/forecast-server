package com.demand.driven.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T>  implements Serializable{
    private String status;
    private String errorCode;
    private String errorMessage;
    private T data;

    /**
     * 失败响应包装
     * @param errorCode
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(String errorCode,String errorMessage){
        return create(ResultType.FAIL.getCode(),errorCode, errorMessage, null );
    }

    /**
     * 失败响应包装2
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(String errorMessage){
        return create(ResultType.FAIL.getCode(),"", errorMessage, null );
    }

    /**
     * 成功响应包装
     * @param t
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T t){
        return create(ResultType.SUCCESS.getCode(),"200","OK", t);
    }

    public static <T> BaseResponse<T> create(String status,String errorCode,String errorMessage,T t){
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(status);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        response.setData(t);

        return response;
    }


}
