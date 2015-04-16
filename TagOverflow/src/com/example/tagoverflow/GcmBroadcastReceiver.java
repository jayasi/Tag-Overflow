package com.example.tagoverflow;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;


public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    String notification_text;
    public static String UserID;
    public static String UserInfo;
    String output = "";
    static int notifyID = 1;
    static int numMessages=0;
    String notif_text;

    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	Log.d("Receieved!!!", "asdsadas");
        
    	if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {

            try {
                notification_text = intent.getExtras().getString("gcm");
                Log.d("notifText",notification_text);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                return;
            }


            try {
                /*type = intent.getExtras().getString("notification_type");
                id = intent.getExtras().getString("notification_id");
                courseID = intent.getExtras().getString("course_id");
                course_name = intent.getExtras().getString("course_name");
                discId = intent.getExtras().getString("disc_id"); */
            	
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                Intent intent_activity;

//                intent_activity = new Intent(context, ActivityMain.class);

                if(numMessages==0) {
                    Log.d("going into","nummessages1");
                    /*if (type.equals("Announcement")) {
                        intent_activity = new Intent(context, ActivityAnnouncements.class);

                    } else if (type.equals("Deadline")) {
                        intent_activity = new Intent(context, ActivityDeadlines.class);

                    } else if (type.equals("Resource")) {
                        intent_activity = new Intent(context, ActivityResources.class);

                    } else if (type.equals("Grade")) {
                        intent_activity = new Intent(context, ActivityGrades.class);

                    } else if (type.equals("Discussion")) {
                        Log.d("updateapp","in discussions");
                        intent_activity = new Intent(context, ActivityDiscussions.class);

                    } else {
                        Log.d("going into","nummessages1 activity main");

                        intent_activity = new Intent(context, ActivityMain.class);
                    } */
                    intent_activity = new Intent(context, Auth.class);
                }
                else{
                    Log.d("going into","nummessages not one1 activity user");

                    intent_activity = new Intent(context, Auth.class);

                }
                intent_activity.setAction("OpenNotif");
              //  intent_activity.putExtra("type", type);
              //  Log.d("notif_type",type);
               // intent_activity.putExtra("Notifid", id);
                //intent_activity.putExtra("courseName", course_name);
                //intent_activity.putExtra("discId",discId);
                // intent_activity.putExtra("id", Integer.parseInt(courseID));
                PendingIntent pending_intent = PendingIntent.getActivity(context, 0, intent_activity, PendingIntent.FLAG_ONE_SHOT);
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                int notifyId =1;
                try {
                    Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    if(numMessages>=1) {
                        notif_text = String.valueOf(numMessages+1)+" new notifications from TagOverflow.";
                    }
                    else{
                        notif_text = notification_text;
                    }
                    numMessages = numMessages+1;
                    Log.d("notif_text",notification_text);
                    NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context)
                            .setContentTitle("TagOverFlow")
                            .setContentText(notification_text)
                            .setContentIntent(pending_intent)
                            .setSmallIcon(R.drawable.app_icon)
                            .setAutoCancel(true)
                            .setSound(notif);
                    mNotifyBuilder.setContentText(notif_text);
                    mNotificationManager.notify(
                            notifyId,
                            mNotifyBuilder.build());
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
