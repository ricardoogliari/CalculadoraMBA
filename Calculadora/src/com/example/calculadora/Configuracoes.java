package com.example.calculadora;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Configuracoes extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_configuracoes);
	}

}
