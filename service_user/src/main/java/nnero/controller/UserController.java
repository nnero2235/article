package nnero.controller;

import com.google.common.base.Strings;
import nnero.domain.User;
import nnero.lib.Salts;
import nnero.lib.exception.ErrorJSONException;
import nnero.lib.util.Utils;
import nnero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * 2017/8/4 下午4:10 created by NNERO
 */
@RestController
public class UserController {

    @Autowired
    UserService mUserService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerUser(@ModelAttribute(name = "user")User user){
        if(Strings.isNullOrEmpty(user.getNickname()) ||
                Strings.isNullOrEmpty(user.getTel()) ||
                Strings.isNullOrEmpty(user.getPassword())){
            throw new ErrorJSONException("昵称,密码，手机不能为空");
        } else if(user.getTel().length() != 11 && Utils.isNumeric(user.getTel())){
            throw new ErrorJSONException("手机必须是11位数字");
        } else if(user.getNickname().length() > 10){
            throw new ErrorJSONException("昵称不能超过10位字符");
        }
        user.setPassword(Utils.md5(user.getPassword(), Salts.PWD_SALT));
        user.setuId(Utils.md5(user.getTel(),Salts.UID_SALT));
        user.setCreateTime(Timestamp.from(Instant.now()));
        user.setLastModifyTime(Timestamp.from(Instant.now()));
        return mUserService.registerUser(user);
    }

    @RequestMapping(value = "/modify/pwd",method = RequestMethod.POST)
    public String modifyPwd(@RequestParam(name = "old_pwd")String oldPassword,
                            @RequestParam(name = "new_pwd")String newPassword,
                            @RequestParam(name = "u_id")String uId){
        if(Strings.isNullOrEmpty(oldPassword) ||
                Strings.isNullOrEmpty(newPassword)){
            throw new ErrorJSONException("新旧密码不能为空");
        }
        String oldPwd = Utils.md5(oldPassword,Salts.PWD_SALT);
        String newPwd = Utils.md5(newPassword,Salts.PWD_SALT);
        return mUserService.modifyPassword(oldPwd,newPwd,uId);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam(name = "nickname")String account,
                        @RequestParam(name = "password")String pwd){
        if(Strings.isNullOrEmpty(account) ||
                Strings.isNullOrEmpty(pwd)){
            throw new ErrorJSONException("用户名或密码为空");
        }
        String p = Utils.md5(pwd,Salts.PWD_SALT);
        return mUserService.validatedUser(account,p);
    }

    @RequestMapping(value = "/auto/login",method = RequestMethod.POST)
    public String autoLogin(@RequestParam(name = "uId")String uId){
        if(Strings.isNullOrEmpty(uId)){
            throw new ErrorJSONException("uId为空");
        }
        return mUserService.autoLoginUser(uId);
    }

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public String check(@RequestParam(name = "nickname")String nickname,
                        @RequestParam(name = "tel")String tel){
        if(Strings.isNullOrEmpty(nickname)){
            throw new ErrorJSONException("nickname为空");
        } else if(Strings.isNullOrEmpty(tel)){
            throw new ErrorJSONException("tel为空");
        }
        return mUserService.check(nickname,tel);
    }
}
