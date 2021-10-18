package com.omug.androidlabtest1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnPersonItemClick {
    private Toolbar mToolbar;
    private PersonDatabase personDatabase;
    private RecyclerView recyclerView;
    private List<Person> persons;
    private PersonAdapter personAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVies();
        displayList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void displayList() {
        personDatabase = PersonDatabase.getDatabase(MainActivity.this);
        new RetrieveTask(this).execute();
    }

    //este metodo carga la informacion de las personas en la lista
    private static class RetrieveTask extends AsyncTask<Void, Void, List<Person>> {
        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Person> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().personDatabase.personDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Person> notes) {
            if (notes != null && notes.size() > 0) {
                activityReference.get().persons.clear();
                activityReference.get().persons.addAll(notes);

                activityReference.get().personAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPersonClick(int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                personDatabase.personDao().delete(persons.get(pos));
                                persons.remove(pos);
                                personAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                MainActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(MainActivity.this,
                                                AddPersonActivity.class).putExtra("person", persons.get(pos)), 100);
                                break;
                        }
                    }
                }).show();

    }

    private void initializeVies() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(listener);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        persons = new ArrayList<>();
        personAdapter = new PersonAdapter(persons, MainActivity.this);
        recyclerView.setAdapter(personAdapter);
    }

    private Toolbar.OnMenuItemClickListener listener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_add:
                    startActivityForResult(new Intent(MainActivity.this, AddPersonActivity.class), 100);
                    break;
                default:
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                persons.add((Person) data.getSerializableExtra("person"));
            } else if (resultCode == 2) {
                persons.set(pos, (Person) data.getSerializableExtra("person"));
            }
            personAdapter.notifyDataSetChanged();
        }
    }
}