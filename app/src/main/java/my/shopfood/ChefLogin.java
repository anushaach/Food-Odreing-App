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

public class ChefLogin extends AppCompatActivity {

    TextInputLayout email, pass;
    Button Signout, Signinphone;
    TextView Forgotpassword, signup;
    FirebaseAuth FAuth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);

        try {
            email = (TextInputLayout) findViewById(R.id.Lemail);
            pass = (TextInputLayout)  findViewById(R.id.Lpassword);
            Signout = (Button) findViewById(R.id.button4);
            signup = (TextView) findViewById(R.id.textView3);
            Forgotpassword =(TextView) findViewById(R.id.forgotpass);
            Signinphone = (Button) findViewById(R.id.btnphone);

            FAuth = FirebaseAuth.getInstance();

            Signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(ChefLogin.this);
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
                                        Toast.makeText(ChefLogin.this, "Congratulation! You Have Successfully Login", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(ChefLogin.this, ChefFoodPanel_BottomNavigation.class);

                                        startActivity(z);
                                        finish();
                                    }else{
                                        ResuableCodeForAll.ShowAlert(ChefLogin.this,"Verification Failed","You Have Not Verified Your Email");

                                    }
                                }else{
                                    mDialog.dismiss();
                                   ResuableCodeForAll.ShowAlert(ChefLogin.this,"Error",task.getException().getMessage());
                                }


                            }
                        });
                    }

                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ChefLogin.this,ChefRegistration.class));
                    finish();
                }
            });

            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ChefLogin.this,ChefForgotPassword.class));
                    finish();
                }
            });
            Signinphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ChefLogin.this,Chefloginphone.class));
                    finish();
                }
            });


        }
        catch (Exception e){
            Toast.makeText(this, "e.getMessage()", Toast.LENGTH_LONG).show();
        }


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