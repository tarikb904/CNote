package com.example.xinus.generic;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClassNotes extends Fragment {


    private RecyclerView recyclerView;
    View v;
    DatabaseReference databaseReference;
    ArrayList<MaterialUpdateModel> materialUpdateModelArrayList;
    public FragmentClassNotes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragment_class_notes, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.classNotesViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        super.onStart();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Class Lecture");

        materialUpdateModelArrayList = new ArrayList<MaterialUpdateModel>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    MaterialUpdateModel materialUpdateModel = dataSnapshot1.getValue(MaterialUpdateModel.class);
                    materialUpdateModelArrayList.add(materialUpdateModel);
                }

                MaterialViewAdapter adapter = new MaterialViewAdapter(getContext() , materialUpdateModelArrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
