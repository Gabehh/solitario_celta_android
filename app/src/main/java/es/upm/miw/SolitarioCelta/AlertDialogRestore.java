package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertDialogRestore extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoRestaurarTitulo)
                .setMessage(R.string.txtDialogoRestaurarPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoRestaurarAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.RestoreGame();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoRestaurarNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.mostrarTablero();
                            }
                        }
                );

		return builder.create();
	}
}
