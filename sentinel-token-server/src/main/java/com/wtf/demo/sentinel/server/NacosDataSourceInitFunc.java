package com.wtf.demo.sentinel.server;

import java.util.List;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 从nacos上去获得动态的限流规则
 */
public class NacosDataSourceInitFunc implements InitFunc {

    // nacos 配置中心的服务host
    private final String remoteAddress = "172.16.107.150";
    private final String groupId = "SENTINEL_GROUP";
    // dataid（names+postfix）
    private final String FLOW_POSTFIX = "-flow-rules";

    // 意味着当前的token-server会从nacos上获得限流的规则
    @Override
    public void init() throws Exception {
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> rds = new NacosDataSource<List<FlowRule>>(remoteAddress, groupId,
                namespace + FLOW_POSTFIX, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
            return rds.getProperty();
        });
    }
}
