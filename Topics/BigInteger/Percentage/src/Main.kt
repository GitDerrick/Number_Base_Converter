fun main() {
    val a = readLine()!!.toBigInteger()
    val b = readLine()!!.toBigInteger()
    println("${(a * 100.toBigInteger() / (a + b)).toInt()}% ${(b * 100.toBigInteger() / (a + b)).toInt()}%")
}