package my.shopfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseOne extends AppCompatActivity {

    Button Chef, Customer, DeliveryPerson;
    Intent intent;
    String type;
    ConstraintLayout bgimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        AnimationDrawable animationDrawable=new AnimationDrawable();

        Context context = this;
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img4),3000);
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img1),3000);
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img4),3000);
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img1),3000);


        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setEnterFadeDuration(1600);

        bgimage=findViewById(R.id.back3);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        intent=getIntent();
        type=intent.getStringExtra("Home").toString().trim();

        Chef = (Button)findViewById(R.id.chef);
        DeliveryPerson=(Button) findViewById(R.id.delivery);
        Customer=(Button) findViewById(R.id.customer);

        Chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    Intent loginemail=new Intent(ChooseOne.this,ChefLogin.class);
                    startActivity(loginemail);
                    finish();

                }
                if(type.equals("Phone")) {
                    Intent loginphone = new Intent(ChooseOne.this, Chefloginphone.class);
                    startActivity(loginphone);
                    finish();
                }
                if(type.equals("SignUp")) {
                    Intent loginRegister = new Intent(ChooseOne.this, ChefRegistration.class);
                    startActivity(loginRegister);
                    finish();
                }

            }
        });

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    Intent loginemailcust=new Intent(ChooseOne.this,Login.class);
                    startActivity(loginemailcust);
                    finish();

                }
                if(type.equals("Phone")) {
                    Intent loginphonecust = new Intent(ChooseOne.this, Loginphone.class);
                    startActivity(loginphonecust);
                    finish();
                }
                if(type.equals("SignUp")) {
                    Intent loginRegistercust = new Intent(ChooseOne.this, Registration.class);
                    startActivity(loginRegistercust);
                    finish();
                }


            }
        });

        DeliveryPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    Intent loginemail=new Intent(ChooseOne.this,Delivery_Login.class);
                    startActivity(loginemail);
                    finish();

                }
                if(type.equals("Phone")) {
                    Intent loginphone = new Intent(ChooseOne.this, Delivery_Loginphone.class);
                    startActivity(loginphone);
                    finish();
                }
                if(type.equals("SignUp")) {
                    Intent RegisterDelivery = new Intent(ChooseOne.this, Delivery_Registration.class);
                    startActivity(RegisterDelivery);
                    finish();
                }


            }
        });


    }
}