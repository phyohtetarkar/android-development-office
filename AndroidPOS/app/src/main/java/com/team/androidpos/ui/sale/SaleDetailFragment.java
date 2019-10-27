package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.R;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SaleDetailFragment extends ListFragment {

    private SaleProductAdapter adapter;
    private SaleActionViewModel saleActionViewModel;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        if (adapter == null) {
            adapter = new SaleProductAdapter();
            adapter.setAdapterItemClickListener(saleProduct -> {
                saleActionViewModel.editSaleProduct.setValue(saleProduct.copy());
                Navigation.findNavController(getView()).navigate(R.id.action_saleDetailFragment_to_saleProductEditFragment);
            });
        }
        return adapter;
    }

    @Override
    protected boolean listenSwipeDelete() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        activity.switchToggle(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saleActionViewModel = ViewModelProviders.of(requireActivity()).get(SaleActionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCheckout = view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_saleDetailFragment_to_completeSaleFragment);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        saleActionViewModel.saleProducts.observe(this, map -> {
            List<SaleProduct> list = new ArrayList<>(map.values());
            adapter.submitList(list);
        });

        saleActionViewModel.sale.observe(this, sale -> {
            View view = getView();
            if (view == null) {
                return;
            }

            TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
            tvTotalPrice.setText(String.valueOf(sale.getTotalPrice()));

            Button btnCheckout = view.findViewById(R.id.btnCheckout);
            btnCheckout.setEnabled(sale.getTotalPrice() > 0);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity activity = (MainActivity) requireActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.switchToggle(true);
    }

    @Override
    protected void deleteItemAt(int position) {
        saleActionViewModel.removeProduct(adapter.getItemAt(position));
    }
}
