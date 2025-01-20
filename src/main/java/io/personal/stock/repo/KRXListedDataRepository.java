package io.personal.stock.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.personal.stock.entity.KRXListedData;

public interface KRXListedDataRepository extends ElasticsearchRepository<KRXListedData, String> {
}
