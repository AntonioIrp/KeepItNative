package hotchemi.android.rate;

import com.example.keepitnative.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

final class DialogManager {

	private DialogManager() {
	}

	static Dialog create(final Context context,
			final boolean isShowNeutralButton,
			final OnClickButtonListener listener, final View view) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.rate_dialog_title);
		// builder.setMessage(R.string.rate_dialog_message);
		if (view != null)
			builder.setView(view);
		builder.setItems(R.array.rate_my_app_dialog,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d("option", String.valueOf(which));
						switch (which) {
						case 0:
							PreferenceHelper.setAgreeShowDialog(context, false);
							if (listener != null)
								listener.onClickButton(which);
							break;
						case 1:
							PreferenceHelper.setRemindInterval(context);
							if (listener != null)
								listener.onClickButton(which);
							break;
						case 2:
							String packageName = context.getPackageName();
							Intent intent = new Intent(Intent.ACTION_VIEW,
									UriHelper.getGooglePlay(packageName));
							context.startActivity(intent);
							PreferenceHelper.setAgreeShowDialog(context, false);
							if (listener != null)
								listener.onClickButton(which);
							break;
						}
					}
				});
		// builder.setPositiveButton(R.string.rate_dialog_ok, new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// String packageName = context.getPackageName();
		// Intent intent = new Intent(Intent.ACTION_VIEW,
		// UriHelper.getGooglePlay(packageName));
		// context.startActivity(intent);
		// PreferenceHelper.setAgreeShowDialog(context, false);
		// if (listener != null) listener.onClickButton(which);
		// }
		// });
		// if (isShowNeutralButton) {
		// builder.setNeutralButton(R.string.rate_dialog_cancel, new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// PreferenceHelper.setRemindInterval(context);
		// if (listener != null) listener.onClickButton(which);
		// }
		// });
		// }
		// builder.setNegativeButton(R.string.rate_dialog_no, new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// PreferenceHelper.setAgreeShowDialog(context, false);
		// if (listener != null) listener.onClickButton(which);
		// }
		// });
		return builder.create();
	}

}