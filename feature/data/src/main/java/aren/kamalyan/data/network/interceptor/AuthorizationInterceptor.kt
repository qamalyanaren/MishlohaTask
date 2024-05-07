package aren.kamalyan.data.network.interceptor

import aren.kamalyan.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
            .build()
        return chain.proceed(newRequest)
    }
}