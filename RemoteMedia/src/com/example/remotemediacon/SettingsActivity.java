package com.example.remotemediacon;

import com.listmedia.classes.Conexao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conexao);
	}
	
	public void conectar(View view) {
		EditText text = (EditText) findViewById(R.id.ipserver);
		Conexao.ip = text.getText().toString();
	}
	
	public void fechar(View view) {
		Intent intent = new Intent(SettingsActivity.this, AndroidListViewActivity.class);
		startActivity(intent);
	}
}
