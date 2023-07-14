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
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Delivery_sendopt extends AppCompatActivity {

    String verificationId;
    FirebaseAuth FAuth;
    Button verify, Resend;
    TextView txt;
    EditText entercode;
    String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_sendopt);

        phoneno = getIntent().getStringExtra("phonenumber").trim();


        entercode = (EditText) findViewById(R.id.code1);
        txt = (TextView) findViewById(R.id.text1);
        Resend = (Button) findViewById(R.id.Resendotp1);
        verify=(Button) findViewById(R.id.Verify1) ;
        FAuth = FirebaseAuth.getInstance();
        Resend.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);


        sendVerificationcode(phoneno);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = entercode.getText().toString().trim();
                Resend.setVisibility(View.INVISIBLE);

                if (code.isEmpty() && code.length()<6){
                    entercode.setError("Enter code");
                    entercode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
        new CountDownTimer(60000, 1000){

            @Override
            public void onTick(long l) {
                txt.setVisibility(View.VISIBLE);
                txt.setText("Resend Code Within "  + l / 1000 + "  Seconds");
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
                Resendotp(phoneno);



                new CountDownTimer(60000, 1000){

                    @Override
                    public void onTick(long l) {
                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Resend Code Within" + l/1000 + "Seconds");
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

    private void Resendotp(String phoneno) {

        sendVerificationcode(phoneno);

    }

    private void sendVerificationcode(String phoneno) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneno,
                60,
                TimeUnit.SECONDS,
                (Activity) TaskExecutors.MAIN_THREAD,
                mCallback
        );


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

            Toast.makeText(Delivery_sendopt.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
        SignInWithPhone(credential);
    }

    private void SignInWithPhone(PhoneAuthCredential credential) {

        FAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(Delivery_sendopt.this,DeliveryFoodPanel_BottomNavigation.class));

                        }else {
                         ResuableCodeForAll.ShowAlert(Delivery_sendopt.this,"Error",task.getException().getMessage());
                        }

                    }
                });

    }
}