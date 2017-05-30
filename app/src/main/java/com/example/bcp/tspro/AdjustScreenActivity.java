package com.example.bcp.tspro;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import bean.GlInfoBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import network.AirStores;
import network.GlInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdjustScreenActivity extends Activity {
    ImageView mIvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_screen);
        mIvShow = (ImageView) findViewById(R.id.show_dpi);
        setImage();
        getMessage();
        getGlInfo();
    }

    private void setImage() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Drawable> e) throws Exception {
                Drawable drawable = getResources().getDrawable(R.mipmap.fun, null);
                e.onNext(drawable);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Toast.makeText(AdjustScreenActivity.this,
                                "onSubscribe", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(@NonNull Drawable drawable) {
                        mIvShow.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getMessage() {
        Retrofit retrofit = new Retrofit.Builder()
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl("http://www.weather.com.cn/")
                .build();
        AirStores apiStores = retrofit.create(AirStores.class);
        Call<ResponseBody> call = apiStores.getWeather("101010100");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Cpeng", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Cpeng", t.getMessage());
            }
        });
    }
    private void getGlInfo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GlInfo glInfo = retrofit.create(GlInfo.class);
        Call<GlInfoBean> call = glInfo.getGlInfo("Guolei1130");
        call.enqueue(new Callback<GlInfoBean>() {
            @Override
            public void onResponse(Call<GlInfoBean> call, Response<GlInfoBean> response) {
                Log.i("Cpeng",response.body().getAvatar_url());
                Log.i("Cpeng",response.body().getStarred_url());
            }

            @Override
            public void onFailure(Call<GlInfoBean> call, Throwable t) {
                Log.i("Cpeng",t.getMessage());
            }
        });
    }

}
