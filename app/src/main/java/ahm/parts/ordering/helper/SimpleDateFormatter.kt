package ahm.parts.ordering.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class SimpleDateFormatter{
    companion object {

        var newDate = Date()
        var newDateString = ""

        fun stringToDate(date: String, formatDate: String): Date {

            val df = SimpleDateFormat(formatDate, Locale.getDefault())
            try {
                newDate = df.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newDate
        }

        fun dateToString(date: Date, formatDate: String): String {
            val df = SimpleDateFormat(formatDate, Locale.getDefault())
            newDateString = df.format(date)
            return newDateString
        }
    }
}