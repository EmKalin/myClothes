package com.example.mytesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ImagesCreationActivity extends AppCompatActivity {

    private RecyclerView mRecylerView;
    private AdaptCreations mAdapter;

    private List<MyCreations> mUploadCretion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_creation);

        mRecylerView = findViewById(R.id.recycleView_creations);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mUploadCretion = new ArrayList<>();

        FirestoreHandler.getAllCreations(new OnCompleteListener<QuerySnapshot>() {
            static final String TAG = "Downloading";
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MyCreations cloth = document.toObject(MyCreations.class);
                        Log.d(TAG, cloth.toString());
                        //cloth.setmKey(document.getId());
                        mUploadCretion.add(cloth);

                        mAdapter = new AdaptCreations(ImagesCreationActivity.this, mUploadCretion);
                        mRecylerView.setAdapter(mAdapter);
                        //mAdapter.setOnItemClickListner(ImagesCreationActivity.this);
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}