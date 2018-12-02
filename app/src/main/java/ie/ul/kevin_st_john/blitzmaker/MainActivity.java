package ie.ul.kevin_st_john.blitzmaker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        // DONE Connect the RecyclerView to the NameAdapter
        BlitzAdapter blitzAdapter = new BlitzAdapter();
        recyclerView.setAdapter(blitzAdapter);

        //blitzAdapter.addBlitz();

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
        View view = getLayoutInflater().inflate(R.layout.blitz_dialog, null, false);

        builder.setView(view);
        builder.setTitle("New Blitz");

        //dialog_quote_edittext is the Edit Text View you created in new XML file moviequote_dialog
        final TextView quoteEditText = view.findViewById(R.id.dialog_name_edittext);
        final TextView movieEditText = view.findViewById(R.id.dialog_location_edittext);

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // This is to push stuff to the cloud
                Map<String, Object> mq = new HashMap<>();
                mq.put(Constants.KEY_NAME, quoteEditText.getText().toString());
                mq.put(Constants.KEY_LOC, movieEditText.getText().toString());
                mq.put(Constants.KEY_CREATED, new Date());
                FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).add(mq);
            }
        });

        builder.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
