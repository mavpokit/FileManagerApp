package com.mavpokit.filemanagerapp;

import android.app.AlertDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> stringlist=new ArrayList<String>();
    private List<File> filelist=new ArrayList<File>();
    private String root="/";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listView);

        File file=new File(root);

        show(file);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File f = filelist.get(position);
                if (f.isDirectory()) {
                    show(f);

                } else {
                    if (f.canRead()) showfileinfo(f);
                    else
                        Toast.makeText(getApplicationContext(), "File can't be read", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }

    private void showfileinfo(File f) {
        String filename=f.getName();
        int pos=filename.lastIndexOf('.');
        String name=filename;
        String type="";
        if (pos>0){
            name = filename.substring(0, pos);
            type = filename.substring(pos + 1, filename.length());
        }

        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(f.getName())
                .setMessage("name: " + name + "\n" +
                        "type: " + type + "\n" +
                        "modified: " + new SimpleDateFormat().format(f.lastModified()) + "\n" +
                        "parent dir: " + f.getParent())
                .create();
        alertDialog.show();

    }

    private void show(File file) {
        if (file.isDirectory()){
            if (!file.canRead()) {
                Toast.makeText(getApplicationContext(), "Dir can't be read", Toast.LENGTH_SHORT).show();
                return;
            }

            stringlist.clear();
            filelist.clear();
            if (!file.getName().equals("")){
                stringlist.add(".."+'\u2934');
                filelist.add(file.getParentFile());
            }


            for(File f:file.listFiles()) {
                filelist.add(f);
                if (f.isDirectory()) stringlist.add(f.getName() + "\\");
                else stringlist.add(f.getName());
            }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringlist);
            listView.setAdapter(adapter);
        }




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
