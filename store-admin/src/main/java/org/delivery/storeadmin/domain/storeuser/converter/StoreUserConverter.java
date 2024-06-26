package org.delivery.storeadmin.domain.storeuser.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
//@Converter 써도 된다.
public class StoreUserConverter {

    public StoreUserEntity toEntity(
        StoreUserRegisterRequest request,
        StoreEntity storeEntity
    ) {

        return StoreUserEntity.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .role(request.getRole())
            .storeId(storeEntity.getId()) //TODO null일 때 에러처리
            .build();
    }

    public StoreUserResponse toResponse(
        StoreUserEntity storeUserEntity,
        StoreEntity storeEntity
    ) {
        return StoreUserResponse.builder()
            .user(StoreUserResponse.UserResponse.builder()
                .id(storeUserEntity.getId())
                .email(storeUserEntity.getEmail())
                .status(storeUserEntity.getStatus())
                .role(storeUserEntity.getRole())
                .registeredAt(storeUserEntity.getRegisteredAt())
                .unregisteredAt(storeUserEntity.getUnregisteredAt())
                .lastLoginAt(storeUserEntity.getLastLoginAt())
                .build()
            )
            .store(
                StoreUserResponse.StoreResponse.builder()
                    .name(storeEntity.getName())
                    .id(storeEntity.getId())
                    .build()
            )
            .build();

    }

    //로그인된 사용자 세션이 들어오면 반환
    //usersession -> storeuserresponse 변환
    public StoreUserResponse toResponse(UserSession userSession){

        return StoreUserResponse.builder()
            .user(StoreUserResponse.UserResponse.builder()
                .id(userSession.getUserId())
                .email(userSession.getEmail())
                .status(userSession.getStatus())
                .role(userSession.getRole())
                .registeredAt(userSession.getRegisteredAt())
                .unregisteredAt(userSession.getUnregisteredAt())
                .lastLoginAt(userSession.getLastLoginAt())
                .build()
            )
            .store(
                StoreUserResponse.StoreResponse.builder()
                    .name(userSession.getStoreName())
                    .id(userSession.getStoreId())
                    .build()
            )
            .build();

    }
}
