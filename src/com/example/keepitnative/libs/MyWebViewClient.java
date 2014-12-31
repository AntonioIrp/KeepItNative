package com.example.keepitnative.libs;

import com.example.keepitnative.AppConfiguration;
import com.example.keepitnative.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MyWebViewClient extends WebViewClient {

	@Override
	public void onLoadResource(WebView view, String url) {
		if (ConnectionStatus.checkInternetConnection() == false) {
			MainActivity.connectionError.setVisibility(LinearLayout.VISIBLE);
		} else {
			MainActivity.connectionError.setVisibility(LinearLayout.INVISIBLE);
		}
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		Activity mainActivity = (Activity) MainActivity.mainActivityContext;

		if (url.startsWith("mailto:")) {
			MailTo mt = MailTo.parse(url);

			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { mt.getTo()
					.toString() });

			if (emailIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
				mainActivity.startActivity(emailIntent);
			}
			return true;

		} else if (url.startsWith("tel:")) {
			Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse(url));
			if (callIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
				mainActivity.startActivity(callIntent);
			}
			return true;

		} else if (url.startsWith("market:")) {
			Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			if (marketIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
				mainActivity.startActivity(marketIntent);
			}
			return true;

		} else if (url.startsWith("sms:") || url.startsWith("smsto:")
				|| url.startsWith("mms:") || url.startsWith("mmsto:")) {
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setData(Uri.parse(url));

			if (smsIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
				mainActivity.startActivity(smsIntent);
			}
			return true;
		} else if (url.startsWith("http://maps.google.")
				|| url.startsWith("http://www.google.es/maps")
				|| url.startsWith("https://maps.google.")
				|| url.startsWith("https://www.google.es/maps")
				|| url.startsWith("geo:")) {
			Intent mapsIntent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse(url));

			if (mapsIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
				mainActivity.startActivity(mapsIntent);
			}
			return true;
		} else if ((url.startsWith("http:") || url.startsWith("https:"))
				&& AppConfiguration.OPEN_IN_EXTERNAL_WINDOW == true) {
			Uri webpage = Uri.parse(url);
			Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
			if (webIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
				mainActivity.startActivity(webIntent);
			}
			return true;
		} else {
			return false;
		}
	}
}
