package com.pthw.mypagingthree.utils.ext

import java.text.DecimalFormat

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

fun String.setSeparator(format: String = "#,###.##"): String = DecimalFormat(format).format(this.toDouble())

fun Int.setSeparator(format: String = "#,###.##"): String = DecimalFormat(format).format(this.toDouble())

fun Double.setSeparator(format: String = "#,###.##"): String = DecimalFormat(format).format(this)



