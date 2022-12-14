package com.pthw.domain.extension

import com.pthw.domain.utils.AppConstants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.dateTimeFormatter(parse: String, format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(parse, Locale.ENGLISH)
    return formatter.format(parser.parse(this.trim()))
}

fun LocalDate.dateTimeFormatter(format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
    return formatter.format(this)
}

fun String.dateParser(parse: String): LocalDate {
    val parser = DateTimeFormatter.ofPattern(parse, Locale.ENGLISH)
    return LocalDate.parse(this, parser)
}

private fun Date.dateFormatter(): String {
    val formatter = SimpleDateFormat(AppConstants.SERVER_DATE_FORMAT, Locale.getDefault())
    return formatter.format(this)
}

fun getLastWeekDate(): Array<String> {
    val calendar = Calendar.getInstance()
    val toDate = calendar.time.dateFormatter()
    calendar.add(Calendar.DATE, -7)
    val fromDate = calendar.time.dateFormatter()
    return arrayOf(fromDate, toDate)
}

fun getLastMonthDate(): Array<String> {
    val calendar = Calendar.getInstance()
    val toDate = calendar.time.dateFormatter()
    calendar.add(Calendar.MONTH, -1)
    val fromDate = calendar.time.dateFormatter()
    return arrayOf(fromDate, toDate)
}

fun Long.dateTimeFormatter(format: String): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date)
}
