package com.example.keepitnative;

import com.example.keepitnative.KeepItNativeApplication.TrackerName;
import com.example.keepitnative.libs.AppTheme;
import com.example.keepitnative.libs.ColorTheme;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutActivity extends Activity {
	LinearLayout twitterButton, facebookButton, emailButton, linkButton,
			versionButton, telButton, mapsButton;
	public static Context aboutActivityContext = null;

	public static int color;

	@SuppressLint({ "NewApi", "CutPasteId" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		aboutActivityContext = this;
		new AppTheme().setMainActivityTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);

		if (!AppConfiguration.ENABLE_USER_GUIDE) {
			ImageView helpIcon = (ImageView) this
					.findViewById(R.id.aboutOfHelpIcon);
			helpIcon.setVisibility(ImageView.GONE);
		}

		new ColorTheme().setTheme(this);

		twitterButton = (LinearLayout) this.findViewById(R.id.twitterButton);
		facebookButton = (LinearLayout) this.findViewById(R.id.facebookButton);
		emailButton = (LinearLayout) this.findViewById(R.id.aboutOfEmailButton);
		linkButton = (LinearLayout) this
				.findViewById(R.id.aboutOfGenericButton);
		versionButton = (LinearLayout) this
				.findViewById(R.id.aboutOfVersionButton);
		telButton = (LinearLayout) this.findViewById(R.id.telButton);
		mapsButton = (LinearLayout) this.findViewById(R.id.mapsButton);

		if (AppConfiguration.TWITTER_BUTTON == false) {
			twitterButton.setVisibility(LinearLayout.GONE);
		} else if (AppConfiguration.FACEBOOK_BUTTON == false) {
			facebookButton.setVisibility(LinearLayout.GONE);
		} else if (AppConfiguration.EMAIL_BUTTON == false) {
			emailButton.setVisibility(LinearLayout.GONE);
		} else if (AppConfiguration.LINK_BUTTON == false) {
			linkButton.setVisibility(LinearLayout.GONE);
		} else if (AppConfiguration.VERSION_BUTTON == false) {
			versionButton.setVisibility(LinearLayout.GONE);
		} else if (AppConfiguration.TEL_BUTTON == false) {
			telButton.setVisibility(LinearLayout.GONE);
		} else if (AppConfiguration.MAP_BUTTON == false) {
			mapsButton.setVisibility(LinearLayout.GONE);
		}

		twitterButton.setOnClickListener(this.listener);
		facebookButton.setOnClickListener(this.listener);
		emailButton.setOnClickListener(this.listener);
		linkButton.setOnClickListener(this.listener);
		versionButton.setOnClickListener(this.listener);
		telButton.setOnClickListener(this.listener);
		mapsButton.setOnClickListener(this.listener);
		
        Tracker t = ((KeepItNativeApplication) getApplication()).getTracker(
                TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);
        t.setScreenName("AboutActivity");
        t.send(new HitBuilders.AppViewBuilder().build());

	}

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Context context = AboutActivity.aboutActivityContext;

			switch (v.getId()) {
			case R.id.twitterButton:
				((AboutActivity) context)
						.goToLink(AppConfiguration.TWITTER_LINK);
				break;
			case R.id.facebookButton:
				((AboutActivity) context)
						.goToLink(AppConfiguration.FACEBOOK_LINK);
				break;
			case R.id.aboutOfEmailButton:
				Log.d("email", "email");
				MailTo mt = MailTo.parse("mailto:"
						+ AppConfiguration.EMAIL_ADDRESS);
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { mt
						.getTo().toString() });

				if (emailIntent.resolveActivity(getPackageManager()) != null) {
					startActivity(emailIntent);
				}
				break;
			case R.id.aboutOfGenericButton:
				((AboutActivity) context).goToLink(AppConfiguration.WEB_LINK);
				break;
			case R.id.aboutOfVersionButton:
				break;
			case R.id.telButton:
				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri
						.parse("tel:" + AppConfiguration.TEL_LINK));
				if (callIntent.resolveActivity(getPackageManager()) != null) {
					startActivity(callIntent);
				}
				break;
			case R.id.mapsButton:
				Intent mapsIntent = new Intent(
						android.content.Intent.ACTION_VIEW,
						Uri.parse(AppConfiguration.MAP_LINK));
				if (mapsIntent.resolveActivity(getPackageManager()) != null) {
					startActivity(mapsIntent);
				}
				break;

			}
		}
	};

	public void goToLink(String url) {
		Uri webpage = Uri.parse(url);
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		if (webIntent.resolveActivity(this.getPackageManager()) != null) {
			startActivity(webIntent);
		}
	}

	public void onHelpMe(View v) {
		Intent intent = new Intent(AboutActivity.this, WebViewUserGuide.class);
		startActivity(intent);
		overridePendingTransition(0, R.anim.no_anim);
	}

	public void onBackToMain(View v) {
		this.finishAboutActivity();
	}

	public void finishAboutActivity() {
		this.finish();
		overridePendingTransition(R.anim.no_anim, R.anim.menu_down);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finishAboutActivity();
		}
		return false;
	}
}
