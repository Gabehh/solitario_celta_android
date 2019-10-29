package es.upm.miw.SolitarioCelta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private DBManager bd;
    private ArrayList<Puntuacion> list;
    private SharedPreferences preferencias;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        bd = new DBManager(this);
        this.GetRanking();
    }

    public void Delete(View view)
    {
        if(bd.Count()>0)
            new AlertDialogDelete().show(getFragmentManager(), "ALERT_DIALOG");
        else
            this.ShowMessage(getString(R.string.txtMensajeoBorrarError));
    }

    public void Clear(){
        bd.Delete();
        this.GetRanking();
    }

    private void GetRanking(){
        list = bd.GetAll(!RankFlag());
        ListView listScore = findViewById(R.id.lvListScore);
        listScore.setAdapter(new PuntuacionAdapter(
                this,
                list,
                R.layout.list_score
        ));
    }

    private void ShowMessage(String message){
        Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
        ).show();
    }

    private boolean RankFlag() {
        return preferencias.getBoolean(
                getString(R.string.key_Ranking),
                getResources().getBoolean(R.bool.default_prefRanking));
    }
}

