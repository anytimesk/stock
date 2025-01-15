package io.personal.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.personal.stock.entity.Config;
import io.personal.stock.service.ConfigService;

@Controller
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping(value = "/conf")
    public String conf(Model model) {

        List<Config> configInfos = configService.getAllConfig();
        model.addAttribute("confs", configInfos);

        return "conf";
    }

    @PostMapping("/conf/add")
    @ResponseBody
    public ResponseEntity<String> addNewConfig(@RequestBody Config config) {
        configService.save(config);

        return ResponseEntity.ok("Config saved successfully");
    }

    @DeleteMapping("/conf/delete/{id}")
    public ResponseEntity<String> deleteConfig(@PathVariable Long id) {
        configService.delete(id);

        return ResponseEntity.ok("Config deleted successfully"); // 성공 메시지를 JSON 형태로 반환
    }
}
