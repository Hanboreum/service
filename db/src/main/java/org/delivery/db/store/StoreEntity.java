package org.delivery.db.store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;

import java.math.BigDecimal;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) //상속관계. 동등성 검사
@SuperBuilder
@Table(name ="store")
public class StoreEntity extends BaseEntity {


   @Column(length = 100, nullable = false)
   private String name;

   @Column(length = 150, nullable = false)
   private String address;

   @Column(nullable = false, columnDefinition = "varchar(45)")
   @Enumerated(EnumType.STRING)
   private StoreStatus status;

   @Column(nullable = false, columnDefinition = "varchar(45)") //enum으로 인식 못할때
   @Enumerated(EnumType.STRING)
   private StoreCategory category;

   private double star;

   @Column(length = 200, nullable = false)
   private String thumbnailUrl;

   @Column(precision = 11, scale = 4, nullable = false)
   private BigDecimal minimumAmount;

    @Column(precision = 11, scale = 4, nullable = false)
   private BigDecimal minimumDeliveryAmount;

    @Column(length = 20)
   private String phoneNumber;

}
