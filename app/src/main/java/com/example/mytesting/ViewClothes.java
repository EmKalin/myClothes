package com.example.mytesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ViewClothes extends AppCompatActivity implements ImageAdapter.OnItemClickListner {

    private RecyclerView mRecylerView;
    private ImageAdapter mAdapter;

    private List<Cloth> mUploadCloth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clothes);


        mRecylerView = findViewById(R.id.recycle_view);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String fileTypeToLoad = intent.getStringExtra("typeOf");
        String beforeIntent = intent.getStringExtra("intent");

        Toast.makeText(this, "Class: "+beforeIntent, Toast.LENGTH_SHORT).show();

        mUploadCloth = new ArrayList<>();



        if (fileTypeToLoad == null){

            FirestoreHandler.getAllCloth(new OnCompleteListener<QuerySnapshot>() {
                static final String TAG = "Downloading";
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Cloth cloth = document.toObject(Cloth.class);
                            Log.d(TAG, cloth.toString());
                            cloth.setmKey(document.getId());
                            mUploadCloth.add(cloth);

                            mAdapter = new ImageAdapter(ViewClothes.this, mUploadCloth, beforeIntent);
                            mRecylerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListner(ViewClothes.this);
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

        } else {

            FirestoreHandler.getCloth(fileTypeToLoad, new OnCompleteListener<QuerySnapshot>() {
                static final String TAG = "Downloading";
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Cloth cloth = document.toObject(Cloth.class);
                            Log.d(TAG, cloth.toString());
                            cloth.setmKey(document.getId());
                            mUploadCloth.add(cloth);

                            mAdapter = new ImageAdapter(ViewClothes.this, mUploadCloth, beforeIntent);
                            mRecylerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListner(ViewClothes.this);
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }

    }



    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal Click at position: "+ViewClothes.class.getSimpleName(), Toast.LENGTH_SHORT).show();

        Cloth selestIm = mUploadCloth.get(position);
        String  selName = selestIm.getName();
        String  selUrl = selestIm.getImageUrl();

        Intent intent = new Intent();
        intent.putExtra("result1", selUrl);
        intent.putExtra("resultName", selName);
        setResult(78, intent);

        super.onBackPressed();

    }

    @Override
    public void onDeleteClick(int position) {
        Cloth selestIm = mUploadCloth.get(position);
        String  selKey = selestIm.getKey();

        StorageReference imref = FirebaseStorage.getInstance().getReferenceFromUrl(selestIm.getImageUrl());
        Log.d("Deleting", String.valueOf(imref));
        imref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("clothes").document(selKey).delete();
                Toast.makeText(ViewClothes.this, "Deleted "+selestIm.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}