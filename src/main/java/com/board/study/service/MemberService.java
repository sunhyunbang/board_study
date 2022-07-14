package com.board.study.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.board.study.entity.member.Member;
import com.board.study.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final MemberRepository memberRepository;

	@Transactional
	public void signup(Member member){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		logger.info("0000000000000000000 : " + member.getId());
		logger.info("1111111111111111111 : " + member.getPassword());
		logger.info("2222222222222222222 : " + member.getEmail());
		member.setPwd(passwordEncoder.encode(member.getPassword()));

		memberRepository.save(member);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			
		Member member = memberRepository.findByEmail(email);
		
		if (member == null) throw new UsernameNotFoundException("Not Found account."); 
		
		return member;
	}
}