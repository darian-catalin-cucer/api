package cucerdariancatalin.api

import cucerdariancatalin.api.utils.DateUtils.DATE_FORMAT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkService {
    inline fun <reified T> createClientByService(
        baseUrl: String = "",
        okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
        moshiConverter: Boolean = false,
        isRx: Boolean = false
    ): T {
        val client = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
        if (moshiConverter)
            client.addConverterFactory(MoshiConverterFactory.create(moshiFactory()))
        else
            client.addConverterFactory(GsonConverterFactory.create(getDefaultGson()))

        if (isRx) client.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        return client.build().create(T::class.java)
    }

    fun moshiFactory(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun getDefaultGson(): Gson {
        return GsonBuilder()
            .setDateFormat(DATE_FORMAT).create()
    }
}