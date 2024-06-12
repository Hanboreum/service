package org.delivery.api.domain.storemenu.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Converter
public class StoreMenuConverter {

    // request -> entity
    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request) {

        return Optional.ofNullable(request)
            .map(it -> {

                return StoreMenuEntity.builder()
                    .storeId(request.getStoreId())
                    .name(request.getName())
                    .amount(request.getAmount())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .build()
                    ;

            })
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    //entity -> response
    public StoreMenuResponse toResponse(
        StoreMenuEntity storeMenuEntity
    ) {
        return Optional.ofNullable(storeMenuEntity)
            .map(it -> {
                return StoreMenuResponse.builder()
                    .id(storeMenuEntity.getId())
                    .name(storeMenuEntity.getName())
                    .storeId(storeMenuEntity.getStoreId())
                    .amount(storeMenuEntity.getAmount())
                    .status(storeMenuEntity.getStatus())
                    .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                    .likeCount(storeMenuEntity.getLikeCount())
                    .sequence(storeMenuEntity.getSequence())
                    .build()
                    ;
            })
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    //위 메서드를 하나씩 호출하면서 리스트로 바꿔줌
    //StoreMenuEntity를 list로 받아 stream을 통해 위쪽에 있는 toResponse 에 각각 하나씩 호출.
    public List<StoreMenuResponse> toResponse(
        List<StoreMenuEntity> list
    ) {
        return list.stream()
            .map(it -> toResponse(it)).collect(Collectors.toList());
    }
}
