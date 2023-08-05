package my.shopfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Delivery_Login extends AppCompatActivity {

    TextInputLayout email, pass;
    Button Signin, Signinphone;
    TextView Forgotpassword, signup;
    FirebaseAuth FAuth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_login);


            email = (TextInputLayout) findViewById(R.id.Demail);
            pass = (TextInputLayout)  findViewById(R.id.Dpassword);
            Signin = (Button) findViewById(R.id.Loginbtn);
            signup = (TextView) findViewById(R.id.donot);
            Forgotpassword =(TextView) findViewById(R.id.Dforgotpass);
            Signinphone = (Button) findViewById(R.id.Dbtnphone);

            FAuth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(Delivery_Login.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.............");
                        mDialog.show();

                        FAuth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(FAuth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(Delivery_Login.this, "Congratulation! You Have Successfully Login", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(Delivery_Login.this, DeliveryFoodPanel_BottomNavigation.class);

                                        startActivity(z);
                                        finish();
                                    }else{
                                        ResuableCodeForAll.ShowAlert(Delivery_Login.this,"Verification Failed","You Have Not Verified Your Email");

                                    }
                                }else{
                                    mDialog.dismiss();
                                    ResuableCodeForAll.ShowAlert(Delivery_Login.this,"Error",task.getException().getMessage());
                                }


                            }
                        });
                    }

                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Register=new Intent(Delivery_Login.this,Delivery_Registration.class);
                    startActivity(Register);
                    finish();
                }
            });

            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Delivery_Login.this,DeliveryForgotPassword.class));
                    finish();
                }
            });
            Signinphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent q=new Intent(Delivery_Login.this,Delivery_Loginphone.class);
                    startActivity(q);
                    finish();
                }
            });


        }



    String emailpattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

    public  boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isValid = false, isValidemail= false, isValidpassword = false;
        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail=true;
            }else {
                email.setErrorEnabled(true);
                email.setError("Invalid Email Address");
            }
        }
        if (TextUtils.isEmpty(pwd)){
            pass.setErrorEnabled(true);
            pass.setError("Password is Required");

        }else {
            isValidpassword=true;
        }
        isValid=(isValidemail && isValidpassword)?true:false;
        return isValid;
    }
}