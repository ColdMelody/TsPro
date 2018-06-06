package com.example.bcp.tspro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import hardware.CustomCamera;


public class MainActivity extends Activity {

    private class Sample {
        private final CharSequence title;
        private final Class<? extends Activity> aClass;

        public Sample(int titleResId, Class<? extends Activity> activityClass) {
            this.title = getResources().getString(titleResId);
            this.aClass = activityClass;
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }

    private Sample[] samples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mLvToAcv = (ListView) findViewById(R.id.lv_to_acv);
        samples = new Sample[]{
                new Sample(R.string.surface_line_activity, SurfaceLineActivity.class),
                new Sample(R.string.anim_activity, AnimActivity.class),
                new Sample(R.string.service, ServiceActivity.class),
                new Sample(R.string.Client, ClientActivity.class),
                new Sample(R.string.music_player, PlayerActivity.class),
                new Sample(R.string.audio_recorder, AudioRecordActivity.class),
                new Sample(R.string.camera, CameraActivity.class),
                new Sample(R.string.custom_camera, CustomCamera.class),
                new Sample(R.string.sensor, SensorActivity.class),
                new Sample(R.string.camera2, Camera2Activity.class),
                new Sample(R.string.download, DownloadActivity.class),
                new Sample(R.string.xmlparser, XmlParserActivity.class),
                new Sample(R.string.socket, SocketActivity.class),
                new Sample(R.string.density, AdjustScreenActivity.class),
                new Sample(R.string.webview, WebviewActivity.class),
                new Sample(R.string.porterduff, PorterDuffActivity.class),
                new Sample(R.string.toolbar_demo,ToolBarActivity.class)};
        if (mLvToAcv != null) {
            mLvToAcv.setAdapter(new ArrayAdapter<>(this, R.layout.listview_content,
                    R.id.list_content, samples));
        }
        if (mLvToAcv != null) {
            mLvToAcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressWarnings("unchecked")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, samples[position].aClass);
                    startActivity(intent);
                }
            });
        }
    }
}
