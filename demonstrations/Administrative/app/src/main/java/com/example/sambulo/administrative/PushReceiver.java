package com.example.sambulo.administrative;

import com.backendless.push.BackendlessBroadcastReceiver;
import com.backendless.push.BackendlessPushService;

/**
 * Created by Mokgako on 2017/09/13.
 */

public class PushReceiver extends BackendlessBroadcastReceiver {
    @Override
    public Class<? extends BackendlessPushService> getServiceClass() {
        return BacklessReceiver.class;
    }
}
