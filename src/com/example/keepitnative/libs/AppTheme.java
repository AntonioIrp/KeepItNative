package com.example.keepitnative.libs;

import android.content.Context;

import com.example.keepitnative.AppConfiguration;
import com.example.keepitnative.R;

public class AppTheme {
	public static String FullscreenWithOpaqueNavigation = "FullscreenWithOpaqueNavigation";
	public static String FullscreenWithTranslucentNavigation = "FullscreenWithTranslucentNavigation";

	public static String StatusbarAndNavigationTranslucent = "StatusbarAndNavigationTranslucent";
	public static String StatusbartAndNavigationOpaque = "StatusbartAndNavigationOpaque";

	public static String StatusbarOpaqueAndNavigationTranslucent = "StatusbarOpaqueAndNavigationTranslucent";
	public static String StatusbarTranslucentAndNavigationOpaque = "StatusbarTranslucentAndNavigationOpaque";

	public void setMainActivityTheme(Context context) {
		if (AppConfiguration.APP_THEME == AppTheme.FullscreenWithOpaqueNavigation) {
			context.setTheme(R.style.FullscreenWithOpaqueNavigation);
		} else if (AppConfiguration.APP_THEME == AppTheme.FullscreenWithTranslucentNavigation) {
			context.setTheme(R.style.FullscreenWithTranslucentNavigation);
		} else if (AppConfiguration.APP_THEME == AppTheme.StatusbarAndNavigationTranslucent) {
			context.setTheme(R.style.StatusbarAndNavigationTranslucent);
		} else if (AppConfiguration.APP_THEME == AppTheme.StatusbartAndNavigationOpaque) {
			context.setTheme(R.style.StatusbartAndNavigationOpaque);
		} else if (AppConfiguration.APP_THEME == AppTheme.StatusbarOpaqueAndNavigationTranslucent) {
			context.setTheme(R.style.StatusbarOpaqueAndNavigationTranslucent);
		} else if (AppConfiguration.APP_THEME == AppTheme.StatusbarTranslucentAndNavigationOpaque) {
			context.setTheme(R.style.StatusbarTranslucentAndNavigationOpaque);
		}
	}
}
