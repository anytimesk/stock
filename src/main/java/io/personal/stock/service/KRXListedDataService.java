package io.personal.stock.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import io.personal.stock.entity.KRXListedData;

public interface KRXListedDataService {
    public void save(KRXListedData KRXListedData);

    public void saveAll(JsonNode items) throws Exception;

    public List<KRXListedData> findAll();
}
