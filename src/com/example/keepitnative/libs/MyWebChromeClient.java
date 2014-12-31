package com.example.keepitnative.libs;

import com.example.keepitnative.AppConfiguration;
import com.example.keepitnative.MainActivity;

import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {
	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
		super.onReceivedIcon(view, icon);
		if (AppConfiguration.SET_FAVICON == true) {
			MainActivity.faviconPlaceholder.setImageBitmap(icon);
		}

	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
		if (AppConfiguration.SET_TITLES == true) {
			MainActivity.webTitle.setText(title);
		}
	}
}
