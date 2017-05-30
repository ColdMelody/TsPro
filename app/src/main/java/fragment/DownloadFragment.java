package fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.Toast;

import network.DownloadCallback;
import network.DownloadTask;

/**
 * Created by Bill on 2017/4/12.
 * Email androidBaoCP@163.com
 */

public class DownloadFragment extends Fragment {
    public static final String TAG = "DownloadFragment";
    private static final String URL_KEY = "UrlKey";

    private DownloadCallback mCallback;
    private String mUrlString;
    private DownloadTask mDownloadTask;

    public static DownloadFragment getInstance(FragmentManager fragmentManager, String url) {

        DownloadFragment downloadFragment =
                (DownloadFragment) fragmentManager.findFragmentByTag(TAG);
        if (downloadFragment==null){
            downloadFragment = new DownloadFragment();
            Bundle bundle = new Bundle();
            bundle.putString(URL_KEY, url);
            downloadFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(downloadFragment, TAG).commit();
        }

        return downloadFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrlString = (String) getArguments().get(URL_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (DownloadCallback) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback = null;
    }
    public void startDownload(){
        cancelDownload();
        mDownloadTask = new DownloadTask();
        mDownloadTask.setCallback(mCallback);
        mDownloadTask.execute(mUrlString);
    }
    public void cancelDownload(){
        if (mDownloadTask!=null){
            mDownloadTask.cancel(true);
        }
    }
}
