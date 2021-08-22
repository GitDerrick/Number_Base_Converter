import java.math.RoundingMode

fun main() {
    val a = readLine()!!.toBigDecimal()
    val b = readLine()!!.toBigDecimal()
    val c = readLine()!!.toBigDecimal()
    println(((a + b + c) / 3.toBigDecimal()).setScale(0, RoundingMode.DOWN))
}