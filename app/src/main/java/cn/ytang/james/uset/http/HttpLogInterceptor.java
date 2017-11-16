package cn.ytang.james.uset.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * retrofit 拦截
 *
 * Created by James on 17/11/16.
 */

public class HttpLogInterceptor implements Interceptor {

    private static final String TAG = "http";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String requestStartMsg = request.method() + ' ' + request.url();
        Log.i(TAG, requestStartMsg);

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = System.nanoTime() - startNs;
        Log.i(TAG, "request message: " + response.code() + ' ' + response.message()  + "(" + tookMs + "ms" + ")");

        return response;
    }
}
