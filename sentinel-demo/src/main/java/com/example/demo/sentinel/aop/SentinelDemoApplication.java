package com.example.demo.sentinel.aop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

@SpringBootApplication
public class SentinelDemoApplication {

    public static void main(String[] args) {
        initFlowRules();
        SpringApplication.run(SentinelDemoApplication.class, args);
    }

    // 初始化规则
    private static void initFlowRules() {
        // 限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        // 资源(方法名称、接口）
        flowRule.setResource("sayHello");
        // 限流的阈值的类型
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(10);
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }

}
