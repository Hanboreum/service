package org.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//db도 빈으로 등록해서 사용하기 위해
@Configuration
@EntityScan(basePackages = "org.delivery.db") //db하위에 있는 @Entity 사용
@EnableJpaRepositories(basePackages = "org.delivery.db")
public class JpaConfig {
}
