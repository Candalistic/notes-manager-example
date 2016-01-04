package com.ten.dmitry.scrollable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

public class NewTaskActivity extends AppCompatActivity {
    public static String INFO = "com.ten.scrollable.NewTaskActivity.info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void createTaskCompleted(View view){
        String name = ((EditText)findViewById(R.id.new_task_name)).getText().toString();
        String description = ((EditText)findViewById(R.id.new_task_description)).getText().toString();
        String key = UUID.randomUUID().toString();
        String [] info = {key, name, description};
        Intent resultIntent = new Intent();
        resultIntent.putExtra(INFO, info);
        setResult(Activity.RESULT_OK, resultIntent);
        this.finish();
    }

    public void cancelTaskCreation(View view){
        finish();
    }
}
