package com.example.jyoti.myproject.Adapter_Class;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.jyoti.myproject.Bean_Class.ContactBean;
import com.example.jyoti.myproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jyoti on 7/15/2017.
 */

public class SelectContactAdapter extends RecyclerView.Adapter<SelectContactAdapter.MyViewHolder> implements Filterable{

    public List<ContactBean> contacts;
    public List<ContactBean> filterContacts;
    private ArrayList<ContactBean> arraylist;
    Context _c;
    MyViewHolder v;

    public SelectContactAdapter(List<ContactBean> selectUsers, Context context) {
        contacts = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<ContactBean>();
        this.arraylist.addAll(contacts);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, phone;
        CheckBox check;

        public MyViewHolder(View view) {
            super(view);
            //  this.view=view;
            title = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.no);
            check = (CheckBox) view.findViewById(R.id.check);


        }

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public SelectContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_contact, parent, false);
        return new SelectContactAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ContactBean rowItem = contacts.get(position);
        holder.title.setText("" + rowItem.getName());
        holder.phone.setText("" + rowItem.getPhone());
        holder.check.setChecked(rowItem.isCheckbox());

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.check.isChecked()) {
                    rowItem.setCheckbox(true);
                } else {
                    rowItem.setCheckbox(false);
                }
            }
        });

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                Log.e("seq",""+charSequence);
                if (charString.isEmpty()) {

                    filterContacts= contacts;
                } else {

                    ArrayList<ContactBean> filteredList = new ArrayList<>();

                    for (ContactBean contact: contacts) {

                        if ((contact.getName().toLowerCase().contains(charString))||(contact.getPhone().toLowerCase().contains(charString) )) {
                            filteredList.add(contact);
                        }
                    }

                    filterContacts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterContacts = (List<ContactBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updateList(List<ContactBean> list){
        contacts = list;
        notifyDataSetChanged();
    }
}

/*
    @Override
        public Object getItem(int i) {
            return _data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = convertView;
            if (view == null) {
                LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.contact_info, null);
                Log.e("Inside", "here--------------------------- In view1");
            } else {
                view = convertView;
                Log.e("Inside", "here--------------------------- In view2");
            }

            v = new ViewHolder();

            v.title = (TextView) view.findViewById(R.id.name);
            v.check = (CheckBox) view.findViewById(R.id.check);
            v.phone = (TextView) view.findViewById(R.id.no);
            v.imageView = (ImageView) view.findViewById(R.id.pic);

            final SelectUser data = (SelectUser) _data.get(i);
            v.title.setText(data.getName());
            v.check.setChecked(data.getCheckedBox());
            v.phone.setText(data.getPhone());

            // Set image if exists
            try {

                if (data.getThumb() != null) {
                    v.imageView.setImageBitmap(data.getThumb());
                } else {
                    v.imageView.setImageResource(R.drawable.image);
                }
                // Seting round image
                Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.image); // Load default image
                roundedImage = new RoundImage(bm);
                v.imageView.setImageDrawable(roundedImage);
            } catch (OutOfMemoryError e) {
                // Add default picture
                v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.image));
                e.printStackTrace();
            }

            Log.e("Image Thumb", "--------------" + data.getThumb());

        */
/*//*
/ Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    data.setCheckedBox(true);
                  } else {
                    data.setCheckedBox(false);
                }
            }
        });*//*


            view.setTag(data);
            return view;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            _data.clear();
            if (charText.length() == 0) {
                _data.addAll(arraylist);
            } else {
                for (SelectUser wp : arraylist) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        _data.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
*/



