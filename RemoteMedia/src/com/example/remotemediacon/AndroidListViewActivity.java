package com.example.remotemediacon;

import java.util.ArrayList;
import java.util.List;

import com.listmedia.classes.TcpClient;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class AndroidListViewActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Generate list View from ArrayList

		List<String> countryList = null;
		TcpClient cl = new TcpClient();

		countryList = cl.start("lista");

		if(countryList == null) {
			countryList = new ArrayList<String>();
			countryList.add("Lista Vazia");
		}
		displayListView(countryList);

	}

	private List<String> getLista(String path) {
		List<String> lista = null;
		TcpClient cl = new TcpClient();
		lista = cl.start(path);
		if (lista == null) {
			lista = templist;
		}
		return lista;
	}

	List<String> listas = new ArrayList<String>();

	List<String> templist;

	private void displayListView(List<String> countryList) {

		templist = countryList;

		// create an ArrayAdaptar from the String Array
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.country_list, countryList);
		setListAdapter(dataAdapter);

		ListView listView = getListView();
		// enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String path = ((TextView) view).getText().toString();
				if(path.equalsIgnoreCase("Lista Vazia")) {
					showToast("Verifique a conexão");
				}
				displayListView(getLista(path));
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.fullscreen:
			TcpClient cl = new TcpClient();
			cl.start("setFullScreenOn");
			return true;
		case R.id.action_settings:
			Intent intent = new Intent(AndroidListViewActivity.this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void showToast(CharSequence text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
