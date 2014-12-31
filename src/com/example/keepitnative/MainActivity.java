package com.example.keepitnative;

import hotchemi.android.rate.AppRate;

import com.example.keepitnative.KeepItNativeApplication.TrackerName;
import com.example.keepitnative.libs.AppTheme;
import com.example.keepitnative.libs.ArrayMenuAdapter;
import com.example.keepitnative.libs.ColorTheme;
import com.example.keepitnative.libs.ConnectionStatus;
import com.example.keepitnative.libs.DownloaderManager;
import com.example.keepitnative.libs.MyWebChromeClient;
import com.example.keepitnative.libs.MyWebViewClient;
import com.example.keepitnative.libs.ShareManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.WebIconDatabase;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
	public static Context mainActivityContext = null;
	public static TextView aboutButtonText;
	public static LinearLayout bottomMenu, shareMenu, splashScreen,
			aboutButton;
	public static LinearLayout transparencyBackground, topBar, adMobContainer,
			shareMenuButtons, connectionError;
	public static WebView webContainer;
	public static ImageView topMenuImage, faviconPlaceholder,
			shareMenuArrowIndicator, aboutImageButton;
	public static TextView webTitle, button1Text, button2Text, button3Text,
			button4Text;
	public static RelativeLayout webContainerLayout;
	public static ListView listOfMenuButtons;
	public static String[] buttonLinks, buttonsIcons;
	ArrayAdapter<String> buttonsTitles;
	WebView.HitTestResult hitTestResult = null;

	private AdView adView;
	Tracker t;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mainActivityContext = MainActivity.this;
		new AppTheme().setMainActivityTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_admob);

		this.prepareScreenElements();
		new ColorTheme().setTheme(this);

		if (!AppConfiguration.DISABLE_RATE_MY_APP) {
			AppRate.with(this).setInstallDays(0) // default 10, 0 means install
													// day.
					.setLaunchTimes(3) // default 10
					.setRemindInterval(2) // default 1
					.setShowNeutralButton(true) // default true
					.setDebug(false) // default false
					.monitor();
		}

		// Show a dialog if meets conditions
		AppRate.showRateDialogIfMeetsConditions(this);

		WebIconDatabase.getInstance().open(
				getDir("icons", MODE_PRIVATE).getPath());
		webContainer.getSettings().setJavaScriptEnabled(true);
		webContainer.getSettings().setPluginState(PluginState.ON);
		webContainer.getSettings().setAllowFileAccess(true);

		webContainer.setWebChromeClient(new MyWebChromeClient());
		webContainer.setWebViewClient(new MyWebViewClient());


		if (this.getIntent().hasExtra("link")) {
			webContainer.loadUrl(getIntent().getExtras().getString("link")
					.toString());
		} else {
			webContainer.loadUrl(AppConfiguration.DEFAULT_PAGE);
		}

		CookieManager.getInstance().setAcceptCookie(true);

		registerForContextMenu(webContainer);

		registerReceiver(DownloaderManager.receiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		
	     // Get tracker.
        t = ((KeepItNativeApplication) getApplication()).getTracker(
            TrackerName.APP_TRACKER);
        
        t.enableAutoActivityTracking(true);
        t.setScreenName("MainActivity");
        t.send(new HitBuilders.AppViewBuilder().build());

	}

	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_save_image) {
			new DownloaderManager().downloadItem(hitTestResult.getExtra(),
					false, null);
		} else if (item.getItemId() == R.id.menu_view_image) {
			webContainer.loadUrl(hitTestResult.getExtra().toString());
		} else if (item.getItemId() == R.id.menu_share_image) {
			new DownloaderManager().downloadItem(hitTestResult.getExtra(),
					true, "image");
		} else if (item.getItemId() == R.id.menu_open_link) {
			webContainer.loadUrl(hitTestResult.getExtra());
		} else if (item.getItemId() == R.id.menu_save_as_link) {
			new DownloaderManager().downloadItem(hitTestResult.getExtra(),
					false, null);
		} else if (item.getItemId() == R.id.menu_share_link) {
			new DownloaderManager().downloadItem(hitTestResult.getExtra()
					.toString(), true, "link");
		} else if (item.getItemId() == R.id.menu_copy_link) {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData
					.newPlainText("URL", hitTestResult.getExtra());
			clipboard.setPrimaryClip(clip);
			Toast.makeText(
					this.getApplicationContext(),
					this.getResources()
							.getString(R.string.link_is_in_clipboard),
					Toast.LENGTH_SHORT).show();
		} else {
			return false;
		}
		return true;
	}

	public void onCreateContextMenu(ContextMenu menu, View view,
		ContextMenu.ContextMenuInfo menuInfo) {
		
		hitTestResult = webContainer.getHitTestResult();

		if ((this.hitTestResult.getType() == 5)
				|| (this.hitTestResult.getType() == 7)) {

			if (!hitTestResult.getExtra().startsWith("file")
					&& (hitTestResult.getType() == HitTestResult.IMAGE_TYPE || hitTestResult
							.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE)) {
				MenuInflater inflater = getMenuInflater();
				menu.setHeaderTitle(hitTestResult.getExtra().toString());
				inflater.inflate(R.menu.web_context_image_menu, menu);

			} else if (!hitTestResult.getExtra().startsWith("file")
					&& (hitTestResult.getType() == HitTestResult.ANCHOR_TYPE || hitTestResult
							.getType() == HitTestResult.SRC_ANCHOR_TYPE)) {
				MenuInflater inflater = getMenuInflater();
				menu.setHeaderTitle(hitTestResult.getExtra().toString());
				inflater.inflate(R.menu.web_context_link_menu, menu);
			} else {

			}
		}
	}

	public void onClickShareButton1(View v) {
		this.showShareMenu(v);
		new ShareManager().shareLink(webContainer.getUrl());
	}

	public void onClickShareButton2(View v) {
		this.showShareMenu(v);
		new ShareManager().shareImage(true, null);
	}

	public void showShareMenu(View v) {
		if (MainActivity.shareMenu.getVisibility() == LinearLayout.INVISIBLE) {
			MainActivity.shareMenu.setVisibility(LinearLayout.VISIBLE);
	        Tracker t = ((KeepItNativeApplication) getApplication()).getTracker(
	                TrackerName.APP_TRACKER);
	        t.setScreenName("Share Menu Clicked");
	        t.send(new HitBuilders.AppViewBuilder().build());
		} else {
			MainActivity.shareMenu.setVisibility(LinearLayout.INVISIBLE);
		}
	}

	public void onTransparencyBackground(View v) {
		showMenu(v);
	}

	public void showMenu(View v) {
		if (MainActivity.bottomMenu.getVisibility() == LinearLayout.INVISIBLE) {
			topMenuImage.setImageResource(R.drawable.ic_cross);
			Animation transparencyFadeIn = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.fade_in);
			transparencyBackground.setVisibility(LinearLayout.VISIBLE);
			transparencyBackground.setAnimation(transparencyFadeIn);

			Animation menuUp = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.menu_up);
			bottomMenu.setVisibility(LinearLayout.VISIBLE);
			bottomMenu.startAnimation(menuUp);
		} else {
			Animation transparencyFadeOut = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.fade_out);
			transparencyBackground.setVisibility(LinearLayout.INVISIBLE);
			transparencyBackground.setAnimation(transparencyFadeOut);

			topMenuImage.setImageResource(R.drawable.ic_menu);
			Animation out = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.menu_down);
			MainActivity.bottomMenu.setVisibility(LinearLayout.INVISIBLE);
			MainActivity.bottomMenu.startAnimation(out);
		}
	}

	public void retryConnection(View v) {
		if (ConnectionStatus.checkInternetConnection() == true) {
			String webUrl = webContainer.getUrl();
			webContainer.loadUrl(webUrl);
			if (connectionError.getVisibility() == LinearLayout.VISIBLE) {
				connectionError.setVisibility(LinearLayout.INVISIBLE);
			}
		}
	}

	public void prepareAdMob() {
		adView = new AdView(this);
		adView.setAdUnitId(AppConfiguration.AD_UNIT_ID);
		adView.setAdSize(AdSize.BANNER);
		LinearLayout layout = (LinearLayout) findViewById(R.id.adMobContainer);
		layout.addView(adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	@Override
	public void onStop() {
		GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();
		if (AppConfiguration.AD_MOB == true) {
			adView.pause();
		}
		super.onStop();
	}
	
	@Override
	public void onPause() {
		if (AppConfiguration.AD_MOB == true) {
			adView.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (AppConfiguration.AD_MOB == true) {
			adView.resume();
		}
	}

	@Override
	public void onDestroy() {
		if (AppConfiguration.AD_MOB == true) {
			adView.destroy();
		}
		this.unregisterReceiver(DownloaderManager.receiver);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (MainActivity.bottomMenu.getVisibility() == LinearLayout.VISIBLE) {
				showMenu(getCurrentFocus());

			} else {
				if (MainActivity.webContainer.canGoBack()) {
					MainActivity.webContainer.goBack();
				}
			}
		}
		return false;
	}

	public void onAboutButtonClick(View v) {
		Intent aboutActivityIntent = new Intent(MainActivity.this,
				AboutActivity.class);
		this.startActivity(aboutActivityIntent);
		this.overridePendingTransition(R.anim.menu_up, R.anim.no_anim);

	}

	public void prepareScreenElements() {
		topBar = (LinearLayout) findViewById(R.id.topBar);
		webContainer = (WebView) findViewById(R.id.webContainer);
		transparencyBackground = (LinearLayout) findViewById(R.id.transparencyBackground);
		bottomMenu = (LinearLayout) findViewById(R.id.bottomMenu);
		topMenuImage = (ImageView) findViewById(R.id.menuButton);
		shareMenu = (LinearLayout) findViewById(R.id.shareMenuPlaceholder);
		faviconPlaceholder = (ImageView) findViewById(R.id.appFavIcon);
		webTitle = (TextView) findViewById(R.id.webTitle);
		shareMenuArrowIndicator = (ImageView) this
				.findViewById(R.id.shareMenuArrowIndicator);
		shareMenuButtons = (LinearLayout) this
				.findViewById(R.id.shareMenuButtons);
		connectionError = (LinearLayout) this
				.findViewById(R.id.noConnectionLayout);
		listOfMenuButtons = (ListView) this
				.findViewById(R.id.listOfMenuButtons);
		aboutButton = (LinearLayout) this.findViewById(R.id.aboutButton);
		aboutImageButton = (ImageView) this.findViewById(R.id.aboutImageButton);
		aboutButtonText = (TextView) this.findViewById(R.id.AboutButtonText);

		String[] titles = this.getResources().getStringArray(
				R.array.MenuButtonsTitles);
		buttonLinks = this.getResources().getStringArray(
				R.array.MenuButtonsLinks);

		ListAdapter customAdapter = new ArrayMenuAdapter(
				this.getApplicationContext(), R.layout.menu_item, titles);
		listOfMenuButtons.setAdapter(customAdapter);
		listOfMenuButtons.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				showMenu(view);
				MainActivity.webContainer.loadUrl(buttonLinks[position]);

			}
		});

		if (AppConfiguration.HIDE_ALL_STUFF == true) {
			topBar.setVisibility(LinearLayout.GONE);
		}
		if (AppConfiguration.AD_MOB == true) {
			this.prepareAdMob();
		}
	}
}
