package com.sinoangel.sazalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Z on 2017/4/6.
 */

public class AlarmServer extends Service {
    private AlarmManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AlarmConctrl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        AlarmUtils.outputLog("闹钟服务创建");
    }

    public class AlarmConctrl extends IAlarmAidlInterface.Stub {

        @Override
        public void setOnceAlarm(long id, long startTime) throws RemoteException {

            Intent intent = new Intent("SINOALARM_START");
            intent.putExtra("DATA", id);
//            intent.putExtra("TIME", startTime);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmServer.this, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
        }

        @Override
        public void setRepeatAlarm(long id, long startTime) throws RemoteException {
//            Intent intent = new Intent("SINOALARM_START");
//            intent.putExtra("DATA", id);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmServer.this, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            manager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
        }

        @Override
        public void cancelAlarm(long id) throws RemoteException {
            Intent intent = new Intent("SINOALARM_START");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmServer.this, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (pendingIntent != null)
                manager.cancel(pendingIntent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmUtils.outputLog("闹钟服务结束");
    }
}
