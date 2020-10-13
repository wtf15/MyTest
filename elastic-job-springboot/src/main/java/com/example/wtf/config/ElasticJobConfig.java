package com.example.wtf.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.example.wtf.job.MyDataFlowJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.wtf.job.MySimpleJob;

/**
 * @author qingmei
 * @date 2020-06-18
 * @desc job参数配置
 */
@Configuration
public class ElasticJobConfig {
    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     * 定义调度器
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MySimpleJob simpleJob, @Value("${wtfJob.cron}") final String cron,
        @Value("${wtfJob.shardingTotalCount}") final int shardingTotalCount,
        @Value("${wtfJob.shardingItemParameters}") final String shardingItemParameters,
        @Value("${wtfJob.jobParameter}") final String jobParameter) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron,
            shardingTotalCount, shardingItemParameters, jobParameter));
    }

    /**
     * 配置任务详细信息
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
        final int shardingTotalCount, final String shardingItemParameters, final String jobParameter) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
            JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).jobParameter(jobParameter).build(),
            jobClass.getCanonicalName())).overwrite(true).build();
    }

    /**
     * 定义调度器
     */
    @Bean(initMethod = "init")
    public JobScheduler dataFlowJobScheduler(final MyDataFlowJob dataFlowJob,
        @Value("${wtfJob.dataflow.cron}") final String cron,
        @Value("${wtfJob.dataflow.shardingTotalCount}") final int shardingTotalCount,
        @Value("${wtfJob.dataflow.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(dataFlowJob, regCenter, getDataFlowJobConfiguration(dataFlowJob.getClass(), cron,
            shardingTotalCount, shardingItemParameters, true));
    }

    /**
     * 配置任务详细信息
     */
    private LiteJobConfiguration getDataFlowJobConfiguration(final Class<? extends DataflowJob> jobClass, // 任务类
        final String cron, // 运行周期配置
        final int shardingTotalCount, // 分片个数
        final String shardingItemParameters, // 分片参数
        final Boolean streamingProcess // 是否是流式作业
    ) {
        return LiteJobConfiguration.newBuilder(
            new DataflowJobConfiguration(JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).build()
            // true为流式作业,除非fetchData返回数据为null或者size为0,否则会一直执行
            // false非流式,只会按配置时间执行一次
                , jobClass.getCanonicalName(), streamingProcess))
            .overwrite(true).build();
    }
    
    /*@Bean(initMethod = "init")
    public JobScheduler scriptJobScheduler(@Value("${gupaoJob.script.cron}") final String cron,
        @Value("${gupaoJob.script.shardingTotalCount}") final int shardingTotalCount,
        @Value("${gupaoJob.script.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(null, regCenter,
            getScriptJobConfiguration("script_job", cron, shardingTotalCount, shardingItemParameters, "D:/1.bat"));
    }
    
    private LiteJobConfiguration getScriptJobConfiguration(final String jobName, // 任务名字
        final String cron, // 运行周期配置
        final int shardingTotalCount, // 分片个数
        final String shardingItemParameters, final String scriptCommandLine // 是脚本路径或者命令
    ) { // 分片参数
        return LiteJobConfiguration.newBuilder(new ScriptJobConfiguration(JobCoreConfiguration
            .newBuilder(jobName, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build()
        // 此处配置文件路径或者执行命令
            , scriptCommandLine)).overwrite(true).build();
    }*/

    /**
     * 动态添加任务
     */
    public void addSimpleJobScheduler(final Class<? extends SimpleJob> jobClass,
                                       final String cron,
                                       final int shardingTotalCount,
                                       final String shardingItemParameters){
        JobCoreConfiguration coreConfig = JobCoreConfiguration
                .newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .jobParameter("wtf")
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
        JobScheduler jobScheduler = new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfig).build());
        jobScheduler.init();
    }

}