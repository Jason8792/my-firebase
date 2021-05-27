package com.jason.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jason.myapplication.R;
import com.jason.myapplication.databinding.PersonItemBinding;
import com.jason.myapplication.entity.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> persons;

    public PersonAdapter(List<Person> persons){
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.setPerson(persons.get(position));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder{
        private PersonItemBinding binding;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PersonItemBinding.bind(itemView);
        }

        public void setPerson(Person Data){
            binding.tvId.setText(Data.getId());
            binding.tvName.setText(String.format("%s %s",Data.getFirstname(),Data.getLastname()));
        }
    }
}
