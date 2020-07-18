package com.bite.bite.api

import android.os.Build
import com.bite.bite.koin.KoinComponents
import com.bite.bite.utils.LogType
import okhttp3.*
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient


class LocaleatHttpClient {

    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor { chain ->

            KoinComponents.logger.log(
                "--> Sending request to "
                        + chain.request().url(), LogType.ApiCall
            )

            val buffer = okio.Buffer()
            chain.request().body()?.writeTo(buffer)

            KoinComponents.logger.log("Data: " + buffer.readUtf8(), LogType.ApiCall)

            val t1 = System.nanoTime()

            val request = chain.request().newBuilder()
//                    .addHeader("User-Agent", System.getProperty("http.agent")!!)
//                    .addHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4,uk;q=0.2,bg;q=0.2")
//                    .addHeader("Accept-Charset", "UTF-8")
//                    .addHeader("Content-type", "application/json")
//                    .addHeader("Application-version", BuildConfig.VERSION_NAME)
//                    .addHeader("Application-platform", "Android " + Build.VERSION.RELEASE)
//                    .addHeader("Application-IP", getIpAddress())
//                    .also {
//                        if (cookies.isNotEmpty() && cookiesEnabled && isUrlWithCookies(chain.request().url())) {
//                            it.addHeader("Cookie", cookies)
//                        } else {
//                            cookiesEnabled = true
//                        }
//                        if (token.isNotEmpty())
//                            it.addHeader("Token", token)
//                    }
                .build()

            val headersRequest = request.headers().names().map {
                "$it - ${request.header(it)}"
            }

            try {
                val response = chain.proceed(request)
                val t2 = System.nanoTime()

                /*
                    At first check if response could be parsed as json by checking
                    content type response header
                     */

                val rawResponse = response.body()?.string()

                KoinComponents.logger.log(
                    "<-- Got response from "
                            + chain.request().url() + " (" +
                            ((t2 - t1) / (1e6)).toInt() + " ms)", LogType.ApiCall
                )

                KoinComponents.logger.log("Raw: $rawResponse", LogType.ApiCall)
                if (response.body() == null || rawResponse == null) {
                    KoinComponents.logger.log("Received null body in response!", LogType.ApiCall)
                    return@addInterceptor Response.Builder()
                        .code(704)
                        .message("Null body")
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_1)
                        .body(ResponseBody.create(MediaType.parse("text"), ""))
                        .build()
                }
                return@addInterceptor response.newBuilder()
                    .body(ResponseBody.create(response.body()?.contentType(), rawResponse)).build()
            } catch (exception: ConnectException) {
                KoinComponents.logger.log(
                    "Timeout connect to ${chain.request().url()}",
                    LogType.ApiCall
                )
                return@addInterceptor Response.Builder()
                    .code(700)
                    .message("Couldn't connect to server, timeout")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.parse("text"), ""))
                    .build()
            } catch (exception: SocketTimeoutException) {
                KoinComponents.logger.log(
                    "SocketTimeoutException from ${chain.request().url()}",
                    LogType.ApiCall
                )
                return@addInterceptor Response.Builder()
                    .code(701)
                    .message("Socket timeout exception")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.parse("text"), ""))
                    .build()
            } catch (exception: IOException) {
                KoinComponents.logger.log(
                    "IO exception, probably no Internet ${chain.request().url()}",
                    LogType.ApiCall
                )
                return@addInterceptor Response.Builder()
                    .code(702)
                    .message("Couldn't connect to server, no Internet")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.parse("text"), ""))
                    .build()
            }
        }
        .build()

}