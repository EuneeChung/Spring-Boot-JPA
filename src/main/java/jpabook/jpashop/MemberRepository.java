package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em; //생성하는 코드는 스프링 부트가 다 해줍니다!

    public Long save(Member member){ // shift + ctrl + T => Test 코드 작성
        em.persist(member);
        return member.getId(); // 왜 멤버 리턴안하고 아이디 리턴하지?? 이거는 스타일차이
        // `커맨드랑 쿼리를 분리해라.`
        // 저장을 하고나면 가급적이면 side effect를 일으키는 커맨드기 때문에,
        // 리턴값을 거의 안 만듦 -> id 정도면 조회가능하기떄문에 id만 반환
    }
    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
