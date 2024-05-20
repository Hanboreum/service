package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.UserErrorCode;
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
    public Api<Object> me(){
        var response = AccountMeResponse.builder()
                .name("name1")
                .email("234@naver.com")
                .registeredAt(LocalDateTime.now())
                .build();


      return Api.ERROR(UserErrorCode.USER_NOT_FOUND,"사용자 없음");
    }
}
