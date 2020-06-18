package com.example.wtf.elasticjob.simple;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author qingmei
 * @date 2020-06-04
 * @desc SimpleJobTest
 */
public class SimpleJobTest {
    // 如果修改了代码，跑之前清空ZK
    public static void main(String[] args) {
        // 1.ZK注册中心
        CoordinatorRegistryCenter coordinatorRegistryCenter =
            new ZookeeperRegistryCenter(new ZookeeperConfiguration("172.16.107.150:2181", "wtf-ejob-standalone"));
        coordinatorRegistryCenter.init();

        // 数据源，使用DBCP,事件追踪
        /*BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://172.16.107.150:3306/elastic_job_log");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        JobEventConfiguration jobEventConfig = new JobEventRdbConfiguration(dataSource);*/

        // 2.定义作业核心配置
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("MySimpleJob", "0/10 * * * * ?", 4)
            .shardingItemParameters("0=wtf0, 1=wtf1, 2=wtf2, 3=wtf3").failover(true).build();
        // 3.定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfiguration =
            new SimpleJobConfiguration(jobCoreConfiguration, MySimpleJob.class.getCanonicalName());

        // 作业分片策略
        // 基于平均分配算法的分片策略
        String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        // 4.定义Lite作业根配置
        // LiteJobConfiguration simpleJobRootConfig =
        // LiteJobConfiguration.newBuilder(simpleJobConfiguration).jobShardingStrategyClass(jobShardingStrategyClass).build();
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();

        // 5.构建Job
        new JobScheduler(coordinatorRegistryCenter, liteJobConfiguration).init();
        // new JobScheduler(coordinatorRegistryCenter, liteJobConfiguration, jobEventConfig).init();
    }
}
