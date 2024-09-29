package ua.acclorite.book_story.data.local.notification

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import ua.acclorite.book_story.R
import ua.acclorite.book_story.data.remote.dto.LatestReleaseInfo
import ua.acclorite.book_story.presentation.core.constants.Constants
import javax.inject.Inject

class UpdatesNotificationServiceImpl @Inject constructor(
    private val application: Application
) : UpdatesNotificationService {
    private val notificationManager = application.getSystemService(NotificationManager::class.java)

    /**
     * Sends notification about new update.
     *
     * @param releaseInfo [LatestReleaseInfo].
     */
    override fun postNotification(releaseInfo: LatestReleaseInfo) {
        val downloadLatestVersionIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(Constants.DOWNLOAD_LATEST_RELEASE_PAGE)
        )
        val latestReleaseIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(Constants.RELEASES_PAGE)
        )

        val downloadLatestVersionPendingIntent = PendingIntent.getActivity(
            application,
            1,
            downloadLatestVersionIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val latestReleasePendingIntent = PendingIntent.getActivity(
            application,
            1,
            latestReleaseIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            application,
            UpdatesNotificationService.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(application.getString(R.string.updates_notification_title))
            .setContentText(
                application.getString(
                    R.string.updates_notification_desc,
                    releaseInfo.name
                )
            )
            .setContentIntent(latestReleasePendingIntent)
            .addAction(
                NotificationCompat.Action.Builder(
                    IconCompat.createWithResource(application, R.drawable.notification_icon),
                    application.getString(R.string.updates_notification_action),
                    downloadLatestVersionPendingIntent
                ).build()
            )
            .build()

        notificationManager.notify(
            0,
            notification
        )
    }
}