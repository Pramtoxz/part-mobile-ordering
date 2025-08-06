package ahm.parts.ordering.injection

import ahm.parts.ordering.BuildConfig
import ahm.parts.ordering.api.ApiService
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.data.constant.Constants
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application, session: Session): OkHttpClient {

        val deviceModel =
            "Android-${Build.VERSION.SDK_INT}-${Build.VERSION.CODENAME}-${Build.MANUFACTURER}-${Build.MODEL}-${Build.DEVICE}"

        val interceptors = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptors.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()

                val accessToken = session.accessToken

                val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val isWifi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                } else {
                    cm.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
                } ?: false

              /*  val requestBuilder = original.newBuilder()
                    .header(
                        "User-Agent",
                        "${BuildConfig.APPLICATION_ID} ${BuildConfig.VERSION_NAME} $deviceModel WiFi:$isWifi"
                    )
                    .method(original.method(), original.body()) */

                val requestBuilder = original.newBuilder()
                    .header(
                        "User-Agent",
                        "PostmanRuntime/7.25.0"
                    )
                    .method(original.method(), original.body())


                if (accessToken.isNullOrEmpty()) {
                    val credentials =
                        Constants.DEFAULT.BASIC_AUTH_USER + ":" + Constants.DEFAULT.BASIC_AUTH_PASS
                    val basicAuth =
                        "Basic " + Base64.encodeToString(credentials.toByteArray(), NO_WRAP)
                    requestBuilder.header("Authorization", basicAuth)
                } else {
                    requestBuilder.header("Authorization", "Bearer $accessToken")
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(interceptors)
            .build()
    }

    /**
     * Mengirimkan Authorization header pada OkHttp
     */
    private class HeaderInterceptor(internal var token: String?) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .header("Authorization", token)
                .method(original.method(), original.body())
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }


}
