package nnero;

import nnero.domain.ArticleDetail;
import nnero.domain.ArticleSummary;
import nnero.service.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 2017/8/3 上午11:39 created by NNERO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
public class DBTests {

    @Autowired
    ArticleService service;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testASMultiInsert(){
        List<ArticleSummary> data = new ArrayList<>();
        ArticleSummary as = new ArticleSummary();
        as.setOriginId("1");
        as.setTitle("NNERO写的文字");
        as.setSummary("一个杯子被打碎了");
        as.setAuthor("nnero");
        as.setWriteTime(Timestamp.valueOf("2017-07-03 09:11:20"));
        as.setLastModifyTime(Timestamp.valueOf("2017-08-02 11:20:20"));
        ArticleSummary as1 = new ArticleSummary();
        as1.setOriginId("2");
        as1.setTitle("转行之路");
        as1.setSummary("Android程序员转行之路");
        as1.setAuthor("MARU");
        as1.setWriteTime(Timestamp.valueOf("2017-06-03 12:11:20"));
        as1.setLastModifyTime(Timestamp.valueOf("2017-08-02 11:20:20"));
        data.add(as);
        data.add(as1);
        service.insertMultiArticleSummary(data);
    }
    @Test
    public void testDetailInsert(){
        ArticleDetail detail = new ArticleDetail();
        detail.setOriginId("c4");
        detail.setContent("iOS程序员转行之路iOS程序员转行之路iOS程序员转行之路iOS程序员转行之路iOS程序员转行之路iOS程序员转行之路iOS程序员转行之路");
        service.insertArticle(detail);
    }

    @Test
    public void testRedis(){
        redisTemplate.opsForSet().add("nnero","haha","hehe","heihei");
        Assert.assertTrue(redisTemplate.opsForSet().isMember("nnero","haha"));
        Assert.assertTrue(redisTemplate.opsForSet().isMember("nnero","1"));
        Assert.assertFalse(redisTemplate.opsForSet().isMember("nnero","5"));
        Assert.assertTrue(0L == redisTemplate.opsForSet().add("nnero","1"));
    }


}
