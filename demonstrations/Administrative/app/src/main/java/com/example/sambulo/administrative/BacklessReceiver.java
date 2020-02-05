package com.example.sambulo.administrative;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.messaging.PublishOptions;
import com.backendless.push.BackendlessPushService;

/**
 * Created by Mokgako on 2017/09/13.
 */

public class BacklessReceiver extends BackendlessPushService {
    @Override
    public boolean onMessage(Context context, Intent intent) {

        String ticker_text= intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        String content_title= intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        String content_text= intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);


        String fromNotification= intent.getStringExtra("message");

        String[]token = fromNotification.split("# ");


        NotificationCompat.Builder nBuilder =new NotificationCompat.Builder(context);
        nBuilder.setSmallIcon(notificationIcon(nBuilder))
                .setTicker(ticker_text)
                .setContentTitle(content_title)
                .setContentText(content_text)
                .setAutoCancel(true);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(content_title);

        if(token.length > 1){
            for(int index=1 ;index < token.length; index++)
            {
                inboxStyle.addLine(token[index]);
            }
        }

        nBuilder.setStyle(inboxStyle);

       Intent bringBack= new Intent(context,Roles.class);
        bringBack.putExtra("content_title",content_title);
        bringBack.putExtra("content_text",content_text);


        TaskStackBuilder stackBuilder= TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(bringBack);

        PendingIntent pending =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pending);

        nBuilder.setFullScreenIntent(pending,false);


        NotificationManager notificationManager =  (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,nBuilder.build());

        return false;
    }
    private  int notificationIcon(NotificationCompat.Builder noteBuilder){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            noteBuilder.setColor(Color.YELLOW);
            return R.drawable.mesage;
        }
        else return R.drawable.mesage;
    }
}
