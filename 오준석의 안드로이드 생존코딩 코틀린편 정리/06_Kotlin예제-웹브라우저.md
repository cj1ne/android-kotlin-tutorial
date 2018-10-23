<br><br>  

<h1>Android Kotlin 정리</h1>  

- 참고 도서 : 오준석의 안드로이드 생존코딩 코틀린편
- 작성자 : 최종원 
- 예제 버전 : Android Studio 3.2 Preview
- 저자 Github : [https://github.com/junsuk5/kotlin-android](https://github.com/junsuk5/kotlin-android "오준석 Kotlin github")
  <br><br><br><br>

<h2>Kotlin 예제 - 웹 브라우저</h2>

 책에서는 레이아웃을 Design을 통해 구현하였지만 여기에서는 Text를 통해 구현하였습니다. 예제에서는 **UI 및 기능 명세만 정의**하고 공부를 위해 직접 구현해보시길 바랍니다.  **구현에 필요한 내용은 아래에 정리**하였으며 어려움이 생길 경우에는 [WebBrowser 소스코드](https://github.com/cj1ne/android-kotlin-tutorial/tree/master/%EC%98%A4%EC%A4%80%EC%84%9D%EC%9D%98%20%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%83%9D%EC%A1%B4%EC%BD%94%EB%94%A9%20%EC%BD%94%ED%8B%80%EB%A6%B0%ED%8E%B8%20%EC%A0%95%EB%A6%AC/06_Kotlin%EC%98%88%EC%A0%9C-%EC%9B%B9%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80)를 참고하시길 바랍니다.

<br>

<h3>UI 및 기능 명세</h3>

![](https://i.imgur.com/Olg4Jk8.png)![](https://i.imgur.com/fQdJ663.png)

<br>

<h4>UI 명세</h4>

**메인화면**

1. URL 입력  (EditText)

   - **ID** : urlEditText
   - **textAppearance** : AppCompat.Large
   - **상단, 좌우 여백** : 8dp
   - **hint** : http://
   - **imeOption** : actionSearch (키보드 내 검색 아이콘 사용 설정)
   - **inputType** : textUri 

   <br>

2. 웹뷰 (WebView)

   - **ID** : webView

   <br>


<br>

<br>

<h4>기능 명세</h4>

<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](https://i.imgur.com/FAR16qO.png)&nbsp;![](https://i.imgur.com/wOZ1Rfp.png)&nbsp;![](https://i.imgur.com/64PAWlv.png)

<br>

1. URL 입력창(EditText)의 주소로 검색 아이콘을 클릭 시 웹 페이지를 탐색합니다.

2. 홈 메뉴를 클릭하면 첫 페이지로 돌아옵니다.

3. 스마트폰의 뒤로 가기 버튼 클릭 시 최초 페이지까지 **페이지를 역행**합니다.

   <br>

   ![](https://i.imgur.com/0ZJGG8J.png)&nbsp;![](https://i.imgur.com/llzKJA4.png)&nbsp;![](https://i.imgur.com/XshZuXk.png)

   <br>

4. 옵션의 검색 사이트 아이템을 클릭하는 경우 **해당 사이트로 연결**합니다.

5. 옵션의 개발자 정보 아이템을 클릭하는 경우 개발자에게 **전화, 문자, 이메일**을 보낼 수 있습니다.

6. 웹뷰를 지속해서 누른 경우 **컨텍스트 메뉴**가 나타나며 현재 페이지를 공유하거나 기본 브라우저에서 열 수 있습니다.<br>

<br>

<br>

<h3>인터넷 권한 설정</h3>

안드로이드에서는 특정 권한이 필요한 동작을 할 때는 **권한을 추가해야**하며, 웹 뷰에 웹 페이지를 표시하려면 인터넷이 필요하므로 인터넷 사용 권한을 추가합니다. 프로젝트에서 **AndroidManifest.xml**파일을 열고 다음과 같이 권한을 추가합니다.

```kotlin
<manifest...>
	...
	  <uses-permission android:name="android.permission.INTERNET"/>
	...
</manifest>
```

<br>

<br>

<h3>웹뷰 기본 설정</h3>

```kotlin
webView.apply {
    settings.javaScriptEnabled = true
    webViewClient = WebViewClient()
}

webView.loadUrl("https://www.google.com")
```

웹뷰를 사용할 때 항상 기본으로 **javaScriptEnabled 기능 활성**과 **WebViewClient 클래스를 지정**을 해야 합니다. 그렇지 않으면 자바스크립트 기능이 작동하지 않거나 웹뷰에 페이지가 표시되지 않고 자체 웹 브라우저가 동작할 수 있습니다. `loadUrl()` 를 사용하여 Url을 전달하면 웹뷰에 페이지가 로딩됩니다.

<br>

<br>

<h3>키보드의 검색 버튼 동작 정의</h3>

```kotlin
urlEditText.setOnEditorActionListener { _, actionId, _ -> // (1)
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {       // (2)
        webView.loadUrl(urlEditText.text.toString())      // (3)
        true
    } else {
        false
    }
}
```

1. EditText의 **setOnEditorActionListener**는 EditText가 선택되고 글자가 입력될 때마다 호출됩니다. 인자로는 반응한 뷰, 액션 ID, 이벤트 세 가지며, 사용하지 않는 인자는 `_`로 대치할 수 있습니다.
2. actionId값은 상수로 정의된 값 중에 검색 버튼에 해당하는 상수와 비교하여 검색 버튼이 눌렸는지 확인합니다.
3. 검색 창에 입력한 주소를 웹뷰에 로딩하고 true를 반환하여 이벤트를 종료합니다.

<br><br>

<h3>뒤로가기 동작 재정의</h3>

```kotlin
override fun onBackPressed() {
    if(webView.canGoBack()){
        webView.goBack()
    } else {
        super.onBackPressed()
    }
}
```

액티비티에서 뒤로가기 키를 눌렀을 때 이벤트를 감지하고 재정의하려면 `onBackPressed()` 메서드를 **오버라이드**합니다. 위의 코드는 웹뷰가 이전 페이지로 갈 수 있다면 이전 페이지로 이동하고, 그렇지 않은 경우 원래 뒤로가기 키의 동작을 수행합니다.

<br><br>

<h3>옵션 메뉴 사용하기</h3>

<h4>1. 메뉴 리소스 파일 작성</h4>

menu 디렉터리를 우클릭 한 상태에서 `New → Android Resource Directory`를 클릭합니다.

![](https://i.imgur.com/P5jy4Uj.png)

Resource type을 **menu**로 선택하고 **OK**를 클릭합니다. 프로젝트 창을 보면 res 폴더 아래에 menu 디렉터리가 생성된 것을 확인할 수 있습니다.

<br>

![](https://i.imgur.com/DhuhD7B.png)

그 다음으로는 menu 디렉터리에서 마우스 우클릭 후 `New → Menu resource file`을 선택합니다. Source set과 File name에 **main**을 입력하고 **OK**를 클릭합니다. 그 다음 이전 예제에서다 다뤘던 [Asset Studio](https://github.com/cj1ne/android-kotlin-tutorial/blob/master/%EC%98%A4%EC%A4%80%EC%84%9D%EC%9D%98%20%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%83%9D%EC%A1%B4%EC%BD%94%EB%94%A9%20%EC%BD%94%ED%8B%80%EB%A6%B0%ED%8E%B8%20%EC%A0%95%EB%A6%AC/05_Kotlin%EC%98%88%EC%A0%9C-%EC%8A%A4%ED%86%B1%EC%9B%8C%EC%B9%98.md#%EB%B2%A1%ED%84%B0-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%A4%80%EB%B9%84)에서 **home**을 검색하여 벡터 이미지를 추가합니다. 

<br>

<h4>2. 메뉴 작성</h4>

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto">

<item android:title="검색 사이트">
    <menu>
        <item
            android:id="@+id/action_naver"
            android:title="네이버" />
        <item
            android:id="@+id/action_google"
            android:title="구글" />
        <item
            android:id="@+id/action_daum"
            android:title="다음" />
    </menu>
</item>
<item android:title="개발자 정보" >
    <menu >
        <item
            android:id="@+id/action_call"
            android:title="전화하기" />
        <item
            android:id="@+id/action_send_text"
            android:title="문자 보내기" />
        <item
            android:id="@+id/action_email"
            android:title="이메일 보내기" />
    </menu>
</item>
<item
    android:id="@+id/action_home"
    android:icon="@drawable/ic_home_black_24dp"
    android:title="Home"
    app:showAsAction="ifRoom" />
</menu>
```

앞서 생성한 **main.xml** 파일에 메뉴를 구성하기 위해서 위와 같이 코드를 작성합니다. [기능 명세](06_Kotlin예제-웹브라우저.md#기능-명세) 4), 5) 항목의 사진과 같이 **검색사이트**와 **개발자정보** **메뉴에** 각각 < 네이버, 구글, 다음 >과 < 전화하기, 문자 보내기, 이메일 보내기 >  아이템이 추가되도록 한 것 입니다.  마지막 아이템은  [기능 명세](06_Kotlin예제-웹브라우저.md#기능-명세) 2) 를 만들기 위한 것이며 **ifRoom** 속성은 공간에 여유가 있을 때 아이콘을 노출시키는 것을 의미합니다.

<br>

<h4>3. 옵션 메뉴 액티비티에 표시하기</h4>

액티비티에서 다음과 같이 `onCreateOptionsMenu( )` 메서드를 오버라이드하여 메뉴 리소스 파일을 지정하면 메뉴가 표시됩니다.

```kotlin
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
}
```

<br>

<h4>4. 옵션 메뉴 클릭 이벤트 처리</h4>

옵션 메뉴를 선택했을 때의 이벤트를 처리하려면 다음과 같이 `onOptionsItemSelected( )` 메서드를 오버라이드하여 메뉴 아이템의 ID로 구분해 처리합니다.

```kotlin
override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId) {
        R.id.action_google, R.id.action_home -> {
            webView.loadUrl("https://www.google.com")
            return true
        }
        R.id.action_naver -> {
            webView.loadUrl("https://m.naver.com")
            return true
        }
        R.id.action_daum -> {
            webView.loadUrl("https://m.daum.net")
            return true
        }
        R.id.action_call -> {
            // 전화 걸기
            return true
        }
        R.id.action_send_text -> {
            // 문자 전송
            return true
        }
        R.id.action_email -> {
            // 이메일 전송
            return true
        }
    }
    return super.onOptionsItemSelected(item)
}
```

<br>

<br>

<h3>컨텍스트 메뉴 사용하기</h3>

<h4>1. 컨텍스트 메뉴 리소스 파일 생성 및 작성</h4>

특정 뷰를 길게 클릭하면 메뉴가 표시되는 **컨텍스트 메뉴**의 리소스 파일 생성 및 작성 과정은 앞서 살펴 본 옵션 메뉴와 거의 동일합니다.

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/action_share"
        android:title="페이지 공유">
    </item>
    <item
    android:id="@+id/action_browser"
    android:title="기존 브라우저에서 열기">
    </item>
</menu>
```

menu 디렉터리에서 마우스 우클릭 후 `New → Menu resource file`을 선택합니다. File name에 **context**을 입력하고 **OK**를 클릭합니다. XML 파일이 생성되면 [기능 명세](06_Kotlin예제-웹브라우저.md#기능-명세) 6) 과 같이 메뉴를 구성하기 위해 위의 코드를 입력합니다.

<br>

<h4>2. 컨텍스트 메뉴 등록하기</h4>

액티비티에서 다음과 같이 `onCreateContextMenu( )` 메서드를 오버라이드하여 메뉴 리소스 파일을 지정하면 메뉴가 표시됩니다. 옵션 메뉴의 `onCreateOptionsMenu( )` 메서드와 메서드 이름에 약간의 차이가 있습니다.

```kotlin
override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context, menu)
}
```

그 다음 **컨테스트 메뉴가 표시될 대상 뷰를 지정**해야합니다. 이 예제에서 액티비티가 main 액티비티밖에 존재하지 않으므로 MainActivity.kt 파일의 onCreate() 메서드에 다음과 같은 코드를 추가합니다.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    ...
    ...
    // 컨텍스트 메뉴 등록
    registerForContextMenu(webView)
}
```

<br>

<h4>3. 컨텍스트 메뉴 클릭 이벤트 처리</h4>

옵션 메뉴의 클릭 이벤트 처리와 동일하게 `onContextItemSelected( )`메서드를 오버라이드하여 메뉴 아이템의 ID로 구분해 처리합니다.

```kotlin
override fun onContextItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId)
    {
        R.id.action_share -> {
            // 페이지 공유
            share(webView.url)
            return true
        }
        R.id.action_browser -> {
            // 기존 웹 브라우저에서 열기
            browse(webView.url)
            return true
        }
    }
    return super.onContextItemSelected(item)
}
```

<br>

<br>

<h3>암시적 인텐트</h3>

안드로이드에는 미리 정의된 인텐트들이 있고 이를 **암시적 인텐트**라고 합니다. 다음은 웹 브라우저에서 해당 url의 페이지를 여는 코드이며, 암시적 인텐트는 대부분 이러한 형태를 하고 있습니다.

```kotlin
var intent = Intent(Intent.ACTION_VIEW)
intent.data = Uri.parse("https://www.google.co.kr")
if (intent.resolveActivity(packageManager) != null) {
    startActivity(intent)
}
```

<br>

그러나, Anko 라이브러리를 사용하면 복잡한 암시적 인텐트 사용 코드를 **획기적으로 줄일 수 있으며** Anko에서 지원하는 암시적 인텐트 기능은 다음과 같습니다.

| 종류                 | 코드                                        |
| -------------------- | ------------------------------------------- |
| 전화 걸기            | makeCall(전화 번호) - 별도의 권한 추가 필요 |
| 문자 보내기          | sendSMS(전화번호, 보낼 문자)                |
| 웹 브라우저에서 열기 | browse(url)                                 |
| 문자열 공유          | share(문자열, [제목])                       |
| 이메일 보내기        | email(받는 메일 주소, 제목, 내용)           |

<br>
