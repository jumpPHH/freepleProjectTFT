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
        contentType: "application/json; charset=UTF-8", // JSON 타입으로 UTF-8로 받겠다.
        dataType : "json",                  // 데이터의 타입은 JSON 이다.
        success : function(data) {

            const httpStatVal = data.httpStatVal;
            console.log("HttpStatus : [" + httpStatVal + "]");

            if(data){
                // 20240918
                // 정상적인 응답 -> 메인 페이지로 이동 ( 로그인에 성공한 memberId 던짐 )
                if(httpStatVal == 200){

                    alert(data.serverUserInfo.mb_id + "님 로그인 성공!");
                    const loginForm = document.createElement('form');

                    loginForm.name = 'loginForm';
                    loginForm.method = 'POST';
                    loginForm.action = '../../main/mainPage';

                    // create element (input)
                    const userInfoInput = document.createElement('input');

                    // set attribute (input)
                    userInfoInput.setAttribute("type", "hidden");
                    userInfoInput.setAttribute("name", "mb_id");
                    userInfoInput.setAttribute("value", data.serverUserInfo.mb_id);

                    // append input (to form)
                    loginForm.appendChild(userInfoInput);

                    // append form (to body)
                    document.body.appendChild(loginForm);

                    // submit form
                    loginForm.submit();

                }else if(httpStatVal == 503){
                    alert("로그인 횟수 5회 초과");
                    return;
                }else if(httpStatVal == 500){
                    alert(data.enteredId + "라는 유저 정보가 없습니다. 다시 한번 확인해주세요.");
                    document.getElementById("mb_id").focus();
                    return;
                }else {
                    // 20240918
                    // 패스워드가 4회 이상 틀렸을때 경고 메세지 출력..
                    if(data.failCount > 4){
                        alert("패스워드가 4회 틀렸습니다. \n 5회 이상 틀릴시 계정이 잠깁니다.");
                        document.getElementById("mb_pw").focus();
                        return;
                    }else{
                        alert("패스워드가 틀렸습니다.");
                        document.getElementById("mb_pw").focus();
                        return;
                    }
                }
            }

        },
        error : function (e) {
            console.log("Error");
            console.log(e.responseText);
        }

    });


}