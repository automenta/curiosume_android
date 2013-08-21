package org.netention.curiosume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	public static final String TARGET_USER = "org.netention.TargetUser";

	private Intent getInventoryIntent(String targetUser) {
		return new Intent(MainActivity.this, InventoryActivity.class).putExtra(TARGET_USER, targetUser);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    ((Button) findViewById(R.id.buttonMainSelf)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(getInventoryIntent("Self"));
            }
        });		
	    ((Button) findViewById(R.id.buttonMainTeam)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(getInventoryIntent(null));
            }
        });
	    ((Button) findViewById(R.id.buttonMainTag)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(MainActivity.this, TagActivity.class));
            }
        });
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
