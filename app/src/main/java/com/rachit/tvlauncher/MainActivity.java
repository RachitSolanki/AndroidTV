package com.rachit.tvlauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

private final String TAG = "MainActivity";

/// Boiler Plate code

ImageView background;
ImageView logo,register;
TextView ChannelName;

String playerId="asZaVe";
CollectionReference userRef;
Query query;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.background);
        logo = findViewById(R.id.logo);
        ChannelName = findViewById(R.id.ChannelName);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startActivityForResult(new Intent(MainActivity.this,RegisterActivity.class),101);
                }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userRef = db.collection("Users");

        query = userRef.whereEqualTo("playerId", playerId);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                Log.e(TAG,((String)documentSnapshot.get("Name")));
                                changeChannel(documentSnapshot);
                        }

                }
        });


}


private void listenToChannels() {
        Log.e(TAG,"ID changed "+playerId);
        query = userRef.whereEqualTo("playerId", playerId);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                Log.e(TAG,((String)documentSnapshot.get("Name")));
                                changeChannel(documentSnapshot);
                        }

                }
        });

}



@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode == RESULT_OK) {
                playerId = data.getExtras().getString("id");
                listenToChannels();
        }

}

        private void changeChannel(DocumentSnapshot doc) {
        logo.setVisibility(View.VISIBLE);
        long what = (long) doc.get("ChannelLogo");
        ChannelName.setText(((String)doc.get("Name")));
        switch (((int)what)) {
                case 1  :  background.setImageResource(R.drawable.friends);
                           logo.setImageResource(R.drawable.cc_logo);
                           break;
                case 2  :  background.setImageResource(R.drawable.avengers_inf);
                           logo.setImageResource(R.drawable.hbo_logo);
                           break;
                default:   background.setImageResource(R.drawable.tv_init);
                           logo.setVisibility(View.GONE);
                           break;
        }
}


}
