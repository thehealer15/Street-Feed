package com.example.streetfeed.authentication;

//import com.example.streetfeed.FillingDetails;
import com.example.streetfeed.Customer_bottom_nav;
import com.example.streetfeed.Shop;
import com.example.streetfeed.Verhoeff;
//import com.example.streetfeed.authentication.SampleDashboard;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.example.streetfeed.FillingDetails;
import com.example.streetfeed.R;
//import com.example.streetfeed.fragments.HomeFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import static com.example.streetfeed.Customer_bottom_nav.user_latitude;
import static com.example.streetfeed.Customer_bottom_nav.user_longitude;

import static com.example.streetfeed.fragments.HomeFragment.postalCode;
import static java.lang.Character.isDigit;

public class ManageOTPvendor extends AppCompatActivity {

    EditText otp;
    Button verifyOTP;
    ImageView topimg,botimg;
    String mobile_no ;
    FirebaseAuth mAuth;
    String otpid;
    TextInputLayout shopName,ownerName, shopAddress, landmark, AdharCardNumber, category;

    Button submit_shopInfo;
    public static String shopContactNumberString;
    ImageView user_image;
    private static final int PICK_IMAGE = 100;
    double vendor_latitude, vendor_longitude;
    ImageView getlocation_ic;
    AutoCompleteTextView dropdown;
    int PERMISSION_ID = 1;
    FusedLocationProviderClient mFusedLocationClient;
    public static int flg=0;



    TextInputLayout itemName,itemPrice;
    Button addItems,submit_shop;
    Map<String,Object> itemList;

    ListView itemNameListView,itemPriceListView;
    LinearLayout info_linear_layout,itemdetails_layout ;
    ArrayAdapter<String> itemNamesadapter , itemPriceadapter;
    ArrayList<String> itemNameList , itemPriceList;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button select;
    ImageView imageView;
    ProgressBar progressBar;
    Button upload;
    public Uri image,imageUrl;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private StorageTask uploading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otpvendor);

        select = (Button) findViewById(R.id.select_image);
        imageView = (ImageView) findViewById(R.id.user_image);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        upload = (Button) findViewById(R.id.upload);
        //StrictMode.enableDefaults();



        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("uploads");
        setTitle("Enter Shop Details");
        getlocation_ic = findViewById(R.id.getlocation_ic);
        shopName = findViewById(R.id.shopName);
        dropdown = findViewById(R.id.dropdown);
        shopAddress = findViewById(R.id.shopAddress);
        landmark = findViewById(R.id.landmark);
        AdharCardNumber = findViewById(R.id.adhar_card_no);
        category = findViewById(R.id.category);
        submit_shopInfo = findViewById(R.id.shop_info_submit_btn);
        ownerName= findViewById(R.id.ownerName);
        mobile_no=getIntent().getStringExtra("mobile").toString();
        shopContactNumberString = mobile_no;


        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemdetails_layout = findViewById(R.id.itemdetails_layout);
        topimg=findViewById(R.id.imageView4);
        botimg=findViewById(R.id.imageView3);

        /**
         * Adapter for category dropdown menu*/

        String[] arr = new String[]{"Food", "Fruites and vegetables", "Household utensils", "Services", "Fashion and clothing", "Drinks and bevrages", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.dropdown, arr);
        dropdown.setAdapter(adapter);
        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploading != null && uploading.isInProgress()) {
                    Toast.makeText(ManageOTPvendor.this, "Uploading in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });



        otp=findViewById(R.id.text_otp);
        verifyOTP=findViewById(R.id.verify_OTP);
        mAuth=FirebaseAuth.getInstance();

        submit_shopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                submitInformation();}
                catch(Exception e)
                {
                    Toast.makeText(ManageOTPvendor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


       initiateOTP();
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(otp.getText().toString().isEmpty())
                    Toast.makeText(ManageOTPvendor.this, "Empty credentials!Try Again", Toast.LENGTH_SHORT).show();
                else if(otp.getText().toString().length() != 6)
                    Toast.makeText(ManageOTPvendor.this, "OTP verification failed!Try Again", Toast.LENGTH_SHORT).show();
                else if(otpid==null)
                {
                    Toast.makeText(ManageOTPvendor.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
            }
        });


    }
    private void openFileChooser () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK
                && data != null && data.getData() != null) {
            image = data.getData();
            Picasso.get().load(image).into(imageView);
        }
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver cR= this.getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile()
    {
        if(image!=null)
        {
            StorageReference sR= storageReference.child(shopContactNumberString+"."+getFileExtension(image));
            uploading=sR.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(ManageOTPvendor.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    sR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl=uri;
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ManageOTPvendor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                }
            });
        }
        else
        {
            Toast.makeText(ManageOTPvendor.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void initiateOTP(){

       PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile_no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(ManageOTPvendor.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override//When the Sim is not in your device
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid=s;

                            }

                            @Override//When sim is in your device
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override//Failed
                            public void onVerificationFailed(@NonNull FirebaseException e)
                            {
                                Toast.makeText(ManageOTPvendor.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) .build()  ;       // OnVerificationStateChangedCallbacks
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManageOTPvendor.this, "Signin Successful", Toast.LENGTH_SHORT).show();

                            changeToDetailsActivity();
                        }
                        else
                        {
                            Toast.makeText(ManageOTPvendor.this, "SignIn code error!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void changeToDetailsActivity()
    {


        otp.setVisibility(View.GONE);
        verifyOTP.setVisibility(View.GONE);
        topimg.setVisibility(View.GONE);
        botimg.setVisibility(View.GONE);

        LinearLayout detailsActivity=findViewById(R.id.parentLayout);
        detailsActivity.setVisibility(View.VISIBLE);

    }

        public void submitInformation() {
        String shopNameString = Objects.requireNonNull(Objects.requireNonNull(shopName.getEditText()).getText().toString());
        String ownerNameString = Objects.requireNonNull(Objects.requireNonNull(ownerName.getEditText()).getText().toString());
        String shopAddressString = Objects.requireNonNull(shopAddress.getEditText()).getText().toString();
        String shopLandmark = Objects.requireNonNull(Objects.requireNonNull(landmark.getEditText()).getText().toString());
        String shopAdharcardInfo = Objects.requireNonNull(Objects.requireNonNull(AdharCardNumber.getEditText()).getText().toString());
        String shopCategory = Objects.requireNonNull(Objects.requireNonNull(category.getEditText()).getText().toString());

        if (shopNameString.trim().isEmpty()) {
            shopName.setError(getString(R.string.empty_value));
        } else if (ownerNameString.trim().isEmpty()) {
            ownerName.setError(getString(R.string.empty_value));
        } else if (shopAddressString.trim().isEmpty()) {
            shopAddress.setError(getString(R.string.empty_value));
        } else if (shopCategory.trim().isEmpty()) {
            category.setError(getString(R.string.empty_value));
        } else if (shopLandmark.trim().isEmpty()) {
            category.setError(getString(R.string.empty_value));
        } else if (user_latitude == 0 || user_longitude == 0) {
            Toast.makeText(this, "Invalid Location", Toast.LENGTH_SHORT).show();
        }


         else if (!Verhoeff.validateVerhoeff(shopAdharcardInfo)) {
            AdharCardNumber.setError(getString(R.string.invalid_prompt));
        } else if (image == null) {

        } else if (Verhoeff.validateVerhoeff(shopAdharcardInfo)) {
                        /*
                         First uploading image then uploading real time data as we are creating a Shop object*/

               Shop currentShop = new Shop(shopNameString, ownerNameString, shopAddressString, shopLandmark, shopAdharcardInfo, shopCategory, user_latitude, user_longitude,imageUrl.toString(),postalCode,shopContactNumberString);
                FirebaseDatabase.getInstance().getReference("Vendor Information").child(shopContactNumberString).setValue(currentShop);

            /*
             * Submit information to firebase data base
             * Now switch/ Pass intent to add items activity:*/


            changeFields();

        }
    }

    private void changeFields(){
        select.setVisibility(View.GONE);
        upload.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        LinearLayout info_layout = findViewById(R.id.basic_info_layout);
        info_layout.setVisibility(View.GONE);
        submit_shopInfo.setVisibility(View.GONE);

        setTitle("Add Items in Cart");
        itemdetails_layout.setVisibility(View.VISIBLE);
       addItems = findViewById(R.id.add_item);
        submit_shop = findViewById(R.id.submit_shop);
        info_linear_layout = findViewById(R.id.info_linear_layout);
        info_linear_layout.setVisibility(View.VISIBLE);
        addItems.setVisibility(View.VISIBLE);
       info_linear_layout.setVisibility(View.VISIBLE);

        itemNameListView = findViewById(R.id.itemNameListView);
        itemPriceListView = findViewById(R.id.itemPriceListView);
        itemNameList = new ArrayList<>();
        itemPriceList  = new ArrayList<>();

        itemNamesadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemNameList);
       itemPriceadapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemPriceList);

        itemPriceListView.setAdapter(itemPriceadapter);
        itemNameListView.setAdapter(itemNamesadapter);
    }

    public void getCoordinates(View v) {
    //   getLastLocation();
    }

   
    public boolean validateNumber(String number){
        if(number.length() !=10)return false;

        for(int i=0;i<number.length();i++){
            if( !isDigit(number.charAt(i))  ){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * Add items code starts here*/


   public void add_item(View view){
       submit_shop.setVisibility(View.VISIBLE);
        info_linear_layout.setVisibility(View.VISIBLE);

        String itemNameString = String.valueOf(itemName.getEditText().getText());
        Integer itemPriceInteger=0;
        try{
            itemPriceInteger = Integer.parseInt(String.valueOf(itemPrice.getEditText().getText()));
        }
        catch (NumberFormatException e){
            itemPrice.setError(getString(R.string.invalid));
        }
        if(itemNameString.trim().isEmpty()){
            itemName.setError(getString(R.string.invalid));
        }
        else{
            /**
             * Add value to listview and then to firebase*/

            //Toast.makeText(this, itemNameString+""+itemPriceInteger.toString(), Toast.LENGTH_SHORT).show();

            itemNameList.add(itemNameString);
            itemPriceList.add(itemPriceInteger.toString());

            // notify both adapter
            itemPriceadapter.notifyDataSetChanged();
            itemNamesadapter.notifyDataSetChanged();;

            itemName.getEditText().setText("");
            itemPrice.getEditText().setText("");
        }
    }
    public void submitFinal(View view)
    {
        int size = itemPriceList.size();
        flg=1;
        for(int i=0;i<size;i++){
            FirebaseDatabase.getInstance().getReference("Vendor Information").child(shopContactNumberString).child("Items").child(itemNameList.get(i)).setValue(itemPriceList.get(i));
        }
        Intent intent = new Intent(getApplicationContext(), Customer_bottom_nav.class);
        startActivity(intent);

    }



}