package nnero.mapper;

import nnero.domain.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * 2017/8/4 下午4:19 created by NNERO
 */
public interface UserMapper {

    void insertUser(User user);

    User getUserByTel(String tel);

    User getUserByNickname(String nickname);

    int modifyPwd(@Param("uId") String uId,
                  @Param("oldPwd") String oldPwd,
                  @Param("newPwd") String newPwd,
                  @Param("lastModifyTime")Timestamp lastModifyTime);

    User verify(@Param("nickname") String nickname,
                @Param("pwd") String pwd);

    User getUserByUid(String uId);
}
