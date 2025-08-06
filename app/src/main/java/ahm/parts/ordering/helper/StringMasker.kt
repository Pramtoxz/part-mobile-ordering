package ahm.parts.ordering.helper

import android.content.Context
import android.content.res.Configuration
import java.text.DecimalFormat
import java.util.*

class StringMasker(private val currentLocale: Locale = Locale.US) {



    fun validateEmpty(value: String?): String {
        return if (value.isNullOrEmpty()) "-"
        else value
    }

    fun validateEmptyBlank(value: String?): String {
        return if (value.isNullOrEmpty()) ""
        else value
    }

    fun getInitial(name: String?): String {

        if (name.isNullOrEmpty()) return "-"

        var initial = ""

        val charArray = name.toCharArray()
        var initialAll = ""
        var prev = name[0]

        for (i in charArray.indices) {
            val cur = charArray[i]
            if (cur == ' ' && prev == ' ') {

            } else {
                initialAll += cur.toUpperCase()
            }
            prev = cur
        }


        for (word in initialAll.split(" ")) {
            initial += word[0].toUpperCase()
        }

        if (initial.length > 1) initial = "${initial[0]}${initial[initial.lastIndex]}"

        return if (initial.isEmpty()) "-" else initial
    }

    fun validateDigits(value: Float?): String {
        val format = DecimalFormat("0.#")
        return if (value == null) "0" else format.format(value)
    }

    fun addZero(value: Int): String {
        return if (value > 9) value.toString() else "0$value"
    }

    /**
     * with ,00
     */
    /*fun addRp(value: Long?): String {
        val v = value ?: 0L
        *//*val formatter = DecimalFormat("#,###")
        return "Rp ${formatter.format(v)},-"*//*
        return "Rp ${String.format(currentLocale, "%,d", v)}"
    }*/

    fun addRp(value: Long?): String {
        val v = value ?: 0L
        /*val formatter = DecimalFormat("#,###")
        return "Rp ${formatter.format(v)},-"*/
        return "Rp ${String.format(currentLocale, "%,.0f", v)}"
    }

    /**
     * with ,00
     */
    /*fun addRp(value: Double?): String {
        try {
            val v = value ?: 0.0
            *//*val formatter = DecimalFormat("#,###")
                return "Rp ${formatter.format(v)},-"*//*
            return "Rp ${String.format(currentLocale, "%,.2f", v)}"
        } catch (e: Exception) {
            return "Rp 0"
        }

        //val result = "Rp ${String.format(currentLocale, "%,.2f", v)}"

        *//*val format = DecimalFormat("0.#")
        return format.format(ddd)*//*

        //return result
    }*/


    fun addRp(value: Double?): String {
        val v = value ?: 0.0
        /*val formatter = DecimalFormat("#,###")
        return "Rp ${formatter.format(v)},-"*/
        return "Rp ${String.format(currentLocale, "%,.0f", v)}"

        //val result = "Rp ${String.format(currentLocale, "%,.2f", v)}"

        /*val format = DecimalFormat("0.#")
        return format.format(ddd)*/

        //return result
    }

    fun addRpNone(value: Long?): String {
        val v = value ?: 0L
        /*val formatter = DecimalFormat("#,###")
        return "Rp ${formatter.format(v)},-"*/
        return String.format(currentLocale, "%,d", v)
    }

    fun addRpNone(value: Double?): String {
        val v = value ?: 0.0
        /*val formatter = DecimalFormat("#,###")
        return "Rp ${formatter.format(v)},-"*/
        return String.format(currentLocale, "%,.2f", v)
    }

    fun getStringByLocal(context: Context, resId: Int, locale: String): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(locale))
        return context.createConfigurationContext(configuration).resources.getString(resId)
    }

    fun getStringByLocal(context: Context, resId: Int, add: String, locale: String): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(locale))
        return context.createConfigurationContext(configuration).resources.getString(resId, add)
    }
}