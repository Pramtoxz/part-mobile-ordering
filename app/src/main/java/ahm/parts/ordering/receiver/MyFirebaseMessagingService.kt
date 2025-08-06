package ahm.parts.ordering.receiver

import ahm.parts.ordering.R
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo.KreditJatuhTempoDetailActivity
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part.PartPromoDetailActivity
import ahm.parts.ordering.ui.home.notification.NotificationActivity
import ahm.parts.ordering.ui.home.notification.detail.NotificationInformationActivity
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")


        Log.e("gcm", "terima")
        Log.e("remote1", "" + remoteMessage.data.toString())

        sendNotification(remoteMessage.data)

        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    @SuppressLint("WrongConstant")
    private fun sendNotification(messageBody: Map<String, String>) {

        val mainIntent = Intent(this, HomeActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val stackBuilder = TaskStackBuilder.create(applicationContext)

        var pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, mainIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val type = messageBody["type"]!!
        val title = messageBody["title"]!!
        val message = messageBody["message"]!!
        val data = messageBody["data"]!!

        Log.e("title", "" + title)
        Log.e("type", "" + type)
        Log.e("message", "" + message)

        if(!type.isNullOrEmpty()){
            when {
                type.equals("New Promo",ignoreCase = true) -> {
                    val notifList = Intent(applicationContext, NotificationActivity::class.java)

                    val intentDetail = Intent(
                        applicationContext,
                        PartPromoDetailActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    intentDetail.putExtra(Constants.BUNDLE.JSON,data)

                    stackBuilder.addNextIntent(mainIntent)
                    stackBuilder.addNextIntent(notifList)
                    stackBuilder.addNextIntent(intentDetail)

                    pendingIntent = stackBuilder.getPendingIntent(99, PendingIntent.FLAG_CANCEL_CURRENT)

                }
                type.equals("Kredit Limit",ignoreCase = true) -> {
                    val notifList = Intent(applicationContext, NotificationActivity::class.java)

                    val intentDetail = Intent(
                        applicationContext,
                        KreditJatuhTempoDetailActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    intentDetail.putExtra(Constants.BUNDLE.JSON,data)
                    intentDetail.putExtra(Constants.BUNDLE.TOOLBAR, applicationContext.getString(R.string.lbl_title_kredit_limit_details))

                    stackBuilder.addNextIntent(mainIntent)
                    stackBuilder.addNextIntent(notifList)
                    stackBuilder.addNextIntent(intentDetail)

                    pendingIntent = stackBuilder.getPendingIntent(99, PendingIntent.FLAG_CANCEL_CURRENT)

                }
                type.equals("information",ignoreCase = true) -> {
                    val notifList = Intent(applicationContext, NotificationActivity::class.java)

                    val intentDetail = Intent(
                        applicationContext,
                        NotificationInformationActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    intentDetail.putExtra(Constants.BUNDLE.JSON,data)

                    stackBuilder.addNextIntent(mainIntent)
                    stackBuilder.addNextIntent(notifList)
                    stackBuilder.addNextIntent(intentDetail)

                    pendingIntent = stackBuilder.getPendingIntent(99, PendingIntent.FLAG_CANCEL_CURRENT)

                }
            }
        }


        val channelId = "channelId"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(99, notificationBuilder.build())
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    @SuppressLint("WrongConstant")
    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val stackBuilder = TaskStackBuilder.create(applicationContext)


        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "channelId"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Test")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(99, notificationBuilder.build())
    }

    companion object {

        fun getTokenFCM(session: Session) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(
                            "Tag",
                            "getInstanceId failed",
                            task.exception!!
                        )
                        return@OnCompleteListener
                    }

                    val token = task.result?.token
                    session.saveFcmId(token)

                })
        }

    }
}
