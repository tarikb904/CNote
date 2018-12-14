package com.example.xinus.generic;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MaterialViewAdapter extends RecyclerView.Adapter<MaterialViewAdapter.MaterialViewViewHolder> {

    private final Context context;
    private final ArrayList<MaterialUpdateModel> list;

    public MaterialViewAdapter(Context context , ArrayList<MaterialUpdateModel> list) {

            this.context = context;
            this.list = list;

    }



    @Override
    public MaterialViewAdapter.MaterialViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recyleview_layout,parent,false);

        return new MaterialViewViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MaterialViewAdapter.MaterialViewViewHolder holder, int position) {

        Picasso.get().load(list.get(position).getImageUrl()).into(holder.imageView);
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.des.setText(list.get(position).getDatadescription());
        holder.ccode.setText(list.get(position).getCoursecode());
        holder.cname.setText(list.get(position).getCoursettle());
        holder.sem.setText(list.get(position).getSemister());
        holder.year.setText(list.get(position).getYear());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MaterialViewViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView des,ccode,cname,sem,year;
        public MaterialViewViewHolder(View itemView) {
            super(itemView);



            imageView = itemView.findViewById(R.id.image);
            des = itemView.findViewById(R.id.description);
            ccode = itemView.findViewById(R.id.coursecode);
            cname = itemView.findViewById(R.id.coursetitle);
            sem = itemView.findViewById(R.id.semister);
            year = itemView.findViewById(R.id.year);

        }
    }
}
