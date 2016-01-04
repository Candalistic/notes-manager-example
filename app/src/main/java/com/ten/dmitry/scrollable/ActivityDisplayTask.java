package com.ten.dmitry.scrollable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ActivityDisplayTask extends AppCompatActivity {
    public static String TASK_PRESERVATION = "com.ten.scrollable.ActivityDisplayTask.task_preserv";
    public static String R_POSITION="com.ten.scrollable.ActivityDisplayTask.position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_display_task);

        // Setting text of textviews to the corresponding name and description values passed
        // from the main activity.
        String task_info = getIntent().getStringExtra(MainActivity.TASK_INFO);
        String [] task_info_array = task_info.split("\n");
        ((TextView)findViewById(R.id.display_task_name)).setText(task_info_array[0]);
        String rest = "";
        int i=1;
        while (i < task_info_array.length){
            rest += task_info_array[i] + "\n";
            i++;
        }
        ((TextView)findViewById(R.id.display_task_description)).setText(rest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_display_task, menu);
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

    public void closeTask(View view){
        Intent result_intent = new Intent();
        result_intent.putExtra(TASK_PRESERVATION, true);
        this.setResult(1, result_intent);
        finish();
    }

    public void cancelExistingTask(View view){
        Intent result_intent = new Intent();
        int pos = getIntent().getIntExtra(MainActivity.VIEW_POSITION, -1);
        result_intent.putExtra(TASK_PRESERVATION, false);
        result_intent.putExtra(R_POSITION, pos);
        this.setResult(Activity.RESULT_OK, result_intent);
        finish();
    }
}
