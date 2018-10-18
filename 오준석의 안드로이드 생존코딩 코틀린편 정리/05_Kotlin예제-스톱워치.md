 <br><br>  

<h1>Android Kotlin 정리</h1>  

- 참고 도서 : 오준석의 안드로이드 생존코딩 코틀린편
- 작성자 : 최종원 
- 예제 버전 : Android Studio 3.2 Preview
- 저자 Github : [https://github.com/junsuk5/kotlin-android](https://github.com/junsuk5/kotlin-android "오준석 Kotlin github")
  <br><br><br><br>

<h2>Kotlin 예제 - 스톱워치</h2>

 책에서는 레이아웃을 Design을 통해 구현하였지만 여기에서는 Text를 통해 구현하였습니다. 예제에서는 **UI 및 기능 명세만 정의**하고 공부를 위해 직접 구현해보시길 바랍니다.  **구현에 필요한 내용은 아래에 정리**하였으며 어려움이 생길 경우에는 [StopWatch 소스코드](https://github.com/cj1ne/android-kotlin-tutorial/tree/master/%EC%98%A4%EC%A4%80%EC%84%9D%EC%9D%98%20%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%83%9D%EC%A1%B4%EC%BD%94%EB%94%A9%20%EC%BD%94%ED%8B%80%EB%A6%B0%ED%8E%B8%20%EC%A0%95%EB%A6%AC/05_Kotlin%EC%98%88%EC%A0%9C-%EC%8A%A4%ED%86%B1%EC%9B%8C%EC%B9%98)를 참고하시길 바랍니다.

<br>

<h3>UI 및 기능 명세</h3>

![](https://i.imgur.com/yLFqW2B.png)![](https://i.imgur.com/fKrudXa.png)

<br>

<h4>UI 명세</h4>

**메인화면**

1. 초 second 표시 (TextView)

   - **ID** : secTextView
   - **textAppearance** : AppCompat.Large
   - **textSize** : 100sp 

   <br>

2. 밀리 초 milli second 표시 (TextView)

   - **ID** : milliTextView
   - **textAppearance** : AppCompat.Large
   - **textSize** : 22sp

   <br>

3. 랩 타임 표시 (ScrollView)

   - **ID** : labLayout (ScrollView 내부의 LinearLayout의 ID)

   <br>

4. 초기화버튼 (FloatingActionButton)

   - **ID** : resetFab
   - **src** : ic_refresh_black_24dp
   - **backgroundTint** : colorAccent
   - **tint** : color/white
   - **왼쪽, 아래 여백** : 16

   <br>

5. 시작, 일시정지 버튼 (FloatingActionButton)

   - **ID** : playFab

   - **src** : ic_play_arrow_black_24dp(default), ic_pause_24dp

     *타이머 시작 시 일시정지 이미지로 바뀌고, 일시 정지 시 시작 이미지로 전환*

   - **backgroundTint** : colorPrimary

   - **아래 여백** : 16 

   - **tint** : color/white

   <br>

6. 랩 타임 기록 버튼 (Button)

   - **ID** : labButton
   - **textSize** : 랩 타임
   - **오른쪽, 아래 여백** : 16

   <br>

<br>

<br>

<h4>기능 명세</h4>

- 타이머를 시작, 일시정지하고 초기화할 수 있습니다.
- 타이머의 시간은 0.01초 단위로 변경되어 나타납니다.
- 타이머 실행 중에 랩 타임을 측정하여 표시할 수 있으며 최근의 랩 타임이 ScrollView 맨 위에 나타납니다.
- 타이머 초기화 시 랩 타임 정보가 사라지며 타이머 시간도 0으로 초기화됩니다.

<br>

<br>

<br>

<h3>Floating Action Button 생성</h3>



![](https://i.imgur.com/8I6gkJa.png)

<br>

이번 예제에서 초기화, 시작, 일시정지 버튼은 FAB를 사용하기 때문에 이를 사용하기 위해 Palette 창에서 Button 카테고리의 FloatingActionButton을 찾습니다. FAB는 기본 제공되는 컴포넌트가 아니기 때문에 다운로드를 받아야합니다. 빨간 네모 박스안에 있는 아이콘을 클릭하면 잠시 후 자동으로 deisgn 라이브러리를 추가하고 싱크합니다.

<br>

<br>

<h3>벡터 드로어블 하위 호환 설정</h3>

**FloatingActionButton** 버튼의 배경에 벡터 이미지를 사용합니다. 안드로이드 5.0 미만의 기기에서도 벡터 이미지가 잘 표시되도록 모듈 수준의 gradle 파일에 다음 코드를 추가합니다.

```kotlin
defaultConfig{
    vectorDrawables.useSupportLibrary = true
}
```

<br>

<br>

<h3>벡터 이미지 준비</h3>

이 예제에서 사용할 시작, 일시정지, 초기화 버튼에 총 3가지 이미지가 필요합니다. 먼저 다음의 순서를 통해 Asset Studio를 실행합니다.

```
프로젝트 창 res 폴더에서 마우스 우클릭 → New → Vector Asset
```

![](https://i.imgur.com/DeAQVdi.png)

<br>

Asset Studio에서 **Clip Art**를 클릭한 후에 `pause`, `play arrow`, `refresh`를 각각 검색한 후에 Next, Finish 버튼을 순서대로 클릭하여 벡터 이미지를 추가합니다. 추가한 이미지는 FloatingActionButton의 **src** 속성을 이용하여 버튼 이미지로 사용할 수 있습니다.

<br>

<br>

<h3>랩 타임을 표시하는 ScrollView</h3>

![](https://i.imgur.com/H4rHSJD.png) ![](https://i.imgur.com/baeJ1nB.png)

<br>

이 예제에서 랩 타임 버튼을 누를 때마다 타임 랩이 누적되어 **ScrollView**에 나타납니다. ScrollView는 자식을 하나만 가지는 특수한 뷰이며, 우리가 실제로 타임 랩을 추가할 곳은 ScrollView가 아니라 ScrollView 내부의 **LinearLayout**입니다. 따라서 위처럼 ScrollView 내부에 LinearLayout을 배치하고 여기에 ID를 작성합니다. 

<br>

<br>

<h3>타이머 구현</h3>

<h4>타이머 시작</h4>

```kotlin
private var time = 0  // 시간을 계산하는 변수
private var timeTask: Timer? = null // null을 허용하는 Timer

private fun start() {
    playFab.setImageResource(R.drawable.ic_pause_black_24dp)

    timerTask = timer(period=10){ // 0.01초마다 수행 (ms 단위)
        time++
        var sec = time / 100
        var milli = time % 100

        runOnUiThread({
            secTextView.text="$sec"
            milliTextView.text="$milli"
        })
    }
}
```

타이머가 시작되면 시작 버튼의 이미지를 일시정지 이미지로 변경하고 0.01초마다 시간을 1(0.01s) 증가시킵니다. 나중에 timer를 취소하기 위해 Timer 객체를 변수에 저장해둘 필요가 있습니다. 또한 timer는 워커 스레드에서 동작하여 UI 조작이 불가능하므로  변경된 시간을 **runOnUiThread**로 감싸서 UI조작이 가능하도록 합니다.

<br>

<h4>타이머 일시정지</h4>

```kotlin
private fun pause(){
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)

        timerTask?.cancel()
    }
```

타이머 시작과 반대로 버튼이 눌리면 버튼의 이미지를 시작버튼의 이미지로 변경하고, 실행 중인 타이머가 있다면 타이머를 취소합니다.

<br>

<h4>타이머 초기화</h4>

```kotlin
private fun reset(){~~
    timerTask?.cancel()

    // 모든 변수 초기화
    time = 0
    isRunning = false
    playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
    secTextView.text="0"
    milliTextView.text="00"

    // 모든 랩타임 제거
    labLayout.removeAllViews()
    lap = 1
}
```

실행 중인 타이머가 있다면 취소하고 모든 변수와 화면에 표시되는 모든 것을 초기화합니다.

<br>

<br>

<h3>동적으로 LinearLayout에 뷰 추가</h3>

ScrollView 내부에 있는 LinearLayout에 타임 랩을 추가 하기 위해 다음과 같이 코드를 작성합니다. 이 때 새롭게 생성된 타임 랩이 항상 ScrollView의 제일 위에 올 수 있도록 `addView( )`메서드의 **두 번째 인자**에 0을 추가합니다.

```kotlin
val textView = TextView(this)
textView.text = "LAB : ${lapTime/100}.${lapTime%100}"
lapLayout.addView(textView, 0)
```



