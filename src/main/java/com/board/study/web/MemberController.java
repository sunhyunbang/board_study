package com.board.study.web;

import com.board.study.entity.member.Member;
import com.board.study.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/login")
	public String getLoginPage(Model model,
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "/member/login";
	}

	@GetMapping("/member/signup")
	public String signupForm(Model model) {
		model.addAttribute("member",new Member());
		return "member/signupForm";

	}

	@PostMapping("/member/signup")
	public String signup(Member member) {
		memberService.signup(member);

		return "redirect:/";
	}
}