package ie.ul.kevin_st_john.blitzmaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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

                    //user can now see it
                    mBlitzDetailsTextView.setText((String) documentSnapshot.get(Constants.KEY_NAME));
                    mBlitzLocationTextView.setText((String) documentSnapshot.get(Constants.KEY_LOC));
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
