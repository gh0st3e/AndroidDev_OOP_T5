import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.util.ArrayList
import kotlin.math.log
import kotlin.math.sqrt

const val MAX_EXPERIENCE: Int = 5000

fun main(args: Array<String>) {
    //VariablesAndConstants()

    //callIsValid()

    //ShowHolidays()

    //doOperation(4,3,'+')

    //val numbers: IntArray = intArrayOf(1, 2, 5, 4, 3)
    //println(indexOfMax(numbers))
    //println(numbers.indexOfMaxExtension())

    //println("He".coincidence("Hello"))

    //println(rFactorial(6))

    //println(isPrime(113))

    //checkIsPrime()

    //listActions()

    //progressMap()
}



fun isValid(login: String, password: String) :Boolean{
    fun notNull() = login.length>0 && password.length >0
    if(!notNull()){
        println("Ежжи логин пустой или пароль пустой или логин и пароль пустой вводи букавы")
        return false
    }
    if(password.length<6 || password.length>12){
        println("Ежжи слишком маленький или слишком большой пароль от 6 до 12 символов вводи ежжи")
        return false
    }
    if(password.contains(' ')){
        println("Ежжи нельзя пробелы в пароле, заново вводи")
        return false
    }
    if(!login.contains('@')){
        println("Ежжи логин это почта введи @")
        return false
    }
    println("Ежжи все харашо, идем дальше")
    return true
}

fun VariablesAndConstants(){
    var intVar: Int = 19
    var DoubleVar: Double = 19.6
    var StringVar: String = "Hello World"
    // var - можем поменять значение после инициализации

    val intVal: Int = 42
    val DoubleVal: Double = 42.6
    val StringVal: String = "Hello Moon"
    //val - не можем поменять значение после инициализации

    val intString: String = "666"
    val changedVar: Int = intString.toInt()
    println(changedVar)

    var fromConsole = readLine()
    var isConstantInt: Int? = fromConsole?.toInt()
    println(isConstantInt)
}

fun callIsValid(){
    do{
        print("Ежжи ввади логин: ")
        var login: String? = readLine()
        print("Ежжи ввади пароль: ")
        var password: String? = readLine()
    }while(!isValid(login.toString(),password.toString()))
}

enum class Day(val holiday: String){
    NEWYEAR("New Year"),
    WOMANDAY("Woman Day"),
    VICTORYDAY("Victory Day"),
}

fun ShowHolidays(){
    var Date:String? = readLine()
    var re = Regex("[0-3][0-9][.][0-2][1-9]")
    if(!Date.toString().matches(re)){
        println("Это не дата ежжи")
    }
    when(Date){
        "31.01" -> println(Day.NEWYEAR.holiday)
        "08.03" -> println(Day.WOMANDAY.holiday)
        "09.05" -> println(Day.VICTORYDAY.holiday)
        "" -> println("Пустая строка ежжи")
        else -> println("Будний день, идем работать")
    }
}

fun doOperation(a:Int, b:Int, operation:Char){
    when(operation){
        '+' -> println(a+b)
        '-' -> println(a-b)
        '*' -> println(a*b)
        '/' -> println(a/b)
        '%' -> println(a%b)
        else -> throw Exception("Это не оператор ежжи")
    }
}

fun indexOfMax(a: IntArray): Int?{
    var max: Int = 0
    var maxIndex: Int = 0
    for(i in 1 until a.size){
        if(a[i]>max){
            max = a[i]
            maxIndex = i
        }
    }
    return maxIndex
}

fun IntArray.indexOfMaxExtension(): Int?{
    var max: Int = 0
    var maxIndex: Int = 0
    for(i in 1 until this.size){
        if(this[i]>max){
            max = this[i]
            maxIndex = i
        }
    }
    return maxIndex
} // Функция расширения

fun String.coincidence(str :String): Int?{
    var num: Int = 0
    for(ch in str){
        if(this.contains(ch)) num++
    }
    return num
}

fun factorial(n: Int): Double{
    var fact: Double = 1.0
    val range = 1..n
    for(i in range){
        fact*=i
    }
    return fact
}

fun rFactorial(n: Int): Double = if (n < 2) 1.0 else n * rFactorial(n - 1)

fun isPrime(value: Int): Boolean{
    for(i in 2..value-1 ){
        if(value%i==0) return false
    }
    return true
}

fun checkIsPrime(){
    var counter:Int = 0
    var arrayCounter = 0
    var firstElements = mutableListOf<Int>()
    val lastElements = arrayOfNulls<Int>(10) // [null, null, null]
    for(i in 2 until 10000){
        if(isPrime(i)) {
            if(counter<20){
                firstElements.add(i)
                counter++
            }
            else if (counter>=20 && arrayCounter<10 ){
                lastElements[arrayCounter] = i
                arrayCounter++
            }
        }

    }
    println(firstElements)
    for(i in 0..9) println(lastElements[i])

}

//fun containsIn(collection: Collection<Int>): Boolean = collection.any {x:Int-> if(collection.contains }

fun listActions(){
    val numbers : ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5)
    numbers.add(6)
    numbers+=7
    for(i in numbers){
        println(i)
    }
    val distinct = numbers.distinct().toList()
    println(distinct.filter(::isPrime))

    println(numbers.groupBy { it%2==0 })
    println(numbers.find { it<4 })

    // все элементы меньше 7?
    println(numbers.all { it < 7 }) // false
    // есть ли элемент больше 10?
    println(numbers.any { it > 10 }) // false

    val (num1,num2) = numbers
    println(num1)
    println(num2)
}

fun progressMap(){
    val Progress = mutableMapOf("Denis" to 38,"Pasha" to 1,"Stas" to 5, "Nikita" to 20, "Daria" to 40);

    var minusMark:Int = 0

    for(person in Progress){
        if(person.value==40)
            Progress.set(person.key,10)
        else if(person.value==39)
            Progress.set(person.key,9)
        else if(person.value==38)
            Progress.set(person.key,8)
        else if(person.value<=37 && person.value >=35)
            Progress.set(person.key,7)
        else if(person.value<=34 && person.value >=32)
            Progress.set(person.key,6)
        else if(person.value<=31 && person.value >=29)
            Progress.set(person.key,5)
        else if(person.value<=28 && person.value >=25)
            Progress.set(person.key,4)
        else if(person.value<=24 && person.value >=22){
            Progress.set(person.key,3)
            minusMark++
        }
        else if(person.value<=21 && person.value >=19) {
            Progress.set(person.key, 2)
            minusMark++
        }
        else if(person.value<=18 && person.value >=0){
            Progress.set(person.key,1)
            minusMark++
        }

    }
    for (person in Progress)
        println(person.key + " Оценка: " + person.value)

    println("Кол-во неудов = " + minusMark)
}