package appcore.com.bindigservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.ResultReceiver;

public class ResultReceiverService extends Service {

    ResultReceiver resultReceiver;

    public ResultReceiverService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //MyResultReceiver receiver = intent.getParcelableExtra("resultreceiver");
        ResultReceiver receiver = intent.getParcelableExtra("resultreceiver");

            for (int i = 0; i < 5; i++) {
                if (receiver != null)
                    receiver.send(0, null);
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
