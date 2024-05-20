package org.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Api <T>{

    private Result result;

    @Valid
    private T body;

    public static <T> Api Ok(T data){
        var api = new Api<T>();
        api.body = data;
        api.result = Result.Ok();
        return api;
    }
}
