package nnero.service;

import nnero.domain.ArticleDetail;
import nnero.domain.ArticleSummary;
import nnero.mapper.ArticleDetailMapper;
import nnero.mapper.ArticleSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2017/8/3 上午11:43 created by NNERO
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    ArticleSummaryMapper mSummaryRepo;

    @Autowired
    ArticleDetailMapper mDetailRepo;

    public void insertMultiArticleSummary(List<ArticleSummary> data){
        if(data != null && data.size() > 0){
            mSummaryRepo.insertMultiSummary(data);
        }
    }

    public void insertArticle(ArticleDetail articleDetail){
        if(articleDetail != null){
            mDetailRepo.insertArticle(articleDetail);
        }
    }
}
