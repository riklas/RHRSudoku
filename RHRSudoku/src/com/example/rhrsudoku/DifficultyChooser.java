package com.example.rhrsudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class DifficultyChooser extends Activity {

	int difficulty = -1;		//difficulty level. 0 ~ kids. 1 ~ easy. 2 ~ medium. 3 ~ hard
	public final static String DIFFICULTY = "difficulty"; //key to use when retrieving difficulty form other activity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_dfchooser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void setKids(View view) {
		difficulty = 0;
		createIntent(difficulty);		
	}
	
	public void setEasy(View view) {
		difficulty = 1;
		createIntent(difficulty);
	}
	
	public void setMedium(View view) {
		difficulty = 2;
		createIntent(difficulty);
	}
	
	public void setHard(View view) {
		difficulty = 3;
		createIntent(difficulty);
	}
	
	public void createIntent(int difficulty) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(DIFFICULTY, difficulty);
		startActivity(intent);
	}
	
}
