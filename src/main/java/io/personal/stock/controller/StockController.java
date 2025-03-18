package io.personal.stock.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.personal.stock.dto.OpenApiReqParam;
import io.personal.stock.entity.KRXListedData;
import io.personal.stock.service.ConfigService;
import io.personal.stock.service.KRXListedDataService;
import io.personal.stock.service.MemberService;
import io.personal.stock.service.OpenApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class StockController {

    @Autowired
    MemberService memberService;

    @Autowired
    ConfigService configService;

    @Autowired
    OpenApiService openApiService;

    @Autowired
    KRXListedDataService krxListedDataService;

    @GetMapping(value = "/stock")
    public String stockMain(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model) {

        memberService.loginCheckAndInsertModel(principal, request, model);

        return "stock";
    }

    @GetMapping(value = "/stock/getCompanyList")
    @ResponseBody
    public List<KRXListedData> getCompanyList() {
        List<KRXListedData> data = krxListedDataService.findAll();

        return data;
    }

    @GetMapping(value = "/stock/getStockPriceInfo")
    @ResponseBody
    public ResponseEntity<JsonNode> getStockPriceInfo(@RequestParam("itmsNm") String itmsNm,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("numOfRows") int numOfRows) {

        // DB로부터 data.go.kr 접속을 위한 인증키, URL 정보를 가져온다.
        HashMap<String, String> data = configService.getConfigData("STOCK_INFO");
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", data.get("AUTH_KEY"));
        params.add("numOfRows", Integer.toString(numOfRows));
        params.add("resultType", "json");
        params.add("itmsNm", openApiService.encodingString(itmsNm, "UTF-8"));

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(data.get("CALLBACK_URL"));
        reqParam.setDetailService("/getStockPriceInfo");
        reqParam.setQueryParam(params);

        String response = openApiService.getOpenApiData(reqParam);
        log.debug("response : {}", response);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode items = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            items = jsonNode.path("response").path("body").path("items").path("item");
            log.debug("items string : {}", items.toString());

            if (items.isMissingNode()) {
                log.warn("items 노드를 찾을 수 없습니다.");
                return ResponseEntity.notFound().build(); // 404 Not Found 응답
            }
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null); // 400 Bad Request 응답
        } catch (Exception e) {
            log.error("기타 오류: {}", e.toString());
            return ResponseEntity.internalServerError().body(null); // 500 Internal Server Error 응답
        }

        return ResponseEntity.ok(items);
    }
}
