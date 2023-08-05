package my.shopfood.customerFoodPanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import my.shopfood.R;
import my.shopfood.ResuableCodeForAll;

public class CustomerForgotpassword extends AppCompatActivity {

    TextInputLayout emailid;
    Button Reset;
    FirebaseAuth Fauth;
    private ResuableCodeForAll ReusableCodeForAll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_forgot_password);
        emailid=(TextInputLayout)findViewById(R.id.email);
        Reset=(Button)findViewById(R.id.reset);

        Fauth=FirebaseAuth.getInstance();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fauth.sendPasswordResetEmail(emailid.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerForgotpassword.this);
                            builder.setMessage("Password has been sent to your Email");
                            builder.setCancelable(false);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    Intent bb=new Intent(CustomerForgotpassword.this, customerPassword.class);
                                    startActivity(bb);

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {

                            ReusableCodeForAll.ShowAlert(CustomerForgotpassword.this,"Error",task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }
}




