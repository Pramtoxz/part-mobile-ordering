package ahm.parts.ordering.helper

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.stock.CalendarRange
import android.app.Activity
import androidx.core.content.ContextCompat
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.Drawable
import android.content.Context


open class CalendarHelper {
    companion object {

        fun calendarInMonth(month: Int) : CalendarRange {
            var startCalendar = Calendar.getInstance()
            var endCalendar = Calendar.getInstance()

            if (month > 0) {

                startCalendar.set(Calendar.MONTH, month)
                endCalendar.set(Calendar.MONTH, month)

                startCalendar.set(Calendar.DAY_OF_MONTH, 1)
                endCalendar.set(
                    Calendar.DAY_OF_MONTH,
                    endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )

                return CalendarRange(startCalendar, endCalendar)
            }else{

                startCalendar.set(Calendar.DAY_OF_MONTH, 1)
                endCalendar.set(
                    Calendar.DAY_OF_MONTH,
                    endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )

                return CalendarRange(startCalendar, endCalendar)
            }
        }


        var timer: Timer? = null

        fun buildHorizontalCalendar(context : Activity, startCalendar:
        Calendar, endCalendar : Calendar, layoutId : Int,
                                    listener : (Calendar?) -> Unit) : HorizontalCalendar{

            val horizontalCalendar = HorizontalCalendar.Builder(context, layoutId)
                .range(startCalendar, endCalendar)
                .datesNumberOnScreen(6)
                .configure() // starts configuration.
                .formatTopText("EEEE")       // default to "MMM".
                .formatMiddleText("dd")    // default to "dd".
                .showTopText(true)              // show or hide TopText (default to true).
                .showBottomText(false)
                .colorTextTop(
                    ContextCompat.getColor(context, R.color.txt_black),
                    ContextCompat.getColor(context, R.color.white)
                )
                .textColor(
                    ContextCompat.getColor(context, R.color.txt_black),
                    ContextCompat.getColor(context, R.color.white)
                )
                .colorTextMiddle(
                    ContextCompat.getColor(context, R.color.txt_black),
                    ContextCompat.getColor(
                        context, (R.color.white)
                    )
                )
                .selectedDateBackground(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_fill_black_calendar_round
                    )
                ) // set selected date cell background.
                .end()          // ends configuration.
                .defaultSelectedDate(Calendar.getInstance())
                .build()

            horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
                override fun onDateSelected(date: Calendar?, position: Int) {
                    if(timer != null)
                        timer?.cancel()

                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            try {
                                listener(date)
                            } catch (e: ArrayIndexOutOfBoundsException) {
                                e.printStackTrace()
                            }
                        }
                    }, 1000)

                }

            }

            return horizontalCalendar
        }


        fun getThreeDots(context: Context): Drawable {
            val drawable = ContextCompat.getDrawable(context, R.drawable.indicator_calendar)

            //Add padding to too large icon
            return InsetDrawable(drawable, 100, 0, 100, 0)
        }
    }
}