<7.4>

maven dependency 이용해서 library 직접 add 안하게 하기
spring

- 라이브러리 평가 양식 - 
용량, 퍼포먼스, 가독성, 사용자 깃헙통계, 사용가능 언어, 결과물 형태, 오픈소스여부, 결제여부, 버전

- 네이버 캘린더 시간표 마크업 소스 확인 해서 로컬에서 바꿔보기
- spring 학습
- 최고 목표는 페이지의 일정 부분만 추출해서 프린트하게 하기

<7.5>
html to xhtml converter apply
플래시 동작 원리
- Jtidy?
목표 : 1. 네이버 캘린더 페이지 소스 export하여 로컬에서 테스트 == export완료/ htmlparser로 테스트시 한글, css 등 깨지고 원본에서 변형되어 변환됨
2. http url통한 변환 기능 구현
3. 간단한 웹페이지 작성 및 깃헙 공유
4. 일정부분 (이미지 파일이나 텍스트 등) 만 가져와서 변환 시켜보기( changing calendar css rules)

- 문제 -
0. 서버사이드에서 이미지로 변환하여 출력하게끔하기 - 이미지로 변환 라이브러리
1. 깔끔한 a4사이즈의 pdf로 변환하려면 결국 소스파일을 수정해야하는지? - 일정부분만 추출하여 렌더링하도록?
2. 시간표 기능이 출력버튼 누르고 팝업되는 뷰에서 볼 수 있는지?
3. google calendar 역시 pdf저장시 원본을 수정하여 변환시킴
4. wkhtmltopdf의 경우 jar file 존재하지 않아 web app에 integration 불가

발견
1. css2의 경우 적용가능/ css3에서 webkit, box-resizing등 브라우저에 dependent한 요소들은 인식 불가(@meaida rule, transition, -moz ..)

<7.6>

- 목표
1. java-html2image 이외 라이브러리 추가조사
2. opentohtml 한글 깨짐 해결하기, page split하기
3. spring 학습과 간단한 서버 구축

spring-boot로 간단한 웹서버 구축 / 보내주신 마크업 형식으로 변환하는 작업 / 인쇄 기능 - 여러페이지 분할해서 가능,  pdf 저장기능 , 프리뷰창 / 브라우저의 프린트 실행시 넘겨주는 데이터 형식( pdf , img)인지 알아야함

로컬 소스파일은 모두 변환 가능 / http url통해 로컬로 받게 하고 프린트 후 지우게 하기?

준영님 알려주신 서적 읽어보기

Thymeleaf 사용시 - html 태그에xmlns:th="http://www.thymeleaf.org" 추가해야함

application.properties에서 spring.thymeleaf.mode=LEGACYHTML5 추가하기
+ <dependency>
			<groupId>net.sourceforge.nekohtml</groupId>
			<artifactId>nekohtml</artifactId>
			<version>1.9.21</version>
		</dependency>
추가하기

<7.7>

1. convert.html을 만들고 requestmapping에서 javawrapper처리 - HtmltoPdf자바파일 만들고 링크의 경우 localhost로 써서 특정 html 변환처리하기

calendar public source : https://calendar.naver.com/publicCalendar.nhn?publishedKey=769d54e7f884c5f930b4ec86eb15dc38

<7.10>

목표
1. 프리뷰 인쇄 및 저장 범위 설정 옵션과 변환 경로 자동설정
2. 이미지 변환하여 프리뷰 미리보기에 뿌려주기
3. 인쇄 범위에 맞는 변환 저장 구현

window.location will kill all scripts currently running in the browser.

target directory의 소스들이 서버상에서 다루어지는 경로이고 파일들을 저장하게됨


인턴 자기평가 메일 확인

1. js - 페이지 개수 표시
2. 변환 시키기

-O landscape 해결

날짜범위 단위를 일단위로 바꿔야함
2014년 3월 캘린더 이미지가 고정적으로 되있음


<7.13>

- 테스트 해봐야할 것
1. 프로세스를 미리 생성한 후 명령어 부여해 변환시켜보기
2. 파일경로와 http경로로 로드 시 차이점
3. convert url의 경우 json data로 ajax콜하기
4. 월 범위 옵션 설정에 따른 프리뷰 이미지 변경
5.  사용자 식별값을 넣어주어 고유한 파일

Byweekly, iCalendar protocol에 따른 사용자 정보 읽어들이기
ical 데이터를 읽어들여서 일정 정보 프로세싱 byweekly/ Ical4J 조사해서 샘플/반복일정샘플 

리서치 화요일 발표 => 화요일 발표 

읽어들여서 마크업작업


<피드백>
- 파일 경로와 URL 경로로 프로세스를 실행했을 때의 속도를 비교하고 더 빠른 방법으로 일관성있게 코드 작성하기
- 멀티 쓰레드에 따른 이슈 고려하기
- CSS, image와 같은 정적 파일들을 모두 local에 저장하면 속도를 빠르게 할 수 있을듯
- 프로세스의 로딩 시간이 기니, 로딩을 미리 시켜놓는 방법에 대해 고려하기

<준영님 조언>
좀 너무 디테일해서 얘기 안했던게 있는데요,

path param으로 값을 넘기는건, 해당 호출이 실제로 디렉토리 구조를 갖지 않으면 일반적으로 쓰이는 방법은 아니에요.
단순히 여러 값을 넘기기 위해서라면 POST로 body에 key/value JSON을 담아서 전달해주는게 낫습니다.
path param과 비교해서 장점을 찾자면, 해당 value가 의미하는 바를 쉽게 알 수 있고 (keyㄷ가 있으니)
길이의 제약이 적고, 서비스의 request path 구조가 좀 더 간단해지고... 정도가 있겠네요. 

RESTful 웹서비스에 대한 얘기도 나오기 시작한지 꽤 됐고, 캘린더 서비스는 CalDAV (WebDAV을 기반으로 한 캘린더링 프로토콜)을 기반으로 설계되어있어서 나중에 여유가 되시면 이쪽도 한번 기웃거려보시길 추천합니다. 

어떤 방법을 쓰건 제대로 돌아가게 짜는게 제일 중요하니 나중에 고민하셔도 될 부분이기는 합니다 ㅎㅎ
최종 발표도 기대되네요 ㅎㅎ

<7.14>

지금 사용하는 버전: biweekly 0.3.3 ical4j 1.0.6
<iCalendar>
- an open standard for exchanging calendar and scheduling information between users and computers.
사용자와 서버간 스케줄링 정보 공유 규약

- iCalendar files typically have the file extension ".ical" ".ics" ".ifb"  or ".icalendar" with a MIME type of "text/calendar"
확장자명,  MIME 타입이란 클라이언트에게 전송된 문서의 다양성을 알려주기 위한 메커니즘

- iCalendar is used to import and synchronize events on various platforms, including smart phones, computer and web applications
다양한 플랫폼에서 이벤트 동기화와 import 가능케 해줌

- An iCalendar file consists of sections starting with "BEGIN:" and ending with "END:".  The "VCALENDAR" is the global section that holds all other sections.  Other sections include "VEVENT" for events, "VTODO" for to-do items, "VJOURNAL" for journal entries, and "VTIMEZONE" for time zone information.  Multiple sections of the same type can be repeated.  For example, multiple "VEVENT" sections can occur in an iCalendar file to describe multiple events.
모든 섹션은 비긴으로 시작해 엔드로 끝남, VCALENDAR는 글로벌 섹션으로 모든 섹션 포괄함, 같은 타입의 섹션 중복 가능(멀티 이벤트위함)

- WebDAV로부터 확장된 CalDAV는 원격서버에서 이벤트 관리 가능케함

- iCalendar 규약이 상세하게 정의되어있지만 확장가능하다. 그러나 벤더마다 지원 가능여부는 다를 것이다. 
예시로 html 마크업(폰트, 색상, 레이아웃..)도 X-ALT-DESC파라미터로 이벤트 포맷에 설정가능케함.

-"Organizer" refers to a role of a "Calendar User" within the scheduling protocol 

- All calendar components start with the letter "V", Scheduling methods defined by iTIP [2446bis] are referred to with capitalized, quoted-strings of text
모든 캘린더 컴포넌트들은  V 로 시작하고 모두  uppercase, 또한 스케줄링 메소드들도 모두 uppercase(REQUEST :캘린더 컴포넌트 생성이나 수정 요청하는 것 등등)

- a SEMICOLON is used to separate property parameters from each other and a COMMA character is used to separate property values in a value list

- WebDAV -
(WebDAV) is an extension of the Hypertext Transfer Protocol (HTTP) that allows clients to perform remote Web content authoring operations
The WebDAV protocol provides a framework for users to create, change and move documents on a server, typically a web server or web share

<biweekly>

- maven/gradle 빌드 툴 사용
- 간단한고 직관적인 api
- iCalendar 모든 명세 충족함
- xml, json 포맷 지원
- 모든 타임존 지원
- json type의 경우 포맷이 일반적인 json형식과 다름

<테스트 해볼것>
-간단한 웹서버 만들어서 사용자가 입력한 정보를 iCalendar 포맷으로 모델에 저장한 후 뷰에 다시 뿌려주기
- 로그인 기능 만들어 사용자 식별하여 정보 저장, 불러오기
- 그 후 현재 프로토타입에 적용시키기 - 불러온 사용자 캘린더 정보를 이용해 html재구성 

1. 입력 데이터 저장하는 모델에서 iCal포맷으로 저장






==Spring==

DI : dependency injection
dependency? : object와 object간의 의존성 - A라는 클래스에서 B라는 클래스의 메소드를 불러와 실행하게 될때 그것을 의존한다

만약 B클래스의 메소드가 바뀌면? -> A안에서도 마찬가지로 변경해줘야함 다수의 클래스가 그럴시 직접 변경 힘들어짐

따라서 유지보수의 어려움을 덜기위해 DI이용 - 3자(Service)가 만들어준 의존객체를 각 클래스에 뿌려주어 변경의 유연성 제공

service역할을 하는 설정파일(XML - applicationContext.xml)을 만들어 각 클래스에서 의존객체들을 주입받음(beans태그 안 객체관리 컨테이너에 설정)

Beans 저장위한 클래스파일에서 객체 생성 방법 : ServiceDao객체 생성

Bean? : 자주 사용하는 객체를 Singleton(인스턴스가 하나뿐인 클래스)로 생성해놓고 어디서든 불러서 쓸 수 있는 것을 Spring 에서 Bean 이라는 이름을 붙인 것
        , IoC(inversion of control)방식으로 관리하는 object임 = managed object

field에 대한 setter 필수 <- xml파일의 bean내에 property value속성 값을 주입시키기위해서 (constructor-arg, 생성자 통한 injection도 있음)

Ioc container(AbstractApplicationContext통한)는 xml에서 context파싱해서 정보 저장한 것 : 인스턴스 생성과 조립 담당 -> 여기서 값이 주입된 Bean 불러옴

spring-boot에서 resource의 static영역에서 img, css, js를 관리해줌 (html파일에서 link,img,src,script 경로 수정하기)

spring-boot에서는 되도록 jsp 사용자제- 표준이긴 하지만 web.xml등 따로 설정해야될 부분도 있고 html을 분리해 관리하기 불편

@Service도 @Conroller나 @Repository처럼 Spring에서 component scan의 대상으로 인지되는 애너테이션이다. 전형적인 스프링 어플리케이션에서는 @Controller가 붙은 클래스 -> @Service가 붙은 클래스 -> @Repository가 붙은 클래스로 요청의 흐름이 이어진다.

xml에서 bean설정(DI)을 자바코드로 하는것 -> @Configuration은 스프링에서 스프링 설정파일로 쓰일 클래스 ApplicationConfig(어노테이션만 위에 해주면 이름은 상관무)- 이안에 @Bean 어노테이션을 적용한 메소드(인스턴스를 반환해주는 )들을 만듬 -> 이 메소드들이 인스턴스를 만들고 컨트롤러에서 쓰일수 있게 함

AOP - 관점지향프로그래밍 : 다중상속이 불가하여 다양한 모듈에 상속기반 공통기능 부여에 한계가 있음 -> 공통기능, 핵심기능을 분리하고 핵심기능에 공통기능이 필요시 적용하는것( 공통기능은 재활용, 핵심기능은 바뀔수 있다는 것)

