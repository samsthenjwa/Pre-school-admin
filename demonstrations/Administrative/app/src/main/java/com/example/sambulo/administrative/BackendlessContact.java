package com.example.sambulo.administrative;


import android.app.Application;
import com.backendless.Backendless;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Mamutele on 2017-08-13.
 */

public class BackendlessContact extends Application{

    public static final String APP_ID="53696F5E-2136-5C06-FFD9-CEE6A1D59C00";
    public static final String API_KEY="71055735-0ED2-D9B0-FF31-91AE5B55D200";
    public  static final String SERVER_URL="http://api.backendless.com";


    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(), APP_ID, API_KEY);


    }
    public  static Date create(int day, int month, int year, int hourOfDay, int minute,int second){
        if(day==0&& month==0&&year==0)
            return null;
        Calendar calendar= Calendar.getInstance();
        calendar.set(year,month-1,day,hourOfDay,minute,second);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
}
