package com.example.airplane;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameOver extends Activity implements OnClickListener {

	private Button btnORepeatGame;
	private Button btnOMainMenu;
	private Button btnOExit;
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gameover);
		

		btnORepeatGame = (Button) findViewById(R.id.xml_replay);
		btnOMainMenu = (Button) findViewById(R.id.xml_btnMainMenu1);
		btnOExit = (Button) findViewById(R.id.xml_btnExit3);

		btnORepeatGame.setOnClickListener(this);
		btnOExit.setOnClickListener(this);
		btnOMainMenu.setOnClickListener(this);
		
	}
	public void Thoat() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Thoát");
		builder.setMessage("Bạn muốn thoát khỏi trò chơi ?");
		builder.setNegativeButton("Đồng ý",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						GameOver.this.finish();
					}
				});
		builder.setPositiveButton("Không",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent i ;
		switch (id) {
		case R.id.xml_replay:
			i = new Intent(GameOver.this, Main_Game.class);
			this.startActivity(i);
			this.finish();
			break;
		case R.id.xml_btnMainMenu1:
			i = new Intent(GameOver.this, MainActivity.class);
			this.startActivity(i);
			this.finish();
			break;
		case R.id.xml_btnExit3:
			Thoat();
			break;

		default:
			break;
		}
	}

}
