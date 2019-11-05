package com.song.androidstudy.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IPCTestActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "IPCTestActivity";

    @BindView(R.id.btn_bind)
    Button bind;
    @BindView(R.id.btn_invoke)
    Button invoke;

    private boolean flag;
    private ITestAidlInterface myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipctest);
        ButterKnife.bind(this);

        bind.setOnClickListener(this);
        invoke.setOnClickListener(this);

        Log.e(TAG, "onCreate: myUid-->"+ Process.myUid());
        Log.e(TAG, "onCreate: myPid-->"+ Process.myPid());
        Log.e(TAG, "onCreate: myTid-->"+ Process.myTid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind:

                Intent intent = new Intent();
                intent.setClass(this, IPCService.class);
                intent.setAction("com.song.androidstudy.ipc.IPCService");

                flag = bindService(intent, new ServiceConnection() {

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        myService = null;
                    }

                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        myService = ITestAidlInterface.Stub.asInterface(service);
                    }

                    @Override
                    public void onBindingDied(ComponentName name) {
                        // 服务挂掉回调
                    }
                }, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_invoke:
                if (myService != null && flag) {
                    try {
                        // TODO 防止进程处理事件太长，导致ANR
                        // 如果是在子进程注册监听，回调回来是在Binder线程池处理，所以客户端需要在回调到主线程
                        String result = myService.getValue("我是客户端");
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//                        setTitle(result);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }
}
