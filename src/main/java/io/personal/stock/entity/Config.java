package io.personal.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String confName; // 설정 변수 Key name

    String confValue; // 설정 변수 Value

    String category; // 설정 변수 카테고리

    String comment; // 설정 변수 설명
}
