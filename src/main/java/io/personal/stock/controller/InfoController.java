package io.personal.stock.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.personal.stock.dto.OpenApiReqParam;
import io.personal.stock.service.ConfigService;
import io.personal.stock.service.KRXListedDataService;
import io.personal.stock.service.OpenApiService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class InfoController {

    @Autowired
    ConfigService configService;

    @Autowired
    OpenApiService openApiService;

    @Autowired
    KRXListedDataService krxListedDataService;

    @GetMapping(value = "/info")
    public String info() {

        return "info";
    }

    @GetMapping(value = "/info/saveCompanyList")
    @ResponseBody
    public ResponseEntity<JsonNode> saveCompanyList(@RequestParam int numOfRows, @RequestParam int pageNo) {
        HashMap<String, String> data = configService.getConfigData("ISIN_CODE");
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", data.get("AUTH_KEY"));
        params.add("resultType", "json");
        params.add("numOfRows", Integer.toString(numOfRows));
        params.add("pageNo", Integer.toString(pageNo));

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(data.get("CALLBACK_URL"));
        reqParam.setDetailService("/getItemInfo");
        reqParam.setQueryParam(params);

        String response = openApiService.getOpenApiData(reqParam);
        log.info("Get Componey List Response: {}", response);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode items = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            items = jsonNode.path("response").path("body").path("items").path("item");
            krxListedDataService.saveAll(items);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
        } catch (Exception e) {
            log.error("기타 오류: {}", e.toString());
        }

        return ResponseEntity.ok(items);
    }
}
