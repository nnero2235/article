package nnero.lib.util;

import com.alibaba.fastjson.JSON;

/**
 * 2017/8/4 下午4:23 created by NNERO
 */
public class JSONResult<T> {

    private String code;

    private String info;

    private T data;

    public JSONResult(){}

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

    public static <T> JSONResult<T> result(String code, String info, T data){
        JSONResult<T> r = new JSONResult<>();
        r.setCode(code);
        r.setInfo(info);
        r.setData(data);
        return r;
    }

    public static <T> String toJSONString(String code,String info,T data){
        return JSON.toJSONString(result(code,info,data));
    }
}
