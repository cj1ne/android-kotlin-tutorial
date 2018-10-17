<br><br>  

<h1>Android Kotlin 정리</h1>  

- 참고 도서 : 오준석의 안드로이드 생존코딩 코틀린편
- 작성자 : 최종원 
- 예제 버전 : Android Studio 3.2 Preview
- 저자 Github : [https://github.com/junsuk5/kotlin-android](https://github.com/junsuk5/kotlin-android "오준석 Kotlin github")
  <br><br><br><br>  

<h2>Kotlin</h2>

<h3> REPL & Scratch</h3>

- Android Studio에서 제공하는 코드를 한 줄씩 실행하는 셸인 **REPL**을 사용하면 새로운 언어를 배울 때 직관적으로 실행시킬 수 있기 때문에 유용합니다.
  <br><br>![](https://i.imgur.com/n2jz1FX.png)<br>Tools → Kotlin → Kotlin REPL을 클릭하면 창이 표시되고 코드작성 후 **Ctrl + Enter**를 누르면 하단에 결과가 표시됩니다.<br>
  <br><br><br>
- REPL은 한 줄 단위 코드로 코드를 실행할 때는 편리하지만 복잡한 코드 테스트 때는 불편합니다. 이런 경우에는 **Scratch**를 사용합니다.
  <br><br>![](https://i.imgur.com/4AwhWCb.png)![](https://i.imgur.com/fnVYmzW.png)<br><br>
  File → New → Scratch File을 클릭하면 언어를 선택하는 창이 표시되고 **Kotlin**을 선택합니다. 에디터 창에 **scratch.kts** 파일이 열리면 여기서 자유롭게 Kotlin 코딩을 연습할 수 있습니다.
  <br><br><br><br>

<h3> Kotlin 문법 01</h3>

<h3>1. 기본 구문</h3>  

- **변수와 상수**
  <br><br>Kotlin에서 변수는 **var**로 상수는 **val**로 선언합니다. val은 자바의 **final** 키워드와 동일합니다.<br>

  ```
  var a:Int = 10 //var 변수명: 자료형 = 값
  val b:Int = 10 //val 상수명: 자료형 = 값  
  ```

  Kotlin은 자료형을 지정하지 않아도 추론하기 때문에 **생략할 수 있습니다.**<br>

  ```
  var a = 10 // 변수 var a:Int  
  val b = 10 // 상수 val b:Int (재할당 불가)
  ```

  <br><br>

- **함수**
  <br><br>
  함수를 선언하는 방법은 다음과 같습니다.
  <br>`fun 함수명(인수1: 자료형, 인수2: 자료형 … ): 반환자료형`<br><br>
  Kotlin에서 반환값이 없을 때 **Unit**형을 사용하며 이는 **Java의 void**에 대응합니다. 반환 값이 Unit인 경우에는 생략할 수 있습니다.<br>

  ```
  fun printKotlin(): Unit {
     println("hello kotlin!")
  }
  
  fun printKotlin() {     // 반환자료형 Unit 생략
     println("hello kotlin!")
  }
  ```

  <br><br><br>

<h3>2. 기본 자료형</h3>  

- **숫자형**<br><br>
  코틀린의 기본 자료형은 모두 객체입니다. Java가 프리미티브 자료형(int, double 등)과 객체 자료형으로 분류되는 것과는 다릅니다.<br><br>
  코틀린에서 숫자를 표현하는 자료형은 다음과 같으며 코틀린 컴파일러는 자료형을 추론합니다.



  - **Double** : 64비트 부동소수점  
  - **Float** : 32비트 부동 소수점  
  - **Long** : 64비트 정수  
  - **Int** : 32비트 정수  
  - **Short** : 16비트 정수

<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![](https://i.imgur.com/LKScuDe.png)<br><br><br>

- **문자형**
  <br><br>
  Kotlin에서 문자를 나타내는 자료형은 다음과 같이 두가지 이며 Char가 숫자형이 아니라는 점이 Java와 다릅니다.<br>

  ```
  String : 문자열
  Char : 하나의 문자
  ```

  <br>여러 줄에 걸쳐 문자열을 표현할 때는 큰따옴표 3개를 리터럴로 사용합니다.<br>

  ```
  val str = """오늘은
  	  날씨가 좋습니다.
  	  빨래를 합시다.
  	  """
  ```

  <br>문자열 비교는 ==을 사용하며 Java의 equls() 메서드와 대응합니다. **Java에서 == 를 오브젝트 비교** 시에 사용하는데 **Kotlin에서는 오브젝트 비교 시에는** `===` 을 사용합니다.<br>

  ```
  var str = "hello"
  if (str == "hello") {
  	 println("안녕하세요")
  } else {
    	 println("안녕 못 해요")
  }
  ```

  <br>

  문자열 템플릿 기능을 사용하면 `+` 기호로 문자열을 연결할 수 있고 `$ `기호를 사용해서 문자열 리터럴 내부에 변수를 쉽게 포함할 수 있습니다.<br>

  ```
  val str = "반갑"
  println(str + "습니다")          // Java와 동일
  
  // Kotlin
  println("$str 하세요")           안녕 하세요
  println("${str}하세요")          안녕하세요
  ```

  <br>

  <br>

- **배열**

   

  배열은 **Array**라는 별도의 타입으로 표현하며 `arrayOf( )` 메서드를 사용하여 생성과 초기화를 함께 수행합니다. 자료형을 유추할 수 있을 때는 이를 생략할 수 있으며 배열 요소에 접근하는 것은 `[ ]`를 이용합니다.

  ```
  val numbers:Array<Int> = arrayOf(1,2,3,4,5)
  val numbers2 = arrayOf(1,2,3,4,5)  // 자료형 생략
  numbers[0] = 5    // [5,2,3,4,5]
  ```

  <br><br>

  <br>

<h3>3. 제어문</h3>

  제어문은 크게 **if, when, for, while**이 있으며 if와 while의 경우 Java와 거의 동일하므로 여기서는 when과 for에 대해서만 다루도록 하겠습니다.

 

- **when**

  `when`은 Java의 **switch**에 해당합니다. 콤마와 in 연산자를 이용해 값의 범위를 자유롭게 지정할 수 있습니다.

  ```
  val x = 1
  
  when(x) {
      1 -> println("x==1")						// 값 하나인 경우
      2,3 -> println("x==2 or x==3")				// 여러 값은 콤마로 지정
      in 4..7 -> println("4부터 7사이")			 // in 연산자로 범위 지정
      !in 8..10 -> println("8부터 10사이가 아님")
      else -> {									// 나머지
          println("x는 1이나 2가 아님")
      }
  }
  ```

  <br>

  <br>

- **for**

  for문은 Java의 foreacn문과 비슷하며 `..` 연산자, `in` 키워드, `downTo` 키워드, `step` 키워드로 배열이나 컬렉션을 순회할 수 있습니다. 

  ```
  var numbers = arrayOf(1,2,3,4,5)
  
  for(num in numbers){
      println(num)   // 1; 2; 3; 4; 5
  }
  
  // 1~3까지 출력
  for(i in 1..3){
      println(i)     // 1; 2; 3; 
  }
  
  // 0~10까지 2씩 증가하며 출력
  for(i in 0..10 step 2){
      println(i)     // 2; 4; 6; 8; 10;
  }
  
  // 10부터 0까지 2씩 감소하며 출력
  for(i in 10 downTo 0 step 2){
      println(i)     // 10; 8; 6; 4; 2;
  }
  ```

  <br>

  <br>
  <br>

<h3>4. 클래스</h3>

- **클래스 선언**

  Java에서는 new 키워드로 객체를 생성하지만 Kotlin은 new 키워드를 사용하지 않습니다. 또한  Java는 변수를 선언만 해도 null이나 0으로 초기화해주었지만 Kotlin에서는  **반드시 초기화를 직접 해주어야 합니다.**

  ```
  // 클래스 선언
  class Shape {
  	var x:Int = 0
  	var y:Int = 0
  	var width:Int = 0
  	var height:Int = 0
  }
  
  // 인스턴스 생성
  val shape = Shape()
  ```

  <br>

  <br>

- **생성자**

  생성자는 `constructor` 키워드를 사용하여 표현하고 클래스명 옆에 선언한 생성자를 **기본생성자**라고 합니다. 기본생성자의 경우 constructor 키워드를 붙이지 않고도 표현할 수 있습니다.

  ```
  class Shape constructor(x:Int, y:Int) {
  	var x:Int = x
  	var y:Int = y
  	var width:Int = 0
  	var height:Int = 0
  }
  
  // 위의 코드와 동일
  class Shape(x:Int, y:Int) {
  	var x:Int = x
  	var y:Int = y
  	var width:Int = 0
  	var height:Int = 0
  }
  ```

  <br>

  기본 생성자 이외에도 다른 생성자들을 만들기 위해서는 constructor 키워드를 사용하여 체인형식으로 메서드처럼 선언합니다.

  ```
  class Shape(x:Int, y:Int) {
  	var x:Int = x
  	var y:Int = y
  	var width:Int = 0
  	var height:Int = 0
  	
  	// 체인형식 - 내가 만든 기본생성자를 호출
  	constructor(x: Int, y: Int, width: int, height: Int) : this(x,y) {
          this.width = width
          this.height = height
  	}
  }
  ```

  <br>

  Kotlin에서는 생성자 이외에도 init 블록에 작성한 코드가 클래스를 인스턴스화할 때 가장 먼저 초기화됩니다.

  ```
  class Shape(x: Int, y: Int) {
      var x: Int = x
      var y: Int = y
      var width: Int = 0
      var height: Int = 0
  
      init {
          println("생성자 호출")
      }
  
      constructor(x: Int, y: Int, width: Int, height: Int) : this(x, y) {
          this.width = width
          this.height = height
      }
  }
  ```

  <br>

  <br>

- **프로퍼티**

  클래스의 속성을 사용할 때는 멤버에 직접 접근하며 이를 **프로퍼티**라고 합니다. Java와 다르게 private 접근지정자로 은닉화된 멤버 변수에도 게터/세터 메서드 없이 프로퍼티로 대체할 수 있습니다.

  ```
  // 클래스 선언
  class Person(var name:String) {
      
  }
  
  // 인스턴스 생성
  val person = Person("멋쟁이")
  person.name = "키다리"   // 쓰기
  println(person.name)    // 읽기
  ```

  <br>

  <br>

- **접근 제한자**

  변수나 함수를 공개하는데 사용하는 키워드입니다. Kotlin에서 사용되는 접근 제한자는 Java와 거의 동일하며다음과 같습니다.

   

  - **public** (생략 가능) : 전체 공개이며 아무것도 쓰지 않으면 기본적으로 public입니다.

  - **private** : 현재 파일 내부에서만 사용할 수 있습니다.

  - **internal** : 같은 모듈 내에서만 사용할 수 있습니다.

  - **protected** : 상속받은 클래스에서 사용할 수 있습니다.


```
  class A {
      val a = 1	// public
      private val b =2
      protected val c = 3
      internal val d = 4
  }
```

  <br>

  <br>

- **클래스의 상속**

  Kotlin에서의 클래스는 기본적으로 상속이 금지되므로 상속이 가능하게 하려면 `open` 키워드를 클래스 선언 앞에 추가합니다.

  ```
  open class Animal {
      
  }
  
  class Dog : Animal() {
      
  }
  ```

  만약 상속받을 클래스가 생성자를 가지고 있다면 다음과 같이 상속받을 수 있습니다.

  ```
  open class Animal(val name: String) {
      
  }
  
  class Dog(name: String) : Animal(name) {
      
  }
  ```

  <br>

  <br>

- **내부 클래스**

  내부 클래스 선언에는 `inner` 키워드를 사용합니다. 내부 클래스는 외부 클래스에 대한 참조를 가지고 있으므로 아래 코드에서 inner 키워드가 없다면 a를 변경할 수 없습니다.

  ```
  class OutClass {
      var a = 10
      
      // 내부 클래스
      inner class OuterClass2 {
          fun something() {
              a = 20  // inner 키워드로 인해 접근 가능
          }
      }
  }
  ```

  <br>

  <br>

- **추상 클래스**

  추상 클래스는 미구현 메서드가 포함된 클래스를 말합니다. Java와 거의 동일하며 클래스와 미구현 메서드 앞에 `abstract` 키워드를 붙입니다. 추상 클래스는 직접 인스턴스화할 수 없고 다른 클래스가 상속하여 미구현 메서드를 구현해야합니다.

  ```
  abstract class A {
      abstract fun func()
      
      fun func2() {
          
      }
  }
  
  class B: A() {
      override fun func() {
          println("hello")
      }
  }
  
  var a = A()  // 에러
  var b = B()  // hello
  ```

  <br>

  <br>

  <br>
