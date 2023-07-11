package my.shopfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    Button signinemail,signinphone,signup;
    ImageView bgimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Animation Zoomin = AnimationUtils.loadAnimation(this,R.anim.zoomin);
        final Animation Zoomout= AnimationUtils.loadAnimation(this,R.anim.zoomout);

        bgimage=findViewById(R.id.imageView2);
        bgimage.setAnimation(Zoomin);
        bgimage.setAnimation(Zoomout);

        Zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(Zoomin);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(Zoomout);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        signinemail=(Button) findViewById(R.id.Signwithemail);
        signinphone=(Button) findViewById(R.id.SignwithPhone);
        signup=(Button) findViewById(R.id.SignUp);

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signemail= new Intent(MainMenu.this,ChooseOne.class);
                signemail.putExtra("Home","Email");
                startActivity(signemail);
                finish();

            }
        });

     signinphone.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent signphone=new Intent(MainMenu.this,ChooseOne.class);
             signphone.putExtra("Home","Phone");
             startActivity(signphone);
             finish();
         }
     });

   signup.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
          Intent signup=new Intent(MainMenu.this,ChooseOne.class);
          signup.putExtra("Home","SignUp");
          startActivity(signup);
          finish();
       }
   });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}