package com.example.streetfeed.fragments;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetfeed.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class Adapter extends FirebaseRecyclerAdapter<Name_Address_Shop,Adapter.viewHolder>
{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    ArrayList<Name_Address_Shop> name_address_shopArrayList;
    public Adapter(@NonNull  FirebaseRecyclerOptions<Name_Address_Shop> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position, @NonNull Name_Address_Shop model)
    {
        holder.textview.setText(model.getName());
        holder.address.setText(model.getAddress());
        Glide.with(holder.imageview.getContext()).load(model.getPurl()).into(holder.imageview);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_single_sample_row,parent,false);
        return new viewHolder(view);
    }

<<<<<<< HEAD
    public class viewHolder extends RecyclerView.ViewHolder
=======
    public static class viewHolder extends RecyclerView.ViewHolder
>>>>>>> b4077eae94aad323cfa32c31addccde1e00e876a
    {
        ImageView imageview;
        TextView textview;
        TextView address;

        public viewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageview = (ImageView) itemView.findViewById(R.id.imageView);
            textview = (TextView) itemView.findViewById(R.id.textView4);
            address = (TextView) itemView.findViewById(R.id.textView5);

        }
    }
   /* public void filter(CharSequence charSequence)
    {
        ArrayList<ModelClass> tempList= new ArrayList<>();
        if(!TextUtils.isEmpty(charSequence))
        {
           for(ModelClass modelClass:dataholder)
           {
               if(modelClass.getTxt().toLowerCase().contains(charSequence))
               {
                   tempList.add(modelClass);
               }
           }
        }
        if(TextUtils.isEmpty(charSequence))
        {
            tempList.addAll(arrayListCopy);
        }

        dataholder.clear();
        dataholder.addAll(tempList);
        notifyDataSetChanged();
        tempList.clear();
    }*/

}

