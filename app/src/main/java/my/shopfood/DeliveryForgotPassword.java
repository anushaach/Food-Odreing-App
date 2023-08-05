package my.shopfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class DeliveryForgotPassword extends AppCompatActivity {

    TextInputLayout forgotpassword;

    FirebaseAuth FAuth;
    Button Reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_forgot_password);

        forgotpassword=(TextInputLayout) findViewById(R.id.forgotEmailid);
        Reset=(Button) findViewById(R.id.forgotreset);
        FAuth=FirebaseAuth.getInstance();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog=new ProgressDialog(DeliveryForgotPassword.this);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setCancelable(false);
                mDialog.setMessage("Logging in..");
                mDialog.show();

                FAuth.sendPasswordResetEmail(forgotpassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                         ResuableCodeForAll.ShowAlert(DeliveryForgotPassword.this,"Password has been sent to your Email");
                        }
                        else{
                            mDialog.dismiss();
                           ResuableCodeForAll.ShowAlert(DeliveryForgotPassword.this,"Error",task.getException().getMessage());
                        }
                    }
                });
            }
        });

    }
    }
