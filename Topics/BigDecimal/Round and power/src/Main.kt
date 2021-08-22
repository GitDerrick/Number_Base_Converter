import java.math.RoundingMode

fun main() {
    val power = readLine()!!.toInt()
    val mode = readLine()!!.toInt()
    val number = readLine()!!.toBigDecimal()
    println(number.setScale(mode, RoundingMode.FLOOR).pow(power))
}