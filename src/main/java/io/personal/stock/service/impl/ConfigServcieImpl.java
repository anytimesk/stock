package io.personal.stock.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.personal.stock.entity.Config;
import io.personal.stock.repo.ConfigRepository;
import io.personal.stock.service.ConfigService;
import io.personal.stock.service.OpenApiService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ConfigServcieImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepo;

    @Autowired
    OpenApiService openApiService;

    public HashMap<String, String> getConfigData(String category) {
        List<Config> list = configRepo.findByCategory(category);
        HashMap<String, String> data = new HashMap<>();

        list.forEach(conf -> {
            if (conf.getConfName().equals("CALLBACK_URL")) {
                data.put(conf.getConfName(), conf.getConfValue());
            } else if (conf.getConfName().equals("AUTH_KEY")) {
                data.put(conf.getConfName(), openApiService.encodingString(conf.getConfValue()));
            }
        });
        log.debug("baseUri : {}", data.get("CALLBACK_URL"));
        log.debug("servicekey : {}", data.get("AUTH_KEY"));

        return data;
    }

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
