package ie.ul.kevin_st_john.blitzmaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BlitzDetails extends AppCompatActivity {

    private TextView mBlitzDetailsTextView;
    private TextView mBlitzLocationTextView;
    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blitz_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBlitzDetailsTextView = findViewById(R.id.blitz_name);
        mBlitzLocationTextView = findViewById(R.id.blitz_location);

        //Pull the data out of the received
        Intent receivedIntent = getIntent();
        String docId = receivedIntent.getStringExtra(Constants.EXTRA_DOC_ID);

        mDocRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).document(docId);
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(Constants.TAG, "Listen Failed");
                    return;
                }
                if (documentSnapshot.exists()) {
                    // save the snapshot
                    mDocSnapshot = documentSnapshot;

                    //set the Edit Text Boxes based on what is in the cloud
                    mBlitzDetailsTextView.setText((String) documentSnapshot.get(Constants.KEY_NAME));
                    mBlitzLocationTextView.setText((String) documentSnapshot.get(Constants.KEY_LOC));
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //moviequote_dialog is the new layout XML file you created
        View view = getLayoutInflater().inflate(R.layout.team_dialog, null, false);

        builder.setView(view);
        builder.setTitle("Add Team");

        final TextView teamEditText = view.findViewById(R.id.dialog_teamname_edittext);

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // This is to push stuff to the cloud

                Map<String, Object> mq = new HashMap<>();

                mq.put(Constants.KEY_TEAM1, teamEditText.getText().toString());

                /*Map<String, Object> mq = new HashMap<>();
                mq.put(Constants.KEY_NAME, quoteEditText.getText().toString());
                mq.put(Constants.KEY_LOC, movieEditText.getText().toString());
                mq.put(Constants.KEY_CREATED, new Date());
                mq.put(Constants.KEY_TEAM1, "");
                mq.put(Constants.KEY_TEAM2, "");
                mq.put(Constants.KEY_TEAM3, "");
                mq.put(Constants.KEY_TEAM4, "");
                mq.put(Constants.KEY_TEAM5, "");
                mq.put(Constants.KEY_TEAM6, "");
                mq.put(Constants.KEY_TEAM7, "");
                mq.put(Constants.KEY_TEAM8, "");
                mq.put(Constants.KEY_TEAM9, "");
                mq.put(Constants.KEY_TEAM10, "");
                mq.put(Constants.KEY_NUM_OF_TEAM, new Integer(0));
                FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).add(mq);*/

                mDocRef.update(mq);
            }
        });
        builder.create().show();
    }

}
