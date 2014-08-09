package com.algoritmos.determinanterina;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class WelcomeDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());
         
        // Setting Dialog Title
        builder.setTitle(R.string.titleWelcom);
         
        // Setting Dialog Message
        builder.setMessage(R.string.messageWelcom);
         
        // Setting Icon to Dialog
        builder.setIcon(R.drawable.bubbleicon);
         
        // Setting OK Button
        builder.setPositiveButton(R.string.positiveBtn, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog
                
                
            }
        });
		
		return builder.create();
		
		
	}
}
