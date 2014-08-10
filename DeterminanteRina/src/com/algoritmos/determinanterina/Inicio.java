package com.algoritmos.determinanterina;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class Inicio extends Activity {
	
	private static final String PREFS_NAME = "settings";
	Spinner itemsS;
	List<String> spinnerArray;
	EditText datosMatr;
	Button enterBtn;
	EditText multiLine;
    private SharedPreferences mShared;
    private SharedPreferences.Editor editor;
    private static String KEY_FIRST_RUN = "";
	
	// Variables regurales
    private int matrix1[][]; 
    private int matrix2[][];
    private int nP;
    private int posM = 1;
    private int f = 0; 
    private int c = 0; 
    private boolean fin = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        
        enterBtn = (Button) findViewById(R.id.button1);
        datosMatr = (EditText) findViewById(R.id.editText1);
        multiLine = (EditText) findViewById(R.id.editText2);
        
        keyPressed(datosMatr);
        
        spinnerArray = new ArrayList<String>();
        spinnerArray.add("2");
        spinnerArray.add("3");
        spinnerArray.add("4");
        spinnerArray.add("5");
        spinnerArray.add("6");
        spinnerArray.add("7");
        spinnerArray.add("8");
        spinnerArray.add("9");
        
        mShared = getSharedPreferences(PREFS_NAME, 0);
        
        if (!mShared.contains("KEY_FIRST_RUN")) {
            welcomeDialog();
            KEY_FIRST_RUN = "firstrun";
        } 
        
        

        editor = mShared.edit();
        editor.putString("KEY_FIRST_RUN", KEY_FIRST_RUN);
        editor.apply();

        //String p = mShared.getString("KEY_FIRST_RUN", "VACIO").toString();
        
       /* editor.putString("prueba", "Luis Martinez");
        editor.apply();*/
        
        //Toast.makeText(getApplicationContext(), mShared.getString("prueba", "EMPTY"), Toast.LENGTH_LONG).show();
        
/*        editor.remove("KEY_FIRST_RUN");
        editor.apply();*/
        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        itemsS = (Spinner) findViewById(R.id.ordenMa);
        itemsS.setPrompt("Seleccione un orden");
        itemsS.setAdapter(adapter);
        
        multiLine.setEnabled(false);
       	datosMatr.setEnabled(false);
       	datosMatr.setHint("Uhm.. Aun no estamos listos");

  
        itemsS.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				selected(itemsS.getItemAtPosition(position).toString());
                restart();
                labelSetFilaColumna();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				labelSetFilaColumna();
				return;
			}
        
			
        });

    }
    
    public void welcomeDialog() {
        DialogFragment newFragment = new WelcomeDialog();
        newFragment.show(getFragmentManager(), "tag");
    }
    
    private void keyPressed(EditText editText) {
    	editText.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
    	        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
    	            (keyCode == KeyEvent.KEYCODE_ENTER)) {
    	          // Perform action on key press
    	        	enterNumber(v);
    	        	  	        	
    	        	requestFocus();
    	        	return true;
    	        }
    	        return false;
			}

    	});
		
	}
    
    private void requestFocus() {
    	datosMatr.setFocusableInTouchMode(true);
		datosMatr.requestFocus();
	}

	public void enterNumber(View v) {
        
        String entrada = datosMatr.getText().toString();
        if (!datosMatr.getText().toString().isEmpty()) {
            guardarenMatriz(Integer.parseInt(entrada), posM);
            datosMatr.getText().clear();

            if (isMatrixFull()) {
                isCompleted();
                if(posM==2) {
                	limpiar();
                }
            }     
            labelSetFilaColumna();
        }
        
        requestFocus();

    }
    
    void guardarenMatriz(int numero, int posMatrix) {
        if(f<nP) {
            if (c<nP) {
                switch(posMatrix) {
                    case 1: {
                        matrix1[f][c] = numero;
                        break;
                    }
                    case 2: {
                        matrix2[f][c] = numero;
                        break;
                    }
                }  
                //System.out.println("Matriz#"+posMatrix+"["+f+"]["+c+"]: "+numero);   
                c++;
            }
                if (nP==c) {
                    f++; c=0;
                } 
                if (nP==f) {
                    fin = true;
                }
        }
    }
    
    public void selected(String s) {
        createMatrix(Integer.parseInt(s));
        limpiar();
    }
    
    void limpiar() {

        datosMatr.setEnabled(true);
        datosMatr.setHint("Digite numero");;
        enterBtn.setEnabled(true);
        f = 0;
        c = 0;
        
    }
    
    void isCompleted() {

                datosMatr.getText().clear();
                enterBtn.setEnabled(false);
                datosMatr.setEnabled(false);
                datosMatr.setHint("Uff, llenamos las matrices");
                f=0;c=0;
                posM++;
                if (posM==3) {
                procesos();
                 }
    }
    

    public void createMatrix(int n) {
        matrix1 = new int[n][n];
        matrix2 = new int[n][n];
        nP= n;
    }

    boolean isMatrixFull() {
        return fin && f==nP;
        
    }
    
    private void restart() {
        f=0;
        c=0;
        posM=1;
        datosMatr.setEnabled(true);
        datosMatr.setHint("Digite numero (matriz #"+posM+")");
        enterBtn.setEnabled(true);
        multiLine.getText().clear();
        
        
    }

    private void labelSetFilaColumna() {

        if (posM!=3) {
            multiLine.setText("Matriz #"+posM+" [Fila: "+(f+1)+"][Columna: "+(c+1)+"]");
        } 
    } 


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void procesos() {
        
        multiLine.getText().clear();
        
        multiLine.append("\nDeterminate #1: "+ Matrices.determinante(matrix1));
        multiLine.append("\nDeterminate #2: "+ Matrices.determinante(matrix2));
        multiLine.append("\n");
    
     if (nP%2==0) {
        int[] suma1 = new int[matrix1.length];
        suma1 = Matrices.sumaFila(suma1, matrix1);
        
        int[] suma2 = new int[matrix2.length];
        suma2 = Matrices.sumaFila(suma2, matrix2);
        
        int[] producto1 = new int[matrix1.length];
        producto1 = Matrices.llenarVector(producto1, 1);
        producto1 = Matrices.productoColumna(producto1, matrix1);
        
        int[] producto2 = new int[matrix2.length];
        producto2= Matrices.llenarVector(producto2, 1);
        producto2 = Matrices.productoColumna(producto2, matrix2);
        
        multiLine.append("\nSuma de filas para determinantes :");
        multiLine.append("\nVector determinante 1: " + Arrays.toString(suma1));
        multiLine.append("\nVector determinante 2: " + Arrays.toString(suma2));
        multiLine.append("\n");
        multiLine.append("\nProducto de columnas para determinantes :");
        multiLine.append("\nVector determinante 1: " + Arrays.toString(producto1));
        multiLine.append("\nVector determinante 2: " + Arrays.toString(producto2));
        multiLine.append("\n");


    }
    if (nP%2!=0 && nP%3==0) {
        
        double sumaDeterminantes = Matrices.determinante(matrix1)+Matrices.determinante(matrix2);

        
        double restaDeterminantes = Matrices.determinante(matrix1)-Matrices.determinante(matrix2);
        
        multiLine.append("\nSuma de determinantes \n");
        multiLine.append(Double.toString(sumaDeterminantes));
        multiLine.append("\n");
        multiLine.append("\nResta de determinantes \n");
        multiLine.append(Double.toString(restaDeterminantes));
        multiLine.append("\n");
    }
    if (nP==2 || nP==3) {
        double productoDeterminantes = Matrices.determinante(matrix1)*Matrices.determinante(matrix2);
        
        double divisionDeterminantes;
        if (Matrices.determinante(matrix2)!=0) {
            divisionDeterminantes = Matrices.determinante(matrix1)/Matrices.determinante(matrix2);
        } else {
            divisionDeterminantes = 0;
        }   
        
        
        multiLine.append("\nProducto de determinantes: \n");
        multiLine.append(Double.toString(productoDeterminantes));
        multiLine.append("\n");
        multiLine.append("\nDivision de determinantes: \n");
        multiLine.append(Double.toString(divisionDeterminantes));
        multiLine.append("\n");
    }
    if (nP>=4 && nP<=7) {
        

        multiLine.append("\nDeterminante con raiz: ");
        if (Matrices.sumaInternaVector(Matrices.sumaFila(new int[matrix1.length], matrix1))>Matrices.productoInternaVector(Matrices.productoColumna(new int[matrix1.length], matrix1))) {
            int [][] matrizNewS1 = Matrices.cambiarMatriz(matrix1, 1);
            double determinanteSuma1 = Matrices.determinante(matrizNewS1);
            multiLine.append("\n");
            multiLine.append(Double.toString(determinanteSuma1));
       
        }
        if (Matrices.sumaInternaVector(Matrices.sumaFila(new int[matrix2.length], matrix2))>Matrices.productoInternaVector(Matrices.productoColumna(new int[matrix2.length], matrix1))) {
            int [][] matrizNewS2 = Matrices.cambiarMatriz(matrix2, 1);
            double determinanteSuma2 = Matrices.determinante(matrizNewS2);

            multiLine.append("\n");
            multiLine.append(Double.toString(determinanteSuma2));
        }
        
        multiLine.append("\n");
        multiLine.append("\nDeterminante con logaritmo: ");
        
        if (Matrices.productoInternaVector(Matrices.productoFila(new int[matrix1.length], matrix1))<Matrices.sumaInternaVector(Matrices.sumaColumna(new int[matrix1.length], matrix1))) {
            int [][] matrizNewP1 = Matrices.cambiarMatriz(matrix1, 2);
            double determinanteProducto1 = Matrices.determinante(matrizNewP1);

            multiLine.append("\n");
            multiLine.append(Double.toString(determinanteProducto1));
        } 
        if (Matrices.productoInternaVector(Matrices.productoFila(new int[matrix2.length], matrix2))<Matrices.sumaInternaVector(Matrices.sumaColumna(new int[matrix1.length], matrix1))) {
            int [][] matrizNewP2 = Matrices.cambiarMatriz(matrix2, 2);
            double determinanteProducto2 = Matrices.determinante(matrizNewP2);

            multiLine.append("\n");
            multiLine.append(Double.toString(determinanteProducto2));
        }
       
    }
}


   

}
