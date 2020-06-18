package com.example.wtf.operate;

import com.example.wtf.job.MySimpleJob1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.wtf.config.ElasticJobConfig;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingmei
 * @date 2020-06-19
 * @desc 动态添加任务测试
 */
@RestController
public class JobController {
    @Autowired
    private ElasticJobConfig elasticJobConfig;

    @RequestMapping("/addJob")
    public void addJob() {
        int shardingTotalCount = 2;
        elasticJobConfig.addSimpleJobScheduler(new MySimpleJob1().getClass(), "0/3 * * * * ?", shardingTotalCount,
            "0=A,1=B");
    }
}
