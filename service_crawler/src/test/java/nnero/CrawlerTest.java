package nnero;

import nnero.crawler.buisness.JianshuCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 2017/8/4 上午10:02 created by NNERO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
public class CrawlerTest {

    @Qualifier("programmer")
    @Autowired
    JianshuCrawler programmerJc;

    @Test
    public void jianshuTest(){
        programmerJc.start();
    }
}
