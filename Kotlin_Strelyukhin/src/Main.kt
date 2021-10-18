import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

// Заполняет ArrayList случайными числами от 0.01 до 0.99
fun fill(k: Int): ArrayList<Double> {
    val arrList = ArrayList<Double>()
    for (i in 1..k) {
        arrList.add(element = ((Math.random() * 100).roundToInt() / 100.0))
    }
    return arrList
}

// Создает двумерный ArrayList с заданным количеством ArrayList и сортирует элементы в последнем
fun createAndSort(n: Int): ArrayList<ArrayList<Double>> {
    val arrList = ArrayList<ArrayList<Double>>()
    val masSize = TreeSet<Int>()
    // Заполняет TreeSet случайными числами - размерами ArrayList
    while (masSize.size < n){
        masSize.add(element = ((Math.random() * 20).roundToInt() + 1))
    }
    var i = 0
    masSize.forEach {
        arrList.add(fill(it))
        if (i % 2 == 0) {
            arrList[i].sort()
        }
        else {
            arrList[i].sortDescending()
        }
        i++
    }
    return arrList
}

// Запрашивает и проверяет пользовательский ввод
fun input(): Int{
    var input: Int? = null
    do {
        input = readLine()?.toIntOrNull()
        if (input != null) {
            input = input as Int
            if (input < 1) {
                println("The number of an arrays must be greater then zero")
            }
        }
        else {
            println("Input error, try again")
        }
    }
    while ((input == null) || (input < 1))
    return (input)
}

fun main(){
    println("Enter a number of arrays")
    val arrList = createAndSort(input())
    arrList.forEach {
        println(it)
    }
}