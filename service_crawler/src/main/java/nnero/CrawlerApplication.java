package nnero;

import nnero.crawler.CrawlerPool;
import nnero.crawler.buisness.JianshuCrawler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 2017/8/3 上午10:40 created by NNERO
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan("nnero.mapper")
public class CrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class,args);
    }


    @Bean("programmer")
    public JianshuCrawler injectJianshuProgrammer(){
        return new JianshuCrawler("程序员");
    }

    @Bean("erp")
    public JianshuCrawler injectJianshuErp(){
        return new JianshuCrawler("创业");
    }

    @Bean("coffee")
    public JianshuCrawler injectJianshuCoffee(){
        return new JianshuCrawler("咖啡");
    }
}
