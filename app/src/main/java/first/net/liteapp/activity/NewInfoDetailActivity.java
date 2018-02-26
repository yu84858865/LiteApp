package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import first.net.liteapp.R;
import first.net.liteapp.view.TitleView;

/**
 * Created by 10960 on 2018/2/24.
 * 资讯详情
 */

public class NewInfoDetailActivity extends BaseActivity {

    private TitleView tv_titleview;
    private WebView webView;
    private TextView tv_loading;

    @Override
    public int getContentView() {
        return R.layout.activity_new_info_detail;
    }

    @Override
    protected void initView() {
        tv_titleview = findViewById(R.id.tv_titleview);
        webView = findViewById(R.id.webView);
        tv_loading = findViewById(R.id.tv_loading);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setData() {
        final String webUrl = "https://www.baidu.com/";
        WebSettings webSettings = webView.getSettings();
        webView.setWebChromeClient(new WebChromeClient());
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                tv_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tv_loading.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(webUrl);
                return true;
            }
        });
        webView.loadUrl(webUrl);

    }

    public static void startActivity(Context context, String title){
        Intent intent = new Intent(context,NewInfoDetailActivity.class);
        context.startActivity(intent);
    }

}
