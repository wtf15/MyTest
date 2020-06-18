package com.example.wtf.job;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qingmei
 * @date 2020-06-18
 * @desc simpleJob
 */
@Component
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------【简单任务】Thread ID: %s, %s,任务总片数: %s, " +
                "当前分片项: %s.当前参数: %s," +
                "当前任务名称: %s.当前任务参数 %s",
            Thread.currentThread().getId(),
            new SimpleDateFormat("HH:mm:ss").format(new Date()),
            shardingContext.getShardingTotalCount(),
            shardingContext.getShardingItem(),
            shardingContext.getShardingParameter(),
            shardingContext.getJobName(),
            shardingContext.getJobParameter()
        ));
    }
}
