package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;

    @Embedded //내장 타입
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY , COMP
    // Enum 타입은 @Enumerated 달아줘야 함.
    // EnumType.ORDINAL  EnumType.STRING 2가지가 있음
    // ORDINAL(Default)-> 숫자로 들어감 => 중간에 status 가 추가되면 망함
    // => 절대로 ORDINAL 사용하지 않기. 꼭 STRING 사용하세요

}
