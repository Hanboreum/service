package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;

    @Override //login에서 로그인 시도시 여기로 넘어옴
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //이메일로 처리. 이메일로 사용자를 찾아옴
        var storeUserEntity = storeUserService.getRegisterUser(username);

        return storeUserEntity.map(it->{
                var user = User.builder()
                    .username(it.getEmail())
                    .password(it.getPassword())
                    .roles(it.getRole().toString())
                    .build();

                return user;

        })
            .orElseThrow(()-> new UsernameNotFoundException(username));

    }
}
