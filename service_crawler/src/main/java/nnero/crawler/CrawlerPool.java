package nnero.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 2017/8/3 下午2:15 created by NNERO
 */
public class CrawlerPool {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerPool.class);

    private int mSize;

    private ExecutorService mExecutorPool;

    private volatile int mCurrentExecutors;

    private List<CrawlerTask> mTasks;

    private boolean isTerminal;

    public CrawlerPool(int poolSize){
        this.mSize = poolSize;
        this.mCurrentExecutors = 0;
        this.mExecutorPool = Executors.newFixedThreadPool(this.mSize);
        this.mTasks = new ArrayList<>();
    }

    public int size(){
        return mSize;
    }

    public void stopAllTasks(){
        if(mTasks.size() > 0){
            logger.info("stop All tasks");
            for(CrawlerTask task : mTasks){
                task.stop();
                this.mCurrentExecutors--;
            }
            this.mExecutorPool.shutdown();
            mTasks.clear();
            isTerminal = true;
        }
    }

    public boolean isTerminal(){
        return isTerminal;
    }

    public void shouldStopAllTasks(){
        if(mTasks.size() > 0){
            boolean shouldStop = false;
            for(CrawlerTask task : mTasks){
                shouldStop = task.isEnd();
            }
            if(shouldStop){
                stopAllTasks();
            } else {
                logger.info("any task not complete!");
            }
        }
    }

    public void waitAllDone() throws InterruptedException {
        this.mExecutorPool.awaitTermination(12, TimeUnit.HOURS);
    }

    public void execute(CrawlerTask task){
        if(this.mCurrentExecutors >= mSize){
            return;
        }
        if(this.mCurrentExecutors == -1){
            this.mCurrentExecutors = 1;
        } else {
            this.mCurrentExecutors++;
        }
        this.mTasks.add(task);
        this.mExecutorPool.execute(task);
    }
}
