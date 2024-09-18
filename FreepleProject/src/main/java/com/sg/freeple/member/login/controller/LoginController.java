package com.sg.freeple.member.login.controller;

import com.sg.freeple.member.login.service.LoginService;
import com.sg.freeple.vo.FP_MemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/member/login/*")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	
	//@로그인 페이지 이동
	@RequestMapping("loginPage") // 로그인 페이지 이동
	public String loginPage() { 
		
		return "member/login/loginPage";
	}

	// 20240915
	// 로그인 프로세스 고도화 진행중.. ~ ing
	@ResponseBody
	@RequestMapping(value= "loginProcess" , method = RequestMethod.POST)
    // @RequestBody 어노테이션은 HTTP 요청의 body 내용을 자바 객체로 매핑하는 역할을 하고
    // @ResponseBody 어노테이션은 자바 객체를 HTTP 요청의 body 내용으로 매핑하는 역할을 합니다.
	public HashMap<String, Object> loginProcess(@RequestBody FP_MemberVo memberVo) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		// Http 응답 값 초기화..
		int httpStatVal = 0;

		// 1. Info Entered By The User Id , Pw : 사용자가 입력한 정보 확인..
		String enteredId = memberVo.getMb_id();
		String enteredPw = memberVo.getMb_pw();

		// Server User Info 
		FP_MemberVo serverUserInfo = loginService.loginProcess(memberVo);

		// Login Fail Count : 로그인 실패 횟수
		int failCount = serverUserInfo.getMb_login_failures();

		// 2. Server User Info : 서버 유저 정보 확인..
		if(Objects.isNull(serverUserInfo)){

			// 서버에 유저 정보 없으면 500 에러 발생..
			httpStatVal = HttpStatus.INTERNAL_SERVER_ERROR.value();
			log.error("Internal Server Error = {}" , httpStatVal + " 번 ERROR 발생");
			log.error("There is no user info = {}" , enteredId + "라는 사용자는 찾을수 없습니다.");
			map.put("httpStatVal",httpStatVal);
			map.put("enteredId" , enteredId);
			return map;

		}else{
			// 1. 로그인시 패스워드가 다르면 failCount ++ 할것..
			if(! serverUserInfo.getMb_pw().equals(enteredPw)){

				//  Server User Login Fail Increase
				loginService.loginFailIncrease(memberVo);

				// 서버에 유저 정보가 있으나 패스워드가 다를시 204 에러 발생..
				httpStatVal = HttpStatus.NO_CONTENT.value();
				log.error("No Content = {}" , httpStatVal + " 번 발생");
				map.put("failCount" , failCount);
				map.put("httpStatVal",httpStatVal);
				return map;

			}

			// ( # 유저 -> 관리자한테 문의 -> 관리자가 응답 -> 유저에게 다시 failCount 롤백.. ) - 이건 나중에 천천히 해도될듯

			/* 로그인 실패 5번 이상이면*/
			if(failCount > 5){

				// 서버에 유저 정보 없으면 503 에러 발생..
				httpStatVal = HttpStatus.SERVICE_UNAVAILABLE.value();
				log.error("Service Unavailable = {}" , httpStatVal + " 번 ERROR 발생");
				log.error("Number of user logins exceeded 5 times = {}" , failCount);
				map.put("httpStatVal",httpStatVal);
				map.put("failCount" , failCount);
				return map;

			} else {
				// Server User Login Date Update..
				loginService.loginDateUpdate(memberVo);

				// 3. 서버에 유저 정보가 맞으면 200 OK..
				httpStatVal = HttpStatus.OK.value();
				log.error("Service OK = {}" , httpStatVal + " 정상 응답");
				map.put("httpStatVal", httpStatVal);

				// 4. 유저의 정보를 담는다..
				map.put("serverUserInfo", serverUserInfo);
				return map;

			}
		}

	}
	
	//@로그아웃 기능 
	@RequestMapping("logoutProcess")// 로그아웃 -> 무조건 메인페이지로감
	public String logoutProcess(HttpSession session) {
		
		session.removeAttribute("serverUserInfo");
		
		return "redirect:../../main/mainPage";
	}
	
	//@아이디 찾기 페이지 1 
	@RequestMapping("firstFindIdPage")
	public String firstFindIdPage() {
		
		return "member/login/firstFindIdPage";
	}
	
	//@아이디 찾기 페이지 2 (기능 : 이메일-> 인증번호 보내기)
	@RequestMapping("secondFindIdPage")
	public String secondFindIdPage(String mb_email) {
		
		//입력한 메일로 인증번호 발송
		loginService.findIdSendEmailKeyProcess(mb_email);
		
		return "member/login/secondFindIdPage";
	}
	
	//@아이디 찾기 페이지 3 (기능 : 인증 번호확인-> 아이디 찾아주기)
	@RequestMapping("lastFindIdPage")
	public String lastFindIdPage(FP_MemberVo fp_MemberVo,String auth_key, Model model) {

		//메일 인증번호 확인이 되고 난뒤 아이디 찾기 버튼을 눌렀을때 서비스 로직. 
		loginService.mailAuth(auth_key); 
		
		model.addAttribute("UserInfo", loginService.getUser(fp_MemberVo, auth_key));
		
		return "member/login/lastFindIdPage";
	}
	
	//@아이디 찾기 끝난 뒤 로그인 버튼 누를시 기존 정보 초기화
	@RequestMapping("closeUserProcess") // 로그인 페이지 이동
	public String closeUserProcess(int mb_no) {
			
		loginService.closeUserProcess(mb_no);
		
		return "member/login/loginPage";
	}
	
	//@비밀번호 찾기 페이지1
	@RequestMapping("firstFindPwPage") 
	public String firstFindPwPage() {
		
		return "member/login/firstFindPwPage";
	}
	
	//@비밀번호 찾기 페이지 2 (기능 : 이메일-> 메일 인증 링크 보냈다는 페이지)
	@RequestMapping("secondFindPwPage")
	public String secondFindPwPage(String mb_email) {
		
		//입력한 메일로 인증번호 발송
		loginService.findPwSendEmailKeyProcess(mb_email);
		
		return "member/login/secondFindPwPage";
	}
	
	//@비밀번호 찾기 - 이메일 인증 처리 -> 비밀번호 재설정 화면으로.
	@RequestMapping("mailAuthProcess")
	public String mailAuthProcess(FP_MemberVo fp_MemberVo,String auth_key, Model model) {
		
		//메일 인증번호 확인이 되고 난뒤 아이디 찾기 버튼을 눌렀을때 서비스 로직. 
		loginService.mailAuth(auth_key);
		
		model.addAttribute("maileKey", auth_key);	
		return "member/login/changePwPage";
	}
	
	//@비밀번호 찾기 3 - 비밀번호 재설정 기능 -> 성공화면 출력.
	@RequestMapping("changePwCompletProcess")
	public String changePwCompletProcess(FP_MemberVo fp_MemberVo, String auth_key, Model model) {
		
		//사용자가 입력한 비밀번호 재설정  
		loginService.ChangeUserPwProcess(fp_MemberVo,auth_key);
		
		model.addAttribute("UserInfo", loginService.getUser(fp_MemberVo, auth_key));
		
		return "member/login/changePwCompletPage";
	}
	
}


