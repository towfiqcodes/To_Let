package com.rit.basa_bari.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rit.basa_bari.R;
import com.rit.basa_bari.models.HostelUpload;
import com.rit.basa_bari.models.MessUpload;

import java.util.List;

public class HostelPostAdapter extends RecyclerView.Adapter<HostelPostAdapter.HostelViewHolder> {

   Context context;
    private List<HostelUpload> hostelUploads;


    public HostelPostAdapter(Context context,List<HostelUpload> hostelUploads) {
        this.context = context;
        this.hostelUploads =hostelUploads ;

    }
    @NonNull
    @Override
    public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context);
        View view = inflater.inflate(R.layout.show_hostel_item, null);
        HostelViewHolder hostelViewHolder=new HostelViewHolder( view );
        return hostelViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HostelViewHolder holder, int position) {
            final  HostelUpload currentUploads=hostelUploads.get( position );
        holder.textRent.setText( currentUploads.getHostelRent() +" BDT");
        holder.textPhoneNumber.setText( currentUploads.getHophone());
        holder.textLocation.setText( currentUploads.getHolocation());
        holder.textGender.setText( currentUploads.getHogender() );
        holder.textMonth.setText( currentUploads.getHomonth() );
    }

    @Override
    public int getItemCount() {
        return hostelUploads.size();
    }

    public class HostelViewHolder extends RecyclerView.ViewHolder{
        TextView textRent,textPhoneNumber,textLocation,textGender,textMonth;
        ImageView imageView;
        CardView button;

        public HostelViewHolder(@NonNull View itemView) {
            super( itemView );
            textRent=itemView.findViewById( R.id.rentTV );
            textPhoneNumber=itemView.findViewById( R.id.phoneNumberTV );
            textLocation=itemView.findViewById( R.id.locationTV );
            imageView=itemView.findViewById( R.id.imageView );
            textGender=itemView.findViewById( R.id.genderTv );
            textMonth=itemView.findViewById( R.id.monthTV );
            button=itemView.findViewById( R.id.cardView );
        }
    }
}
