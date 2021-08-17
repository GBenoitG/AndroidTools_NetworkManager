# AndroidTools_NetworkManager
Personal tools, used as a template in my personal apps to manage networks connections via Retrofit.

# Implementation

## Retrofit
Use the latest [Retrofit version](https://github.com/square/retrofit#download).
``` gradle
    //    Retrofit & Gson
    def retrofit_version = '2.9.0'
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
```

## OkHttp
Use the latest compatible [OkHttp version](https://github.com/square/okhttp#releases).
``` gradle
    //    OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
```

# NetworkManager
[NetworkManager](app/src/main/java/com/bendev/networkmanager/data/network/NetworkManager.kt) contains all network instances like OkHttp client and Retrofit client.
Those instances use constants values:
* `Constants.NETWORK_TIMEOUT_IN_S` -> `const val NETWORK_TIMEOUT_IN_S = 30L` 
* `Constants.GSON_DATE_FORMAT` -> `const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"`

``` kotlin
object NetworkManager {

    // TODO Add network services

    private val httpClient = OkHttpClient.Builder()
        .writeTimeout(Constants.NETWORK_TIMEOUT_IN_S, TimeUnit.SECONDS)
        .readTimeout(Constants.NETWORK_TIMEOUT_IN_S, TimeUnit.SECONDS)
        // Add network interceptor
        //.addNetworkInterceptor()
        .build()

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .setDateFormat(Constants.GSON_DATE_FORMAT)
        .create()

    private val retrofit = Retrofit.Builder()
        // TODO Set up base url
        //.baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

}
```

It is necessary to implement a `baseUrl` variable/constant with flavor or inside a constants file.

Then, implement each service as a lazy loaded variable from this template:
``` kotlin
    val myService: MyService by lazy {
        retrofit.create(MyService::class.java)
    }
```

## BaseDataSource
Here, we use DataSource implementations to call them into repositories layer. They extend [BaseDataSource](app/src/main/java/com/bendev/networkmanager/data/network/services/BaseDataSource.kt):
``` kotlin
abstract class BaseDataSource {

    protected suspend fun <T : Any> getResult(networkCall: suspend () -> Response<T>): Result<T> {
        try {
            val response = networkCall()
            if (response.isSuccessful) {
                val body = response.body()
                return Result.Success(body)
            }
            return error(NetworkError.fromCode(response.code()))
        } catch (e: Exception) {
            // TODO Add Timber dependency
            //Timber.e(e)
            return error(NetworkError.fromException(e))
        }
    }

    private fun error(errorType: NetworkError) : Result.Error {
        // TODO Add Timber dependency
        //Timber.w(errorType.toString())
        return Result.Error(errorType)
    }

}
```

We can find `getResult(...)` that return a sealed class [Result<T>](app/src/main/java/com/bendev/networkmanager/data/network/result/Result.kt) is returned. If there is an error/exception the function return a [NetworkError](app/src/main/java/com/bendev/networkmanager/data/network/result/NetworkError.kt) enum via the constructor `Result.Error(...)`. 
