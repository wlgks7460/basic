package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository){
        this.memberRepository = springDataJpaMemberRepository;
    }


    public List<MemberResponseDto> members(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member member : members){
            MemberResponseDto memberResponseDto = new MemberResponseDto();
            memberResponseDto.setId(member.getId());
            memberResponseDto.setName(member.getName());
            memberResponseDto.setEmail(member.getEmail());
            memberResponseDto.setPassword(member.getPassword());
            memberResponseDto.setCreate_time(member.getCreate_time());
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }

    @Transactional
//    Transactional어노체이션 클래스 단위로 붙이면 모든 먀서드에 각각 Transactional 적용
//    Transactiona을 적용하면 한 매서드 단위로 트랜재션 지정
    public void save(MemberRequestDto memberRequestDto) throws IllegalArgumentException{

            Member member = new Member(memberRequestDto.getName(), memberRequestDto.getEmail(), memberRequestDto.getPassword());
            memberRepository.save(member);

////        Transactional 테스트
//            Member member = new Member(memberRequestDto.getName(), memberRequestDto.getEmail(), memberRequestDto.getPassword());
//            memberRepository.save(member);
//            if(member.getName().equals("kim")){
//                throw new IllegalArgumentException();
//            }
    }

    public void delete(int id){
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        memberRepository.delete(member);
    }

    public MemberResponseDto findById(int id) throws EntityNotFoundException{
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setPassword(member.getPassword());
        memberResponseDto.setCreate_time(member.getCreate_time());
        return memberResponseDto;
    }

    public void memberUpdate(MemberRequestDto memberRequestDto){
        Member member = memberRepository.findById(memberRequestDto.getId()).orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberRequestDto.getName(),memberRequestDto.getPassword());
        memberRepository.save(member);
    }

}
