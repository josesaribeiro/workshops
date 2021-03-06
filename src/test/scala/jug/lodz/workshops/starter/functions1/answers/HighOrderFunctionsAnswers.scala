package jug.lodz.workshops.starter.functions1.answers

import jug.lodz.workshops.starter.functions1.answers.Exercise3.{DEBUG, ERROR, INFO}
import org.scalatest.{MustMatchers, WordSpec}

class HighOrderFunctionsAnswers extends WordSpec with MustMatchers {

  "EXERCISE1" should {
    "return functions from methods" in {
      def createAddPrefix(prefix: String): String => String = input => prefix + input
      def createComparatorToConstant(constant: Int): Int => Boolean = input => input > constant


      val f1 = createAddPrefix("prefix_")
      val f2 = createComparatorToConstant(7)


      f1("text") mustBe "prefix_text"
      f1("someOtherText") mustBe "prefix_someOtherText"
      f2(1) mustBe false
      f2(8) mustBe true

    }
  }


  "EXERCISE2" should {
    "custom andThen" in {
      //do without using andThen from function object
      def customAndThen(f1:Int=>String,f2:String => Exercise2Record) : Int => Exercise2Record =
          i => f2(f1(i))  //<- exercise

      //generate n a letters
      val f1:Int=>String= n => (1 to n).map(_=>"a").mkString
      val f2:String => Exercise2Record = Exercise2Record.apply

      val composed=customAndThen(f1,f2)

      composed(7) mustBe Exercise2Record("aaaaaaa")
      composed(3) mustBe Exercise2Record("aaa")

    }
  }

  //Complete method in Exercise3 object
  "EXERCISE3" should {
    "create function-logger" in {
      val logDebug=Exercise3.createLogger(DEBUG)
      val logInfo=Exercise3.createLogger(INFO)
      val logError=Exercise3.createLogger(ERROR)

      logDebug("text") mustBe "[DEBUG] text"
      logInfo("text") mustBe "[INFO] text"
      logError("text") mustBe "[ERROR] text"
    }
  }


  "EXERCISE4" should {
    "created curried and ucurried" in {
        val addInts: (Int,Int) => Int = (i1,i2) => i1+i2
        val intsCurried: Int => Int => Int = Exercise4.curry(addInts)
        intsCurried(2)(3) mustBe 5
        Exercise4.unCurry(intsCurried)(2,3)  mustBe 5

        val repeatString : (String,Long) => String = (s,n) => (1L to n).map(_ => s).mkString
        val repeatStringCurried: String => Long => String = Exercise4.curryGeneric(repeatString)
        val repeatTest: Long => String =repeatStringCurried("TEST")
        repeatTest(1) mustBe "TEST"
        repeatTest(2) mustBe "TESTTEST"
        repeatTest(4) mustBe "TESTTESTTESTTEST"

        Exercise4.unCurryGeneric(repeatStringCurried)("TEST",3L) mustBe "TESTTESTTEST"
    }
  }

}

case class Exercise2Record(value:String)

object Exercise3{
  sealed trait LogLevel
  case object DEBUG extends LogLevel
  case object INFO extends LogLevel
  case object ERROR extends LogLevel

  def createLogger(level:LogLevel) : String => String = level match {
    case DEBUG => s => s"[DEBUG] $s"
    case INFO => s => s"[INFO] $s"
    case ERROR => s => s"[ERROR] $s"
  }
}

object Exercise4 {
  def curry(f:(Int,Int)=>Int) : Int => Int => Int = a => b => f(a,b)
  def unCurry(f:Int => Int=>Int) : (Int,Int) => Int = (a,b) => f(a)(b)

  def curryGeneric[A,B,C](f:(A,B)=>C) : A => B => C = a => b => f(a,b)
  def unCurryGeneric[A,B,C](f:A => B => C) : (A , B) => C = (a,b) => f(a)(b)
}