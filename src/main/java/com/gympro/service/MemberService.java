package com.gympro.service;

import com.gympro.entity.Member;
import com.gympro.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public List<Member> searchMembers(String keyword) {
        return memberRepository.searchByNameOrPhone(keyword);
    }

    public List<Member> getRecentMembers() {
        return memberRepository.findTop5ByOrderByJoinDateDesc();
    }

    public long getTotalMemberCount() {
        return memberRepository.count();
    }
}
