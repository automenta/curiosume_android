package org.netention.curiosume;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ClassifyTagActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify_tag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.classify_tag, menu);
		return true;
	}

}
