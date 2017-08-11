package nnero.controller;

import com.google.common.base.Strings;
import nnero.domain.ArticleDetail;
import nnero.lib.Article;
import nnero.lib.exception.ErrorJSONException;
import nnero.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;

/**
 * 2017/8/5 下午5:02 created by NNERO
 */
@RestController
public class ArticleController {

    @Autowired
    ArticleService mArticleService;

    @RequestMapping("/list")
    public String getArticleList(@RequestParam(name = "category")String category,
                                 @RequestParam(name = "page")int page,
                                 @RequestParam(name = "pageSize")int pageSize){
        if(page <= 0 || pageSize <= 0){
            throw new ErrorJSONException("page和pageSize必须大于0");
        }
        category = Article.CATEGORY_ALL;
        return mArticleService.getArticleList(category,page,pageSize);
    }

    @RequestMapping(value = "/detail/{oId:.+}",method = RequestMethod.POST)
    public String getArticleDetail(@PathVariable(name = "oId")String oId,
                                   @RequestParam(name = "uId")String uId){
        if(Strings.isNullOrEmpty(oId) || oId.length() != 12){
            throw new ErrorJSONException("oId 必须是12位字符串");
        }
        return mArticleService.getArticleDetail(oId,uId);
    }

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public String likeArticle(@RequestParam(name = "oId")String oId,
                              @RequestParam(name = "uId")String uId){
        if(Strings.isNullOrEmpty(oId) || oId.length() != 12){
            throw new ErrorJSONException("oId 必须是12位字符串");
        }else if(Strings.isNullOrEmpty(uId) || uId.length() != 16){
            throw new ErrorJSONException("uId 必须是16位字符串");
        }
        return mArticleService.likeArticle(oId,uId);
    }

    @RequestMapping(value = "/unlike",method = RequestMethod.POST)
    public String unlikeArticle(@RequestParam(name = "oId")String oId,
                              @RequestParam(name = "uId")String uId){
        if(Strings.isNullOrEmpty(oId) || oId.length() != 12){
            throw new ErrorJSONException("oId 必须是12位字符串");
        }else if(Strings.isNullOrEmpty(uId) || uId.length() != 16){
            throw new ErrorJSONException("uId 必须是16位字符串");
        }
        return mArticleService.unLikeArticle(oId,uId);
    }

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public String commentArticle(@RequestParam(name = "oId")String oId,
                              @RequestParam(name = "uId")String uId,
                              @RequestParam(name = "comment")String comment){
        if(Strings.isNullOrEmpty(oId) || oId.length() != 12){
            throw new ErrorJSONException("oId 必须是12位字符串");
        } else if(Strings.isNullOrEmpty(uId) || uId.length() != 16){
            throw new ErrorJSONException("uId 必须是16位字符串");
        } else if(Strings.isNullOrEmpty(comment)){
            throw new ErrorJSONException("comment 为空");
        }
        return mArticleService.commentArticle(oId,uId,comment);
    }
}
