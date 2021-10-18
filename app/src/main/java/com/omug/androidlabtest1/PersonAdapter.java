package com.omug.androidlabtest1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.BeanHolder>{
    private List<Person> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnPersonItemClick onPersonItemClick;

    public PersonAdapter(List<Person> list, Context context) {
        layoutInflater = layoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onPersonItemClick = (OnPersonItemClick) context;
    }

    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //This is how I get the custom cell
        View view = layoutInflater.inflate(R.layout.person_list_item, parent, false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: " + list.get(position));
        holder.textViewName.setText(list.get(position).getName());
        holder.textViewAge.setText(String.valueOf(list.get(position).getAge()));
        holder.textViewTuition.setText(String.valueOf(list.get(position).getTuition()));
        holder.textViewStartDate.setText(String.valueOf(list.get(position).getStartDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName;
        TextView textViewAge;
        TextView textViewTuition;
        TextView textViewStartDate;

        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.tv_name);
            textViewAge = itemView.findViewById(R.id.tv_age);
            textViewTuition = itemView.findViewById(R.id.tv_tution);
            textViewStartDate = itemView.findViewById(R.id.tv_startdate);
        }

        @Override
        public void onClick(View view) {
            onPersonItemClick.onPersonClick(getAdapterPosition());
        }
    }

    public interface OnPersonItemClick {
        void onPersonClick(int pos);
    }
}
