package com.example.keepitnative.libs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.example.keepitnative.MainActivity;

public class DownloaderManager {

	static long downloadId;
	static DownloadManager manager;
	public static String shareType = null;
	public static String uriSource;

	public static BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (DownloaderManager.shareType != null) {
				Query query = new Query();
				query.setFilterById(downloadId);
				Cursor cur = manager.query(query);

				if (cur.moveToFirst()) {
					int columnIndex = cur
							.getColumnIndex(DownloadManager.COLUMN_STATUS);
					if (DownloadManager.STATUS_SUCCESSFUL == cur
							.getInt(columnIndex)) {
						String uriString = cur
								.getString(cur
										.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

						File mFile = new File(Uri.parse(uriString).getPath());
						// Toast.makeText(MainActivity.mainActivityContext,
						// shareType, Toast.LENGTH_LONG).show();
						if (shareType == "image") {
							new ShareManager().shareImage(false,
									Uri.fromFile(mFile));
						}
						// else if (shareType == "link") {
						// new ShareManager().shareLink(uriSource);
						// }

					} else {
						Toast.makeText(MainActivity.mainActivityContext,
								"fail", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};

	public String downloadItem(String uriSource, boolean sharing, String string) {

		DownloaderManager.shareType = string;
		DownloaderManager.uriSource = uriSource;
		if (shareType == "link") {
			new ShareManager().shareLink(uriSource);
		} else {
			try {
				String fileName = DownloaderManager.getFileNameFromUrl(new URL(
						uriSource));
				DownloadManager.Request request = new DownloadManager.Request(
						Uri.parse(uriSource));

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}

				request.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS, fileName);
				manager = (DownloadManager) MainActivity.mainActivityContext
						.getSystemService(Context.DOWNLOAD_SERVICE);
				downloadId = manager.enqueue(request);

			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;

			}
		}

		return uriSource;
	}

	public static String getFileNameFromUrl(URL url) {
		String urlString = url.getFile();
		return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0];
		// .split("#")[0];
	}
}
