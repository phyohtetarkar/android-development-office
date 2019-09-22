package com.team.androidpos.ui.sale;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.androidpos.R;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.product.ProductAndCategoryAdapter;
import com.team.androidpos.util.PermissionUtil;

public class SaleProductsFragment extends ListFragment {

    private ProductAndCategoryAdapter adapter;
    private SaleProductsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        adapter = new ProductAndCategoryAdapter();
        adapter.setAdapterItemClickListener(vo -> {
            // TODO
        });

        viewModel = ViewModelProviders.of(this).get(SaleProductsViewModel.class);
        viewModel.products.observe(this, adapter::submitList);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sale, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                // TODO
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductsFragment_to_saleDetailFragment);
                return true;
            case R.id.action_filter:
                // TODO
                return true;
            case R.id.action_scan:
                if (!PermissionUtil.hasCameraPermission(this)) {
                    return true;
                }
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductsFragment_to_barcodeScanFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.hide();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.categoryId.setValue(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.PERMISSION_CAMERA) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductsFragment_to_barcodeScanFragment);
            }
        }
    }

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        return adapter;
    }

    @Override
    protected boolean listenSwipeDelete() {
        return false;
    }
}
