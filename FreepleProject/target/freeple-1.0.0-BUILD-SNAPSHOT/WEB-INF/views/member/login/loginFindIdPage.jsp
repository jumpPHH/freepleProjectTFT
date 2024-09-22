<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기 페이지 1</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css">

	<script src="<c:url value="/resources/js/login/loginProcess.js" />"></script>
</head>
<body>
<jsp:include page="../../global/mainTop.jsp"></jsp:include>
	<div style="width:1200px; margin: 0 auto">
		<div class="container-fluid">
		
			<div class="row my-5">
				<div class="col"></div>
				<div class="col">
				
					<div class="row my-4">
						<div class="col">
							<h4>아이디 찾기</h4>
						</div>
					</div>
					
					<div class="row my-2">
						<div class="col">
							<div id="checkEmailAlert" name="checkEmailAlert" style="font-size: small;"></div>
						</div>
					</div>
					
					<div class="row my-2">
						<div class="col">
							<p style="font-size: small;">가입하신 이메일 주소를 입력해주세요.<br>
							이메일로 인증번호를 발송합니다.</p>
						</div>  					
					</div>

					<div class="row my-2">
		                <div class="col">
		                	<input type="email" class="form-control" placeholder="이메일 입력" id="mb_email" name="mb_email">
		                </div>
					</div>
					
					<div class="row my-2">
						<div class="col d-grid">
							<button id="checkEmailBtn" name="checkEmailBtn" onclick="fn_checkEmail()" class="btn btn-primary">인증번호 보내기</button>
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
</html>