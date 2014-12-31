package com.example.keepitnative.libs;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.keepitnative.AppConfiguration;
import com.example.keepitnative.MainActivity;
import com.example.keepitnative.R;

public class ShareManager {

	boolean fullScreenMode = true;
	Uri imageUri = null;
	static Activity mainActivity;

	public static String getFileNameFromUrl(URL url) {
		String urlString = url.getFile();
		return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0]
				.split("#")[0];
	}

	public void shareImage(boolean mode, Uri image) {
		mainActivity = (Activity) MainActivity.mainActivityContext;
		this.fullScreenMode = mode;

		if (this.fullScreenMode == true) {
			this.imageUri = ShareManager.captureSnapshoot();
		} else {
			this.imageUri = image;
		}

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
		sendIntent.setType("image/*");

		if ((AppConfiguration.SHARE_IMAGE_ONLY == true)
				|| (MainActivity.webContainer.getUrl().startsWith("file:") == true)) {
			sendIntent.putExtra(Intent.EXTRA_TEXT, mainActivity.getResources()
					.getString(R.string.sharing_image_introduction_text));
			sendIntent.putExtra("sms_body", mainActivity.getResources()
					.getString(R.string.sharing_image_introduction_text));
		} else {
			String finalMessage = mainActivity.getResources().getString(
					R.string.sharing_image_introduction_text)
					+ " " + MainActivity.webContainer.getUrl().toString();

			sendIntent.putExtra(Intent.EXTRA_TEXT, finalMessage);
			sendIntent.putExtra("sms_body", finalMessage);
		}

		mainActivity.startActivity(Intent.createChooser(sendIntent,
				mainActivity.getResources().getText(R.string.share_with)));
	}

	public void shareLink(String link) {
		mainActivity = (Activity) MainActivity.mainActivityContext;
		String url = link;// MainActivity.webContainer.getUrl();

		if (url.startsWith("file:")) {
			Toast.makeText(
					mainActivity.getApplicationContext(),
					mainActivity.getResources().getString(
							R.string.error_sharing_link), Toast.LENGTH_LONG)
					.show();
		} else {
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, mainActivity.getResources()
					.getString(R.string.sharing_link_introduction_text)
					+ " "
					+ url);
			sendIntent.setType("text/*");
			mainActivity.startActivity(Intent.createChooser(sendIntent,
					mainActivity.getResources().getText(R.string.share_with)));
		}
	}

	private static Bitmap getBitmapForVisibleRegion(WebView webview) {
		Bitmap returnedBitmap = null;
		webview.setDrawingCacheEnabled(true);
		returnedBitmap = Bitmap.createBitmap(webview.getDrawingCache());
		webview.setDrawingCacheEnabled(false);
		return returnedBitmap;
	}

	@SuppressWarnings("deprecation")
	private static Uri captureSnapshoot() {
		Bitmap bmp = null;

		if (AppConfiguration.CAPTURE_ONLY_VISIBLE_PART == true) {
			bmp = getBitmapForVisibleRegion(MainActivity.webContainer);
		} else {
			bmp = picture2Bitmap(new PictureDrawable(
					MainActivity.webContainer.capturePicture()));
		}

		Uri imageUri = saveImageToExternalStorage(bmp);
		return imageUri;
	}

	private static Bitmap picture2Bitmap(PictureDrawable pictureDrawable) {
		Bitmap bitmap = Bitmap.createBitmap(
				pictureDrawable.getIntrinsicWidth(),
				pictureDrawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawPicture(pictureDrawable.getPicture());
		return bitmap;
	}

	private static Uri saveImageToExternalStorage(Bitmap finalBitmap) {
		String root = mainActivity.getApplicationContext()
				.getExternalCacheDir().toString();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-" + n + ".jpg";
		File file = new File(root, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG,
					AppConfiguration.CAPTURED_IMAGE_QUALITY, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Uri.fromFile(file);
	}
}
