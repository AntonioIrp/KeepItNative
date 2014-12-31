package com.example.keepitnative;

import com.example.keepitnative.libs.AppTheme;
import com.example.keepitnative.libs.ColorTheme;

public class AppConfiguration {

	// COLOR AND BARS CUSTOMIZATION
	public static String COLOR_THEME = ColorTheme.STORK;
	public static String APP_THEME = AppTheme.StatusbarAndNavigationTranslucent;

	// APP BEHAVIOUR
	public static float SPLASH_SCREEN_TIMER = 0.5f; // seconds. For 3.4 seconds ---> 3.4f											
	public static String DEFAULT_PAGE = "file:///android_asset/helptext/index.html";
	public static boolean SET_FAVICON = true; // Download and show the web favicon if true											
	public static boolean SET_TITLES = true; // Get and show the web Title if true										
	public static boolean OPEN_IN_EXTERNAL_WINDOW = false; // open http: or https: link in default browser if true														
	public static boolean HIDE_ALL_STUFF = false; // Hides the action bar if true
	
													
	// ADMOB
	public static boolean AD_MOB = true; // adMob is enabled if true
	public static String AD_UNIT_ID = "ca-app-pub-3266169360849925/9951786894"; //ad unit identifier

	// SHARING PHOTO PREFERENCES
	public static boolean CAPTURE_ONLY_VISIBLE_PART = false; // captures full web page if false, only
																//visible region if true																											
	public static int CAPTURED_IMAGE_QUALITY = 100; // MAX LIMIT 100%
	public static boolean SHARE_IMAGE_ONLY = false; // Shares image and link if false. Or shares only image if true
	
	//PARSE NOTIFICATION
	public static String PARSE_APPLICATION_ID = "cR4wXKD0fZsD7BFRA4Xy8JvgYMkajTLYO7b5DarS";
	public static String PARSE_CLIENT_KEY = "wcNc8b8qStsbCpFOaIB9UFGFadIMOlJYTBVZ6Id8";
	
	//RATE MY APP
	public static boolean DISABLE_RATE_MY_APP = false;
	public static final String MY_APP_GOOGLE_PLAY_LINK = "https://play.google.com/store/apps/details?id=";
	
	//ABOUT SCREEN BUTTONS CONFIGURATION
	public static boolean ABOUT_BUTTON_IS_ENABLED = true; //Hides the menu about button if false
	public static boolean ENABLE_USER_GUIDE = true;
	
	public static boolean TWITTER_BUTTON = true;
	public static String TWITTER_LINK = "http://twitter.com";
	
	public static boolean FACEBOOK_BUTTON = true;
	public static String FACEBOOK_LINK = "http://facebook.com";
	
	public static boolean MAP_BUTTON = true;
	public static String MAP_LINK = "https://www.google.es/maps/@38.735605,-3.0133942,15z";
	
	public static boolean EMAIL_BUTTON = true;
	public static String EMAIL_ADDRESS = "example@example.com";
	
	public static boolean LINK_BUTTON = true;
	public static String WEB_LINK = "http://myWebSite.com";
	
	public static boolean TEL_BUTTON = true;
	public static String TEL_LINK = "+42 54324234";
	
	public static boolean VERSION_BUTTON = true;
													
}
