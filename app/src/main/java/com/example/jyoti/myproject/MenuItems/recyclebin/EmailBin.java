package com.example.jyoti.myproject.MenuItems.recyclebin;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jyoti.myproject.Adapter_Class.MessageAdapter;
import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.Fragments.EmailFragment;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.recyclerview_functions.DividerItemDecoration;
import com.example.jyoti.myproject.recyclerview_functions.RecyclerTouchListener;

import java.util.List;

import static com.example.jyoti.myproject.R.style.MyDialogTheme;

/**
 * Created by Jyoti on 7/22/2017.
 */

public class EmailBin  extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<ReminderBean> reminderlist;
    RecyclerView recyclerView;
    MessageAdapter adapter;
    TextView tvinfo;

    database db;

    private EmailFragment.OnFragmentInteractionListener mListener;

    public EmailBin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmailFragment newInstance(String param1, String param2) {
        EmailFragment fragment = new EmailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_email, container, false);
        tvinfo=(TextView)view.findViewById(R.id.tvinfo);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        db=new database(getActivity());

        SetCardView();
        RecyclerViewClick();
        return view;
    }

    public void SetCardView()
    {
        reminderlist=db.getEmailReminder(1);
        if(reminderlist.size()<1)
        {
            tvinfo.setVisibility(View.VISIBLE);
        }
        else {
            tvinfo.setVisibility(View.INVISIBLE);

            Log.e("List", "" + reminderlist);
            adapter = new MessageAdapter(getActivity(), reminderlist);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public void RecyclerViewClick()
    {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener()
        {


            @Override
            public void onClick(View view, int position) {
                reminderlist.get(position).getStatus();
                // reminderlist.get(position).;
/*                Intent intent=new Intent(getActivity(),ShowEmployee.class);
                intent.putExtra("id",String.valueOf(emplist.get(position).getId()));
                startActivity(intent);*/
            }

            @Override
            public void onLongClick(View view, final int position) {
                final CharSequence[] options = new CharSequence[]{"Restore", "Permanent Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // builder.setTitle("Add Video!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Restore")){
                            String num=String.valueOf(reminderlist.get(position).getReminder_id());
                            database db=new database(getActivity());
                            db.deleteReminder(num,0);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                            reminderlist=db.getEmailReminder(1);
                            adapter=new MessageAdapter(getActivity(),reminderlist);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            dialog.cancel();

                        }
                        else if(options[item].equals("Permanent Delete"))
                        {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(),MyDialogTheme);
                            builder2.setTitle("Delete Reminder").setMessage("Are You Sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String num=String.valueOf(reminderlist.get(position).getReminder_id());
                                    database db=new database(getActivity());
                                    db.pemanentdeleteReminder(num);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                    reminderlist=db.getEmailReminder(1);
                                    adapter=new MessageAdapter(getActivity(),reminderlist);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    dialog.cancel();

                                }
                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).setIcon(R.drawable.pdelete).show();


                        }
                    }
                });
                builder.show();
            }
        }
        ));

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SetCardView();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
