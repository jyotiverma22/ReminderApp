package com.example.jyoti.myproject.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.Reminder.CreateReminder;
import com.example.jyoti.myproject.Reminder.ShowReminder;
import com.example.jyoti.myproject.ToDoList.CreateToDoList;
import com.example.jyoti.myproject.recyclerview_functions.DividerItemDecoration;
import com.example.jyoti.myproject.recyclerview_functions.RecyclerTouchListener;

import java.util.List;

import static com.example.jyoti.myproject.R.style.MyDialogTheme;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
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
    database db;
    TextView tvinfo;
    FloatingActionButton fab;


    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        db=new database(getActivity());
        tvinfo=(TextView)view.findViewById(R.id.tvinfo);
        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        SetCardView();
        RecyclerViewClick();
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = new CharSequence[]{"Reminder", "TodoList"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // builder.setTitle("Add Video!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Reminder")){
                            Intent intent=new Intent(getActivity(),CreateReminder.class);
                            startActivity(intent);
                            dialog.cancel();
                        }
                        else if(options[item].equals("TodoList"))
                        {
                            Intent intent=new Intent(getActivity(),CreateToDoList.class);
                            startActivity(intent);
                            dialog.cancel();
                        }
                    }
                });
                builder.show();
            }
        });


    }

    public void SetCardView()
    {
        reminderlist=db.getMessageReminder(0);
        Log.e("List",""+reminderlist);
        if(reminderlist.size()<1)
        {
            tvinfo.setVisibility(View.VISIBLE);
        }
        else {
            tvinfo.setVisibility(View.INVISIBLE);

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
                Intent intent=new Intent(getActivity(),ShowReminder.class);
                Log.e("Reminder id",""+reminderlist.get(position).getReminder_id());
                intent.putExtra("Rid",String.valueOf(reminderlist.get(position).getReminder_id()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                final CharSequence[] options = new CharSequence[]{"Move to Trash"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),MyDialogTheme);
                // builder.setTitle("Add Video!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(options[item].equals("Move to Trash"))
                        {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(),MyDialogTheme);
                            builder2.setTitle("Move to Trash").setMessage("Are You Sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String num=String.valueOf(reminderlist.get(position).getReminder_id());
                                    database db=new database(getActivity());
                                    db.deleteReminder(num,1);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Moved to Trash", Toast.LENGTH_SHORT).show();
                                    reminderlist=db.getMessageReminder(0);
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
                                    }).setIcon(R.drawable.delete).show();


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
