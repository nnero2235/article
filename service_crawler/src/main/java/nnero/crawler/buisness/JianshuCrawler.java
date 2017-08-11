package nnero.crawler.buisness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nnero.crawler.Crawler;
import nnero.crawler.HttpClient;
import nnero.domain.ArticleDetail;
import nnero.domain.ArticleSummary;
import nnero.service.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2017/8/3 下午4:53 created by NNERO
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JianshuCrawler extends Crawler {

    private static final String TAG_REGEX = "<.+?>";

    @Autowired
    ArticleService summaryService;

    int mTotalPage = -1;

    String keyword;

    public JianshuCrawler(String keyword) {
        super("http://www.jianshu.com",
                "http://www.jianshu.com/search/do?order_by=default&type=note&page=1&q="+keyword,
                keyword+"_fetch_queue", keyword+"_fetched_queue");
        this.keyword = keyword;
    }

    @Autowired
    public void injectHttpClient(HttpClient httpClient){
        this.mHttpClient = httpClient;
    }

    @Autowired
    public void injectRedisTemplate(StringRedisTemplate stringRedisTemplate){
        this.mRedisClient = stringRedisTemplate;
    }

    @Override
    public Map<String, String> getHeaders(String url) {
        Map<String, String> headers = null;
        headers = new HashMap<>();
        headers.put("Host", "www.jianshu.com");
        if (url.contains("/search/do")) {
            headers.put("Accept", "application/json");
            headers.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        }
        return headers;
    }

    @Override
    public void doWithResult(String response,String url) {
        if(url.contains("/search/do")) { //列表页
            JSONObject obj = JSON.parseObject(response);
            JSONArray arr = obj.getJSONArray("entries");
            if (arr == null) {
                return;
            }
            List<ArticleSummary> data = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                JSONObject o = arr.getJSONObject(i);
                String slug = o.getString("slug");
                String title = o.getString("title");
                String content = o.getString("content");
                String author = o.getJSONObject("user").getString("nickname");
                String writeTime = o.getString("first_shared_at");
                ArticleSummary as = new ArticleSummary();
                as.setOriginId(slug);
                title = title.replaceAll(TAG_REGEX, "");
                title = title.length() > 30 ? title.substring(0,30) : title;
                as.setTitle(title);
                content = content.replaceAll(TAG_REGEX, "");
                content = content.length() > 50 ? content.substring(0,50) : content;
                as.setSummary(content);
                as.setAuthor(author.replaceAll(TAG_REGEX, ""));
                as.setWriteTime(Timestamp.from(Instant.parse(writeTime)));
                as.setLastModifyTime(Timestamp.from(Instant.now()));
                data.add(as);
//                if(i==0) {
//                    logger.info(as.toString());
//                }
            }
            summaryService.insertMultiArticleSummary(data);
            logger.info("success insert summary:"+data.size());
        } else { // 详情页
            ArticleDetail ad = new ArticleDetail();
            ad.setOriginId(url.substring(url.lastIndexOf("/")+1));
            Document doc = Jsoup.parse(response);
            Elements es = doc.select("div .show-content");
            String content = es.html();
            ad.setContent(content);
            summaryService.insertArticle(ad);
//            logger.info(ad.toString());
            logger.info("success insert article:"+ad.getOriginId());
        }
    }

    @Override
    public void doWithLinks(String response,String url, StringRedisTemplate redisClient) {
        if(url.contains("/search/do")) {
            JSONObject obj = JSON.parseObject(response);
            if (obj != null && mTotalPage == -1) {//未初始化 列表页的连接
                mTotalPage = obj.getIntValue("total_pages");
                for (int i = 0; i < mTotalPage; i++) {
                    String targetURL = mBaseURL + "/search/do?order_by=default&type=note&page=" + i + "&q=" + keyword;
                    redisClient.opsForSet().add(mFetchQueueKey, targetURL);
                }
                logger.info("success insert pages:" + mTotalPage);
            }
            if(obj != null){//详情页的连接
                JSONArray arr = obj.getJSONArray("entries");
                if (arr == null) {
                    return;
                }
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    String slug = o.getString("slug");
                    redisClient.opsForSet().add(mFetchQueueKey,mBaseURL+"/p/"+slug);
                }
            }
        } else {// 详情页
            //详情页上没有其他页面的连接
        }
    }
}
