package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("response/entity")
public class ResponseEntityController {
//    @ResponseStatus 어노테이션 방식
    @GetMapping("responsestatus")
    @ResponseStatus(HttpStatus.CREATED)
    public String responseStatus(){
        return "OK";
    }

    @GetMapping("responsestatus2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member responseStatus2(){
        Member member = new Member("son","hm7@naver.com", "7777");
        return member;
    }

//    ResponseEntity 객체를 직접 생성한 방식
    @GetMapping("custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("son","hm7@naver.com", "7777");
        return new ResponseEntity<>(member,HttpStatus.CREATED);
    }
//    ResponseEntity<String>일 경우 text/html로 설정
    @GetMapping("custom2")
    public ResponseEntity<String> custom2(){
        String html = "<h1>없는 ID입니다.</h1>";
        return new ResponseEntity<>(html,HttpStatus.NOT_FOUND);
    }

//    map형태의 메세지 커스텀
    public static ResponseEntity<Map<String, Object>> erresponseMessage(HttpStatus status, String message){
        Map<String,Object> body = new HashMap<>();
//        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        body.put("status", Integer.toString(status.value()));
        body.put("error message", message);
        return new ResponseEntity<>(body, status);
    }

//    status 201, message : 객체, map_custom2
    public static ResponseEntity<Map<String, Object>> responseMessage(Object member, HttpStatus status){
        Map<String,Object> body2 = new HashMap<>();
//        Member member = new Member("son","hm7@naver.com", "7777");
//        HttpStatus status = HttpStatus.CREATED;
        body2.put("status", Integer.toString(status.value()));
        body2.put("message", member);
        return new ResponseEntity<>(body2, status);
    }

//    매서드 체이닝 : ResponseEntitiy의 클래스매서트 사용
    @GetMapping("chaing1")
    public ResponseEntity<Member> chaining1(){
        Member member = new Member("son","hm7@naver.com", "7777");
        return ResponseEntity.ok(member);
    }

    @GetMapping("chaing2")
    public ResponseEntity<Member> chaining2(){
        return ResponseEntity.notFound().build();
    }

    @GetMapping("chaing3")
    public ResponseEntity<Member> chaining3(){
        Member member = new Member("son","hm7@naver.com", "7777");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }



}

