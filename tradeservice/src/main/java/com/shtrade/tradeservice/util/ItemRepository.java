package com.shtrade.tradeservice.util;

import com.shtrade.tradeservice.entity.ItemElasticIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<ItemElasticIndex,Integer> {

}
