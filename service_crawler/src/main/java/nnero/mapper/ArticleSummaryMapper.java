package nnero.mapper;

import nnero.domain.ArticleSummary;

import java.util.List;

/**
 * 2017/8/3 上午11:13 created by NNERO
 */
public interface ArticleSummaryMapper {

    void insertMultiSummary(List<ArticleSummary> summaryList);
}
