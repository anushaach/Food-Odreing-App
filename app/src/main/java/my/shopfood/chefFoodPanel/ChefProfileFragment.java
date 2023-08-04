package my.shopfood.chefFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import my.shopfood.R;

public class ChefProfileFragment extends Fragment {

    Button postDish;
    ConstraintLayout backimg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_profile, null);
        getActivity().setTitle("Post Dish");

        AnimationDrawable animationDrawable=new AnimationDrawable();
        Context context = requireContext(); // or getContext()

        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img4),3000);
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img1),3000);
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img4),3000);
        animationDrawable.addFrame(ContextCompat.getDrawable(context,R.drawable.img1),3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setEnterFadeDuration(1600);


        backimg = v.findViewById(R.id.back1);
        backimg.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        postDish=(Button)v.findViewById(R.id.post_dish);

        postDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Chef_PostDish.class));

            }
        });





        return v;
    }
}
