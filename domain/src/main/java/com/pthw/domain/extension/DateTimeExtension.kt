package com.pthw.domain.extension


import com.pthw.domain.utils.AppConstants
import com.pthw.domain.utils.AppConstants.INVESTIGATION_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.LOCAL_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.LOCAL_INVESTIGATION_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.LOCAL_PHARMACY_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.LOCAL_TIME_FORMAT
import com.pthw.domain.utils.AppConstants.MEDICAL_RECORD_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.ORDER_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.SERVER_DATE_FORMAT
import com.pthw.domain.utils.AppConstants.SERVER_TIME_FORMAT
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
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

private fun formatDate(
    formatter: DateTimeFormatter,
    parser: DateTimeFormatter,
    stringToFormat: String
): String {
    return runCatching {
        formatter.format(parser.parse(stringToFormat))
    }.getOrElse {
        stringToFormat
    }
}

private fun formatDateWithoutCatch(
    formatter: DateTimeFormatter,
    parser: DateTimeFormatter,
    stringToFormat: String
): String {
    return formatter.format(parser.parse(stringToFormat))
}

fun getCurrentDateForServerFormat(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT)
    return currentDate.format(formatter)
}

fun getEndDateForServerFormat(): String {
    val currentDate = LocalDate.now().minus(6, ChronoUnit.MONTHS)
    val formatter = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT)
    return currentDate.format(formatter)
}

fun String.toLocalTime(): String {
    val formatter = DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(SERVER_TIME_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}

fun String.toOrderTime(): String {
    val formatter = DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(ORDER_DATE_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}

fun String.toInvestigationDate(): String {
    val formatter = DateTimeFormatter.ofPattern(LOCAL_INVESTIGATION_DATE_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(INVESTIGATION_DATE_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}

fun String.toInvestigationTime(): String {
    val formatter = DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(INVESTIGATION_DATE_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}

fun LocalDate.formatToLocalDateString(): String {
    return format(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT, Locale.ENGLISH))
}


fun String.toServerDate(): String {
    val formatter = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT, Locale.ENGLISH)
    return formatDateWithoutCatch(formatter, parser, this.trim())
}

fun String.toServerTime(): String {
    val formatter = DateTimeFormatter.ofPattern(SERVER_TIME_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT, Locale.ENGLISH)
    return formatDateWithoutCatch(formatter, parser, this.trim())
}


fun String.toLocalDate(): String {
    val formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}

fun String.toMedicalRecordDate(): String {
    val formatter = DateTimeFormatter.ofPattern(MEDICAL_RECORD_DATE_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}

fun String.toDate(): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.parse(this)
}

fun String.calculateAge(): String? {
    val format = "yyyy-MM-dd"
    return try {
        val sf = SimpleDateFormat(
            format,
            Locale.ENGLISH
        )
        sf.isLenient = true
        val birthDate = sf.parse(this)
        val now = Date()
        var age = now.year - birthDate.year
        birthDate.hours = 0
        birthDate.minutes = 0
        birthDate.seconds = 0
        birthDate.year = now.year
        if (birthDate.after(now)) {
            age -= 1
        }
        age.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        "Unknown"
    }
}

fun String.toLocalPharmacyDate(): String {
    val formatter = DateTimeFormatter.ofPattern(LOCAL_PHARMACY_DATE_FORMAT, Locale.ENGLISH)
    val parser = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT, Locale.ENGLISH)
    return formatDate(formatter, parser, this.trim())
}