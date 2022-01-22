package com.example.mytesting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class CreateCreation extends AppCompatActivity {

    TextView mSelectedDate;
    private DatePickerDialog.OnDateSetListener mDatteSetListner;

    Button mTop;
    Button mBottom;
    Button mCreat;
    EditText cName;

    ImageView firstCl;
    ImageView secondCl;

    String firstName;
    String firstUrl;

    String secondName;
    String secondUrl;

    ActivityResultLauncher<Intent> activityResultLauncherForFirst = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78){
                        Intent intent = result.getData();

                        if (intent != null){
                            firstUrl = intent.getStringExtra("result1");
                            firstName = intent.getStringExtra("resultName");
                            Picasso.with(CreateCreation.this).load(firstUrl).into(firstCl);
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> activityResultLauncherForSecond = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78){
                        Intent intent = result.getData();

                        if (intent != null){
                            secondUrl = intent.getStringExtra("result1");
                            secondName = intent.getStringExtra("resultName");
                            Picasso.with(CreateCreation.this).load(secondUrl).into(secondCl);
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_creation);

        mSelectedDate = findViewById(R.id.selectDat);

        mTop = findViewById(R.id.selected1cl);
        mBottom = findViewById(R.id.selected2cl);
        mCreat = findViewById(R.id.createCreationWithDate);
        cName = findViewById(R.id.nameCrt);

        firstCl = findViewById(R.id.imageCloth1);
        secondCl = findViewById(R.id.imageCloth2);



        mTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starActivityOfShowClothesForFisrts();
            }
        });

        mBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starActivityOfShowClothesForSecond();
            }
        });



        mSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateCreation.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDatteSetListner,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.MAGENTA));
                dialog.show();
            }
        });

        mDatteSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = month + "/" + day + "/" + year;
                mSelectedDate.setText(date);
            }
        };


        mCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = cName.getText().toString();
                String date = mSelectedDate.getText().toString();


                if(!name.isEmpty() && !date.isEmpty() && !firstName.isEmpty() && !secondName.isEmpty() && !firstUrl.isEmpty() && !secondUrl.isEmpty()){
                    FirestoreHandler.addCreation(name, date, firstName, secondName, firstUrl, secondUrl, CreateCreation.this);
                } else {
                    Toast.makeText(CreateCreation.this, "Select all field!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(CreateCreation.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    void openImageGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
    }

    public void starActivityOfShowClothesForFisrts() {
        Intent intent = new Intent(this, ViewClothes.class);

        intent.putExtra("intent", getClass().getSimpleName());
        activityResultLauncherForFirst.launch(intent);

    };


    public void starActivityOfShowClothesForSecond() {
        Intent intent = new Intent(this, ViewClothes.class);

        intent.putExtra("intent", getClass().getSimpleName());
        activityResultLauncherForSecond.launch(intent);

    };

}