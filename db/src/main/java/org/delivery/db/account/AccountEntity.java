package org.delivery.db.account;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

@SuperBuilder //부모로부터 상속받은 변수 사용 가능
@Data
@EqualsAndHashCode(callSuper = true) //부모 엔티티의 값 포함해 비교
@Entity
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity extends BaseEntity {

}
