package com.encore.basic.controller;

import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

//1)회원가입, 회원조회
//2)메모리DB ->List에 담기
//3)회원가입
//3-1)get url : members/create
//3-2)post url : members/create
//3-3)from태그, input 태그
//4)회원목록조회
//getmapping, 화면
//4-1) get url : members
//화면 : member/memberList
//이름, email, password
//테이블 구조 -> td는 적당하게 3줄 정도 채워주세요
//service 어노테이션을 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로 등록
//스프링 빈이란 스프링이 생성하고 관리하는 객체를 의미
//제어의 역전(Inversion of Control) -> IOC컨테이너가 스프링빈을 관리(빈을 생성, 의존성 주입)
@Controller
//@RequiredArgsConstructor
public class MemberController {

//    @Autowired  //의존성 주입(DI) 방법1 => 필드 주입 방식
//    private MemberService memberService;

//    의존성 주입(DI) 방법2 -> 생성자 주입 방식이고, 가장 많이 사용하는 방법
//    장점 : final을 통해 상수로 사용가능, 다형성 구현 가능, 순환참조방지
//    생성자가 1개 밖에 없을 때에는 Autowired 생략 가능
    private final MemberService memberService;
    @Autowired()
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

////    의존성주입방법 3. @RequiredArgsConstructor를 이용한 방식
////    @RequiredArgsConstructor : @NonNull어노테이션이 붙어있는 필드 또는 final 필드를 대상으로 생성자 생성
//    private final MemberService memberService;


    @GetMapping("/")
    public String home() {
        return "member/header";
    }

    @GetMapping("members/create")
    public String memberCreateScreen() {
        return "member/member-create-screen";
    }

    @PostMapping("members/create")
    public String save(MemberRequestDto memberRequestDto) {
//        트랜잭션 및 예외처리 테스트
//        try{
//            memberService.save(memberRequestDto);
//            return "redirect:/members";
//        }catch (IllegalArgumentException e){
//            return "member/404-error-page";
//        }
        memberService.save(memberRequestDto);
//        url redirect
        return "redirect:/members";
    }

    @GetMapping("members")
    public String members(Model model){
        model.addAttribute("memberList", memberService.members());
        return "member/member-list";
    }


    @GetMapping("member/find")
    public String memberFind(@RequestParam(value = "id") int id, Model model) {
        try {
            model.addAttribute("member", memberService.findById(id));
            return "member/member-list-detail";
        }catch (EntityNotFoundException e){
            return "member/404-error-page";
        }

    }
    @GetMapping("member/delete")
    public String memberDelete(@RequestParam(value = "id") int id){
        memberService.delete(id);
        return "redirect:/members";
    }

    @PostMapping("member/update")
    public String memberUpdate(MemberRequestDto memberRequestDto){
        memberService.memberUpdate(memberRequestDto);
        return "redirect:/member/find?id="+memberRequestDto.getId();
    }
}

