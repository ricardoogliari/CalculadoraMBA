package com.example.calculadora;

import java.util.List;

import com.example.calculadora.model.Historico;
import com.example.calculadora.sqlite.BDManager;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaHistorico extends ActionBarActivity{

	private ListView lv;
	private ArrayAdapter<Historico> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<Historico> historicos = ((GlobalApplication)getApplication()).bdManager.listaOperacoes();
		
		adapter = new ArrayAdapter<Historico>(this, android.R.layout.simple_list_item_1, historicos);
		lv = new ListView(this);
		lv.setAdapter(adapter);
		
		setContentView(lv);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_clean) {
			((GlobalApplication)getApplication()).bdManager.clean();
			adapter.clear();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}