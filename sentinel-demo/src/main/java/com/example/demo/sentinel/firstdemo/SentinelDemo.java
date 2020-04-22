package com.example.demo.sentinel.firstdemo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

public class SentinelDemo {

    private static String resource = "doTest";

    // 初始化规则
    private static void initFlowRules() {
        // 限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        // 资源(方法名称、接口）
        flowRule.setResource(resource);
        // 限流的阈值的类型
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(10);
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }

    //
    public static void main(String[] args) {
        // 初始化一个规则
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                // 它做了什么
                entry = SphU.entry(resource);
                System.out.println("Hello Word");
            } catch (BlockException e) {// 如果被限流了，那么会抛出这个异常
                e.printStackTrace();
            } finally {
                if (entry != null) {
                    entry.exit();// 释放
                }
            }
        }
    }

}
