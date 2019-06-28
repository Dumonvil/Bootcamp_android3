package com.example.bootcamp_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.bootcamp_android.MainActivity.itempsition;
import static com.example.bootcamp_android.MainActivity.itemtext;

public class EditActivity extends AppCompatActivity {
    EditText editText;
    int position;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = (EditText)findViewById(R.id.itemedit);

        editText.setText(getIntent().getStringExtra(itemtext));
        position = getIntent().getIntExtra(itempsition,0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(itemtext,editText.getText().toString());
                intent.putExtra(itempsition,position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }


}
