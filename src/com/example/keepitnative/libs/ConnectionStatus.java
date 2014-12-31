package com.example.keepitnative.libs;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.keepitnative.MainActivity;

public class ConnectionStatus {

	public static boolean checkInternetConnection() {
		Context context = MainActivity.mainActivityContext;

		ConnectivityManager con_manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (con_manager.getActiveNetworkInfo() != null
				&& con_manager.getActiveNetworkInfo().isAvailable()
				&& con_manager.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}