package first.net.liteapp.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import first.net.liteapp.constant.DataRequestResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 10960 on 2018/2/22.
 */

public class DataRequestUtils {

    private static final String TAG="DataRequestUtils";
    private static Handler mHandler = new Handler();
    private static OkHttpClient mOkHttpClient;

    public static synchronized OkHttpClient getInstance(){
        if (mOkHttpClient ==null){
            mOkHttpClient= new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
                @Override
                public void log(String message) {
                    Log.i(TAG,"message = "+message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return mOkHttpClient;
    }


    public static void get(final Context context, String reqUrl, final DataRequestResult dataRequestResult) {
//        final OkHttpClient mOkHttpClient=new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(reqUrl).get()
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","application/json")
                .addHeader("Connection", "close");
        Request request = requestBuilder.build();
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataRequestResult.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i(TAG, "cache---" + str);
                } else {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    Log.i(TAG, "network---" + str);
                    final String result = response.body().string();
                    Log.i(TAG, "result---" + result);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataRequestResult.onSuccess(result);
                        }
                    });
                }

            }
        });
    }

    public static void post(final Context context, String reqUrl, String params, final DataRequestResult dataRequestResult) {
//        OkHttpClient mOkHttpClient=new OkHttpClient();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(type,params);
        Request request = new Request.Builder()
                .url(reqUrl)
                .post(formBody)
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","application/json")
                .addHeader("Connection", "close")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataRequestResult.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.i(TAG, result);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dataRequestResult.onSuccess(result);
                    }
                });
            }
        });
    }




}
