package com.example.calculadora;

import java.util.Calendar;
import java.util.Date;

import com.example.calculadora.model.Historico;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends ActionBarActivity {

	private EditText edtResult;
	
	private boolean lastActionIsNumber;
	private boolean numberHasPoint;
	
	private int maxNrCasasDecimais;
	private int actualNrCasasDecimais;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		edtResult = (EditText) findViewById(R.id.edtResult);
	}
	
	@Override
	protected void onResume() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String nrCasasDecimaisStr = sp.getString("nr_casas_decimais", null);
		try {
			maxNrCasasDecimais = nrCasasDecimaisStr == null? 0 : Integer.parseInt(nrCasasDecimaisStr);
		} catch (NumberFormatException e){
			Toast.makeText(this, "O número de casas decimais informado nas configurações é muito grande!",
					Toast.LENGTH_LONG).show();
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, Configuracoes.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.action_history) {
			Intent intent = new Intent(this, ListaHistorico.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void clickOnC(View v){
		edtResult.setText("");
		lastActionIsNumber = false;
		numberHasPoint = false;
	}
	
	public void checkNumber(String number){
		if (lastActionIsNumber){
			if (numberHasPoint){
				if (actualNrCasasDecimais < maxNrCasasDecimais){
					edtResult.setText(edtResult.getText() + number);
					actualNrCasasDecimais++;
				}
			} else {
				edtResult.setText(edtResult.getText() + number);
			}
		} else {
			lastActionIsNumber = true;
			edtResult.setText(edtResult.getText() + number);
		}
	}
	
	public void clickOnSeven(View v){
		checkNumber("7");
	}
	
	public void clickOnEight(View v){
		checkNumber("8");
	}
	
	public void clickOnNine(View v){
		checkNumber("9");
	}

	public void clickOnDivide(View v){
		if (lastActionIsNumber){
			edtResult.setText(edtResult.getText() + " / ");
			initialState();
		}
	}

	public void clickOnFour(View v){
		checkNumber("4");
	}
	
	public void clickOnFive(View v){
		checkNumber("5");
	}
	
	public void clickOnSix(View v){
		checkNumber("6");
	}
	
	public void clickOnMultiple(View v){
		if (lastActionIsNumber){
			edtResult.setText(edtResult.getText() + " * ");
			initialState();
		}
	}
	
	public void initialState(){
		lastActionIsNumber = false;
		actualNrCasasDecimais = 0;
		numberHasPoint = false;
	}
	
	public void clickOnOne(View v){
		checkNumber("1");
	}
	
	public void clickOnTwo(View v){
		checkNumber("2");
	}
	
	public void clickOnThree(View v){
		checkNumber("3");
	}
	
	public void clickOnSubtraction(View v){
		if (lastActionIsNumber){
			edtResult.setText(edtResult.getText() + " - ");
			initialState();
		}
	}
	
	public void clickOnPoint(View v){
		if (lastActionIsNumber == true && !numberHasPoint){
			edtResult.setText(edtResult.getText() + ".");
			numberHasPoint = true;
		}
	}
	
	public void clickOnZeo(View v){
		checkNumber("0");
	}
	
	public void clickOnEqual(View v){
		if (lastActionIsNumber){
			String expressao = edtResult.getText().toString();
			if (expressao.trim().length() == 0){
				Toast.makeText(this, "Expressão vazia", 5000).show();
			} else {
				Historico historico = new Historico();
				historico.operacao = expressao;
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int dia = cal.get(Calendar.DAY_OF_MONTH);
				int mes = cal.get(Calendar.MONTH) + 1;
				int ano = cal.get(Calendar.YEAR);
				int hora = cal.get(Calendar.HOUR_OF_DAY);
				int minuto = cal.get(Calendar.MINUTE);
				int segundo = cal.get(Calendar.SECOND);
				int semana = cal.get(Calendar.DAY_OF_WEEK);
				String semanaTxt = "";
				switch (semana){
					case 1:
						semanaTxt = "Domingo";
						break;
					case 2:
						semanaTxt = "Segunda-Feira";
						break;
					case 3:
						semanaTxt = "Terca-Feira";
						break;
					case 4:
						semanaTxt = "Quarta-Feira";
						break;
					case 5:
						semanaTxt = "Quinta-Feira";
						break;
					case 6:
						semanaTxt = "Sexta-Feira";
						break;
					case 7:
						semanaTxt = "Sábado";
				}
				historico.data = (dia < 10 ? "0" + dia : "" + dia) + "/" +  
						(mes < 10 ? "0" + mes : "" + mes) + "/" + 
						ano + " " + semanaTxt;
				historico.hora = (hora < 10 ? "0" + hora : "" + hora) + ":" +  
						(minuto < 10 ? "0" + minuto : "" + minuto) + ":" + 
						(segundo < 10 ? "0" + segundo : "" + segundo);
				((GlobalApplication)getApplication()).bdManager.insereOperacao(historico);
				String[] partes = expressao.split(" ");
				float operador1 = 0;
				float operador2 = 0;
				String operando = null;
				for (int i = 0; i < partes.length; i++){
					if (i % 2 == 0){
						if (operando == null){
							operador1 = Float.parseFloat(partes[i]);
						} else {
							operador2 = Float.parseFloat(partes[i]);
							
							switch (operando){
								case "+":
									operador2 = operador1 + operador2;
									break;
								case "-":
									operador2 = operador1 - operador2;
									break;
								case "/":
									operador2 = operador1 / operador2;
									break;
								case "*":
									operador2 = operador1 * operador2;
							}
							
							operando = null;
						}
					} else {
						operando = partes[i];
					}
				}
				
				String resultado = String.format("%."+maxNrCasasDecimais+"f", operador2).replace(',', '.');
				if (resultado.indexOf(".") > 0){
					while (resultado.charAt(resultado.length() - 1) == '0'){
						resultado = resultado.substring(0, resultado.length() - 1);
					}
					
					while (resultado.charAt(resultado.length() - 1) == '.'){
						resultado = resultado.substring(0, resultado.length() - 1);
					}
					
					actualNrCasasDecimais = 0;
					numberHasPoint = false;
				}
				
				if (resultado.indexOf(".") > 0){
					actualNrCasasDecimais = resultado.length() - resultado.indexOf(".") - 1;
					numberHasPoint = true;
				}
				
				edtResult.setText(resultado);
			}
		} else {
			Toast.makeText(this, "Expressão incompleta", 5000).show();
		}
	}
	
	public void clickOnSum(View v){
		if (lastActionIsNumber){
			edtResult.setText(edtResult.getText() + " + ");
			initialState();
		}
	}
}
