<br><br>  

<h1>Android Kotlin 정리</h1>  

- 참고 도서 : 오준석의 안드로이드 생존코딩 코틀린편
- 작성자 : 최종원 
- 예제 버전 : Android Studio 3.2 Preview
- 저자 Github : [https://github.com/junsuk5/kotlin-android](https://github.com/junsuk5/kotlin-android "오준석 Kotlin github")
  <br><br><br><br>  

<h2>1. Android Studio 설정</h2> 

책에서는 Android Studio 설치 방법부터 자세하게 설명하지만 이는 타 블로그에서도 쉽게 찾을 수 있으므로 생략하고 Android Studio 설치 후 설정하면 유용한 **인코딩 설정**과 **패키지 자동 삭제**만 소개합니다.
<br/>

- **인코딩 설정**
  <br><br>
  Windows는 EUC-KR 계열 인코딩을 기본으로 사용하기 때문에 한글로 작성된 문장들이 깨져 보일 수 있습니다. 이를 위해 UTF-8로 변환합니다.
  <br><br>
  ​	
  1. Settings(Ctrl+Alt+S) → Editor → File Encodings
     <br><br>
     ![](https://i.imgur.com/bNDObwC.png)
     <br><br>
  2. Global Encoding, Project Encoding을 모두 UTF-8로 설정합니다.

<br><br>

- **패키지 자동 삭제**

  <br>기본 설정으로 패키지가 자동 import가 되지만 API 제거 시 import문을 자동으로 정리해주면 코드가 더 깔끔하게 유지 될 수 있습니다.

  <br>

  1. Settings(Ctrl+Alt+S) → Editor → General → Auto Import
     <br><br>
     ![](https://i.imgur.com/lRFUVhE.png)
     <br><br>
  2. **Add unambiguous import on the fly**와 **Optimize imports on the fly**를 모두 체크하여 반영합니다.
     <br><br><br><br><br>

<h2>2. 기기와 에뮬레이터 준비</h2>  

1장과 동일하게 Android 기기, 에뮬레이터를 실행하는 기본적인 내용은 생략하고 **Android Kotlin 프로젝트 생성 시 필요한 설정**과 에뮬레이터 실행 시 유용한 **한국어 설정**만 소개하도록 하겠습니다.
<br><br>

- **Android Kotlin 프로젝트 생성**
  <br><br>![](https://i.imgur.com/02AHV5Y.png)
  <br><br>
  프로젝트 생성 방법은 Java로 프로젝트를 생성하는 것과 대부분 동일하나 **Include Kotlin support**를 체크해주어야 합니다.
  <br><br><br>
- **에뮬레이터 한국어 설정**<br><br>에뮬레이터가 제공하는 기본 언어는 영어이므로 한국어로 설정하려면 다음의 순서를 따릅니다.![](https://i.imgur.com/bcw66UW.png)
  <br><br>
  Setting 앱 실행 → 제일 하단의 System → Language &input → Add a language → 한국어 → 대한민국 클릭 후 우측의 이동 아이콘을 드래그하여 **가장 위로 오도록** 이동합니다.
  <br><br><br><br><br>