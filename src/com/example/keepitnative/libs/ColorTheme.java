package com.example.keepitnative.libs;

import com.example.keepitnative.AboutActivity;
import com.example.keepitnative.AppConfiguration;
import com.example.keepitnative.MainActivity;
import com.example.keepitnative.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ColorTheme {
	public static String PURPLE = "Purple";
	public static String POWERED_PURPLE = "Powered_purple";
	public static String BLUE = "Blue";
	public static String RED = "Red";
	public static String PINK = "Pink";
	public static String GREEN = "Green";
	public static String ORANGE = "Orange";
	public static String SEA_SUNSET = "Sea_sunset";
	public static String STORK = "Stork";

	public static Activity mainActivity;
	public static Activity aboutActivity;

	@SuppressLint("NewApi")
	public void setTheme(Context context) {

		if (context == MainActivity.mainActivityContext) {
			mainActivity = (Activity) MainActivity.mainActivityContext;

			int color = mainActivity
					.getApplicationContext()
					.getResources()
					.getIdentifier(
							"array/" + AppConfiguration.COLOR_THEME,
							null,
							mainActivity.getApplicationContext()
									.getPackageName());
			String[] colorTheme = mainActivity.getApplicationContext()
					.getResources().getStringArray(color);

			setTopBarColor(Color.parseColor(colorTheme[0]));
			setShareMenuColor(Color.parseColor(colorTheme[1]));
			setDarkLines(Color.parseColor(colorTheme[2]));
			setAboutButtonColor();
		}

		if (context == AboutActivity.aboutActivityContext) {
			aboutActivity = (Activity) AboutActivity.aboutActivityContext;
			int color = aboutActivity
					.getApplicationContext()
					.getResources()
					.getIdentifier(
							"array/" + AppConfiguration.COLOR_THEME,
							null,
							aboutActivity.getApplicationContext()
									.getPackageName());
			String[] colorTheme = aboutActivity.getApplicationContext()
					.getResources().getStringArray(color);

			LinearLayout bar = (LinearLayout) aboutActivity
					.findViewById(R.id.aboutOfHeader);
			bar.setBackgroundColor(Color.parseColor(colorTheme[0]));

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				SystemBarTintManager tintManager = new SystemBarTintManager(
						aboutActivity);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setStatusBarTintColor(Color
						.parseColor(colorTheme[0]));
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				LayoutParams params = bar.getLayoutParams();

				if (AppConfiguration.APP_THEME == AppTheme.StatusbarAndNavigationTranslucent) {
					params.height = 108;
					bar.setLayoutParams(params);
					bar.requestLayout();
					aboutActivity.getWindow().setStatusBarColor(
							Color.TRANSPARENT);
				}

				if (AppConfiguration.APP_THEME == AppTheme.StatusbarOpaqueAndNavigationTranslucent) {
					params.height = 108;
					bar.setLayoutParams(params);
					bar.requestLayout();
					aboutActivity.getWindow().setStatusBarColor(Color.BLACK);
				}
				if (AppConfiguration.APP_THEME == AppTheme.StatusbarTranslucentAndNavigationOpaque) {
					aboutActivity.getWindow().setStatusBarColor(
							Color.TRANSPARENT);
				}
			}
		}
	}

	@SuppressLint("NewApi")
	private void setTopBarColor(int color) {
		MainActivity.topBar.setBackgroundColor(color);

		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			SystemBarTintManager tintManager = new SystemBarTintManager(
					ColorTheme.mainActivity);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintColor(color);

		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			LinearLayout lp = MainActivity.topBar;
			LayoutParams params = lp.getLayoutParams();
			// Activity main = (Activity)MainActivity.mainActivityContext;

			if (AppConfiguration.APP_THEME == AppTheme.StatusbarAndNavigationTranslucent
					&& AppConfiguration.HIDE_ALL_STUFF == false) {
				params.height = 108;
				lp.setLayoutParams(params);
				lp.requestLayout();
				mainActivity.getWindow().setStatusBarColor(color);
			}
			if (AppConfiguration.APP_THEME == AppTheme.StatusbarAndNavigationTranslucent
					&& AppConfiguration.HIDE_ALL_STUFF == true) {
				mainActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
			}

			if (AppConfiguration.APP_THEME == AppTheme.StatusbarOpaqueAndNavigationTranslucent
					&& AppConfiguration.HIDE_ALL_STUFF == false) {
				params.height = 108;
				lp.setLayoutParams(params);
				lp.requestLayout();
				mainActivity.getWindow().setStatusBarColor(Color.BLACK);
			}
			if (AppConfiguration.APP_THEME == AppTheme.StatusbarOpaqueAndNavigationTranslucent
					&& AppConfiguration.HIDE_ALL_STUFF == true) {
				params.height = 32;
				lp.setLayoutParams(params);
				lp.requestLayout();
				lp.setVisibility(LinearLayout.VISIBLE);
				mainActivity.getWindow().setStatusBarColor(Color.BLACK);
			}

			if (AppConfiguration.APP_THEME == AppTheme.StatusbarTranslucentAndNavigationOpaque
					&& AppConfiguration.HIDE_ALL_STUFF == false) {
				params.height = 108;
				lp.setLayoutParams(params);
				lp.requestLayout();
				mainActivity.getWindow().setStatusBarColor(color);
			}
			if (AppConfiguration.APP_THEME == AppTheme.StatusbarTranslucentAndNavigationOpaque
					&& AppConfiguration.HIDE_ALL_STUFF == true) {
				// mainActivity.getWindow().setStatusBarColor(Color.BLACK);
				// // Toast.makeText(mainActivity.getApplicationContext(), "s",
				// Toast.LENGTH_LONG).show();
				// // //
				// mainActivity.getWindow().setNavigationBarColor(Color.BLACK);
				// mainActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
				// // mainActivity.getWindow().setNavigationBarColor(Color.RED);
			}

		}
	}

	private void setAboutButtonColor() {
		if (AppConfiguration.ABOUT_BUTTON_IS_ENABLED) {
			String[] aboutButtonConfiguration = mainActivity
					.getApplicationContext().getResources()
					.getStringArray(R.array.AboutButtonConfiguration);
			MainActivity.aboutButton.setBackgroundColor(Color
					.parseColor(aboutButtonConfiguration[2]));
			MainActivity.aboutButtonText.setText(aboutButtonConfiguration[0]);
			String imageUri = "drawable/" + aboutButtonConfiguration[1];
			try {
				int imageResource = mainActivity
						.getApplicationContext()
						.getResources()
						.getIdentifier(
								imageUri,
								null,
								mainActivity.getApplicationContext()
										.getPackageName());
				Drawable image = mainActivity.getApplicationContext()
						.getResources().getDrawable(imageResource);
				MainActivity.aboutImageButton.setImageDrawable(image);
			} catch (Exception e) {
				MainActivity.aboutImageButton.setVisibility(ImageView.GONE);
			}
		} else {
			MainActivity.aboutButton.setVisibility(LinearLayout.GONE);
		}
	}

	private void setShareMenuColor(int color) {
		MainActivity.shareMenuButtons.setBackgroundColor(color);
		MainActivity.shareMenuArrowIndicator.setColorFilter(color, Mode.ADD);
	}

	private void setDarkLines(int color) {
		LinearLayout topDarkLine = (LinearLayout) mainActivity
				.findViewById(R.id.topDarkLine);
		LinearLayout shareMenuDarkLine = (LinearLayout) mainActivity
				.findViewById(R.id.shareMenuBottomLine);
		topDarkLine.setBackgroundColor(color);
		shareMenuDarkLine.setBackgroundColor(color);
	}
}
