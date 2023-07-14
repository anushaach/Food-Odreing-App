package my.shopfood;

import android.app.Dialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class ResuableCodeForAll {

    public static void ShowAlert(ChefRegistration context, String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Dialog dialog = null;
                dialog.dismiss();
            }
        }).setTitle(title).setMessage(message).show();

    }

    public static void ShowAlert(ChefLogin chefLogin, String verification_failed, String message) {
    }

    public static void ShowAlert(Login login, String error, String message) {
    }

    public static void ShowAlert(Registration registration, String error, String message) {
    }

    public static void ShowAlert(Chefsendopt chefsendopt, String error, String message) {
    }

    public static void ShowAlert(Delivery_Login delivery_login, String verification_failed, String s) {
    }

    public static void ShowAlert(Delivery_Registration delivery_registration, String error, String message) {
    }

    public static void ShowAlert(Delivery_sendopt delivery_sendopt, String error, String message) {
    }
}
