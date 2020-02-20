package com.example.taskmaster;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmaster.TaskFragment.OnListFragmentInteractionListener;
import com.example.taskmaster.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {
static final String TAG = "mnf.ViewAdapter";
    private final List<Tasks> mValues;
    private final OnListFragmentInteractionListener mListener;
    public View vv ;

    public MyTaskRecyclerViewAdapter(List<Tasks> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mBodyView.setText(mValues.get(position).getBody());

        holder.mStateView.setText(mValues.get(position).getState());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "it was clicked");
                Context context = vv.getContext();
                Log.i(TAG, "--------------" + context.getClass().getName());
                if(context.getClass().getName().equals("com.example.taskmaster.AllTasks")){
//                    Context context = getApplicationContext();
                    CharSequence confirmation = " Title is: " + holder.mItem.title + " body is: " + holder.mItem.body + " state is: " + holder.mItem.state;
//                            "Your click is on all task page";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, confirmation, duration);
                    toast.show();
                    toast.setGravity(Gravity.TOP | Gravity.LEFT,  500,850);
                }
                else if (context.getClass().getName().equals("com.example.taskmaster.MainActivity")){
                    Intent gotoTaskdetailPage = new Intent(context, TaskDetail.class);


                    gotoTaskdetailPage.putExtra("title", holder.mItem.title);


                    gotoTaskdetailPage.putExtra("body", holder.mItem.body);
                    gotoTaskdetailPage.putExtra("state", holder.mItem.state);



                    context.startActivity((gotoTaskdetailPage));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;

        public final TextView mBodyView;

        public final TextView mStateView;


        public Tasks mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            vv = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mBodyView = view.findViewById(R.id.body);
            mStateView = (TextView) view.findViewById(R.id.state);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStateView.getText() + "'";
        }
    }
}
