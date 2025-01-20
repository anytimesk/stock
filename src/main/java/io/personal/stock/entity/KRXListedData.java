package io.personal.stock.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "krx_listed_data")
public class KRXListedData {
    @Id
    private String srtnCd; // _id 필드와 매핑

    private String basDt; // 기준일자

    private String isinCd; // 종목코드

    private String mrktCtg; // 시장구분

    private String itmsNm; // 종목명

    private String crno; // 법인등록번호

    private String corpNm; // 법인명
}
