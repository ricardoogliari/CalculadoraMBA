package com.example.calculadora;

import com.example.calculadora.sqlite.BDManager;

import android.app.Application;

public class GlobalApplication extends Application{

	public BDManager bdManager;
	
	@Override
	public void onCreate() {
		bdManager = new BDManager(this);
		super.onCreate();
	}
	
}
