package com.example.keepitnative.libs;

import com.example.keepitnative.AppConfiguration;
import com.example.keepitnative.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArrayMenuAdapter extends ArrayAdapter<String> {

	TextView buttonTitle;
	ImageView buttonIcon;
	LinearLayout menuItemRow;
	Context mainActivityContext;

	public ArrayMenuAdapter(Context context, int resource) {
		super(context, resource);
	}

	public ArrayMenuAdapter(Context applicationContext, int resource,
			String[] titles) {
		super(applicationContext, resource, titles);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.menu_item, null);
		}

		String title = this.getItem(position);
		String[] images = this.getContext().getResources()
				.getStringArray(R.array.MenuButtonsIcons);

		int color = this
				.getContext()
				.getResources()
				.getIdentifier("array/" + AppConfiguration.COLOR_THEME, null,
						this.getContext().getPackageName());
		String[] colorTheme = this.getContext().getResources()
				.getStringArray(color);

		if (title != null) {
			buttonIcon = (ImageView) v.findViewById(R.id.menuButtonIcon);
			buttonTitle = (TextView) v.findViewById(R.id.menuButtonText);
			menuItemRow = (LinearLayout) v.findViewById(R.id.menuItemRow);
			buttonTitle.setText(title);
			menuItemRow.setBackgroundColor(Color
					.parseColor(colorTheme[position + 3]));

			String imageUri = "drawable/" + images[position];

			try {
				int imageResource = this
						.getContext()
						.getResources()
						.getIdentifier(imageUri, null,
								this.getContext().getPackageName());
				Drawable image = this.getContext().getResources()
						.getDrawable(imageResource);
				buttonIcon.setImageDrawable(image);
			} catch (Exception e) {
				buttonIcon.setVisibility(ImageView.GONE);
			}
		}

		return v;
	}

}
