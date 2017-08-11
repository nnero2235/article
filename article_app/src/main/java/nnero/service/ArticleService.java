package nnero.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import nnero.bean.ArticleDetail;
import nnero.bean.ArticleSummary;
import nnero.bean.User;
import nnero.component.HttpClient;
import nnero.util.Api;
import nnero.util.JSONResponse;
import nnero.vo.ArticleDetailVO;
import nnero.vo.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2017/8/10 上午10:45 created by NNERO
 */
@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    HttpClient mHttp;

    public List<ArticleVO> getArticleList(int page, int pageSize){
        Map<String, String> params = new HashMap<>();
        params.put("category", "all");
        params.put("page", page+"");
        params.put("pageSize", pageSize+"");
        params.put("token", "1");
        List<ArticleVO> list = new ArrayList<>();
        try {
            JSONResponse<List<ArticleSummary>> response =
                    JSON.parseObject(mHttp.get(Api.API_GET_ARTICLE_LIST, params), new TypeReference<JSONResponse<List<ArticleSummary>>>() {});
            if ("1000".equals(response.getCode())) {
                for(ArticleSummary a:response.getData()){
                    ArticleVO vo = new ArticleVO();
                    BeanUtils.copyProperties(a,vo);
                    list.add(vo);
                }
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return list;
    }

    public ArticleDetailVO getArticleDetail(String originId,String uId){
        Map<String, String> formData = new HashMap<>();
        formData.put("token", "1");
        formData.put("uId",uId);
        ArticleDetailVO vo = new ArticleDetailVO();
        try {
            JSONResponse<ArticleDetail> response =
                    JSON.parseObject(mHttp.post(Api.API_GET_ARTICLE_DETAIL+"/"+originId, formData), new TypeReference<JSONResponse<ArticleDetail>>() {});
            if ("1000".equals(response.getCode())) {
                BeanUtils.copyProperties(response.getData(),vo);
                vo.setLiked(response.getData().getLiked() == 1);
            }
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return vo;
    }

    public String articleLike(String originId,String uId){
        Map<String, String> formData = new HashMap<>();
        formData.put("token", "1");
        formData.put("oId", originId);
        formData.put("uId", uId);
        try {
            JSONResponse<?> response =
                    JSON.parseObject(mHttp.post(Api.API_ARTICLE_LIKE, formData), new TypeReference<JSONResponse<?>>() {});
            return JSON.toJSONString(response);
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return JSONResponse.toJSONString("2000","服务器错误",null);
    }

    public String articleUnLike(String originId,String uId){
        Map<String, String> formData = new HashMap<>();
        formData.put("token", "1");
        formData.put("oId", originId);
        formData.put("uId", uId);
        try {
            JSONResponse<?> response =
                    JSON.parseObject(mHttp.post(Api.API_ARTICLE_UNLIKE, formData), new TypeReference<JSONResponse<?>>() {});
            return JSON.toJSONString(response);
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return JSONResponse.toJSONString("2000","服务器错误",null);
    }

    public String articleComment(String originId,String uId,String comment){
        Map<String, String> formData = new HashMap<>();
        formData.put("token", "1");
        formData.put("oId", originId);
        formData.put("uId", uId);
        formData.put("comment", comment);
        try {
            JSONResponse<?> response =
                    JSON.parseObject(mHttp.post(Api.API_ARTICLE_COMMENT, formData), new TypeReference<JSONResponse<?>>() {});
            return JSON.toJSONString(response);
        } catch (IOException e) {
            logger.error("Http error: " + e.getMessage());
        }
        return JSONResponse.toJSONString("2000","服务器错误",null);
    }
}

