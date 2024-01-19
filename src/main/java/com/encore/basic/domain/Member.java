package com.encore.basic.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
//entity어노테이션을 통해 mariadb의 테이블 및 컬럼을 자동 생서
//class명은 테이블명, 변수명은 컬럼명
@Entity
//모든 매개변수를 넣은 생성자
@NoArgsConstructor
public class Member {
    @Setter
    @Id //pk
//    identity = auto_increment 설정 auto=JPA구현체가 자동으로 적절한 키생성 전략
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    String은 DBdml varchar로 변환
    private String name;
    @Column(nullable = false, length = 50) //name옵션을 통해 DB의 컬럼명 별도 지정가능
    private String email;
    private String password;
    @Setter
    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime create_time;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public void updateMember(String name, String password){
        this.name = name;
        this.password = password;
    }

}
