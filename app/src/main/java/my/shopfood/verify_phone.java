package my.shopfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify_phone extends AppCompatActivity {

    String verificationId;
    FirebaseAuth FAuth;

    Button  Resend;
    TextView txt;
    EditText entercode;
    Button verify;
    String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        FAuth=FirebaseAuth.getInstance();

        phoneno = getIntent().getStringExtra("phonenumberr");
        if (phoneno ==null || phoneno.isEmpty()) {

            Toast.makeText(this, "Phone number", Toast.LENGTH_SHORT).show();
            finish();
        }
        entercode = (EditText) findViewById(R.id.codee);
        txt = (TextView) findViewById(R.id.text);
        Resend = (Button) findViewById(R.id.Resendotpp);
        verify = (Button) findViewById(R.id.Verifyy);


        Resend.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);

        sendVerificationcode(phoneno);
        verify = (Button) findViewById(R.id.Verifyy);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = entercode.getText().toString().trim();

                if (code.isEmpty() ||  code.length()<6){
                    entercode.setError("Enter code");
                    entercode.requestFocus();
                }else {
                    verifyCode(code);
                }
            }
        });
        new CountDownTimer(60000, 1000){

            @Override
            public void onTick(long l) {
                txt.setVisibility(View.VISIBLE);
                txt.setText("Resend Code Within " + l / 1000 + " Seconds");
            }

            @Override
            public void onFinish() {

                Resend.setVisibility(View.VISIBLE);
                txt.setVisibility(View.INVISIBLE);

            }
        }.start();

        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resend.setVisibility(View.INVISIBLE);
               sendVerificationcode(phoneno);


                new CountDownTimer(60000, 1000){

                    @Override
                    public void onTick(long l) {
                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Resend Code Within " + l/1000 + "Seconds");
                    }

                    @Override
                    public void onFinish() {

                        Resend.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.INVISIBLE);
                    }
                }.start();

            }


        });

    }



    private void sendVerificationcode(String phoneno) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phoneno)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallback).build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                entercode.setText(code);
                verifyCode(code);
            }
        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(verify_phone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
        linkCredential(credential);
    }

    private void linkCredential(PhoneAuthCredential credential) {

        FAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(verify_phone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(verify_phone.this,MainMenu.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(verify_phone.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }
}