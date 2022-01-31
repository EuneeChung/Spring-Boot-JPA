package jpabook.jpashop;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


//    @Test
//    public void testMember() throws Exception{
//        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedId);
//
//        //then
//        Assertions.assertEquals(findMember.getId(),member.getId());
//        Assertions.assertEquals(findMember.getUsername(),member.getUsername());
//
//        // => 이렇게 하면 에러가 나야 함.
//        // No EntityManager with actual transaction
//        // 트랜잭션이 없다. 엔티티 매니저를 통한 엔티티 변경사항은 모두 트랜잭션 안에서 이루어져야 함.
//    }

    @Test
    @Transactional
    @Rollback(value = false) // DB에서 확인할 수 있음
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertEquals(findMember.getId(),member.getId());
        Assertions.assertEquals(findMember.getUsername(),member.getUsername());
        Assertions.assertEquals(findMember, member); // equals hashcode 구현 안했기 때문에 ==비교임
        System.out.println("findMember == member  "+(findMember == member));
        // 같은 트랜잭션안에서 저장하고, 조회하면 영속성 컨텍스트가 같음
        // 같은 영속성 컨텍스트 안에서 id 값이 같으면 같은 엔티티로 인식.
    }
}