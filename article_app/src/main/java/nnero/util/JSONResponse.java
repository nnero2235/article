package nnero.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * 2017/8/9 上午10:24 created by NNERO
 */
public class JSONResponse<T> {

    private String code;

    private String info;

    private T data;

    public JSONResponse(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> String toJSONString(String code,String info,T data){
        JSONResponse<T> response = new JSONResponse<>();
        response.setCode(code);
        response.setInfo(info);
        response.setData(data);
        return JSON.toJSONString(response);
    }
}
