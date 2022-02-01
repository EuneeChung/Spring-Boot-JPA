package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입 선언
    // => @Embedded 과 Address의 @Embedded 둘 중 하나만 있어도 되지만, 보통은 두개 다 선언함
    private Address address;

    @OneToMany(mappedBy = "member") // 일대다 관계 (Order에선 @ManyToOne)
    // 나는 주인이 아니에요, 주인의 거울입니다 => mappedBy = "(Order 테이블에 있는) member 필드"
    private List<Order> orders = new ArrayList<>();
}
