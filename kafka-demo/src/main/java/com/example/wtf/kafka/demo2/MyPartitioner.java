package com.example.wtf.kafka.demo2;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

/**
 * @author qingmei
 * @date 2020-05-22
 * @desc 自定义Partitioner
 */
public class MyPartitioner implements Partitioner {
    private Random random = new Random();

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] bytes1, Cluster cluster) {
        // 获取集群中指定topic的所有分区信息
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numOfPartition = partitionInfos.size();
        int partitionNum = 0;
        // key没有设置
        if (key == null) {
            // 随机指定分区
            partitionNum = random.nextInt(numOfPartition);
        }else{
            partitionNum = Math.abs((value.hashCode())) % numOfPartition;
        }
        System.out.println("key->" + key + ",value->" + value + "->send to partition:" + partitionNum);
        return partitionNum;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
