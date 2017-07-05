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

