package ahm.parts.ordering.helper

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

open class CalendarUtils {
    companion object {
        fun getCurrentDate(format: String): String {
            val simpleDateFormatter = SimpleDateFormatter()
            //val sdf = SimpleDateFormat(format)

            val IDN = Locale("id", "ID", "ID")

            val sdf = SimpleDateFormat(format, IDN)

            return sdf.format(Date())
        }

        fun setFormatDate(OldDate: String, Oldformat: String, newFormat: String): String {
            try {
                val IDN = Locale("id", "ID", "ID")

                //var format = SimpleDateFormat(Oldformat, Locale.UK)
                var format = SimpleDateFormat(Oldformat, IDN)

                var newDate: Date? = null
                try {
                    newDate = format.parse(OldDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    newDate = null
                    return "-"
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    newDate = null
                    return "-"
                }

//            format = SimpleDateFormat(newFormat, Locale("in", "ID", "ID"))
                format = SimpleDateFormat(newFormat, IDN)

                return format.format(newDate)
            } catch (i: IllegalArgumentException) {
                return "-"
            }

        }


        fun sumRangeDate(strStartDate: String, strEndDate: String): Long {
            return sumRangeDate(strStartDate, strEndDate, SERVER_DATE_TIME_FORMAT)
        }


        fun sumRangeDate(strStartDate: String, strEndDate: String, format: String): Long {

            var format = SimpleDateFormat(format)

            try {
                var startDate = format.parse(strStartDate)
                var endDate = format.parse(strEndDate)
                var rangeInMili = (endDate.time - startDate.time)

                return TimeUnit.MILLISECONDS.toDays(rangeInMili)

            } catch (e: ParseException) {
                e.printStackTrace()
            }


            return 0
        }

        fun getDateFromPicker(date: Date): String {
            var strDate = ""

            strDate = setFormatDate(date.toString(), PICKER_DATE_FORMAT, SERVER_DATE_FORMAT)

            return strDate
        }


        fun thisYear(): String {
            val IDN = Locale("id", "ID", "ID")

            val formatter: DateFormat =
                SimpleDateFormat(" yyyy", IDN)
            val calendar = Calendar.getInstance()
            return formatter.format(calendar.time)
        }

    }


}

const val SERVER_PARAM_DATE_FORMAT = "dd-MM-yyyy"
const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val PICKER_DATE_FORMAT = "EEE MMM dd HH:mm:ss zz yyyy"
const val VIEW_DATE_FORMAT = "dd MMMM yyyy"
const val VIEW_DATE_SHORT_FORMAT = "dd MMM yyyy"
const val VIEW_DATE_TIME_FORMAT = "dd MMMM yyyy HH:mm"
const val FILE_DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss"