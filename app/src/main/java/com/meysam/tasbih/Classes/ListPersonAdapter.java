package com.meysam.tasbih.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meysam.tasbih.R;

import java.util.ArrayList;
import java.util.List;

public class ListPersonAdapter extends BaseAdapter {
    Context context;
    private List<Person> personList;
    private ArrayList<Person> arrayList;
    LayoutInflater inflater;

    public ListPersonAdapter(Context context , List<Person> personList){

        this.context = context;
        this.personList = personList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Person>();
        this.arrayList.addAll(personList);
    }

    public class ViewHolder{
        public TextView txtName;
    }
    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.person_row, null);
            holder.txtName = view.findViewById(R.id.txt_person_row);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtName.setText(personList.get(position).getNamePerson());

        return view;
    }


}
