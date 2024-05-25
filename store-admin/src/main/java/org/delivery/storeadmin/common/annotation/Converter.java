package org.delivery.storeadmin.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
//converter 어노테이션 가진 건 데이터 변환해주는 것으로 쓸 것
public @interface Converter {

    @AliasFor(annotation = Service.class)
    String value() default "";
}
