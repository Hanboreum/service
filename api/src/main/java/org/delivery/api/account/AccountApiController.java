package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
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
                .email("234@naver.com")
                .name("name1")
                .registeredAt(LocalDateTime.now())
                .build();

      return Api.Ok(response);
    }
}
