package com.atypon.backstage;

import com.atypon.domain.ArticleSubmission;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackstageConsumerImpl implements BackstageConsumer {
    private volatile boolean keepRunning = true;
    private final BlockingQueue<ArticleSubmission> submissionsQueue;
    private final Processor<ArticleSubmission> mainProcessor =
            new MainProcessor();
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public BackstageConsumerImpl(BlockingQueue<ArticleSubmission> submissionsQueue) {
        this.submissionsQueue = submissionsQueue;
    }

    @Override
    public void start() {
        singleThreadExecutor.execute(() -> {
            while (keepRunning) {
                try {
                    ArticleSubmission articleSubmission = submissionsQueue.take();
                    mainProcessor.process(articleSubmission);
                } catch (ProcessingException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void stop() {
        keepRunning = false;
        singleThreadExecutor.shutdownNow();
    }

}
