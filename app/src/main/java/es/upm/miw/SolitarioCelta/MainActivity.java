package es.upm.miw.SolitarioCelta;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.content.Context;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.jetbrains.annotations.NotNull;



public class MainActivity extends AppCompatActivity {

	SCeltaViewModel miJuego;
    public final String LOG_KEY = "MiW";
    private final int LONGITUD_MENSAJE = 140; // Máxima longitud mensajes
    private SharedPreferences preferencias;
    private DBManager bd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        bd = new DBManager(this);
        mostrarTablero();
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(@NotNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        Log.i(LOG_KEY, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuego.jugar(i, j);
        Log.i(LOG_KEY, "#fichas=" + miJuego.numeroFichas());

        mostrarTablero();
        if (miJuego.juegoTerminado()) {
            Puntuacion puntuacion = new Puntuacion(GetUser(),GetDate(),this.miJuego.numeroFichas());
            if(bd.AddItem(puntuacion)==-1) this.ShowMessage(getString(R.string.guarduarPuntuacionError));
            new AlertDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    button.setChecked(miJuego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }

        TextView textCount = findViewById(R.id.countFichas) ;
        textCount.setText(String.valueOf(this.miJuego.numeroFichas()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                new AlertDialogRestart().show(getFragmentManager(), "ALERT_DIALOG");
                return true;
            case R.id.opcGuardarPartida:
                this.SaveGame();
                return true;
            case R.id.opcRecuperarPartida:
                if(this.miJuego.numeroFichas()==32)
                    this.RestoreGame();
                else
                    new AlertDialogRestore().show(getFragmentManager(), "ALERT_DIALOG");
                return true;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, History.class));
                return true;
            default:
                this.ShowMessage(getString(R.string.txtSinImplementar));
        }
        return true;
    }

    private boolean UsedMemorySD() {
        return getResources().getBoolean(R.bool.default_prefTarjetaSD);
    }

    private String GetNameFile() {
        return getString(R.string.default_NombreFich);
    }

    private String GetUser() {
        return preferencias.getString(
                getString(R.string.key_NameUser),
                getString(R.string.default_NameUser)
        );
    }

    private String GetDate(){
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.DAY_OF_MONTH)) + "-"
                + (calendar.get(Calendar.MONTH)+1) + "-"
                + (calendar.get(Calendar.YEAR)) + " "
                + (calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + (calendar.get(Calendar.MINUTE));
    }

    private void ShowMessage(String message){
        Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
        ).show();
    }

    public void SaveGame(){
        try{
            String gameSerialized = miJuego.serializaTablero();
            FileOutputStream fos;
            if (!UsedMemorySD()) {
                File dir = getFilesDir();
                File file = new File(dir, GetNameFile());
                if(file.exists()) file.delete();
                fos = openFileOutput(GetNameFile(), Context.MODE_APPEND);
            } else {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = getExternalFilesDir(null) + "/" + GetNameFile();
                    File file = new File(path);
                    if(file.exists()) file.delete();
                    fos = new FileOutputStream(path, true);
                } else {
                    this.ShowMessage(getString(R.string.txtErrorMemExterna));
                    return;
                }
            }
            fos.write(gameSerialized.getBytes());
            fos.close();
            this.ShowMessage(getString(R.string.guardarPartida));
        }catch (Exception ex) {
            this.ShowMessage(ex.getMessage());
        }
    }

    public void RestoreGame(){
        try{
            BufferedReader fin;
            if (!UsedMemorySD()) {
                File dir = getFilesDir();
                File file = new File(dir, GetNameFile());
                if(file.exists()){
                    fin = new BufferedReader(
                        new InputStreamReader(openFileInput(GetNameFile())));
                }
                else{
                    throw new Exception(getString(R.string.txtErrorMemExterna));
                }
            } else {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = getExternalFilesDir(null) + "/" + GetNameFile();
                    File file = new File(path);
                    if(file.exists()){
                        fin = new BufferedReader(new FileReader(new File(path)));
                    }
                    else{
                        throw new Exception(getString(R.string.errorRestore));
                    }
                } else {
                    this.ShowMessage(getString(R.string.txtErrorMemExterna));
                    return;
                }
            }
            String line = fin.readLine();
            fin.close();
            this.miJuego.deserializaTablero(line);
            this.mostrarTablero();
        }catch (Exception ex) {
            this.ShowMessage(ex.getMessage());
        }
    }

}
