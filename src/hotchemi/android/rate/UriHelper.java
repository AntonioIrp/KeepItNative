package hotchemi.android.rate;

import com.example.keepitnative.AppConfiguration;

import android.net.Uri;

final class UriHelper {

	private static final String GOOGLE_PLAY = AppConfiguration.MY_APP_GOOGLE_PLAY_LINK;

	private UriHelper() {
	}

	static Uri getGooglePlay(String packageName) {
		return packageName == null ? null : Uri
				.parse(GOOGLE_PLAY + packageName);
	}

}