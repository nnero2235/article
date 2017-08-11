package nnero.service;

import nnero.domain.User;
import nnero.lib.Code;
import nnero.lib.exception.ErrorJSONException;
import nnero.lib.util.JSONResult;
import nnero.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * 2017/8/4 下午4:19 created by NNERO
 */
@Service
@Transactional
public class UserService {

    @Autowired
    UserMapper mUserRepo;

    public String registerUser(User user){
        if(mUserRepo.getUserByTel(user.getTel()) != null) {
            throw new ErrorJSONException("手机已经被注册!");
        } else if(mUserRepo.getUserByNickname(user.getNickname()) != null){
            throw new ErrorJSONException("昵称已经存在!");
        } else {
            mUserRepo.insertUser(user);
            if (user.getId() != 0) { //成功
                user.setPassword("");//不能返回password
                return JSONResult.toJSONString(Code.CODE_SUCCESS,"",user);
            }
        }
        //失败
        throw new ErrorJSONException("服务器错误");
    }

    public String modifyPassword(String oldPwd,String newPwd,String uId){
        int affectRow = mUserRepo.modifyPwd(uId,oldPwd,newPwd, Timestamp.from(Instant.now()));
        if(affectRow != 0){
            return JSONResult.toJSONString(Code.CODE_SUCCESS, "", null);
        } else {//密码对不上
            throw new ErrorJSONException("旧密码错误");
        }
    }

    public String validatedUser(String nickname,String pwd){
        User u = mUserRepo.verify(nickname,pwd);
        if(u != null){
            u.setPassword("");
            return JSONResult.toJSONString(Code.CODE_SUCCESS,"",u);
        } else {
            throw new ErrorJSONException("用户名或密码不正确");
        }
    }

    public String autoLoginUser(String uId){
        User u = mUserRepo.getUserByUid(uId);
        if(u != null && uId.equals(u.getuId())){
            return JSONResult.toJSONString(Code.CODE_SUCCESS,"",u);
        } else {
            throw new ErrorJSONException("用户不存在");
        }
    }

    public String check(String nickname,String tel){
        User u = mUserRepo.getUserByNickname(nickname);
        User u1 = mUserRepo.getUserByTel(tel);
        if(u != null){
            return JSONResult.toJSONString(Code.CODE_FAIL,"昵称已经存在",null);
        } else if(u1 != null){
            return JSONResult.toJSONString(Code.CODE_FAIL,"手机已经存在",null);
        } else {
            return JSONResult.toJSONString(Code.CODE_SUCCESS,"",null);
        }
    }
}
