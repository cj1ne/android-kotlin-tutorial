 <br><br>  

<h1>Android Kotlin 정리</h1>  

- 참고 도서 : 오준석의 안드로이드 생존코딩 코틀린편
- 작성자 : 최종원 
- 예제 버전 : Android Studio 3.2 Preview
- 저자 Github : [https://github.com/junsuk5/kotlin-android](https://github.com/junsuk5/kotlin-android "오준석 Kotlin github")
  <br><br><br><br>

<h2>Kotlin 예제 - 비만도 계산기</h2>

 책에서는 레이아웃을 Design을 통해 구현하였지만 저는 Text를 통해 구현하였습니다. 앞으로의 모든 예제에서는 **UI 및 기능 명세만 정의**하고 공부를 위해 직접 구현해보시길 바랍니다. 구현에 필요한 **문법에 관해서는 아래에 정리**하였으며 어려움이 생길 경우에는 **BmiCalculator 소스코드**를 참고하시길 바랍니다.

<br>

<h3>UI 및 기능 명세</h3>

​    ![](https://i.imgur.com/1Si3Es7.png)           ![](https://i.imgur.com/RrZWa0e.png)

<br>

<h4>UI 명세</h4>

1. **메인화면**

   - 키 입력 (EditText)

     - **상단, 좌우 여백**  : 16

     - **ID** : heightEditText

     - **hint** : 키

       <br>

   * 몸무게 입력 (EditText)

     - **상단, 좌우 여백**  : 16
     - **ID** : weightEditText
     - **hint** : 몸무게 

     <br>

   - 결과 버튼 (Button)

     - **상단, 우측 여백**  : 16
     - **ID** : resultButton
     - **text** : 결과

     <br><br>

2. **결과화면**

   - 결과 문장 (TextView)

     - **상단 여백** : 130
     - **좌우 여백** : 16
     - **ID** : resultTextView

     <br>

   - 결과 이미지 (ImageView)

     - **가로** : 100
     - **세로** : 100
     - **ID** : imageView
     - **이미지 경로** : BMI에 따라 표정이 다른 이미지 경로로 설정

<br>

<br>

<h4>기능 명세</h4>

- 키와 몸무게를 입력하고 결과 버튼을 누르면 다른 홤녀에서 비만도 결과를 문자와 그림으로 보여줍니다.

- 마지막에 입력했던 키와 몸무게는 자동으로 저장됩니다. (SharedPreference)

- BMI 계산식은 다음과 같습니다.

  ```
  val bmi = weight / Math.pow(height / 100.0, 2.0)
  ```

<br>

<br>

<br>

<h3>Anko common 라이브러리 사용 준비</h3>

1. 프로젝트 창에서 **build.gradle (Module: app)** 파일을 엽니다.

2. dependencies 항목에 Anko 라이브러리를 추가합니다.

   ```
   dependencies{
       implementation "org.jetbrains.anko:anko:$anko_version"
   }
   ```

3. 프로젝트 창에서 **build.gradle (Project: ~)** 파일을 엽니다.

4. buildscript 블록에 Anko 라이브러리 버전을 변수에 지정합니다.

   ```
   ext.anko_version = '0.10.5'
   ```

   <br>![](https://i.imgur.com/kWT6fin.png)

5. `Sync Now`를 클릭하여 싱크합니다.

<br>

<br>

<br>

<h3>버튼 클릭 이벤트 리스너</h3>

xml 파일에서 버튼의 id가  `android:id="@+id/resultButton"`와 같이 설정되어 있다면 버튼 클릭 이벤트 리스너는 다음과 같이 설정할 수 있습니다. Java에 비해 코드가 간결해진 것을 확인할 수 있습니다.

```
resultButton.setOnClickListener{
    // 버튼이 클릭되면 할 일을 작성하는 부분
}
```

<br>

<br>

<br>

<h3>액티비티 전환</h3>

액티비티를 전환은 다음과 같이 수행할 수 있습니다. 현재 액티비티는 MainActivity이고  ResultActivity로 이동하는 것을 가정하겠습니다.

```
// Intent(현재 액티비티, 전환할 액티비티)

var intent = Intent(this, ResultActivity::class.java)
startActivity(intent)
```

<br>

하지만 자주 사용하는 코드 치고는 타이핑양이 적지 않습니다. 이 부분을 Anko 라이브러리를 사용하면 다음과 같이 간단하게 작성할 수 있습니다.

```
startActivity<ResultActivity>()
```

<br>

<br>

<br>

<h3>이전 화면으로 돌아가는 업 네비게이션</h3>

다른 화면으로 이동 후, 뒤로 가기를 눌러 이전 화면으로 이동할 수 있습니다. 하지만 화면상으로는 이전 액티비티와의 상관관계를 알 수 있는 어떠한 표시도 없는데 이럴 경우 **업 네비게이션**을 설정하여 상단 툴바에 뒤로가기 아이콘을 표시할 수 있습니다.

<br>

1. 프로젝트 창에서 **AndroidManifest.xml** 파일을 엽니다.

2. 두 번째 액티비티에 **parentActivityName** 속성을 추가합니다.

   ```
   // 부모 액티비티 지정
   <activity 
       android:name=".ResultActivity"	// 현재 액티비티
       android:parentActivityName=".MainActivity">  // 이전 액티비티(부모)
   </activity>
   ```

<br>

<br>

<br>

<h3>인텐트에 데이터 담기</h3>

인텐트는 데이터를 담아서 다른 액티비티에 전달하는 역할도 수행하며 코드는 다음과 같습니다.

```
var intent = Intent(this, ResultActivity::class.java)

// intent.putExtra(키, 값)
intent.putExtra("weight", weightEditText.text.toString())
intent.putExtra("height", heightEditText.text.toString())
startActivity(intent)
```

Anko를 적용하면 좀 더 간단히 작성할 수 있습니다.

```
startActivity<ResultActivity>(
      "weight" to weightEditText.text.toString(),
      "height" to heightEditText.text.toString()
)
```

<br>

<br>

<br>

<h3>인텐트에서 데이터 꺼내기</h3>

인텐트에서 데이터를 꺼내려면 `get~~Extra()` 메서드를 사용합니다. 전달받은 데이터가 문자열인 경우 `getStringExtra( )` 메서드를 사용합니다.

```
// intent.get~~Extra("키")
val height = intent.getStringExtra("height").toInt()
val weight = intent.getStringExtra("weight").toInt()
```

<br>

<br>

<br>

<h3>토스트</h3>

Java에서 사용하던 토스트 코드도 Kotlin에서 다음과 같이 사용이 가능합니다.

```
Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()
```

그러나 Anko를 적용하면 다음과 같이 줄일 수 있습니다.

```
toast("$bmi")
```

<br>

<br>

<br>

<h3>SharedPreference로 데이터 저장 및 불러오기</h3>

<br>

<h4>데이터 저장하기</h4>

```
private fun saveData(height: Int, weight: Int)
{
    val pref = PreferenceManager.getDefaultSharedPreferences(this) // (1)
    va  editor = pref.edit()               // (2)
        
    editor.putInt("KEY_HEIGHT", height)    // (3)
            .putInt("KEY_WEIGHT", weight)
            .apply()                       // (4)
}
```

1. PreferenceManager를 사용해 Preference 객체를 얻습니다.
2. Preference 객체의 Editor 객체를 얻습니다. 이 객체를 사용해 데이터를 담을 수 있습니다.
3. Editor 객체에 `putㅁㅁ` 메서드를 사용하여 키와 값 형태의 쌍으로 데이터를 저장합니다.
4. 설정된 내용을 반영합니다.

<br>

<br>

<h4>데이터 불러오기</h4>

```
private fun loadData()
{
    val pref = PreferenceManager.getDefaultSharedPreferences(this)  // (1)
    val height =pref.getInt("KEY_HEIGHT", 0)        // (2)
    val weight =pref.getInt("KEY_WEIGHT", 0)

    if(height != 0 && weight != 0) {                
        heightEditText.setText(height.toString())   
        weightEditText.setText(weight.toString())
    }
}
```

1. Preference 객체를 얻습니다.
2. `getㅁㅁ` 메서드를 사용하여 저장된 값을 불러옵니다. 두 번째 인자는 저장된 값이 없을 때 반환할 값을 의미합니다.