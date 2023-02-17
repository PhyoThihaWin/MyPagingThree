package com.pthw.appbase.extension

import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat

private const val CHAR_CODE = 4112
fun String.toMMNum() = this.fold("") { acc, c ->
    if (c in '0'..'9') {
        // acc + ((c.toInt() - '0'.toInt()) + '၀'.toInt())
        acc + (c.toInt() + CHAR_CODE).toChar()
    } else {
        acc + c
    }
}

fun Int.toMMNum() = this.toString().fold("") { acc, c ->
    if (c in '0'..'9') {
        // acc + ((c.toInt() - '0'.toInt()) + '၀'.toInt())
        acc + (c.toInt() + CHAR_CODE).toChar()
    } else {
        acc + c
    }
}

//todo replace with DateFormatter of java 8 time library
fun String.toDateOfBirth(): String {
    Timber.i("toDateOfBirth=$this")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val annDate = dateSdf.parse(this)

    return simpleDateFormat.format(annDate)
}

//todo replace with DateFormatter of java 8 time library
fun String.toNewDateOfBirth(): String {
    Timber.i("toDateOfBirth=$this")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateSdf = SimpleDateFormat("yyyy-MM-dd")
    val annDate = dateSdf.parse(this)

    return simpleDateFormat.format(annDate)
}

fun String.toLastSixDigitNrcCode(): String {
    var code = this
    code = code.substring(code.length - 6, code.length)
    return code
}

fun String.toLanguageFormat(): String {
    return if (this.isNotEmpty()) {
        var value = this.replace("[", "")
            .replace("]", "")
            .replace("\\t", "")
            .replace(",", "/")
            .replace("\"", "")

        if (value.equals("null", true)) {
            value = ""
        }
        value
    } else this
}

fun String.toAppointmentTypeForApiCall(): String {
    return if (this.uppercase() == "ONLINE") this.uppercase()
    else "INPERSON"
}


fun String.setSeparator(format: String = "#,###.##"): String {
    return runCatching {
        DecimalFormat(format).format(this.toDouble())
    }.getOrElse { "0" }
}

fun Int.setSeparator(format: String = "#,###.##"): String =
    DecimalFormat(format).format(this.toDouble())

fun Double.setSeparator(format: String = "#,###.##"): String = DecimalFormat(format).format(this)

fun <T> List<T>.toArrayList() = this.toCollection(ArrayList())

fun String.isAllUpperCase(): Boolean {
    var isUppercase = false
    forEach {
        isUppercase = it.isUpperCase()
    }
    return isUppercase
}

fun String.replaceLetterUppercase(): String {
    val stringBuilder = StringBuilder()
    forEachIndexed { i, s ->
        val modifiedChar = if (i == 0) {
            s
        } else {
            s.lowercaseChar()
        }
        stringBuilder.append(modifiedChar)
    }
    return stringBuilder.toString()
}

