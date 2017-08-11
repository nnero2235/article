package nnero.mapper;

import nnero.domain.ArticleComment;
import nnero.domain.ArticleDetail;
import nnero.domain.ArticleLike;
import nnero.domain.ArticleSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2017/8/5 下午5:20 created by NNERO
 */
public interface ArticleUserMapper {

    List<ArticleSummary> getArticleList(@Param("category") String category,
                                        @Param("start") int start,
                                        @Param("total") int total);

    ArticleDetail getArticleDetail(@Param("oId")String oId,
                                   @Param("uId")String uId);

    void insertArticleLike(ArticleLike articleLike);

    ArticleLike getArticleLike(@Param("oId")String oId,
                               @Param("uId")String uId);

    int deleteArticleLike(ArticleLike articleLike);

    void insertArticleComment(ArticleComment articleComment);

}
