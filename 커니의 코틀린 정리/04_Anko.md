<br><br>



<h1>Anko</h1>  

- 참고 도서 : 차세대 안드로이드 개발자를 위한 커니의 코틀린

- 작성자 : 최종원 

- 저자 블로그 : [https://www.androidhuman.com/](https://www.androidhuman.com/ "커니의 안드로이드")

- 예제 Github 주소 : [https://github.com/kunny/kunny-kotlin-book](https://github.com/kunny/kunny-kotlin-book/ "커니의 안드로이드 예제") 

  <br><br><br><br>

<h2>1. Anko Commons</h2> 

Anko Commons는 안드로이드 애플리케이션을 작성할 때 일반적으로 자주 구현하는 기능을 간편하게 추가할 수 있는 유틸리티 함수를 제공합니다. 먼저 라이브러리 사용을 위해 다음과 같이 설정합니다.

<br>

1. 프로젝트 창에서 **build.gradle (Module: app)** 파일을 엽니다.

2. dependencies 항목에 Anko 라이브러리를 추가합니다.

   ```kotlin
   dependencies{
       implementation "org.jetbrains.anko:anko:$anko_version"
   }
   ```

3. 프로젝트 창에서 **build.gradle (Project: ~)** 파일을 엽니다.

4. buildscript 블록에 Anko 라이브러리 버전을 변수에 지정합니다.

   ```kotlin
   ext.anko_version = '0.10.5'
   ```

   <br>![](https://i.imgur.com/kWT6fin.png)

5. `Sync Now`를 클릭하여 싱크합니다.

<br>

<br>

<h3>토스트 표시하기</h3>

`toast()` 및 `toastLong()`함수를 사용하면 **토스트 메세지를 표시**할 수 있습니다. 토스트는 Context 클래스의 인스턴스가 필요하므로, 이 클래스 혹은 이를 상속하는 **클래스 내부에서만 사용가능**합니다.

```kotlin
// 다음 코드와 동일한 역할을 합니다.
Toast.makeText(context, "Hello, Kotlin !", Toast.LENGTH_SHORT).show()
toast("Hello, Kotlin!")
```

<br>

<br>

<h3>다이얼로그 생성 및 표시하기</h3>

`alert()`함수를 사용하면 **AlertDialog**를 생성할 수 있으며, Context 클래스 혹은 이를 상속하는 클래스 내부에서만 사용할 수 있습니다.

```kotlin
// 다이얼로그의 제목과 본문 지정
alert(title = "Message", message = "Let's learn Kotlin!") {
    
    // AlterDialog.Builder.setPositiveButton()에 대응
    positiveButton("Yes") {
        toast("Yay!")
    }
    
    // AlterDialog.Builder.setNegativeButton()에 대응
    negativeButton("No") {
        longToast("Nope!")
    }
}.show()
```

<br>

![](https://i.imgur.com/zxUO0rE.png)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](https://i.imgur.com/vxBAqIE.png)

<br>

여러 항목 중 하나를 선택할 때 사용하는 **리스트 다이얼로그**는 `selector()`함수를 사용하여 생성할 수 있습니다.

```kotlin
//  다이얼로그에 표시할 목록 생성
val cities = listOf("Seoul", "Tokyo", "Mountain View", "Singapore")

// 리스트 다이얼로그를 생성하고 표시합니다.
selector(title = "Select City", items = cities) { dlg, selection ->
    // 항목을 선택했을 때 수행할 동작
    toast("you selected ${cities[selection]}!")
}
```

<br>

 **프로그레스 다이얼로그**는 파일 다운로드 상태와 같이 진행률을 표시해주는 `progressDialog()`함수와 진행률을 표시하지 않는 `indeterminateProgressDialog()` 함수를 사용하여 생성할 수 있습니다. 

```kotlin
// 진행률을 표시하는 다이얼로그 생성
val pd = progressDialog(title = "File Download", message = "Downloading...")

// 다이얼로그 표시
pd.show()

// 진행률 조정
pd.progress = 50

// 진행률을 표시하지 않는 다이얼로그 생성 및 표시
indeterminateProgressDialog(message = "Please wait...").show()
```

<br>

![](https://i.imgur.com/19C1j76.png)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](https://i.imgur.com/JrOIP4z.png)

<br>

<br>

<h3>인텐트 생성 및 사용하기</h3>

인텐트는 컴포넌트 간에 **데이터를 전달**할 때와 **액티비티나 서비스를 실행**하는 용도로 사용합니다.  `intentFor()`함수를 사용하면 다음과 같이 간소한 형태로 인텐트를 생성할 수 있습니다.

```kotlin
val intent = intentFor<DetailActivity>(
				// 부가 정보를 Pair 형태로 추가
				"id" to 150L, "title" to "Awesome item")

				// 인텐트 플래그 설정
				.noHistory()
```

 <br>

인텐트에 플래그를 설정하지 않는다면, **인텐트 생성과 컴포넌트 호출을 동시에 수행**할 수 있습니다. 이들 함수는 Context 클래스를 필요하므로, 해당 클래스 혹은 이를 상속하는 클래스 내부에서만 사용가능합니다.

```kotlin
// 부가정보를 포함하여 DetailActivity 실행
startActivity<DetailActivity>("id" to 150L, "title" to "Awesome item")

// 부가정보 없이 DataSyncService 실행
startService<DataSyncService>
```

<br>

이 외에도, 자주 사용하는 특정 작업을 바로 수행할 수 있는 함수들을 제공합니다.

```kotlin
// 전화를 거는 인텐트 실행
makeCall(number = "01012345678")

// 문자메세지를 발생하는 인텐트 실행
sendSMS(number = "01012345678", text = "Hello, kotlin!")

// 웹 페이지를 여는 인텐트 실행
browse(url = "https://google.com")

// 이메일을 발송하는 인텐트 실행
email(email = "cjw--@nate.com", subject = "Hello, Choi", text = "How are you?")
```

<br>

<br>

<h3>로그 메세지 기록하기</h3>

**AnkoLogger**를 사용하면 매번 태그를 함께 입력할 필요 없이 훨씬 편리하게 로그 메세지를 기록할 수 있습니다. AnkoLogger를 사용하려면 이를 사용할 클래스에서 **AnkoLogger 인터페이스**를 구현하면 됩니다.

```kotlin
class MainActivity: AppCompatActivity(), AnkoLogger {
    
    // Log.DEBUG 레벨로 로그 메세지 기록
    // String 타입이 아닌 인자는 해당 인자의 toString() 함수 반환값 기록
    error(180)
    ...
}
```

<br>

AnkLogger에서 제공하는 함수를 사용하여 로그 메세지를 기록하는 경우, **로그 태그로 해당 함수가 호출되는 클래스의 이름을 사용**합니다. 로그 태그를 바꾸고 싶다면 **loggerTag 프로퍼티를 오버라이드**합니다.

```kotlin
class MainActivity: AppCompatActivity(), AnkoLogger {
    
    // 이 클래스 내에서 출력되는 로그 태그를 "Main"으로 지정
    override val loggerTag: String get() = "Main"
    ...
}
```

<br>

<br>

<h3>단위 변환하기</h3>

Anko에서 제공하는 `dip()` 및 `sp()`함수를 사용하면 **해당 단위에서 픽셀 단위 양방향으로 매우 간단히 변환**할 수 있습니다. 이 함수들은 Context를 상속한 클래스 혹은 커스텀 뷰 클래스 내에서 사용할 수 있습니다.

```kotlin

```

<br>

<br>

<h3>기타</h3>

`configuration()` 함수를 사용하면 **특정 단말기 환경일 때에만 실행할 코드**를 간단하게 구현할 수 있습니다. 다음은 지정할 수 있는 단말기 환경 목록과 사용 예입니다.

| 매개변수 이름 | 단말기 환경 종류                  |
| ------------- | --------------------------------- |
| density       | 화면 밀도                         |
| language      | 시스템 언어                       |
| long          | 화면 길이                         |
| nightMode     | 야간 모드 여부                    |
| orientation   | 화면 방향                         |
| rightToLeft   | RTL 레이아웃 여부                 |
| screenSize    | 화면 크기                         |
| smallestWidth | 화면의 가장 작은 변의 길이        |
| uiMode        | UI 모드 (일반, TV, 차량, 시계 등) |

<br>

```kotlin
class MainActivity: AppCompatActivity() {
    fun doSomething() {
        configuration(orientation = Orientation.LANDSCAPE, language = "ko") {
            // 단말기가 가로 방향이면서 시스템 언어가 한국어일 때 수행할 코드 
        }
    }
}
```

<br>

단순히 단말기의 OS 버전에 따라 분기를 수행하는 경우 `doFromSdk()`와 `doIfSdk()`를 사용할 수 있습니다.

```kotlin
doFromSdk(Build.VERSION_CODES.O) {
    // 안드로이드 8.0 이상 기기에서 수행할 코드
}

doIfSdk(Build.VERSION_CODES.N) {
     // 안드로이드 7.0 기기에서만 수행할 코드
}
```

<br>

<br>

<br>

<br>

<h2>2. Anko Layouts</h2>

