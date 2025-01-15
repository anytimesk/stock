package io.personal.stock.service;

import java.util.List;

import io.personal.stock.entity.Config;

public interface ConfigService {
    public List<Config> getAllConfig();

    public Config save(Config item);

    public void delete(Long id);
}
