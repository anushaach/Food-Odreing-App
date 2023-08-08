package my.shopfood.chefFoodPanel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.theartofdev.edmodo.cropper.CropImage;



import java.util.UUID;


import my.shopfood.R;

public class Chef_PostDish extends AppCompatActivity {

    ImageButton imageButton;
    Button post_dish;
    Spinner Dishes;
    TextInputLayout desc, qty, pri;
    String description, quantity, price, dishes;
    Uri imageuri;
    private Uri mCropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference dataaa;
    FirebaseAuth FAuth;
    StorageReference ref;
    String ChefId;
    String RandomUId;
    String State, City, Sub;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_post_dish);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Dishes = findViewById(R.id.dishes);
        desc = findViewById(R.id.description);
        qty = findViewById(R.id.Quantity);
        pri = findViewById(R.id.price);
        post_dish = findViewById(R.id.post);
        FAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FoodSupplyDetails");

        try {
            String userid = FAuth.getCurrentUser().getUid();
            databaseReference = firebaseDatabase.getReference("Chef").child(userid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Chef chefc = dataSnapshot.getValue(Chef.class);
                    if (chefc != null) {
                        State = chefc.getState();
                        City = chefc.getCity();
                        Sub = chefc.getSuburban();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Chef_PostDish.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    imageuri = data.getData();
                    imageButton.setImageURI(imageuri);
                }
            }
        });

        imageButton = findViewById(R.id.imageupload);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick();
            }
        });

        post_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishes = Dishes.getSelectedItem().toString().trim();
                description = desc.getEditText().getText().toString().trim();
                quantity = qty.getEditText().getText().toString().trim();
                price = pri.getEditText().getText().toString().trim();

                if (isValid() && imageuri != null) {
                    uploadImage();
                } else {
                    Toast.makeText(Chef_PostDish.this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onSelectImageClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }

    private boolean isValid() {
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");

        boolean isValiDescription, isValidPrice, isvalidQuantity, isvalid;
        isValiDescription = isValidPrice = isvalidQuantity = isvalid = true;

        if (TextUtils.isEmpty(description)) {
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
            isValiDescription = false;
        }

        if (TextUtils.isEmpty(quantity)) {
            qty.setErrorEnabled(true);
            qty.setError("Quantity is Required");
            isvalidQuantity = false;
        }

        if (TextUtils.isEmpty(price)) {
            pri.setErrorEnabled(true);
            pri.setError("Price is Required");
            isValidPrice = false;
        }

        isvalid = isValiDescription && isvalidQuantity && isValidPrice;
        return isvalid;
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(Chef_PostDish.this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        RandomUId = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child(RandomUId);
        ChefId = FAuth.getCurrentUser().getUid();

        UploadTask uploadTask = ref.putFile(imageuri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    FoodSupplyDetails info = new FoodSupplyDetails(dishes, quantity, price, description, downloadUri.toString(), RandomUId, ChefId);
                    databaseReference.child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
                            .setValue(info)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Chef_PostDish.this, "Dish Posted successfully", Toast.LENGTH_SHORT).show();
                                    // Clear input fields and image selection after successful posting
                                    desc.getEditText().setText("");
                                    qty.getEditText().setText("");
                                    pri.getEditText().setText("");
                                    imageuri = null;
                                    imageButton.setImageResource(R.drawable.baseline_add_to_photos_24);



                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Chef_PostDish.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Chef_PostDish.this, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






