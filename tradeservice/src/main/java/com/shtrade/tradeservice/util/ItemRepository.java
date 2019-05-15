package com.shtrade.tradeservice.util;

import com.shtrade.tradeservice.entity.ItemElasticIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<ItemElasticIndex,Integer> {
    public List<ItemElasticIndex> findByNameLike(String str);
}
