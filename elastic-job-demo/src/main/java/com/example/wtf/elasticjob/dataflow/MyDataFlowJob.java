package com.example.wtf.elasticjob.dataflow;

import java.util.Arrays;
import java.util.List;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

/**
 * @author qingmei
 * @date 2020-06-04
 * @desc MyDataFlowJob
 */
public class MyDataFlowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("开始获取数据");
        // 假装从文件或者数据库中获取到了数据
        return Arrays.asList("wtf1", "wtf2", "wtf3");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        for (String val : list) {
            // 处理完数据要移除掉，不然就会一直跑
            System.out.println("开始处理数据：" + val);
        }
    }
}
