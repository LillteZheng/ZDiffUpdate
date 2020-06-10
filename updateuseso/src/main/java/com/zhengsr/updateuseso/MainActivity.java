package com.zhengsr.updateuseso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.zhengsr.zdiffupdate.UpdateJni;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    public void test(View view) {
        new UpdateTask().execute();
    }

    class UpdateTask extends AsyncTask<Void,Void, File> {

        @Override
        protected File doInBackground(Void... voids) {
            //自己的apk 可以用这个
            // String sourceDir = getApplicationInfo().sourceDir;
            String sourceDir = "/data/app/com.zhengsr.diffupdate-1/base.apk";

            String patch = Environment.getExternalStorageDirectory().getAbsolutePath()+"/patch.patch";

            String newApk = Environment.getExternalStorageDirectory().getAbsolutePath()+"/new.apk";

            File file1 = new File(patch);
            File file2 = new File(newApk);

            //差分包建议在子线程中运行，防止阻塞主线程，这里是测试，所以没关系
            long time = System.currentTimeMillis();

            UpdateJni.diffUpdate(sourceDir,patch,newApk);

            return new File(newApk);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            //2、安装
            Intent i = new Intent(Intent.ACTION_VIEW);
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
            }else {
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String packageName = getApplication().getPackageName();
                Uri contentUri = FileProvider.getUriForFile(MainActivity.this, packageName+ ".fileProvider", file);
                i.setDataAndType(contentUri,"application/vnd.android.package-archive");
            }
            startActivity(i);
        }
    }
}
