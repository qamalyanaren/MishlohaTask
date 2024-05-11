package aren.kamalyan.data.network.interceptor

import android.content.Context
import aren.kamalyan.domain.extension.hasNetworkConnection
import okhttp3.Interceptor

class NetworkConnectionInterceptor(private val context: Context) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val newRequest = if (context.hasNetworkConnection()) {
            request.newBuilder().header("Cache-Control", "public, max-age=60").build()
        } else {
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}")
                .build()
        }
        return chain.proceed(newRequest)
    }
}