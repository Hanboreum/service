package org.delivery.storeadmin.domain.storemenu.converter;

import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreMenuConverter {

    //n개가 담겨져있을 수도
    private StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity){
        return StoreMenuResponse.builder()
            .id(storeMenuEntity.getId())
            .name(storeMenuEntity.getName())
            .status(storeMenuEntity.getStatus())
            .amount(storeMenuEntity.getAmount())
            .sequence(storeMenuEntity.getSequence())
            .likeCount(storeMenuEntity.getLikeCount())
            .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
            .build();
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list){
        return list.stream()
            .map(it->{
                return toResponse(it);
            }).collect(Collectors.toList());
    }
}
