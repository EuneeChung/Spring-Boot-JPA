package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id") // category_item의 item
    )
    private List<Item> items = new ArrayList<>();
    // 샘플 상 다대다 관계, 실무에선 사용하지 않는 것을 권장
    // 왜 쓰지 말아야 할까요? 더 필드 추가가 불가능
    // 실무에선 복잡한 매핑에서는 사용 불가..


    // 카테고리는 계층형 구조 -> 상위, 하위를 알 수 있어야 함.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    // 셀프로 양방향 연관관계 => 이름만 같을 뿐이지, 다른 Entity와 맵핑하는 것과 같습니다

    // 엔티티 설계시 4. 연관관계 편의 메소드 ==
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
