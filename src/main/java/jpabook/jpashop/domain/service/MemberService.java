package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // lombok
public class MemberService {

    /**
     * 필드 인젝션 Autowired
     * 필드 선언시 @Autowired를 바로 붙임
     */
    private final MemberRepository memberRepository;


    /**
     * 생성자 인젝션
     * 생성자에 @Autowired
     * 생성자가 하나만 있을 경우 자동 인젝션을 해주므로 어노테이션을 붙이지 않아도됨
     *
     @RequiredArgsConstructor // lombok 어노테이션을 클래스에 붙이면 아래 생성자를 자동으로 만들어줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    /**
     * setter 인젝션 
     * 장점 - 테스트시 가짜 repository 등을 주입해 테스트 가능
     * 단점 - runtime시 누군가 중간에 바꿀 가능성이 생김
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */
    
    /**
     * 회원 가입
     * 트렌젝셔널 디폴트값 = (readOnly = false)
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증 문제시 예외
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원 검증
     */
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
