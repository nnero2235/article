package nnero.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import nnero.bean.User;
import nnero.component.HttpClient;
import nnero.util.Api;
import nnero.util.JSONResponse;
import nnero.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 2017/8/9 上午9:36 created by NNERO
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    HttpClient mHttp;

    public UserVO login(UserVO userVO) {
        Map<String, String> formData = new HashMap<>();
        formData.put("nickname", userVO.getNickname());
        formData.put("password", userVO.getPassword());
        formData.put("token", "1");
        try {
            JSONResponse<User> response =
                    JSON.parseObject(mHttp.post(Api.API_LOGIN, formData), new TypeReference<JSONResponse<User>>() {});
            if ("1000".equals(response.getCode())) {
                User user = response.getData();
                userVO.setuId(user.getuId());
            } else {
                userVO.setMsg("密码或用户名错误");
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
            userVO.setMsg("服务器出现故障");
        }
        return userVO;
    }

    public UserVO autoLogin(String uId) {
        Map<String, String> formData = new HashMap<>();
        formData.put("uId", uId);
        formData.put("token", "1");
        try {
            JSONResponse<User> response =
                    JSON.parseObject(mHttp.post(Api.API_AUTO_LOGIN, formData), new TypeReference<JSONResponse<User>>() {});
            if ("1000".equals(response.getCode())) {
                User user = response.getData();
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(user, userVO);
                userVO.setAge(user.getAge()+"");
                return userVO;
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return null;
    }

    public boolean checkNicknameAndTelAvailable(UserVO userVO){
        Map<String, String> formData = new HashMap<>();
        formData.put("nickname", userVO.getNickname());
        formData.put("tel", userVO.getTel());
        formData.put("token", "1");
        try {
            JSONResponse<User> response =
                    JSON.parseObject(mHttp.post(Api.API_CHECK_NICKNAME, formData), new TypeReference<JSONResponse<User>>() {});
            if ("1000".equals(response.getCode())) {
                return true;
            } else {
                userVO.setMsg(response.getInfo());
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return false;
    }

    public boolean register(UserVO userVO){
        Map<String, String> formData = new HashMap<>();
        formData.put("nickname", userVO.getNickname());
        formData.put("password", userVO.getPassword());
        formData.put("sex", userVO.getSex());
        formData.put("age", userVO.getAge());
        formData.put("tel", userVO.getTel());
        formData.put("token", "1");
        try {
            JSONResponse<User> response =
                    JSON.parseObject(mHttp.post(Api.API_REGISTER, formData), new TypeReference<JSONResponse<User>>() {});
            if ("1000".equals(response.getCode())) {
                return true;
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return false;
    }

    public boolean modifyPassword(UserVO userVO){
        Map<String, String> formData = new HashMap<>();
        formData.put("u_id", userVO.getuId());
        formData.put("old_pwd", userVO.getPassword());
        formData.put("new_pwd", userVO.getNewPassword());
        formData.put("token", "1");
        try {
            JSONResponse<User> response =
                    JSON.parseObject(mHttp.post(Api.API_MODIFY_PASSWORD, formData), new TypeReference<JSONResponse<User>>() {});
            if ("1000".equals(response.getCode())) {
                return true;
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return false;
    }
}
