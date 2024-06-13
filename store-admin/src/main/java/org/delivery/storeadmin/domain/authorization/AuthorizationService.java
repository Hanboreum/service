package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storeuser.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//자격증명
@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    @Override //login에서 로그인 시도시 여기로 넘어옴
    //spring security 는 UserDetails에서 pw를 가져와 입력받은 pw와 비교
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //이메일로 처리. 이메일로 사용자를 찾아옴
        var storeUserEntity = storeUserService.getRegisterUser(username);
        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
            storeUserEntity.get().getStoreId(),
            StoreStatus.REGISTERED);

        return storeUserEntity.map(it->{

            //사용자가 있다면
            var userSession = UserSession.builder()
                .userId(it.getId())
                .password(it.getPassword())
                .email(it.getEmail())
                .status(it.getStatus())
                .role(it.getRole())
                .registeredAt(it.getRegisteredAt())
                .unregisteredAt(it.getUnregisteredAt())
                .lastLoginAt(it.getLastLoginAt())

                .storeId(storeEntity.get().getId())
                .storeName(storeEntity.get().getName())
                .build();

                return userSession;

        })
            .orElseThrow(()-> new UsernameNotFoundException(username));

    }
}
