package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //어딘가에 내장될 수 있다.
@Getter
public class Address {
    // 값 타임 => 변경되면 안 된다. immutable
    // 생성할 때만, 값이 셋팅되게 setter 제공 x 하는게 좋은 설계
    // JPA가 생성할때, 리플렉션이나 등등의 기술 스택을 써야 하는데
    // 기본 생성자가 없으면 하지 못함!
    // protected 까지 JPA가 허용해주기떄문에 protected 기본 생성자 생성


    private String city;
    private String street;
    private String zipcode;

    protected Address(){
    }

    public Address(String city, String street, String zipcode){
        this.city=city;
        this.street=street;
        this.zipcode=zipcode;
    }
}
