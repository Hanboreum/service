package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * user domain login 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //리턴과 매개변수 모두 entity 외부에서 이 서비스를 사용하고 싶다면 entity를 넣어야 함
    public UserEntity register(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(it->{
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity); //save된 userentity를 비즈니스 로직으로
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT,"User entity Null"));
    }

    public UserEntity login(String email,
                            String password
    ){
        var entity = getUserWithThrow(email,password);
        return  entity;
    }

    public UserEntity getUserWithThrow(String email, String password){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(Long userId){//오버로딩
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
