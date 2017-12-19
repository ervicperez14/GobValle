package com.ervic.mac.gobvalle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;


/**
 * Created by ervic on 12/6/17.
 */

public class MyfirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "FirebaseNotification";
    String titulo;
    String texto;
    long calID = 3;
    long startMillis = 0;
    long endMillis = 0;
    Calendar beginTime = Calendar.getInstance();
    Context context = this;
    String fecha, hora;
    char year[] = new char[4];
    char month[] = new char[2];
    char day[] = new char[2];
    char hours[] = new char[2];
    char minutes[] = new char[2];
    char seconds[] = new char[2];

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            titulo = remoteMessage.getNotification().getTitle();
            texto = remoteMessage.getNotification().getBody();

            Log.d(TAG, "NOTIFICACION RECIBIDA");
            Log.d(TAG, "Título: " + titulo);
            Log.d(TAG, "Texto: " + texto);
            Agregar();

            //showNotification(titulo,texto);
        }


        if (remoteMessage.getData() != null) {
            Log.d(TAG, "DATOS RECIBIDOS");
            Log.d(TAG, "Fecha " + remoteMessage.getData().get("fecha"));
            Log.d(TAG, "Hora " + remoteMessage.getData().get("hora"));

            fecha = remoteMessage.getData().get("fecha");
            fecha.getChars(6, 10, year, 0);
            fecha.getChars(3, 5, month, 0);
            fecha.getChars(0, 2, day, 0);


            hora = remoteMessage.getData().get("hora");
            hora.getChars(0, 2, hours, 0);
            hora.getChars(3, 5, minutes, 0);
            hora.getChars(6, 8, seconds, 0);
            Log.e("Year", String.valueOf(year));
            Log.e("Month", String.valueOf(month));
            Log.e("Day", String.valueOf(day));

            Log.e("Hour", String.valueOf(hours));
            Log.e("Minute", String.valueOf(minutes));
            Log.e("Second", String.valueOf(seconds));



            /**Calendar cal = Calendar.getInstance();

            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(day)));
            cal.set(Calendar.MONTH, Integer.parseInt(String.valueOf(month)) + 1);
            cal.set(Calendar.YEAR, Integer.parseInt(String.valueOf(year)));

            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(String.valueOf(hours)));
            cal.set(Calendar.MINUTE, Integer.parseInt(String.valueOf(minutes)));
            cal.set(Calendar.SECOND, Integer.parseInt(String.valueOf(seconds)));


            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
            values.put(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);

            values.put(CalendarContract.Events.ALL_DAY, false);
            values.put(CalendarContract.Events.RRULE, "FREQ=DAILY");
            values.put(CalendarContract.Events.TITLE, titulo);
            values.put(CalendarContract.Events.CALENDAR_ID,1);
            values.put(CalendarContract.Events.DESCRIPTION, texto);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                long eventID = Long.parseLong(uri.getLastPathSegment());
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = Long.parseLong(uri.getLastPathSegment());
**/
        }

    }
    private void showNotification(String title, String text) {

        String titulo = (title == null || title.isEmpty()) ? "Notificación importante" : title;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle(titulo)
                .setContentText(text)
                .setAutoCancel(true);

        Intent notIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent contIntent = PendingIntent.getActivity(getApplicationContext(), 0, notIntent, 0);

        notificationBuilder.setContentIntent(contIntent);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
    /**
    private void sendNotification(String messageTitle,String messageBody) {
        Intent intent = new Intent(this, DeliveryRecieved.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code *///, intent,PendingIntent.FLAG_UPDATE_CURRENT);
/**
        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_dart_board)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification *///, notificationBuilder.build());
        //getApplicationContext().startActivity(intent);
   // }
public void Agregar() {


    Calendar cal = Calendar.getInstance();


    boolean val = false; //Controlador del ciclo while
    Intent intent = null;
    while (val == false) {

        try {
            cal.set(Calendar.YEAR, Integer.parseInt(String.valueOf(year)));                 //
            cal.set(Calendar.MONTH, (Integer.parseInt(String.valueOf(month)) - 1));   // Set de AÑO MES y Dia
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(day)));       //


            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(String.valueOf(hours)));// Set de HORA y MINUTO
            cal.set(Calendar.MINUTE, Integer.parseInt(String.valueOf(minutes)));            //

            intent = new Intent(Intent.ACTION_EDIT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("vnd.android.cursor.item/event");

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);

            intent.putExtra(CalendarContract.Events.ALL_DAY, true);
            intent.putExtra(CalendarContract.Events.TITLE, titulo);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, texto);
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Prueba");

            startActivity(intent);
            val = true;
        } catch (Exception e) {
            //Log.e("CatchError","Fecha Inválida");
            Log.e("CatchError",e.toString());
            //Toast.makeText(getApplicationContext(), "Fecha Inválida", Toast.LENGTH_LONG).show();
        }
    }

}
}
