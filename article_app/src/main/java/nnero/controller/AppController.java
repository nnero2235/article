package nnero.controller;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import nnero.bean.ArticleSummary;
import nnero.bean.User;
import nnero.service.ArticleService;
import nnero.service.UserService;
import nnero.util.JSONResponse;
import nnero.util.Util;
import nnero.vo.ArticleDetailVO;
import nnero.vo.ArticleVO;
import nnero.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 2017/8/8 下午5:17 created by NNERO
 */
@Controller
public class AppController {

    @Autowired
    UserService mUserService;

    @Autowired
    ArticleService mArticleService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(@CookieValue(value = "uId",required = false)String uId,
                        RedirectAttributes model){
        if(Strings.isNullOrEmpty(uId)){
            return "redirect:/login";
        }
        UserVO userVO = mUserService.autoLogin(uId);
        if(userVO != null) {
            model.addFlashAttribute("user",userVO);
            return "redirect:/article/main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/article/main",method = RequestMethod.GET)
    public String articleList(Model model){
        if(!model.containsAttribute("user")){
            return "redirect:/";
        }
        List<ArticleVO> list = mArticleService.getArticleList(1,10);
        model.addAttribute("articleList",list);
        return "article_list";
    }

    @RequestMapping(value = "/a/{originId:.+}",method = RequestMethod.GET)
    public String articleDetail(@CookieValue("uId")String uId,
                                @RequestParam("title")String title,
                                @PathVariable(name = "originId")String originId,
                                Model model){
        UserVO user = mUserService.autoLogin(uId);
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("user",user);
        ArticleDetailVO vo = mArticleService.getArticleDetail(originId,uId);
        vo.setTitle(title);
        model.addAttribute("detail",vo);
        return "article_detail";
    }

    @RequestMapping("/login")
    public String login(Model model){
        if(!model.containsAttribute("user")){
            model.addAttribute("user",new UserVO());
        }
        return "login";
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "user")UserVO user, HttpServletResponse response, RedirectAttributes model){
        if(Strings.isNullOrEmpty(user.getNickname()) || Strings.isNullOrEmpty(user.getPassword())){
            user.setMsg("用户名或者密码为空");
            model.addFlashAttribute("user",user);
            return "redirect:/login";
        }
        user = mUserService.login(user);
        if(Strings.isNullOrEmpty(user.getuId())){
            model.addFlashAttribute("user",user);
            return "redirect:/login";
        } else {
            model.addFlashAttribute("user",user);
            Cookie cookie = new Cookie("uId",user.getuId());
            cookie.setPath("/");
            cookie.setMaxAge(60*1000*60);
            response.addCookie(cookie);
            return "redirect:/article/main";
        }
    }

    @RequestMapping("/register")
    public String register(Model model){
        if(!model.containsAttribute("user")){
            model.addAttribute("user",new UserVO());
        }
        return "register";
    }

    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public String register(@ModelAttribute(name = "user")UserVO userVO,RedirectAttributes model) {
        if (Strings.isNullOrEmpty(userVO.getNickname())
                || Strings.isNullOrEmpty(userVO.getPassword())
                || Strings.isNullOrEmpty(userVO.getSurePassword())
                || Strings.isNullOrEmpty(userVO.getSex())
                || Strings.isNullOrEmpty(userVO.getTel())) {
            userVO.setMsg("每项都需要填，不能留空");
        } else if (userVO.getSex().length() != 1
                || (!"m".equals(userVO.getSex())
                && !"f".equals(userVO.getSex())
                && !"n".equals(userVO.getSex()))) {
            userVO.setMsg("sex 必须填m,f,n 其中一个");
        } else if (userVO.getTel().length() != 11
                || !Util.isNumeric(userVO.getTel())) {
            userVO.setMsg("手机 必须是11位数字");
        } else if (!userVO.getPassword().equals(userVO.getSurePassword())) {
            userVO.setMsg("2次密码不一致");
        } else if (!Util.isNumeric(userVO.getAge())) {
            userVO.setMsg("年龄必须是数字");
        } else if (!mUserService.checkNicknameAndTelAvailable(userVO)) {
            //内部设置了msg
        } else if (mUserService.register(userVO)) {
            userVO.setMsg("注册成功！");
        } else {
            userVO.setMsg("服务器错误");
        }
        model.addFlashAttribute("user", userVO);
        return "redirect:/register";
    }

    @RequestMapping("/edit")
    public String edit(@CookieValue(value = "uId",required = false)String uId, Model model){
        if(!model.containsAttribute("user")){
            if(!Strings.isNullOrEmpty(uId)) {
                UserVO userVO = mUserService.autoLogin(uId);
                userVO.setPassword("");
                userVO.setSurePassword("");
                model.addAttribute("user", userVO);
            } else {
                return "redirect:/";
            }
        }
        return "edit";
    }

    @RequestMapping(value = "/edit.do",method = RequestMethod.POST)
    public String edit(@ModelAttribute(name = "user")UserVO userVO,
                       @CookieValue(name = "uId")String uId,
                       HttpServletResponse response,
                       RedirectAttributes model){
        userVO.setuId(uId);
        if(Strings.isNullOrEmpty(userVO.getPassword())
                ||Strings.isNullOrEmpty(userVO.getNewPassword())
                ||Strings.isNullOrEmpty(userVO.getSureNewPassword())){
            userVO.setMsg("旧密码，新密码，确认密码为空");
        } else if(!userVO.getNewPassword().equals(userVO.getSureNewPassword())){
            userVO.setMsg("2次密码输入不一致");
        } else if(mUserService.modifyPassword(userVO)){
            Cookie cookie = new Cookie("uId","");
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            userVO.setMsg("服务器错误");
        }
        model.addFlashAttribute("user",userVO);
        return "redirect:/edit";
    }

    @RequestMapping(value = "/detail/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam(name = "oId")String oId,
                       @RequestParam(name = "uId")String uId){
        if(Strings.isNullOrEmpty(oId)||Strings.isNullOrEmpty(uId)){
            return JSONResponse.toJSONString("2000","参数为空",null);
        }
        return mArticleService.articleLike(oId,uId);
    }

    @RequestMapping(value = "/detail/unlike",method = RequestMethod.POST)
    @ResponseBody
    public String unLike(@RequestParam(name = "oId")String oId,
                       @RequestParam(name = "uId")String uId){
        if(Strings.isNullOrEmpty(oId)||Strings.isNullOrEmpty(uId)){
            return JSONResponse.toJSONString("2000","参数为空",null);
        }
        return mArticleService.articleUnLike(oId,uId);
    }

    @RequestMapping(value = "/detail/comment",method = RequestMethod.POST)
    @ResponseBody
    public String comment(@RequestParam(name = "comment")String comment,
                          @CookieValue(name = "uId")String uId,
                          @RequestParam(name = "oId")String oId){
        if(Strings.isNullOrEmpty(oId)||Strings.isNullOrEmpty(uId)||
                Strings.isNullOrEmpty(comment)){
            return JSONResponse.toJSONString("2000","参数为空",null);
        }
        return mArticleService.articleComment(oId,uId,comment);
    }
}
