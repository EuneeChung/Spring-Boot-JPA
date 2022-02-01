package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name="orders") // name 설정 따로 안 해주면, 관례적으로 테이블명은 class name.

@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 다대일 관계
    @JoinColumn(name="member_id")
    private Member member;
    // 양방향 관계에서 주인을 정해야 한다.
    // 객체는 변경 포인트가 2 군데, 테이블은 FK 하나
    // 쉽게 맞추기 위해서 주인이라는 개념을 도입
    // 연관관계 주인은 누구로 할것이냐?
    // 주로, FK와 가까운 친구로 설정한다
    // 주인은 그대로 두고, 반대 주인이 아닌 친구 설정을 해주어야 한다.


    // 엔티티 설계시 주의점 2. 모든 연관관계는 지연로딩으로 설정
    // @ManyToOne(fetch = FetchType.EAGER)
    // Order를 조회할 때, Join해서 member를 한방에 같이 조회
    // JPQL select o from order o; => SQL select * from order 로 번역
    // n+1(order) member를 조회하기위해 쿼리가 날라감
    // EAGER 의 뜻은 조인해서 한방에 가지고 온다는 뜻이 아니라, order를 조회하는 시점에
    // member를 꼭 같이 조회하겠다!
    // 즉시로딩 EAGER 보다는 지연로딩 LAZY를 쓰는 것을 권장합니다
    // 연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 도는 엔티티 그래프 기능 사용할 것!

    // @XtoOne (@OneToOne,@ManyToOne) 관계는 기본적으로 EAGER 즉시로딩 -> 직접 지연로딩으로 설정해야 한다.
    // toOne의 fetch 전략은 EAGER
    // @OneToX의 기본 fetch 전략은  LAZY 두개가 달라유

    // 하튼 정리하자면, @XtoOne (@OneToOne,@ManyToOne) 관계는 다 찾아서 직접 지연로딩으로 설정해야 한다.
    // 지연로딩 LAZY로 안 바꾸면 연관된 쿼리가 다 날라가유 ~

    @OneToMany(mappedBy = "order", cascade =CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // persist(orderItemA)
    // persist(orderItemB)
    // persist(orderItemC)
    // persist(order)
    // 로 해야 하는거

    // @(cascade =CascadeType.ALL) 설정이 되어있다면 persist를 전파해줘서
    // persist(order) 한 줄이면 돼유


    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
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

    // 엔티티 설계시 4. 연관관계 편의 메소드 ==
    // 양방향일때 양쪽을 원자적으로 묶어서 셋팅하기 위해 사용
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
