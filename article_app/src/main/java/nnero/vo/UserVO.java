package nnero.vo;

/**
 * 2017/8/9 上午9:24 created by NNERO
 */
public class UserVO {

    private String nickname;

    private String password;

    private String surePassword;

    private String newPassword;

    private String sureNewPassword;

    private String age;

    private String sex;

    private String tel;

    private boolean rememberMe;

    private String uId;

    private String msg;

    public UserVO(){}

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getSureNewPassword() {
        return sureNewPassword;
    }

    public void setSureNewPassword(String sureNewPassword) {
        this.sureNewPassword = sureNewPassword;
    }

    public String getSurePassword() {
        return surePassword;
    }

    public void setSurePassword(String surePassword) {
        this.surePassword = surePassword;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
