package com.example.mytesting;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreHandler {

    private static final String TAG = FirestoreHandler.class.getName();

    static void addClothe(String nameCloth, String typeCloth, String colorCloth, String imageCloth, String imageUrl){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> clothe = new HashMap<>();
        clothe.put("name", nameCloth);
        clothe.put("type", typeCloth);
        clothe.put("color", colorCloth);
        clothe.put("image", imageCloth);
        clothe.put("imageUrl", imageUrl);

// Add a new document with a generated ID
        db.collection("clothes")
                .add(clothe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    static void addCreation(String nameOfCreation, String date, String firstClothName, String secondClothName, String firstClothUrl, String secondClothUrl, Context context){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> creation = new HashMap<>();
        creation.put("name", nameOfCreation);
        creation.put("date", date);
        creation.put("firstName", firstClothName);
        creation.put("secondName", secondClothName);
        creation.put("firstUrl", firstClothUrl);
        creation.put("secondUrl", secondClothUrl);


// Add a new document with a generated ID
        db.collection("creations")
                .add(creation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Creations created!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    static void getCloth(String type, OnCompleteListener<QuerySnapshot> listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clothes")
                .whereEqualTo("type", type)
                .get()
                .addOnCompleteListener(listener);
    }

    static void getAllCloth(OnCompleteListener<QuerySnapshot> listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clothes")
                .get()
                .addOnCompleteListener(listener);
    }

    static void getAllCreations(OnCompleteListener<QuerySnapshot> listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("creations")
                .get()
                .addOnCompleteListener(listener);
    }
}
