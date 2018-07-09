package com.rachit.tvlauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Rachit Solanki on 9/7/18.
 */
public class RegisterActivity extends AppCompatActivity {
        EditText stbId;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);
                stbId = findViewById(R.id.stbId);

                findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                              if(stbId.getText().toString().equals("")||stbId.getText().toString().equals(" ")) {
                                      Toast.makeText(RegisterActivity.this,"Field cannot be blank",Toast.LENGTH_SHORT).show();
                                      return;
                              }

                              Intent intent = new Intent();
                              intent.putExtra("id",stbId.getText().toString());
                              setResult(RESULT_OK,intent);
                              finish();
                        }
                });
        }
}
