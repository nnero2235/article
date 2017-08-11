package nnero.task;

import nnero.crawler.buisness.JianshuCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 2017/8/4 上午10:11 created by NNERO
 */
@Component
public class ScheduleCrawlerTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleCrawlerTask.class);

//    @Qualifier("programmer")
//    @Autowired
//    JianshuCrawler programmerJc;

    @Qualifier("erp")
    @Autowired
    JianshuCrawler erpJc;

//    @Qualifier("coffee")
//    @Autowired
//    JianshuCrawler coffeeJc;

//    @Scheduled(fixedRate = 12 * 60 * 60000,initialDelay = 10000)
//    @Async
//    public void jianshuProCrawler(){
//        if(!programmerJc.isTerminal()) {
//            programmerJc.start();
//        }
//    }

    @Scheduled(fixedRate = 12 * 60 * 60000,initialDelay = 10000)
    @Async
    public void jianshuERPCrawler(){
//        if(programmerJc.isTerminal() && !erpJc.isTerminal()) {
            erpJc.start();
//        }
    }

//    @Scheduled(fixedRate = 60000,initialDelay = 60000)
//    @Async
//    public void jianshuCoffeeCrawler(){
//        if(programmerJc.isTerminal() && erpJc.isTerminal() && !coffeeJc.isTerminal()) {
//            coffeeJc.start();
//        }
//    }

    @Scheduled(fixedRate = 60000,initialDelay = 50000)
    public void checkCrawlerShouldStop(){
        logger.info("check crawler should stop!");
//        programmerJc.shouldShutdown();
        erpJc.shouldShutdown();
//        coffeeJc.shouldShutdown();
    }
}
