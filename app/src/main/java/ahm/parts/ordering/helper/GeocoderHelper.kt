package ahm.parts.ordering.helper

import android.content.Context

import ahm.parts.ordering.BuildConfig
import ahm.parts.ordering.R
import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import java.util.concurrent.TimeUnit

/**
 * Created by Mauricio Giordano on 1/21/16.
 * Author: Mauricio Giordano (mauricio.c.giordano@gmail.com)
 * Copyright (c) by Booze, 2016 - All rights reserved.
 */
object GeocoderHelper {
    private var service: Service? = null

    interface Service {
        @GET("json")
        fun parse(
            @Query("address") address: String,
            @Query("key") key: String,
            @Query("components") componenet: String
        ): Call<JsonElement>
    }

    private fun getService(): Service? {
        if (service == null) {
            val interceptors = HttpLoggingInterceptor()
            interceptors.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
            client.readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) client.addInterceptor(interceptors)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create(Service::class.java)
        }

        return service
    }

    interface OnLocationGathered {
        fun onGathered(location: Location?)
    }

    fun gatherFromAddress(
        context: Context,
        address: String,
        onLocationGathered: OnLocationGathered?
    ) {
        val parsed = GeocoderHelper.getService()?.parse(
            address,
            context.getString(R.string.api_google_2),
            "country:ID"
        )
        parsed?.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.body() == null) {
                    onLocationGathered?.onGathered(null)
                    return
                }
                val location = Location()
                val strJson = response.body()!!.toString()
                try {

                    Log.e("json location", strJson)

                    val json = response.body()!!.asJsonObject
                    val result = json.getAsJsonArray("results").get(0).asJsonObject
                    val address = result.getAsJsonArray("address_components")

                    for (i in 0 until address.size()) {
                        val obj = address.get(i).asJsonObject
                        val types = obj.getAsJsonArray("types")

                        for (j in 0 until types.size()) {
                            val type = types.get(j).asString

                            if (type == "street_number") {
                                location.number = obj.get("long_name").asString
                                break
                            } else if (type == "route") {
                                location.street = obj.get("long_name").asString
                                break
                            } else if (type == "postal_code") {
                                location.zipCode = obj.get("long_name").asString
                                break
                            }
                        }
                    }

                    val geometry = result.getAsJsonObject("geometry")
                    val loc = geometry.getAsJsonObject("location")

                    location.latitude = loc.get("lat").asDouble
                    location.longitude = loc.get("lng").asDouble
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                onLocationGathered?.onGathered(location)
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                onLocationGathered?.onGathered(null)
            }

        })

    }

    class Location {
        var street: String? = null
        var number: String? = null
        var zipCode: String? = null
        var description: String? = null
        var latitude: Double = 0.toDouble()
        var longitude: Double = 0.toDouble()

        override fun toString(): String {
            val jsonObject = JsonObject()
            jsonObject.addProperty("street", street)
            jsonObject.addProperty("number", number)
            jsonObject.addProperty("zipCode", zipCode)
            jsonObject.addProperty("latitude", latitude)
            jsonObject.addProperty("longitude", longitude)
            jsonObject.addProperty("description", description)

            return jsonObject.toString()
        }

        companion object {

            fun fromJson(jsonElement: JsonElement): Location {
                val location = Location()
                val jsonObject = jsonElement.asJsonObject

                location.street = jsonObject.get("street").asString
                location.number = jsonObject.get("number").asString
                location.zipCode = jsonObject.get("zipCode").asString
                location.latitude = jsonObject.get("latitude").asDouble
                location.longitude = jsonObject.get("longitude").asDouble
                location.description = jsonObject.get("description").asString

                return location
            }
        }

    }
}