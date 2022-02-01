package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 관계 전략을 지정해야 한다. => 싱글 테이블 전략 선언
@DiscriminatorColumn(name="dtype") // 타입 설정 ex) Book이면 어떻게 할거야
@Getter @Setter
public abstract class Item { // 상속 관계 맵핑 하기 위해 추상 클래스
    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy ="items" )
    private List<Category> categoryList= new ArrayList<>();
}
