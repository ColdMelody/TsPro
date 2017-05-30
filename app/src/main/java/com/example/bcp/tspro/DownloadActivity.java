package com.example.bcp.tspro;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;

import fragment.DownloadFragment;
import network.DownloadCallback;

public class DownloadActivity extends Activity implements DownloadCallback<String> {

    private boolean mDownLoading = false;
    private DownloadFragment mDownloadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Button mBtn = (Button)findViewById(R.id.blur);
        BlurMaskFilter filter = new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID);
        mDownloadFragment = DownloadFragment.getInstance(getFragmentManager(), "baidu.com");
        startDownload();
    }

    private void startDownload() {
        if (!mDownLoading && mDownloadFragment != null) {
            mDownloadFragment.startDownload();
            mDownLoading = true;
        }
    }

    @Override
    public void updateFromDownload(String result) {
        //在这里更新UI
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        // 获取当前网络信息
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch (progressCode) {
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
            default:
                break;
        }
    }

    @Override
    public void finishDownloading() {
        mDownLoading = false;
    }
}
