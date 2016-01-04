package com.ten.dmitry.scrollable;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ListViewExtended{
    /*
        A class for Scroller app. Provides interface for easily managing entries in the
        scrollable list.
     */
    private ListView listView;
    private ArrayAdapter<Task> adapter;
    private SharedPreferences appData;
    private ArrayList<String> keys;

    public ListViewExtended(Activity activity, ListView view){
        /*
            Creates a new instance of the class with a specified activity and ListView.
            Initially, the there are no tasks in the app.
         */
        ArrayList<Task> tasks = new ArrayList<>();
        listView = view;
        keys = new ArrayList<>();

        // Getting hold of activity`s preference file.
        appData = activity.getPreferences(Context.MODE_PRIVATE);
        HashMap map = (HashMap) appData.getAll();
        if(!(map.size() == 0)) {
            Set key_set = map.keySet();
            for (Object key : key_set)
                keys.add((String)key);
            for (String key : keys) {;
                String name, description = "";
                String[] info = appData.getString(key, "").split("\n");
                name = info[0];
                for (int i = 1; i < info.length; i++)
                    description += info[i] + "\n";
                tasks.add(new Task(key, name, description));
            }
        }
        adapter = new ArrayAdapter<>(activity, R.layout.activity_list_view_item, tasks);
        listView.setAdapter(adapter);
    }

    public ListViewExtended(Activity activity, ListView view, ArrayList<Task> tasks){
        /*
            Creates a new instance of the class with specified parameters.
            This constructor takes the list of activities and fills the app with them.
         */
        listView = view;
        keys = new ArrayList<>(tasks.size());
        for(Task task: tasks)
            keys.add(task.getKey());
        appData = activity.getPreferences(Context.MODE_PRIVATE);
        adapter = new ArrayAdapter<>(activity, R.layout.activity_list_view_item, tasks);
        listView.setAdapter(adapter);
    }

    public boolean contains(Task task){
        for(int i=0; i<adapter.getCount(); i++)
            if(adapter.getItem(i).equals(task))
                return true;
        return false;
    }

    public void addTask(Task task){
        /*
            Adds a new task to the list view and saves its data in the
            SharedPreferences file.
         */
        adapter.add(task);
        keys.add(task.getKey());
        SharedPreferences.Editor edit = appData.edit();
        edit.putString(task.getKey(), task.getName()+"\n"+task.getDescription());
        edit.apply();
    }

    public void cancelTask(String key){
        /*
            Removes a task from the list view with a given key.
            Also deletes data about this task from appData.
         */
        for(int i=0; i<adapter.getCount(); i++)
            if(adapter.getItem(i).getKey().equals(key)){
                Task task = adapter.getItem(i);
                adapter.remove(task);
                keys.remove(task.getKey());
                SharedPreferences.Editor edit = appData.edit();
                edit.remove(key);
                edit.apply();
            }
    }

    public Task getTask(String key){
        for(int i=0; i<adapter.getCount(); i++)
            if(adapter.getItem(i).getKey().equals(key))
                return adapter.getItem(i);
        return null;
    }

    public Task getTaskByPos(int pos){
        if(pos>=0 && pos<adapter.getCount())
            return adapter.getItem(pos);
        throw new IndexOutOfBoundsException();
    }

    public int getPositionForView(View view){
        return listView.getPositionForView(view);
    }

    public String [] getKeys(){
        String [] key_array = new String[keys.size()];
        for(int i=0; i<keys.size(); i++)
            key_array[i] = keys.get(i);
        return key_array;
    }
}
