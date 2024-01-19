package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository{
//    EntityManager는 JPA의 핵심 클래스(객체)
//    Entity의 생명주기를 관리. 데이터베이스와의 모든 상호작용을 책임.
//    엔티티를 대상으로 CRUD하는 기능을 제공
    private final EntityManager entityManager;

    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
//        persist : 전달된 엔티티(member)가 EntityManger의 관리상태가 되도록 만들어주고, 트랜잭션이 커밋 될 때 데이터베이스에 저장. insert 포함.
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(int id) {
//        find메서드는 pk를 매개 변수로 준다
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public void delete(Member member) {
//        remove 메서드 사용
//        update의 경우 merge메서드 사용

    }
////    pk 외의 컬럼으로 조회할 때
//    public List<Member> findByName(String name){
//        List<Member> members = entityManager.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name",name).getResultList();
//        return members;
//    }

    @Override
    public List<Member> findAll() {
//        jpql : jpa의 쿼리문법
//        장점 : DB에 따라 문법이 달라지지 않는 객체지향언어, 컴파일타임에서 check.(SpringDataJpa의 @Query 기능)
//        단점 : DB고유의 기능과 성능을 극대화하기 어려움.
        List<Member> members = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return members;
    }

}
