package com.example.taskmaster;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaster.dummy.DummyContent;
import com.example.taskmaster.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TaskFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            List<Tasks> listOfTasks = new ArrayList<>();

            MyDatabase myDb;
            myDb = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "add_task").allowMainThreadQueries().build();
            listOfTasks= myDb.taskToDatabase().getAll();
            String TAG = "checker";
            Log.i(TAG, listOfTasks.toString());


//            listOfTasks.add(new Tasks("hygiene", "brush", "new"));
//            listOfTasks.add(new Tasks("work", "dress", "assigned"));
//            listOfTasks.add(new Tasks("piano", "play", "in progress"));
//            listOfTasks.add(new Tasks("guitar", "string", "complete"));
//            listOfTasks.add(new Tasks("java", "brush", "new"));
//            listOfTasks.add(new Tasks("dishes", "play", "in progress"));
//            listOfTasks.add(new Tasks("cook", "well", "new"));
//            listOfTasks.add(new Tasks("play", "brush", "complete"));
//            listOfTasks.add(new Tasks("socialize", "string", "in progress"));
//            listOfTasks.add(new Tasks("eat", "brush", "assigned"));
//            listOfTasks.add(new Tasks("sleep", "play", "in progress"));
//            listOfTasks.add(new Tasks("work", "well", "assigned"));
//            listOfTasks.add(new Tasks("java", "string", "complete"));
//            listOfTasks.add(new Tasks("work", "brush", "new"));


//            recyclerView = findViewById(R.id.fragment);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(new MyTaskListRecyclerViewAdapter(this.listOfTasks, this));

            recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(listOfTasks, null));
        }
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}