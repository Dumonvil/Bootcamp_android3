package com.example.bootcamp_android;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int codetext = 10;
    public static String itemtext = "itemtext";
    public static String itempsition = " itemposition";
    Button btn;
    EditText text;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.addbutton);
        list = (ListView)findViewById(R.id.LV);
        text = (EditText)findViewById(R.id.itemadd);

        read();

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        list.setAdapter(arrayAdapter);

        //
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra(itemtext,arrayList.get(position));
                intent.putExtra(itempsition,position);
                startActivityForResult(intent,codetext);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Item delete to List", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == codetext){
            String b = data.getExtras().getString(itemtext);
            int c = data.getExtras().getInt(itempsition);
            arrayList.set(c,b);
            write();
            Toast.makeText(this, "Mise a jour item succes", Toast.LENGTH_SHORT).show();
            arrayAdapter.notifyDataSetChanged();

        }
    }

    //save Item to list
    public void saveitem(View view){
        String a = text.getText().toString();
        if (a.isEmpty()){
            Toast.makeText(this, "Ce champ ne peut etre vide", Toast.LENGTH_SHORT).show();
        }else{
            arrayAdapter.add(a);
            write();
            text.setText("");
            Toast.makeText(this, "item ajoute dans la liste", Toast.LENGTH_SHORT).show();
        }
    }
    private File getitem(){
        return new File(getFilesDir(),"itemlist.txt");
    }
    private void read(){
        try {
            arrayList = new ArrayList<>(FileUtils.readLines(getitem(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void write() {
        try {
            FileUtils.writeLines(getitem(),arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
