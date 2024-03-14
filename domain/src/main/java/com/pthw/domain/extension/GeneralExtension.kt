package com.pthw.domain.extension

import java.text.DecimalFormat


fun String.toLanguageFormat(): String {
    return if (this.isNotEmpty()) {
        var value = this.replace("[", "")
            .replace("]", "")
            .replace("\"", "")

        if (value.equals("null", true)) {
            value = ""
        }
        value
    } else this
}

fun String.setSeparator(format: String = "#,###.##"): String {
    return runCatching {
        DecimalFormat(format).format(this.toDouble())
    }.getOrElse { "0" }
}

fun Int.setSeparator(format: String = "#,###.##"): String =
    DecimalFormat(format).format(this.toDouble())