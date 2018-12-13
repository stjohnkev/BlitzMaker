package ie.ul.kevin_st_john.blitzmaker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private List<DocumentSnapshot> mBlitzSnapshots = new ArrayList<>();

    //constructor
    public TeamAdapter(){
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

    @NonNull
    @Override
    public TeamAdapter.TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamitemview, parent, false);
        return new TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.TeamViewHolder teamViewHolder, int position) {

        //Get the Document Snapshot
        //mBlitzSnapshots is getDocuments
        //

        DocumentSnapshot ds = mBlitzSnapshots.get(position);
        String team1 = (String)ds.get(Constants.KEY_TEAM1);
        String team2 = (String)ds.get(Constants.KEY_TEAM2);
        String team3 = (String)ds.get(Constants.KEY_TEAM3);

        //needs to be cast to a string array as get returns a generic object
        // Get the values out of the document snapshot
        //String[] teamsArray = new String[10];
        //teamsArray = (String[]) ds.get(Constants.KEY_TEAMS);

        //String location = (String)ds.get(Constants.KEY_LOC);
        //Set the values in the View Holder

        teamViewHolder.mTeamNameTextView.setText(team1);
       // holder.mLocTextView.setText(location);

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class TeamViewHolder extends RecyclerView.ViewHolder{
        private TextView mTeamNameTextView;
        public TeamViewHolder(@NonNull final View itemView){
            super(itemView);
            mTeamNameTextView = itemView.findViewById(R.id.itemview_team);
           // mLocTextView = itemView.findViewById(R.id.itemview_blitzLocation);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentSnapshot ds = mBlitzSnapshots.get(getAdapterPosition());
                    Context c = itemView.getContext();
                    Intent intent =new Intent(c, BlitzDetails.class);

                    //push data to the 2nd screen
                    // you are passing to the other intent
                    intent.putExtra(Constants.EXTRA_DOC_ID, ds.getId());
                    c.startActivity(intent);
                }
            });*/

        }
    }
}
