package io.personal.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.personal.stock.entity.Config;
import io.personal.stock.repo.ConfigRepository;
import io.personal.stock.service.ConfigService;

@Service
public class ConfigServcieImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepo;

    public List<Config> getAllConfig() {
        return configRepo.findAll();
    }

    @Transactional
    public Config save(Config item) {
        return configRepo.save(item);
    }

    @Transactional
    public void delete(Long id) {
        configRepo.deleteById(id);
    }
}
