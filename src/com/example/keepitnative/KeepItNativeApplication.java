package com.example.keepitnative;

import java.util.HashMap;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;

import android.app.Application;

public class KeepItNativeApplication extends Application {
	
	// The following line should be changed to include the correct property id.
	private static final String PROPERTY_ID = "UA-57204587-1";

	//Logging TAG
	private static final String TAG = "MyApp";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
	APP_TRACKER, // Tracker used only in this app.
	GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
	ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public KeepItNativeApplication() {
		super();
	}

	synchronized Tracker getTracker(TrackerName trackerId) {
	if (!mTrackers.containsKey(trackerId)) {

	GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
	Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
	: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(PROPERTY_ID)
	: analytics.newTracker(R.xml.ecommerce_tracker);
	mTrackers.put(trackerId, t);

	}
	return mTrackers.get(trackerId);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, AppConfiguration.PARSE_APPLICATION_ID,
				AppConfiguration.PARSE_CLIENT_KEY);

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

	}

}
