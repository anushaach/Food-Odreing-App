package my.shopfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;


public class ChefRegistration extends AppCompatActivity {
    String[] NepaliFood = {"Chitwan", "Butwal", "Nepaljung"};
    String[] NewariFood = {"Kathamdu", "Pokhara"};

    TextInputLayout Fname, Lname, Email, Pass, CPass, mobileno, houseno, area, pincode;
    Spinner Statespin, Cityspin;
    Button signup, Emaill, phone;
    CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname, lname, emailid, password, confpassword, mobile, house, Area, Pincode, role = "Chef", statee, cityy;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);

        Fname = (TextInputLayout) findViewById(R.id.Firstname);
        Lname = (TextInputLayout) findViewById(R.id.Lastname);
        Email = (TextInputLayout) findViewById(R.id.Email);
        Pass = (TextInputLayout) findViewById(R.id.Pwd);
        CPass = (TextInputLayout) findViewById(R.id.Cpass);
        mobileno = (TextInputLayout) findViewById(R.id.Mobileno);
        houseno = (TextInputLayout) findViewById(R.id.houseNo);
        pincode = (TextInputLayout) findViewById(R.id.Pincode);
        Statespin = (Spinner) findViewById(R.id.Statee);
        Cityspin=(Spinner)findViewById(R.id.Citys);
        area = (TextInputLayout) findViewById(R.id.Area);
        Cpp=(CountryCodePicker)findViewById(R.id.CountryCode);





        signup = (Button) findViewById(R.id.Signup);
        Emaill = (Button) findViewById(R.id.email);
        phone = (Button) findViewById(R.id.phone);

        firebaseDatabase = FirebaseDatabase.getInstance();


        Statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object value = adapterView.getItemAtPosition(i);
                statee = value.toString().trim();
                if (statee.equals("NepaliFood")) {
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : NepaliFood) {
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefRegistration.this, android.R.layout.simple_spinner_item,list);
                    Cityspin.setAdapter(arrayAdapter);


                } else if (statee.equals("Newari Food")) {
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : NewariFood) {
                        list.add(cities);
                    }




                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefRegistration.this, android.R.layout.simple_spinner_item,list);
                    Cityspin.setAdapter(arrayAdapter);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object value = adapterView.getItemAtPosition(i);
                cityy=value.toString().trim();
                if (cityy.equals("chitwan")){
                    ArrayList<String> list=new ArrayList<>();
                    for (String text : NepaliFood){
                        list.add(text);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefRegistration.this, android.R.layout.simple_spinner_item,list);

                } else if (cityy.equals("kathmandu")) {

                    ArrayList<String>list=new ArrayList<>();
                    for (String text : NepaliFood){
                        list.add(text);

                }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefRegistration.this, android.R.layout.simple_spinner_item,list);
                } else if (cityy.equals("Pokhara")) {
                    ArrayList<String>list=new ArrayList<>();
                    for (String text : NepaliFood){
                        list.add(text);
                }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChefRegistration.this, android.R.layout.simple_spinner_item,list);
                    Cityspin.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        databaseReference = firebaseDatabase.getInstance().getReference("Chef");
        FirebaseApp.initializeApp(this);
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confpassword = CPass.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                house = houseno.getEditText().getText().toString().trim();
                Pincode = pincode.getEditText().getText().toString().trim();


                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(ChefRegistration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress please wait............");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Mobile No", mobile);
                                        hashMap1.put("First Name", fname);
                                        hashMap1.put("Last Name", lname);
                                        hashMap1.put("EmailId", emailid);
                                        hashMap1.put("City", cityy);
                                        hashMap1.put("Area", Area);
                                        hashMap1.put("Password", password);
                                        hashMap1.put("Pincode", Pincode);
                                        hashMap1.put("State", statee);
                                        hashMap1.put("Confirm Password", confpassword);
                                        hashMap1.put("House", house);

                                        firebaseDatabase.getInstance().getReference("Chef")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();

                                                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefRegistration.this);
                                                                    builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                         

                                                                            Dialog dialog = new Dialog(context);
                                                                            dialog.show();
                                                                            dialog.dismiss();

                                                                            String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                            Intent b = new Intent(ChefRegistration.this,ChefVerifyPhone.class);
                                                                            b.putExtra("phonenumber", phonenumber);
                                                                            startActivity(b);


                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                } else {
                                                                    mDialog.dismiss();
                                                                    ResuableCodeForAll.ShowAlert(ChefRegistration.this, "Error", task.getException().getMessage());

                                                                }
                                                            }
                                                        });

                                                    }
                                                });


                                    }
                                });

                            } else {
                                mDialog.dismiss();
                                ResuableCodeForAll.ShowAlert(ChefRegistration.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChefRegistration.this, Chefloginphone.class);
                startActivity(i);
                finish();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(ChefRegistration.this, Chefloginphone.class);
                startActivity(e);
                finish();
            }
        });
    }

    String emailpattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        CPass.setErrorEnabled(false);
        CPass.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        pincode.setErrorEnabled(false);
        pincode.setError("");

        boolean isvalid = false, isValidname = false, isValidemail = false, isValidpassword = false, isValidconfpassword = false, isValidmobilenum = false, isValidarea = false, isValidhousenum = false, isValidpincode = false;
        if (TextUtils.isEmpty(fname)) {
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(lname)) {
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(emailid)) {
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        } else {
                if (emailid.matches(emailpattern)) {
                    isValidemail = true;
                } else {
                    Email.setErrorEnabled(true);
                    Email.setError("Enter a Valid Email Id");
                }
            }
            if (TextUtils.isEmpty(password)) {
                Pass.setErrorEnabled(true);
                Pass.setError("Enter Password");
            } else {
                if (password.length() < 8) {
                    Pass.setErrorEnabled(true);
                    Pass.setError("Password is weak");
                } else {
                    isValidpassword = true;
                }
            }
            if (TextUtils.isEmpty(confpassword)) {
                CPass.setErrorEnabled(true);
                CPass.setError("Enter Password Again");
            } else {
                if (!password.equals(confpassword)) {
                    CPass.setErrorEnabled(true);
                    CPass.setError("Password Doesn't Match");
                } else {
                    isValidconfpassword = true;
                }
            }
            if (TextUtils.isEmpty(mobile)) {
                mobileno.setErrorEnabled(true);
                mobileno.setError("Mobile Number Is Required");
            } else {
                if (mobile.length() < 10) {
                    mobileno.setErrorEnabled(true);
                    mobileno.setError("Invalid Mobile Number");
                } else {
                    isValidmobilenum = true;
                }
            }
            if (TextUtils.isEmpty(house)) {
                houseno.setErrorEnabled(true);
                houseno.setError("HouseNo is Required");
            } else {
                isValidhousenum = true;
            }
            if (TextUtils.isEmpty(Area)) {
                area.setErrorEnabled(true);
                area.setError("Area Is Required");
            } else {

                isValidarea = true;
            }
            if (TextUtils.isEmpty(Pincode)) {
                pincode.setErrorEnabled(true);
                pincode.setError("Please Enter Pincode");
            } else {

                isValidpincode = true;
            }
            isvalid = (isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname) ? true : false;

            return isvalid;
        }

    }
