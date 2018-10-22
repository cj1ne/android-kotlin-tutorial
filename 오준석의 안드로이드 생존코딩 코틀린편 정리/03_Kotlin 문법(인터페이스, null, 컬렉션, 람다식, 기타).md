<br><br>  

<h1>Android Kotlin 정리</h1>  

- 참고 도서 : 오준석의 안드로이드 생존코딩 코틀린편
- 작성자 : 최종원 
- 예제 버전 : Android Studio 3.2 Preview
- 저자 Github : [https://github.com/junsuk5/kotlin-android](https://github.com/junsuk5/kotlin-android "오준석 Kotlin github")
  <br><br><br><br>  

<h2>Kotlin 문법 02</h2>

<h3>1. 인터페이스</h3>

**인터페이스**는 미구현 메서드를 포함하여 클래스에서 이를 구현합니다. 추상클래스와 비슷하지만 클래스가 단일 상속만 되는 반면 인터페이스는 **다중 구현**이 가능합니다. OnClickListener처럼 주로 클래스에 동일한 속성을 부여해 같은 메서드라도 다른 행동을 할 수 있게 하는데 사용합니다. 사용법은 Java와 거의 동일합니다.

<br>

- **인터페이스의 선언**

  인터페이스에 추상 메서드를 포함할 수 있으며 abstract 키워드를 생략할 수 있습니다.

  ```kotlin
  interface Runnable {
      fun run()
  }
  ```

  인터페이스는 구현이 없는 메서드뿐만 아니라 구현된 메서드도 포함할 수 있습니다.

  ```kotlin
  interface Runnable{
      fun run()
      
      fun fastRun() = println("빨리 달린다")
  }
  ```

  <br>

  <br>

- **인터페이스의 구현**

  인터페이스를 구현할 때는 인터페이스 이름을 콜론(:) 뒤에 나열합니다. 그리고 미구현 메서드를 작성하는데 이때 `override` 키워드 메서드 앞에 추가합니다.

  ```kotlin
  class Human : Runnable {
      override fun run() {
          println("달린다")
      }
  }
  ```

  <br><br>

- **상속과 인터페이스를 함께 구현**

  상속과 인터페이스를 함께 구현할 수 있습니다. 상속은 하나의 클래스만 상속하는 반면 인터페이스는 콤마로 구분하여 여러 인터페이스를 동시에 구현할 수 있습니다.

  ```kotlin
  open class Animal {
      
  }
  
  interface Runnable {
      fun run()
      
      fun fastRun() = println("빨리 달린다")
  }
  
  interface Eatable {
      fun eat()
  }
  
  class Dog : Animal(), Runnable, Eatable {
      override fun eat() {
          println("먹는다")
      }
      
      override fun run() {
          println("달린다")
      }
  }
  
  val dog = Dog()
  dog.run()
  dog.eat()
  ```

  <br>

  <br>

  <br>

<h3>2. null 가능성</h3>

Kotlin은 기본적으로 객체를 불변으로 보고 null값을 허용하지 않습니다. null값을 허용하려면 별도의 연산자가 필요하고 허용을 했더라도 별도의 연산자들을 사용하여 안전하게 호출해야 합니다.

<br>

- **null 허용?**

  Kotlin은 기본적으로 null값을 허용하지 않기 때문에 모든 객체는 생성과 동시에 값을 대입하여 **초기화해야** 합니다. Kotlin에서 null값을 허용하려면 자료형의 오른쪽에 **? 기호**를 붙여주면 됩니다.

  ```kotlin
  val a: String         // 에러 : 반드시 초기화 해야 함
  val a: String = null  // 에러 : Kotlin은 기본적으로 null 허용하지 않음
  val a: String? = null // OK
  ```

  <br>

  <br>

- **lateinit 키워드로 늦은 초기화**

  Android를 개발할 때 초기화를 나중에 해야할 경우가 있는데, 이 때 `lateinit` **키워드**를 변수 선언 앞에 추가하면 됩니다. lateinit을 사용 시 초기화를 잊지 않도록 주의해야 합니다.

  ```kotlin
  lateinit var a: String    // OK
  
  a = "hello"
  println(a)   //hello
  ```

  - var 변수에서만 사용합니다.
  - null값으로 초기화할 수 없습니다.
  - 초기화 전에는 변수를 사용할 수 없습니다.
  - Int, Long, Double, Float에서는 사용할 수 없습니다.

  <br>

  <br>

- **lazy로 늦은 초기화**

  lateinit이 var로 선언한 변수의 늦은 초기화라면 lazy는 값을 **변경할 수 없는 val**을 사용할 수 있습니다. val 선언 뒤에 by lazy 블록에 초기화에 필요한 코드를 작성합니다. 마지막 줄에는 초기화할 값을 작성합니다. **str이 처음 호출될 때** 초기화 블록의 코드가 실행됩니다.

  ```kotlin
  val str: String by lazy {
      println("초기화")
      "hello"
  }
  
  println(str)    // 초기화; hello
  println(str)    // hello
  ```

  - val에서만 사용합니다.

  <br>

  <br>

- **null값이 아님을 보증(!!)**

  변수 뒤에 `!!`를 추가하면 **null값이 아님을 보증**하게 됩니다. 다음과 같이 null값이 허용되는 name 변수의 경우 String? 타입이기 때문에 String 타입으로 변환하려면 !!를 붙여서 null값이 아님을 보증해야 합니다.

  ```kotlin
  val name: String? = "피노키오"
  
  val name2: String = name    // 에러
  val name3: String? = name   // OK
  val name4: String = name!!  // OK
  ```

  <br><br>

- **안전한 호출**

  메서드 호출 시 점(.) 연산자 대신 `?. `연산자를 사용하면 **null값이 아닌 경우**에만 호출됩니다.

  ```kotlin
  val str: String? = null
  
  var upperCase = if (str!=null) str else null  // null
  upperCase = str?.toUpperCase  // null
  ```

  <br>

  <br>

- **엘비스 연산자(?:)**

  안전한 호출 시 null이 아닌 기본값을 반환하고 싶을 때는 **엘비스 연산자**를 함께 사용합니다. 

  ```kotlin
  val str: String?=null
  
  var upperCase = if(str!=null) str else null    // null
  upperCase = str?.toUpperCase ?: "초기화하시오"   // 초기화하시오
  ```

  <br>

  <br>

  <br>

<h3>3. 컬렉션</h3>

컬렉션은 개발에 유용한 자료구조를 말하며 안드로이드 개발에서는 리스트나 맵이 자주 사용됩니다.

<br>

- **리스트**

  요소를 변경할 수 없는 읽기 전용 리스트는 `lisfOf( )` 메서드로 작성할 수 있습니다.

  ```kotlin
  val foods: List<String> = listOf("라면", "갈비", "밥")
  ```

  요소를 변경하는 리스트를 작성할 때는 `mutableListOf( )` 메서드를 사용합니다. Java와 다르게 특정 요소에 접근할 때 [ ] 로 접근할 수 있습니다.

  ```kotlin
  val foods = mutableListOf("라면", "갈비", "밥")
  
  foods.add("초밥")      // [라면, 갈비, 밥, 초밥]
  foods.removeAt(0)     //  [갈비, 밥, 초밥]
  foods[1] = "부대찌개"   // [갈비, 부대찌개, 초밥]
  ```

  <br>

  <br>

- **맵**

  맵은 키(key)와 값(value)의 쌍으로 이루어진 키가 중복될 수 없는 자료구조입니다. 리스트와 마찬가지로 `mapOf( )` 메서드로 읽기 전용 맵을 만들 수 있고, `mutableMapOf( )` 메서드로 수정 가능한 맵을 만들 수 있습니다.

  ```kotlin
  // 읽기 전용 맵
  val map = mapOf("a" to 1, "b" to 2, "c" to 3)
  
  // 변경 가능한 맵
  val citiesMap = mutableMapOf("한국" to "서울", "일본" to "동경", "중국" to "북경")
  
  // 요소에 덮어쓰기
  citiesMap["한국"] = "서울특별시"
  // 추가
  citiesMap["미국"] = "워싱턴"
  
  // 맵의 키와 값 탐색
  for((key,value) in map) {
      println("$key -> $value")
  }
  ```

  <br>

  <br>

- **집합**

  집합은 중복되지 않는 요소들로 구성된 자료구조입니다. `setOf( )` 메서드로 읽기 전용 집합을, `mutableSetOf( )` 메서드로 수정 가능한 집합을 생성합니다.

  ```kotlin
  // 읽기 전용 집합
  val citySet = setOf("서울", "수원", "부산")
  
  // 수정 가능한 집합
  val citySet2 = mutableSetOf("서울", "수원", "부산")
  citySet2.add("안양")        // [서울, 수원, 부산, 안양]
  citySet2.remove("수원")     // [서울, 부산, 안양]
  ```

  <br>

  <br>

  <br>

<h3>4. 람다식</h3>

람다식은 하나의 함수를 표현하는 방법으로 익명 클래스나 익명 함수를 간결하게 표현할 수 있어 매우 유용합니다. 아래와 같이 반환 자료형을 생략하고 블록과 return을 생략할 수 있습니다.

```kotlin
fun add(x: Int, y: Int): Int {
    return x + y
}

//위의 코드와 동일
fun add(x: Int, y: Int) = x + y
var add = {x: Int, y:Int -> x + y}  // {인수1: 타입1, 인수2: 타입2 -> 본문}
```

<br>

<br>

<br>

<h3>기타 기능</h3>

- **확장 함수**

  Kotlin은 확장 함수 기능을 사용하여 쉽게 기존 클래스에 함수를 추가할 수 있습니다. 확장 함수를 **추가할 클래스에 점을 찍고** 함수 이름을 작성합니다.

  ```kotlin
  fun Int.isEven() = this % 2 == 0
  
  val a = 5
  val b = 6
  
  println(a.isEven())   // false
  println(b.isEven())   // true
  ```

  <br>

  <br>

- **형변환**

  숫자형 자료끼리는 `to자료형( )` 메서드를 사용하여 형변환이 가능합니다.

  ```kotlin
  val a = 10L
  val b = 20
  
  val c = a.toInt()    // Long → Int 
  val d = b.toDouble() // Int → Double
  val e = a.toString() // Long → String
  ```

  숫자 형태의 문자열을 숫자로 바꿀 때는 Java와 동일하게 `Integer.parseInt( )` 메서드를 사용합니다.

  ```kotlin
  val intStr = "10"
  val str = Integer.parseInt(intStr)
  ```

  일반 클래스 간에 형변환을 하려면 `as` 키워드를 사용합니다.

  ```kotlin
  open class Animal
  
  class Dog : Animal()
  
  val dog = Dog()
  
  val animal = dog as Animal  // Dog → Animal형으로 변환
  ```

  <br>

  <br>

- **형 체크**

  `is` 키워드 사용하여 형을 체크할 수 있으며 이는 Java의 instanceOf에 해당합니다.

  ```kotlin
  val str = "hello"
  
  if(str is String) {
      println("This is String")
  }
  ```

  <br>

  <br>

- **고차 함수**

  Kotlin에서는 함수의 인수로 함수를 전달하거나 함수를 반환할 수 있습니다.

  ```kotlin
  // 인수 : 숫자, 숫자, 하나의 숫자를 인수로 하는 반환값이 없는 함수
  // callback에 x와 y의 합을 전달하면 callback은 하나의 숫자를 받고 반환이 없는 함수
  fun add(x: Int, y: Int, callback: (sum: Int) -> Unit){
      callback(x + y)
  }
  
  // 함수는 { }로 감싸고 내부에서는 반환값을 it으로 접근 가능
  add(5,3 { println(it)})   // 8
  ```

  <br>

  <br>

- **동반 객체**

  Kotlin에는 Java의 static 이 없습니다. Kotlin에서는 Java 의 static 함수와 같이 인스턴스 생성 없이 해당 클래스의 함수나 프로퍼티를 참조하기 위해서 사용하는 키워드가 `companion` 키워드입니다. `companion` 은 `object`와 함께 쓰입니다.

  ```kotlin
  class Fragment {
      companion object {
          fun newInstance() : Fragment {
              println("생성")
          }
      }
  }
  
  val fragment = Fragment.newInstance()
  ```

  <br>

  <br>

- **let ( ) 함수**

  `let ( )` 함수는 블록에 자기 자신을 인수로 전달하고 수행된 결과를 반환합니다. 인수로 전달한 객체는 it으로 참조합니다.

  ```kotlin
  // fun <T,R> T.let(block: (T) -> R): R
  var result = str?.let{
      Integer.parseInt(it)
  }
  ```

  <br>

  <br>

- **with ( ) 함수**

  `with ( )` 함수는 인수로 객체를 받고 블록에 리시버 객체로 전달합니다. 그리고 수행된 결과를 반환하며 리시버 객체로 전달된 객체는 this로 접근이 가능합니다.

  ```kotlin
  // fun <T,R> with(receiver: T, block T.() -> R): R
  with(str){
      println(toUpperCase())
  }
  ```

  <br>

  <br>

- **apply ( ) 함수**

  `apply ( )` 함수는 블록에 객체 자신이 리시버 객체로 전달되고 이 객체가 반환됩니다.

  ```kotlin
  // fun <T> T.apply(block: T.() -> Unit): T
  val result = car?.apply {
      car.setColor(Color.RED)
      car.setPrice(1000)
  }
  ```

  <br>

  <br>

- **run( ) 함수**

  1. 익명 함수 처럼 사용하는 경우 블록의 결과를 반환합니다.

     ```kotlin
     // fun <R> run(block: () -> R): R
     val avg = run {
         val korean = 100
         val english = 100
         val math = 50
         
         (korean + english + math) / 3.0
     }
     ```

  2. 객체에서 호출하는 경우 객체를 블록의 리시버 객체로 전달하고 블록의 결과를 반환합니다.

     ```kotlin
     // fun <T,R> T.run(block: T.() -> R): R
     str?.run{
         println(toUpperCase())
     }
     ```
