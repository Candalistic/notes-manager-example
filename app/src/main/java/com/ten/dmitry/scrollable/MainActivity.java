package com.ten.dmitry.scrollable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static String TASK_INFO = "com.ten.scrollable.MainActivity.task_info";
    public static String VIEW_POSITION = "com.ten.scrollable.MainActivity.view_pos";
    public static int REQUEST_CODE_TASK_CLICK = 1, REQUEST_CODE_TASK_CREATE = 0;
    private static String SAVED_DATA = "com.ten.scrollable.MainActivity.saved_data";
    private ListViewExtended list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String[] keys = savedInstanceState.getStringArray(SAVED_DATA);
            ArrayList<Task> tasks = new ArrayList<>();
            SharedPreferences appData = getPreferences(Context.MODE_PRIVATE);
            for (String key : keys) {
                String name, description = "";
                String[] info = appData.getString(key, "").split("\n");
                name = info[0];
                for (int i = 1; i < info.length; i++)
                    description += info[i] + "\n";
                tasks.add(new Task(key, name, description));
            }
            list = new ListViewExtended(this, (ListView)findViewById(R.id.list_view), tasks);
        }
        catch(NullPointerException e){
            list = new ListViewExtended(this, (ListView)findViewById(R.id.list_view));
        }
        //setting text_size according to settings value
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int size = Integer.parseInt(sharedPreferences.getString("text_size", "18"));


    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        SharedPreferences data = getPreferences(Context.MODE_PRIVATE);
        HashMap map = (HashMap) data.getAll();
        Object [] key_set = map.keySet().toArray();
        String [] keys = new String[key_set.length];
        for(int i=0; i< key_set.length; i++)
            keys[i] = (String) key_set[i];
        outState.putStringArray(SAVED_DATA, keys);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.clear_all){
            String [] keys = list.getKeys();
            for(String key: keys)
                list.cancelTask(key);
            return true;
        }
        else if(id == R.id.add_task){
            Intent intent = new Intent(this, NewTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE_TASK_CREATE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_TASK_CREATE && resultCode == Activity.RESULT_OK){
            String [] new_info = data.getStringArrayExtra(NewTaskActivity.INFO);
            Task new_task = new Task(new_info[0], new_info[1], new_info[2]);
            list.addTask(new_task);
        }
        else if(requestCode == REQUEST_CODE_TASK_CLICK && resultCode == Activity.RESULT_OK){
            boolean decision = data.getBooleanExtra(ActivityDisplayTask.TASK_PRESERVATION, true);
            if(!decision){
                int pos = data.getIntExtra(ActivityDisplayTask.R_POSITION, -1);
                System.out.println("Deleting task at pos " + pos);
                list.cancelTask(list.getTaskByPos(pos).getKey());
            }
        }
    }

    public void listItemClick(View view){
        int pos = list.getPositionForView(view);
        String task_info = list.getTaskByPos(pos).toString()+"\n" +
                list.getTaskByPos(pos).getDescription();
        Intent intent = new Intent(this, ActivityDisplayTask.class);
        intent.putExtra(TASK_INFO, task_info);
        intent.putExtra(VIEW_POSITION, pos);
        startActivityForResult(intent, REQUEST_CODE_TASK_CLICK);
    }
}
