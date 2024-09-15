<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css">
<style type="text/css">
a:link {
  color : black;
  text-decoration: none;
}
a:visited {
  color : black;
  text-decoration: none;
}
a:hover {
  color : red;
  text-decoration: underline;
}
a:active {
  color : green;
  text-decoration: none;
}
</style>

<script type="text/javascript">
	/* 20240915 */
	/* 로그인 프로세스 고도화 진행중 ~ ing */
	function fn_loginProcess() {

		const loginData =
				{
					//1. jQuery 속성 선택자 (Attribute)
					"mb_id" : $('input[name="mb_id"]').val() ,
					"mb_pw" : $('input[name="mb_pw"]').val()

					// 2. jQuery 기본 선택자
					// "mb_id" : $('#mb_id').val() ,
					// "mb_pw" : $('#mb_pw').val()
				};

		$.ajax({
			type: 'POST',                      // HTTP 요청 방식(GET, POST)
			url: '../login/loginProcess',      // 클라이언트가 요청을 보낼 서버의 URL 주소
			data: JSON.stringify(loginData),   // HTTP 요청과 함께 서버로 보낼 데이터
			contentType: "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {

				const httpStatVal = data.httpStatVal;
				console.log("HttpStatus : [" + httpStatVal + "]");

				// 정상적인 응답..
				if(httpStatVal == 200){
					alert(data.serverUserInfo.mb_id + "님 로그인 성공!");
					return;
				}else if(httpStatVal == 503){
					alert("로그인 횟수 5회 초과");
					return;
				}else if(httpStatVal == 500){
					alert(data.enteredId + "라는 유저 정보가 없습니다.");
					return;
				}else {
					alert("패스워드가 틀렸습니다.");
					return;
				}

			},
			error : function (e) {
				console.log("Error");
				console.log(e.responseText);
			}

	});
	}
	
</script>
</head>
<body>
	<jsp:include page="../../global/mainTop.jsp"></jsp:include>
   
	<div style="width:1200px; margin: 0 auto">
		<div class="container-fluid">	
		
			<div class="row my-2"><div class="col"></div></div>

			<div class="row">
			
				<div class="col"></div>

				<div class="col-6">
					<div class="row my-5">
						<div class="col"><h3 class="text-center">로그인</h3></div>
					</div>
					
					<div id="checkAlertBox" style="font-size: small;"></div>
					
					<div class="row my-2"><!-- ID -->
						<div class="col">
							<input type="text" id="mb_id" name="mb_id" class="form-control" placeholder="아이디">
						</div>
					</div>
					
					<div class="row my-2"><!-- PW -->
						<div class="col">
							<input type="password" id="mb_pw" name="mb_pw" class="form-control" placeholder="비밀번호">
						</div>					
					</div>
					 
					<div class="row mt-3">
						<div class="col-3"><a href="../login/firstFindIdPage">아이디 찾기</a></div>
						<div class="col">|</div>
						<div class="col-3"><a href="../login/firstFindPwPage">비밀번호 찾기</a></div>
						<div class="col">|</div>
						<div class="col-4"><a href="../signup/acceptPage">회원가입</a></div>						
					</div>
					
					<div class="row mt-3"><!-- 로그인 버튼 -->
						<div class="col d-grid">
							<button onclick="fn_loginProcess()" class="btn btn-primary">로그인</button>
						</div>
					</div>
				</div>
				
				<div class="col"></div>
				
			</div>		
		</div> 
	</div>
	
	<br><br><br><br>
		
	<jsp:include page="../../global/mainBottom.jsp"></jsp:include>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>	
</body>
</body>
</html>