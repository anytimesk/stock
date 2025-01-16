package io.personal.stock.dto;

import org.springframework.util.LinkedMultiValueMap;

import lombok.Data;

@Data
public class OpenApiReqParam {
    private String endPointURL;

    private String detailService;

    private LinkedMultiValueMap<String, String> queryParam;
}
