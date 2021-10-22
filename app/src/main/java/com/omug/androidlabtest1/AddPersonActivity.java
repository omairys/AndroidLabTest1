package com.omug.androidlabtest1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.ref.WeakReference;
import java.sql.Date;

public class AddPersonActivity extends AppCompatActivity {
    private TextView et_name, et_age, et_tuition, et_start_date;
    private PersonDatabase personDatabase;
    private Person person;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initUI();
    }

    private void initUI() {
        // Associate variables with view elements
        et_name = findViewById(R.id.inputName);
        et_age = findViewById(R.id.inputAge);
        et_tuition = findViewById(R.id.inputTuition);
        et_start_date = findViewById(R.id.intputStartDate);

        personDatabase = PersonDatabase.getDatabase(AddPersonActivity.this);
        Button button = findViewById(R.id.button_edit);

        if ((person = (Person) getIntent().getSerializableExtra("person")) != null) {
            //getSupportActionBar().setTitle("Update Person");
            update = true;
            button.setText("Update");
            et_name.setText(person.getName());
            et_age.setText(Integer.toString(person.getAge()));
            et_tuition.setText(Double.toString(person.getTuition()));
            et_start_date.setText(person.getStartDate().toString());
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update) {
                    person.setName(et_name.getText().toString());
                    person.setAge(Integer.parseInt(et_age.getText().toString()));
                    person.setTuition(Double.parseDouble(et_tuition.getText().toString()));
                    Date date = Date.valueOf(et_start_date.getText().toString());
                    person.setStartDate(date);
                    personDatabase.personDao().update(person);
                    setResult(person, 2);
                } else {
                    person = new Person(et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), Double.parseDouble(et_tuition.getText().toString()), Date.valueOf(et_start_date.getText().toString()));
                    new InsertTask(AddPersonActivity.this, person).execute();
                }
            }
        });
    }

    private void setResult(Person person, int flag) {
        setResult(flag, new Intent().putExtra("person", person));
        finish();
    }


    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddPersonActivity> activityReference;
        private Person person;

        // only retain a weak reference to the activity
        InsertTask(AddPersonActivity context, Person person) {
            activityReference = new WeakReference<>(context);
            this.person = person;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented person id
            long j = activityReference.get().personDatabase.personDao().insertPerson(person);
            person.setId(j);
            Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            Log.w("Bool ", "onPostExecute----------------------: " + bool);
            if (bool) {
                activityReference.get().setResult(person, 1);
                activityReference.get().finish();
            }
        }
    }
}
