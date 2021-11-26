package com.app.suricatos.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    const val DATE_TIME = "yyyy-MM-dd'T'00:00:00"
    const val DATE_MONTH_YEAR = "dd/MM/yyyy"

    fun parseStringToDate(date: String): String {

        val sdf2 = SimpleDateFormat(DATE_MONTH_YEAR)
        val result = sdf2.parse(date)

        val sdf = SimpleDateFormat(DATE_TIME)
        return (sdf.format(result))

    }


}