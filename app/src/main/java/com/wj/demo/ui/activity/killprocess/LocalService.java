package com.wj.demo.ui.activity.killprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.wj.demo.inter.ProcessService;
import com.wj.library.helper.ToastHelper;
import com.wj.library.util.LogUtil;

/**
 * Created by idea_wj on 2017/2/16.
 */

public class LocalService extends Service {
    String TAG = LocalService.class.getSimpleName();

    private MyBinder binder;
    private MyConn conn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        if(conn==null)
            conn = new MyConn();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.bindService(new Intent(this,RemoteService.class),conn, Context.BIND_IMPORTANT);
        return super.onStartCommand(intent, flags, startId);
    }

    class MyBinder extends ProcessService.Stub{

        @Override
        public String getServiceName() throws RemoteException {
            return "LocalService";
        }
    }

    class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.e(TAG,"连接远程服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {  //连接异常时候会回调
            //此应用中代表，当远程服务RemoteService被干掉后，本地服务localService调用此方法
            ToastHelper.toastLong(LocalService.this,"远程服务被杀死");
            //远程服务被杀死后，两者之间的绑定关系会消失，需要重新绑定
            //重新启动远程服务，启动后，远程服务会重新绑定本地服务
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
            //本地服务需要重新绑定一次远程服务
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),conn, Context.BIND_IMPORTANT);
        }
    }
}
