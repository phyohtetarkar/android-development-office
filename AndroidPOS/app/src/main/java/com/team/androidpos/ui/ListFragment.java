package com.team.androidpos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.androidpos.R;

public abstract class ListFragment extends Fragment {

    protected abstract RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter();
    protected abstract boolean listenSwipeDelete();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter());

        if (listenSwipeDelete()) {
            SwipeDeleteGestureCallback callback = new SwipeDeleteGestureCallback(requireContext());
            callback.setOnSwipeDeleteListener(position -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure to delete?")
                        .setNegativeButton(android.R.string.cancel, (di, i) -> {
                            adapter().notifyItemChanged(position);
                            di.dismiss();
                        })
                        .setPositiveButton(R.string.delete, (di, i) -> {
                            deleteItemAt(position);
                            di.dismiss();
                        })
                        .show();
            });

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this::onFabClick);

        // TODO hide/show on recyclerView scroll

    }

    protected void onFabClick(View v) {}

    protected void deleteItemAt(int position) {}
}
