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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 10960 on 2018/2/22.
 */

public class DataRequestUtils {

    private static final String TAG="http_";
    private static Handler mHandler = new Handler();

    public static void getAsynHttp(final Context context, String reqUrl, final DataRequestResult dataRequestResult) {
        final OkHttpClient mOkHttpClient=new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(reqUrl).get();
//        //可以省略，默认是GET请求
//        requestBuilder.method("GET",null);
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

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }

    private static void postAsynHttp(final Context context, String reqUrl, RequestBody formBody, final DataRequestResult dataRequestResult) {
        OkHttpClient mOkHttpClient=new OkHttpClient();
//        RequestBody formBody = new FormBody.Builder()
//                .add("size", "10")
//                .build();
        Request request = new Request.Builder()
                .url(reqUrl)
                .post(formBody)
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

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

        });
    }




}
