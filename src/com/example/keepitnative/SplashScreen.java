package com.example.keepitnative;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.keepitnative.libs.AppTheme;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplashScreen extends Activity {

	public static Context splashScreenContext;
	public static String link = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		new AppTheme().setMainActivityTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		ParseAnalytics.trackAppOpened(getIntent());
		PushService.setDefaultPushCallback(this, SplashScreen.class,
				R.drawable.ic_notification);
		ParseInstallation.getCurrentInstallation().saveInBackground();

		if (getIntent().hasExtra("com.parse.Data")) {
			Bundle mBundle = getIntent().getExtras();

			if (mBundle != null) {
				String mData = mBundle.getString("com.parse.Data");
				if (mData.startsWith("{"))
					try {
						JSONObject jsonObj = new JSONObject(mData);
						SplashScreen.link = jsonObj.getString("link")
								.toString();
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}
		}

		if (AppConfiguration.SPLASH_SCREEN_TIMER == 0) {
			this.openMainActivity();
		} else {
			splashScreenContext = SplashScreen.this;
			new WaitThisTime(this).execute();
		}
	}

	public void openMainActivity() {
		Intent i = new Intent().setClass(SplashScreen.this, MainActivity.class);
		if (link != null) {
			i.putExtra("link", SplashScreen.link);
		}
		startActivity(i);
		this.overridePendingTransition(0, 0);
		finish();
	}

	public class WaitThisTime extends AsyncTask<Void, Void, Void> {
		public SplashScreen splashScreen;

		public WaitThisTime(SplashScreen splashScreen) {
			this.splashScreen = splashScreen;
		}

		@Override
		protected void onPostExecute(Void result) {
			this.splashScreen.openMainActivity();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				long time = (long) (AppConfiguration.SPLASH_SCREEN_TIMER * 1000);
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
