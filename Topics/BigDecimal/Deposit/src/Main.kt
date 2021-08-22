import java.math.RoundingMode

fun main() {
    val a = readLine()!!.toBigDecimal()
    val b = readLine()!!.toBigDecimal()
    val c = readLine()!!.toInt()
    val ans = a * (1.toBigDecimal() + b.setScale(2 + b.scale()) / 100.toBigDecimal()).pow(c)
    println("Amount of money in the account: ${ans.setScale(2, RoundingMode.CEILING)}")
}