package com.jason.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jason.myapplication.adapter.PersonAdapter;
import com.jason.myapplication.databinding.ActivityMainBinding;
import com.jason.myapplication.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String PERSON_REF = "person";
    private static final String PERSON_PROP1 = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Person> persons = new ArrayList<>();
        PersonAdapter personadapter = new PersonAdapter(persons);
        binding.rvData.setLayoutManager(new LinearLayoutManager(this));
        binding.rvData.setAdapter(personadapter);
        binding.save.setOnClickListener(view->{
            Person person = new Person();
            person.setId(binding.etId.getText().toString());
            person.setFirstname(binding.etFirstName.getText().toString());
            person.setLastname(binding.etLastName.getText().toString());
            savePersonData(person);
            binding.etId.setText("");
            binding.etFirstName.setText("");
            binding.etLastName.setText("");
            Snackbar snackbar = Snackbar.make(binding.rootlayout,R.string.success_message,Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.action_ok,view1->snackbar.dismiss());
            snackbar.show();
        });
        fetchfirebasedata(persons, personadapter);
    }
    private void  fetchfirebasedata(List<Person> persons, PersonAdapter personadapter){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(PERSON_REF);
        dbref.orderByChild(PERSON_PROP1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                persons.clear();
                for(DataSnapshot childSnap:snapshot.getChildren()){
                    persons.add(childSnap.getValue(Person.class));
                }
                personadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,R.string.cancel_message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePersonData(Person person){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(PERSON_REF);
        dbref.push().setValue(person);
    }
}