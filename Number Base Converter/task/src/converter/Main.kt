package converter

import java.math.BigDecimal
import java.math.RoundingMode

fun convertIntegerFromDecimal (number: String, targetBase: BigDecimal): String {
    var num = number.toBigDecimal()
    var result = ""
    var remainder: BigDecimal
    while (num > 0.toBigDecimal()) {
        remainder = num % targetBase
        if (remainder < 10.toBigDecimal()) result += remainder else result += 'a' + remainder.toInt() - 10
        num = num.divide(targetBase, 0, RoundingMode.DOWN)
    }
    return result.reversed()
}

fun convertFromDecimal(number: String, targetBase: BigDecimal): String {
    var result = ""
    var remainder: BigDecimal

    return if ("." in number) {
        val integerString = number.substringBefore('.')
        val fractionalNumber = "0.${number.substringAfter('.')}".toBigDecimal()
        var num = fractionalNumber
        repeat (5) {
            remainder = (num * targetBase).setScale(0, RoundingMode.DOWN)
            if (remainder < 10.toBigDecimal()) result += remainder else result += 'a' + remainder.toInt() - 10
            num = num * targetBase - remainder
        }
        "${convertIntegerFromDecimal(integerString, targetBase)}.$result"
    } else convertIntegerFromDecimal(number, targetBase)
}

fun convertIntegerToDecimal (number: String, sourceBase: BigDecimal) : String {
    var result = 0.toBigDecimal()
    val numIntegerString = number.lowercase()
    for (i in numIntegerString.indices) {
        result += if (numIntegerString[i] in '0'..'9') {
            numIntegerString[i].toString().toBigDecimal() * sourceBase.pow(numIntegerString.length - i - 1)
        }
        else (numIntegerString[i] - 'a' + 10).toString().toBigDecimal() * sourceBase.pow(numIntegerString.length - i - 1)
    }
    return result.toString()
}

fun convertToDecimal (number: String, sourceBase: BigDecimal): String {
    var result = 0.toBigDecimal()

    return if ("." in number) { //need to do 2 parts, a "." is present
        val integerString = number.substringBefore('.')
        val fractionalString = number.substringAfter('.')
        result += convertIntegerToDecimal(integerString, sourceBase).toBigDecimal()
        for (i in fractionalString.indices) {
            result += if (fractionalString[i] in '0'..'9') {
                fractionalString[i].toString().toBigDecimal().setScale(99)  / sourceBase.pow( i + 1)
            }
            else (fractionalString[i] - 'a' + 10).toString().toBigDecimal().setScale(99)  / sourceBase.pow(i + 1)
        }
        result.toString()
    } else {
        convertIntegerToDecimal(number, sourceBase)
    }
}

fun main() {
    while (true) {
        println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val input1 = readLine()!!
        if (input1 == "/exit") break else {
            val (num1, num2) = input1.split(" ")
            val sourceBase = num1.toBigDecimal()
            val targetBase = num2.toBigDecimal()
            while (true) {
                println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
                val num = readLine()!!
                val answer: String
                if (num == "/back")  break else {
                    answer = when {
                        sourceBase == targetBase -> num
                        sourceBase == 10.toBigDecimal() -> convertFromDecimal(num, targetBase)
                        targetBase == 10.toBigDecimal() -> if ('.' in num) {
                            convertToDecimal(num, sourceBase).toBigDecimal().setScale(5, RoundingMode.HALF_UP).toString()
                        } else convertToDecimal(num, sourceBase)
                        else -> {
                            convertFromDecimal(convertToDecimal(num, sourceBase), targetBase)
                        }
                    }
                }
                println("Conversion result: $answer")
            }
        }
    }
}