package nnero.crawler;

import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2017/8/3 下午2:39 created by NNERO
 */
public class CrawlerTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerTask.class);

    private boolean mForeverRun;

    private boolean isEnd;//是否结束了

    public CrawlerTask(){
        mForeverRun = true;
    }

    public void stop(){
        mForeverRun = false;
    }

    public void crawler(){

    }

    public boolean isEnd(){
        return isEnd;
    }

    @Override
    public void run() {
        try {
            while (mForeverRun){
                crawler();
            }
        } catch (Exception e) {
            logger.error("CrawTask Error:"+e.getMessage());
        }
        isEnd = true;
    }
}
