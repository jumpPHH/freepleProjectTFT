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