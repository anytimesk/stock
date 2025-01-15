package io.personal.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.personal.stock.entity.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

}
