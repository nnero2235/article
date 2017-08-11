package nnero.crawler;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * 2017/8/3 下午2:43 created by NNERO
 */
public abstract class Crawler {

    protected static final Logger logger = LoggerFactory.getLogger(Crawler.class);

    protected String mBaseURL;

    protected String mStartURL;

    protected String mFetchQueueKey;

    protected String mFetchedQueueKey;

    protected CrawlerPool mPool;

    protected HttpClient mHttpClient;

    protected StringRedisTemplate mRedisClient;

    public Crawler(String baseURL, String startURL,String fetchQueueKey,String fetchedQueueKey) {
        this.mBaseURL = baseURL;
        this.mStartURL = startURL;
        this.mFetchQueueKey = fetchQueueKey;
        this.mFetchedQueueKey = fetchedQueueKey;
        this.mPool = new CrawlerPool(5);
    }

    public void shutdown() {
        mPool.stopAllTasks();
    }

    public boolean isTerminal(){
        return mPool.isTerminal();
    }

    public void shouldShutdown(){
        mPool.shouldStopAllTasks();
    }

    public abstract Map<String,String> getHeaders(String url);

    public abstract void doWithResult(String response,String url);

    public abstract void doWithLinks(String response,String url,StringRedisTemplate redisClient);

    public void start() {
        logger.info("crawler start!");
        mRedisClient.opsForSet().add(mFetchQueueKey, mStartURL);
        for (int i = 0; i < mPool.size(); i++) {
            logger.info("crawler create task:"+i);
            mPool.execute(new CrawlerTask(){

                int timeCount;

                @Override
                public void crawler() {
                    if(timeCount == 60){//30秒 还不能获取到一个url 则退出
                        stop();
                        return;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        logger.error("sleep error: e:"+e.getMessage());
                    }
                    String url = mRedisClient.opsForSet().pop(mFetchQueueKey);
                    if(!Strings.isNullOrEmpty(url) &&
                            !mRedisClient.opsForSet().isMember(mFetchedQueueKey,url)){
                        timeCount = 0;
                        mRedisClient.opsForSet().add(mFetchedQueueKey,url);
                        try {
                            String response = mHttpClient.get(url,getHeaders(url));
                            logger.info("Download Success: "+url);
                            doWithResult(response,url);
                            doWithLinks(response,url,mRedisClient);
                        } catch (IOException e) {
                            logger.error("http IO: e:"+e.getMessage());
                        }
                    } else {
                        timeCount++;
                    }
                }
            });
        }
        try {
            mPool.waitAllDone();
        } catch (InterruptedException e) {
            logger.error("wait error: e:"+e.getMessage());
        }
        logger.info("crawler finished All");
    }
}
