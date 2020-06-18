package com.example.wtf.elasticjob.dataflow;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author qingmei
 * @date 2020-06-04
 * @desc DataFlowJobTest
 */
public class DataFlowJobTest {
    // 如果修改了代码，跑之前清空ZK
    public static void main(String[] args) {
        // 1.ZK注册中心
        CoordinatorRegistryCenter regCenter =
            new ZookeeperRegistryCenter(new ZookeeperConfiguration("172.16.107.150:2181", "wtf-ejob-dataFlow"));
        regCenter.init();

        // 2.定义作业核心配置
        JobCoreConfiguration dataJobCoreConfig =
            JobCoreConfiguration.newBuilder("MyDataFlowJob", "0/4 * * * * ?", 2).build();
        // 3.定义DATAFLOW类型配置
        DataflowJobConfiguration dataJobConfig =
            new DataflowJobConfiguration(dataJobCoreConfig, MyDataFlowJob.class.getCanonicalName(), true);

        // 作业分片策略
        // 基于平均分配算法的分片策略
        String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        // 4.定义Lite作业根配置
        // LiteJobConfiguration dataflowJobRootConfig =
        // LiteJobConfiguration.newBuilder(dataJobConfig).jobShardingStrategyClass(jobShardingStrategyClass).build();
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataJobConfig).build();

        // 5.构建Job
        new JobScheduler(regCenter, dataflowJobRootConfig).init();
        // new JobScheduler(regCenter, dataflowJobRootConfig, jobEventConfig).init();
    }
}
