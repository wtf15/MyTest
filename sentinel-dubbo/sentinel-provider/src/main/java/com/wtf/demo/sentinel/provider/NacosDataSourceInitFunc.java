package com.wtf.demo.sentinel.provider;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

//从nacos上去获得动态的限流规则
public class NacosDataSourceInitFunc implements InitFunc {

    //token-server的ip
    private final String CLUSTER_SERVER_HOST="localhost";
    //token-server 端口
    private final int CLUSTER_SERVER_PORT=9999;
    //请求超时时间
    private final int REQUEST_TIME_OUT=200000;
    //namespace
    private final String APP_NAME="App-wtf";

    //nacos的配置()
    //nacos 配置中心的服务host
    private final String remoteAddress="172.16.107.150";
    private final String groupId="SENTINEL_GROUP";
    //dataid（names+postfix）
    //限流规则后缀
    private final String FLOW_POSTFIX="-flow-rules";

    //意味着当前的token-server会从nacos上获得限流的规则
    @Override
    public void init() throws Exception {
        //加载集群-信息
        loadClusterClientConfig();

        registryClusterFlowRuleProperty();
    }

    //通过硬编码的方式，配置连接到token-server服务的地址,{这种在实际使用过程中不建议，后续可 以基于动态配置源改造}
    private void loadClusterClientConfig(){
        ClusterClientAssignConfig assignConfig=new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clientConfig=new ClusterClientConfig();
        //token-client请求 token-server获取令牌的超时时间
        clientConfig.setRequestTimeout(REQUEST_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }

    /**
     * 注册动态规则Property
     * 当client与Server连接中断，退化为本地限流时需要用到的该规则
     * 该配置为必选项，客户端会从nacos上加载限流规则，请求tokenserver时，会戴上要check的规
     * 则id
     * {这里的动态数据源，我们稍后会专门讲到}
     */
    private void registryClusterFlowRuleProperty(){
        // 使用 Nacos 数据源作为配置中心，需要在 REMOTE_ADDRESS 上启动一个 Nacos 的服务
        ReadableDataSource<String, List<FlowRule>> rds=
            new NacosDataSource<List<FlowRule>>(remoteAddress,groupId,APP_NAME+FLOW_POSTFIX,
                source -> JSON.parseObject(source,new TypeReference<List<FlowRule>>(){}));
        // 为集群客户端注册动态规则源
        FlowRuleManager.register2Property(rds.getProperty());
    }

}
