package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreAdapter extends FirestorePagingAdapter<AdminModel,FirestoreAdapter.AdminViewHolder> {

    private  OnListItemClick onListItemClick;
    public FirestoreAdapter(@NonNull FirestorePagingOptions<AdminModel> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdminViewHolder holder, int position, @NonNull AdminModel model) {
        holder.textView.setText(model.getTitle());
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textOFCard);
        return new AdminViewHolder(convertView);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state)
        {
            case ERROR:
               // Toast.makeText(Dashboard.this,"Something Went Wrong", Toast.LENGTH_SHORT).show();
            case FINISHED:
              //  Toast.makeText(Dashboard.this,"You Reached The End", Toast.LENGTH_SHORT).show();
            case LOADING_INITIAL:
               // dataLoad.setVisibility(View.VISIBLE);
            case LOADING_MORE:
               // dataLoad.setVisibility(View.VISIBLE);
            case LOADED:
              //  dataLoad.setVisibility(View.GONE);

        }
    }


    public   class AdminViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView img;
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textOFCard);
            img = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }
    public interface  OnListItemClick{
        void onItemClick(DocumentSnapshot snapshot,int position);
    }
}
