package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // name 설정 따로 안 해주면, 관례적으로 테이블명은 class name.

@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne // 다대일 관계
    @JoinColumn(name="member_id")
    private Member member;
    // 양방향 관계에서 주인을 정해야 한다.
    // 객체는 변경 포인트가 2 군데, 테이블은 FK 하나
    // 쉽게 맞추기 위해서 주인이라는 개념을 도입
    // 연관관계 주인은 누구로 할것이냐?
    // 주로, FK와 가까운 친구로 설정한다
    // 주인은 그대로 두고, 반대 주인이 아닌 친구 설정을 해주어야 한다.


    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItmes = new ArrayList<>();

    @OneToOne
    @JoinColumn(name ="delivery_id")
    private Delivery delivery;
    // 일대일 관계 FK 를 어디다 둘것인가?!
    // 이 프로젝트의 요구사항에서는 주문에서 배송조회가 가능하고 반대의 상황은 없기 때문에
    // orders 테이블에 delivery_id FK를 두었다.
    // 연관관계의 주인은 Order에 있는 delivery 임!


    private LocalDateTime orderDate;
    //    private Date date; @Date 도 달아야 하는데 8버전에서는 로컬 사용 가능

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER , CANCEL]
}
