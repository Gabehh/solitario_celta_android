package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PuntuacionAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Puntuacion> score;
    private int resource;

    public PuntuacionAdapter(Context context, ArrayList<Puntuacion> score, int resource) {
        super(context, resource, score);
        this.context = context;
        this.score = score;
        this.resource = resource;
        setNotifyOnChange(true);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resource, null);
        }
        Puntuacion puntuacion = score.get(position);
        if (puntuacion != null) {
            TextView tvNombre = convertView.findViewById(R.id.tvName);
            tvNombre.setText(puntuacion.getName());
            TextView tvTamanio = convertView.findViewById(R.id.tvDate);
            tvTamanio.setText(String.valueOf(puntuacion.getDate()));
            TextView tvPrecio = convertView.findViewById(R.id.tvFichas);
            tvPrecio.setText(String.valueOf(puntuacion.getFichas()));
        }
        return convertView;
    }


}


