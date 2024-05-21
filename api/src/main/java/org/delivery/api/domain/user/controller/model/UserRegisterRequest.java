package org.delivery.api.domain.user.controller.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//가입시 보냄
@Data
@AllArgsConstructor
@NoArgsConstructor
//외부로부터 들어옴
public class UserRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String password;

}
