package com.wtf.demo;

import java.io.IOException;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

@SpringBootApplication
public class SentinelProviderApplication {

    public static void main(String[] args) throws IOException {
        // 初始化限流规则
        // 本地规则
         initFlowRule();

        //表示当前的节点是集群客户端
        // nacos上配置的规则
        //ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
        SpringApplication.run(SentinelProviderApplication.class, args);
        System.in.read();
    }

    private static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        // 针对具体的方法限流
        flowRule.setResource("com.wtf.demo.sentinel.SentinelService:sayHello(java.lang.String)");
        // 限流阈值 qps=10
        flowRule.setCount(10);
        // 限流阈值类型(QPS 或并发线程数)
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 流控针对的调用来源，若为 default 则不区分调用来源
        flowRule.setLimitApp("default");
        // 流量控制手段(直接拒绝、Warm Up、匀速排队)
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }

}
