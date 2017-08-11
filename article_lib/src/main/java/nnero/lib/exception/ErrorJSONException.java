package nnero.lib.exception;

import nnero.lib.Code;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 2017/8/5 下午4:03 created by NNERO
 */
public class ErrorJSONException extends RuntimeException {

    private String code;

    public ErrorJSONException(String msg){
        super(msg);
        code = Code.CODE_FAIL;
    }

    public ErrorJSONException(String code,String msg){
        super(msg);
        this.code = code;
    }

    public String code(){
        return code;
    }
}
