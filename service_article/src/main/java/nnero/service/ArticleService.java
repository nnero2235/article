package nnero.service;

import com.alibaba.fastjson.JSON;
import nnero.domain.ArticleComment;
import nnero.domain.ArticleDetail;
import nnero.domain.ArticleLike;
import nnero.domain.ArticleSummary;
import nnero.lib.Code;
import nnero.lib.exception.ErrorJSONException;
import nnero.lib.util.JSONResult;
import nnero.mapper.ArticleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.geom.AreaOp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * 2017/8/5 下午5:18 created by NNERO
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    ArticleUserMapper mArticleRepo;

    public String getArticleList(String category,int page,int pageSize){
        int start = (page-1) * pageSize;
        List<ArticleSummary> list = mArticleRepo.getArticleList(category,start,pageSize);
        if(list != null && list.size() > 0){
            return JSONResult.toJSONString(Code.CODE_SUCCESS,"",list);
        } else {
            throw new ErrorJSONException(Code.CODE_LIST_NULL,"数据为空");
        }
    }


    public String getArticleDetail(String oId,String uId){
        ArticleDetail ad = mArticleRepo.getArticleDetail(oId,uId);
        if(ad != null){
            return JSONResult.toJSONString(Code.CODE_SUCCESS,"",ad);
        }
        throw new ErrorJSONException(Code.CODE_DETAIL_NULL,"没有对应详情");
    }

    public String likeArticle(String oId,String uId){
        ArticleLike al = mArticleRepo.getArticleLike(oId,uId);
        if(al != null){
            throw new ErrorJSONException(Code.CODE_LIKED,"已经喜欢过该文章了");
        } else {
            ArticleLike articleLike = new ArticleLike();
            articleLike.setOriginId(oId);
            articleLike.setuId(uId);
            articleLike.setLikeTime(Timestamp.from(Instant.now()));

            mArticleRepo.insertArticleLike(articleLike);
            if (articleLike.getAlId() != 0) {
                return JSONResult.toJSONString(Code.CODE_SUCCESS, "", null);
            }
        }
        throw new ErrorJSONException("数据库异常");
    }

    public String unLikeArticle(String oId,String uId){
        ArticleLike al = mArticleRepo.getArticleLike(oId,uId);
        if(al != null){
            int affectRows = mArticleRepo.deleteArticleLike(al);
            if (affectRows != 0) {
                return JSONResult.toJSONString(Code.CODE_SUCCESS, "", null);
            }
        } else {
            throw new ErrorJSONException(Code.CODE_UNLIKED,"您没有喜欢过该文章");
        }
        throw new ErrorJSONException("数据库异常");
    }

    public String commentArticle(String oId,String uId,String comment){
        ArticleComment ac = new ArticleComment();
        ac.setOriginId(oId);
        ac.setuId(uId);
        ac.setComment(comment);
        ac.setCommentTime(Timestamp.from(Instant.now()));
        mArticleRepo.insertArticleComment(ac);
        if(ac.getAcId() != 0){
            return JSONResult.toJSONString(Code.CODE_SUCCESS,"",null);
        }
        throw new ErrorJSONException("数据库异常");
    }
}
