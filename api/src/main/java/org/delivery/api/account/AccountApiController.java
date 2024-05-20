package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
//http://localhost:8080/api/account
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<AccountMeResponse> me(){
        var response = AccountMeResponse.builder()
                .name("name1")
                .email("234@naver.com")
                .registeredAt(LocalDateTime.now())
                .build();


        var str = "안녕";
        var age = 0;

        try{
            Integer.parseInt(str);
        }catch (Exception e){
            throw  new ApiException(ErrorCode.SERVER_ERROR, e, " 사용자 me 호출시 에러");
        }



      return Api.OK(response);
    }
}
