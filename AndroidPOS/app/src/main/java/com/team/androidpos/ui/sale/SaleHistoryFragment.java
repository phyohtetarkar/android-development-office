package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.androidpos.R;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.MainActivity;

public class SaleHistoryFragment extends ListFragment {

    private SaleAdapter adapter;
    private SaleHistoryViewModel viewModel;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        return adapter;
    }

    @Override
    protected boolean listenSwipeDelete() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SaleAdapter();
        adapter.setAdapterItemClickListener(sale -> {
            Bundle args = new Bundle();
            args.putLong(SaleReceiptFragment.KEY_SALE_ID, sale.getId());
            args.putInt(SaleReceiptFragment.KEY_NAV_BACK, SaleReceiptFragment.NAV_SALE_HISTORY);
            Navigation.findNavController(getView()).navigate(R.id.action_saleHistoryFragment_to_saleReceiptFragment, args);
        });

        viewModel = ViewModelProviders.of(this).get(SaleHistoryViewModel.class);
        viewModel.sales.observe(this, adapter::submitList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.dateTime.setValue(null);

        MainActivity activity = (MainActivity) requireActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.switchToggle(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.hide();
    }
}
