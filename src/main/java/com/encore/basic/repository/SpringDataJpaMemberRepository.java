package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//쿼리문 없이 DB에 데이터 요청 : ORM(Object Relational Mapping)
@Repository
//spring data jpa의 기본 기능을 쓰기 위해서는 JpaRepository를 상속해야함
//상속시에 entity면과 해당 entity의 pk타입을 명시
//구현 클래스와 스펙은 SimpleJpaRepository 클래스에 있고,
//실질적인 구동상황에서 hibernate 구현체에 동작위임.
public interface SpringDataJpaMemberRepository extends MemberRepository, JpaRepository<Member, Integer> {

}
