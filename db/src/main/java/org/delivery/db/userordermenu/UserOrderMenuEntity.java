package org.delivery.db.userordermenu;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.hibernate.internal.build.AllowNonPortable;

@Data
@NoArgsConstructor
@AllowNonPortable
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_order_menu") //n
public class UserOrderMenuEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userOrderId; //1:n

    @Column(nullable = false)
    private Long storeMenuId; //1:n

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "varchar(50)") //
    private UserOrderStatus status;
}
