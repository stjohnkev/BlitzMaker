package ie.ul.kevin_st_john.blitzmaker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

import static ie.ul.kevin_st_john.blitzmaker.BlitzAdapter.BlitzViewHolder.*;

public class BlitzAdapter extends RecyclerView.Adapter<BlitzAdapter.BlitzViewHolder>{


    private List<DocumentSnapshot> mBlitzSnapshots = new ArrayList<>();

    public BlitzAdapter(){
        //Create a reference to the firestore collection
        CollectionReference blitzRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH);
        // Order by
        blitzRef.orderBy(Constants.KEY_CREATED, Query.Direction.DESCENDING).limit(50).addSnapshotListener(new EventListener<QuerySnapshot>() {
            //Listen for events changing in the firestore
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.w(Constants.TAG, "Listening failed");
                    return;
                }
                //save the documents
                mBlitzSnapshots = documentSnapshots.getDocuments();
                notifyDataSetChanged();
            }
        });
    }
/*
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView=recyclerView;
    }*/

    /*public void addBlitz(){
        //TODO - attempted
        Blitz newBlitz = new Blitz();
        mBlitz.add(0, newBlitz);
        //newFood.getImageResourceId();

        notifyItemInserted(0);
        notifyItemRangeChanged(0, mBlitz.size());
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);

    }*/

    /*public void removeName(int index){
        mFoods.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(0,mFoods.size());
    }
*/
    @NonNull
    @Override
    public BlitzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO - Attempted
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blitzitemview, parent, false);
        return new BlitzViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull BlitzViewHolder holder, int position) {
        //Get the Document Snapshot
        DocumentSnapshot ds = mBlitzSnapshots.get(position);
        //needs to be cast to a string as get returns a generic object
        // Get the values out of the document snapshot
        String name = (String)ds.get(Constants.KEY_NAME);
        String location = (String)ds.get(Constants.KEY_LOC);
        //Set the values in the View Holder

        holder.mNameTextView.setText(name);
        holder.mLocTextView.setText(location);


        /*final Blitz blitz = mBlitz.get(position);
        holder.mName.setText(blitz.getName());
        holder.mLocation.setText(blitz.getLocation());
*/
        /*
        holder.mName.setText(food.getName());
        holder.mImageView.setImageResource(food.getImageResourceId());
        holder.mRatingBar.setRating(food.getRating());*/
        // TODO get food from array of foods

    }

    @Override
    public int getItemCount() {
        return mBlitzSnapshots.size();
    }


    //BLITZVIEWHOLDER CLASS
    class BlitzViewHolder extends RecyclerView.ViewHolder{


        private TextView mNameTextView;
        private TextView mLocTextView;
        //private RatingBar mRatingBar;

        public BlitzViewHolder(@NonNull final View itemView){
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemview_blitzName);
            mLocTextView = itemView.findViewById(R.id.itemview_blitzLocation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //  DocumentSnapshot ds = mBlitzSnapshots.get(getAdapterPosition());
                   //Context c = itemView.getContext();
                   //Intent intent =new Intent(c, MovieQuoteDetailActivity.class);

                    //push data to the 2nd screen
                    // you are passing to the other intent
                    //intent.putExtra(Constants.EXTRA_DOC_ID, ds.getId());
                    //c.startActivity(intent);
                }
            });

        }
           // super(itemView);
          //  mImageView=itemView.findViewById(R.id.food_pic);
            //mName=itemView.findViewById(R.id.itemview_blitzName);
            //mLocation=itemView.findViewById(R.id.itemview_blitzLocation);
           // mRatingBar=itemView.findViewById(R.id.rating_bar);

            //DONE Delete a food on a a long press
            //itemView.setOnLongClickListener(new View.OnLongClickListener() {
              //  @Override
                //public boolean onLongClick(View v) {
                    //removeName(getAdapterPosition());
                  //  return true;
                //}
            //});

    }
}

