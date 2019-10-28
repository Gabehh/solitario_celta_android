package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private DBManager bd;
    private ArrayList<Puntuacion> list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
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
        list = bd.GetAll();
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
}

