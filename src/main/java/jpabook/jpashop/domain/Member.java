package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입 선언
    // => @Embedded 과 Address의 @Embedded 둘 중 하나만 있어도 되지만, 보통은 두개 다 선언함
    private Address address;

    @OneToMany(mappedBy = "member") // 일대다 관계 (Order에선 @ManyToOne)
    // 나는 주인이 아니에요, 주인의 거울입니다 => mappedBy = "(Order 테이블에 있는) member 필드"
    private List<Order> orders = new ArrayList<>();
    // 엔티티 설계시 주의점 3. 컬렉션은 필드에서 초기화 하자.
    // null 문제에서 안전하다.
    // 하이버네이트가 엔티티를 영속화 할 때, 하이버네이트가 제고하는 내장 컬렉션으로 변경한다. -> 추적가능해야해서 등등 의 이유로
    // class java.util.ArrayList => class org.hibernate.collection.internal.PersistentBag
    // 생성자나 외부에서 리스트를 다시 초기화해버린다면, 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다..
    // 그러니까, 있는거 그대로 쓰는게 제일 안전합니다

}