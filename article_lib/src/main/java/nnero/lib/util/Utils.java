package nnero.lib.util;

import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2017/8/4 下午4:47 created by NNERO
 */
public class Utils {

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String md5(String origin,String salt){
        return Hashing.md5().hashString(
                origin+ salt, Charset.forName("utf8")).toString()
                .substring(0,16);
    }
}
