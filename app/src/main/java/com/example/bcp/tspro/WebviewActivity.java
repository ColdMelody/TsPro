package com.example.bcp.tspro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebviewActivity extends Activity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        final String mimeType = "text/html";
        final String encoding = "utf-8";
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadData("<a href='x'>Hello World! - 1</a>", mimeType, encoding);
//        TextView mTvShowHTML = (TextView) findViewById(R.id.tv_show_html);
//        mTvShowHTML.setText(parseHTML());
        webView.addJavascriptInterface(new JavaScriptInterface(this),"Android");
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
//        webView.loadUrl("file:///android_asset/click.html");
        webView.loadUrl("https://www.baidu.com/");
    }

    private String parseHTML() {

        StringBuilder builder = new StringBuilder();
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
        String text = doc.body().text();
        Element element1 = doc.select("title").first();
        String title = element1.text();
        Element element2 = doc.select("p").first();
        String p = element2.text();
        return builder.append(text).append("\n").append(title).append("\n").append(p).toString();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 名称不重要，只是去象征意义
    class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().getHost().equals("https://www.baidu.com/")){
                // 拦截，自己应用打开连接
                return false;
            }
            // 不做拦截
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

}

