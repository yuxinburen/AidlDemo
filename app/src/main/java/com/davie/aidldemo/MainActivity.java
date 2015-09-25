package com.davie.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davie.aidlservice.IRemoteService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button btn_start;
    private Button btn_end;
    private TextView tv_show;

    /**
     * 远程服务代理对象
     */
    private IRemoteService mRemoteService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, " bind service connectted ");

            mRemoteService = IRemoteService.Stub.asInterface(service);

            try {
                String str = mRemoteService.getAbs(-5) + " . 第一次调用";
                Log.i(TAG, str);

                tv_show.append(str + "\n");

                Log.i(TAG, " the thread sleep 5000ms ");

                tv_show.append("the thread sleep 5000ms" + "\n");
                Thread.sleep(5000);

                str = mRemoteService.getAbs(-1000) + " . 第二次调用";
                Log.i(TAG, str);
                tv_show.append(str + "\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
            Log.i(TAG, " onServiceDisconnected ");
            tv_show.append("onServiceDisconnected"+"\n");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Log.i(TAG, " activity onCreate ");
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_end = (Button) findViewById(R.id.btn_end);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_start.setOnClickListener(this);
        btn_end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                Toast.makeText(MainActivity.this, " 点击了开始按钮 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.davie.aidlservice", "com.davie.aidlservice.RemoteService"));

                startService(intent);

                bindService(intent, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_end:
                unbindService(mConnection);
                break;
            default:
                break;
        }
    }
}
