# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* BufferedReader.readLine() 메소드를 log에 찍을 때 사용하고 url를 얻기위해 또 사용했을때 /index.html를 얻는게 아닌 두번째 라인에 localhost:8080을 얻게되어서 wepapp에있는 index.html파일을 못읽었다.

### 요구사항 2 - get 방식으로 회원가입
* 테스트코드로 먼저 파싱하는것 해보고 적용해보았다.

### 요구사항 3 - post 방식으로 회원가입
* User객체에 값들 set했다. 요구사항에 부합하는건지 궁금하다.

### 요구사항 4 - redirect 방식으로 이동
* 302가 임시 리다이렉트라는데 그냥 헤더에서 200코드대신 302코드로 바꿔주는게 맞는건가싶다. 그리고 form action url를 index.html로만 바꿔주면 해당 요구사항에 맞게한건가도 궁금하다

### 요구사항 5 - cookie
* 삽질 후 해결

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 
