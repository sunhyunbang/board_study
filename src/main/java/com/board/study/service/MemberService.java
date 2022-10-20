package com.board.study.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.board.study.entity.member.Member;
import com.board.study.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService implements UserDetailsService {


	private final MemberRepository memberRepository;

	@Transactional
	public void signup(Member member){
		log.info("0000000000000000000 : " + member.getId());

		validateDuplicateMember(member);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPwd(passwordEncoder.encode(member.getPassword()));

		memberRepository.save(member);
	}

	/*
	* 중복 회원 검사
	* */
	private void validateDuplicateMember(Member member) {
		memberRepository.findByEmail(member.getEmail())
				.ifPresent(m -> {
					throw new IllegalStateException("이미 존재하는 회원입니다.");
				});
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//		Member member = memberRepository.findByEmail(email);
//		if (member == null) throw new UsernameNotFoundException("Not Found account.");
//
//		return member;
		Optional<UserDetails> userDetails = memberRepository.findByEmail(email).map(member ->
			Member.builder()
					.email(member.getEmail())
					.pwd(member.getPwd())
					.lastLoginTime(member.getLastLoginTime())
					.build()
		);

		return userDetails.orElseThrow(() -> new UsernameNotFoundException("이메일 주소를 찾을 수 없습니다"));
	}
}