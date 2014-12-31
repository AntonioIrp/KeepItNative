package com.example.keepitnative;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Control User guide
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewUserGuide extends Activity {
	WebView wb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.setTheme(R.style.FullscreenWithTranslucentNavigation);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_user_guide);

		// Manejo del elemento WebView para la visualización del manual de
		// usuario
		wb = (WebView) findViewById(R.id.webUserGuide);

		// JavaScript y caché activados
		wb.getSettings().setJavaScriptEnabled(true);
		wb.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// Handle the error
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		// Elemento file que se cargará en el WebView
		String htmlUserGuide = "file:///android_asset/userguide/index.html";
		wb.loadUrl(htmlUserGuide);
	}

	public void finishAboutActivity() {
		this.finish();
		overridePendingTransition(R.anim.no_anim, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finishAboutActivity();
		}
		return false;
	}
}
