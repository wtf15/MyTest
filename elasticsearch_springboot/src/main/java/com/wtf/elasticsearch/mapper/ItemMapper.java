package com.wtf.elasticsearch.mapper;

import com.wtf.elasticsearch.bean.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemMapper extends ElasticsearchRepository<Item, String> {

}
