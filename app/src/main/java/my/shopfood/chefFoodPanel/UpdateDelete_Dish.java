package my.shopfood.chefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import my.shopfood.R;

public class UpdateDelete_Dish extends AppCompatActivity {
    TextInputLayout desc, qty, pri;
    TextView Dishname;
    ImageButton imageButton;
    Uri imageuri;
    String dburi;
    private Uri mCropimageuri;
    Button Update_dish, Delete_dish;
    String description, quantity, price, dishes, chefId;
    String RandomUID;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth FAuth;
    String ID;
    private ProgressDialog progressDialog;
    DatabaseReference dataa;
    String State, City, Area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_dish);

        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        Dishname = (TextView) findViewById(R.id.dish_name);
        imageButton = (ImageButton) findViewById(R.id.imageupload);
        Update_dish = (Button) findViewById(R.id.Updatedish);
        Delete_dish = (Button) findViewById(R.id.Deletedish);
        ID = getIntent().getStringExtra("updatedeletedish");
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = firebaseDatabase.getInstance().getReference("chef").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Chef cheff = snapshot.getValue(Chef.class);
                State = cheff.getState();
                City = cheff.getCity();
                Area = cheff.getArea();

                Update_dish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        description = desc.getEditText().getText().toString().trim();
                        quantity = qty.getEditText().getText().toString().trim();
                        price = pri.getEditText().getText().toString().trim();

                        if (isValid()) {
                            if (imageuri != null) {
                                UploadImage();
                            } else {
                                updatedesc(dburi);
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatedesc(String dburi) {
    }

    private void UploadImage() {
    }

    private boolean isValid() {
    }
}