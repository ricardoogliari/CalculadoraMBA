package com.example.calculadora.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.example.calculadora.model.Historico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDManager extends SQLiteOpenHelper{

	private static final String BD_NAME = "historico_operacoes";
	private static final int BD_VERSION = 1;
	private static final String BD_TABLE_NAME = "historico";
	private static final String BD_FIELD_DATE = "date";
	private static final String BD_FIELD_HOUR = "hour";
	private static final String BD_FIELD_OPERATION = "operation";
	
	private static final String SQL_CREATE_DB = "create table "+BD_TABLE_NAME+" (" +
			"id integer primary key autoincrement, " +
			BD_FIELD_DATE + " text not null, " +
			BD_FIELD_HOUR + " text not null, " +
			BD_FIELD_OPERATION + " text not null" +
			")";	
	
	public BDManager(Context context) {
		super(context, BD_NAME, null, BD_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase bd) {
		bd.execSQL(SQL_CREATE_DB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}
	
	public void insereOperacao(Historico historico){
		SQLiteDatabase bd = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(BD_FIELD_DATE, historico.data);
		cv.put(BD_FIELD_HOUR, historico.hora);
		cv.put(BD_FIELD_OPERATION, historico.operacao);
		
		bd.insert(BD_TABLE_NAME, null, cv);
		
		bd.close();
	}
	
	public List<Historico> listaOperacoes(){
		SQLiteDatabase bd = getReadableDatabase();
		
		List<Historico> historico = new ArrayList<Historico>();
		
		Cursor cursor = bd.query(BD_TABLE_NAME, null, null, null, null, null, null);
		if (cursor.getCount() > 0){
		cursor.moveToLast();
			do {
				Historico hist = new Historico();
				hist.data = cursor.getString(cursor.getColumnIndex(BD_FIELD_DATE));
				hist.hora = cursor.getString(cursor.getColumnIndex(BD_FIELD_HOUR));
				hist.operacao = cursor.getString(cursor.getColumnIndex(BD_FIELD_OPERATION));
				historico.add(hist);
			} while (cursor.moveToPrevious());
		}
		
		bd.close();
		
		return historico;
	}

	public void clean() {
		SQLiteDatabase bd = getWritableDatabase();
		
		bd.delete(BD_TABLE_NAME, null, null);
		
		bd.close();
	}

}
