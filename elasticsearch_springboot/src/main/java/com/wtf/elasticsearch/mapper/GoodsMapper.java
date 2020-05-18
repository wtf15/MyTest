package com.wtf.elasticsearch.mapper;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wtf.elasticsearch.bean.Goods;

public interface GoodsMapper extends ElasticsearchRepository<Goods, String> {
    List<Goods> findByName(String name);

    List<Goods> findByName(String name, Pageable pageable);
}
