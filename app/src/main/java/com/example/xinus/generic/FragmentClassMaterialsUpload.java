package com.example.xinus.generic;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClassMaterialsUpload extends Fragment {
    EditText UploadClassMaterialsDescription,UploadClassMaterialsCourseTitle,UploadClassMaterialsCourseCode,UploadClassMaterialsChapterName;
    Spinner UploadClassMaterialsSlectMaterialType,UploadClassMaterialsSelectSemister,UploadClassMaterialsSelectYear;
    Button UploadClassMaterialsUpload;

    public String yearSelected;
    public String semisterSelected;
    public String typeSelected;

    public FragmentClassMaterialsUpload() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_class_materials_upload, container, false);

        UploadClassMaterialsDescription = (EditText)view.findViewById(R.id.classMaterialUploaddesCription);
        UploadClassMaterialsCourseTitle = (EditText)view.findViewById(R.id.classMaterialUploadCourseTitle);
        UploadClassMaterialsCourseCode = (EditText)view.findViewById(R.id.classMaterialUploadchooseCourseCode);
        UploadClassMaterialsChapterName = (EditText)view.findViewById(R.id.classMaterialUploadinputChapter);

        UploadClassMaterialsSlectMaterialType = (Spinner)view.findViewById(R.id.classMaterialUploadchooseTopicSpinner);
        UploadClassMaterialsSelectSemister = (Spinner)view.findViewById(R.id.classMaterialUploadSelectSemister);
        UploadClassMaterialsSelectYear = (Spinner)view.findViewById(R.id.classMaterialUploadSelectYear);


        UploadClassMaterialsUpload = (Button)view.findViewById(R.id.classMaterialUploadchooseButton);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets() , "roboto.ttf");
        UploadClassMaterialsDescription.setTypeface(typeface);
        UploadClassMaterialsCourseTitle.setTypeface(typeface);
        UploadClassMaterialsCourseCode.setTypeface(typeface);
        UploadClassMaterialsChapterName.setTypeface(typeface);
        UploadClassMaterialsUpload.setTypeface(typeface);


        selectSemister();
        selectYear();
        selsectType();

        UploadClassMaterialsUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Type" , typeSelected);
                bundle.putString("Description" , UploadClassMaterialsDescription.getText().toString().trim());
                bundle.putString("CourseTitle" , UploadClassMaterialsCourseTitle.getText().toString().trim());
                bundle.putString("ChapterName" , UploadClassMaterialsChapterName.getText().toString().trim());
                bundle.putString("CourseCode" , UploadClassMaterialsCourseCode.getText().toString().trim());
                bundle.putString("Semister" , semisterSelected);
                bundle.putString("Year" , yearSelected);
                Intent intent = new Intent(getActivity() , ImageUpload.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        return view;
    }

    private void selsectType() {
        List <String> optionsToSelect = new ArrayList<String >();
        optionsToSelect.add("Class Lecture");
        optionsToSelect.add("Class Materials");

        ArrayAdapter<String> adapterForSelectMaterialType = new ArrayAdapter<String>(this.getActivity() , android.R.layout.simple_spinner_item , optionsToSelect);
        adapterForSelectMaterialType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UploadClassMaterialsSlectMaterialType.setAdapter(adapterForSelectMaterialType);

        UploadClassMaterialsSlectMaterialType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setType(String s) {
        typeSelected = s;
    }

    private Void selectYear() {
        String yearSelected;
        List<String> Year = new ArrayList<String>();
        Year.add("2018");
        Year.add("2017");
        Year.add("2016");
        Year.add("2015");
        Year.add("2014");
        Year.add("2013");

        ArrayAdapter<String> adapterForSelectYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Year);
        adapterForSelectYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UploadClassMaterialsSelectYear.setAdapter(adapterForSelectYear);

        UploadClassMaterialsSelectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setValueOfyear( parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return null;
    }

    private void setValueOfyear(String s) {
        yearSelected = s;
    }

    private Void selectSemister() {
        String semiste = null;
        List <String> semister = new ArrayList<String >();
        semister.add("Spring");
        semister.add("Summer");
        semister.add("Fall");

        ArrayAdapter<String> adapterForSelectSemister = new ArrayAdapter<String>(this.getActivity() , android.R.layout.simple_spinner_item , semister);
        adapterForSelectSemister.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        UploadClassMaterialsSelectSemister.setAdapter(adapterForSelectSemister);

        UploadClassMaterialsSelectSemister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSemister(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  null;
    }

    private void setSemister(String s) {
        semisterSelected = s;
    }



}
