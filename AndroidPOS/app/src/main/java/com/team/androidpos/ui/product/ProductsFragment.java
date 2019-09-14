package com.team.androidpos.ui.product;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.R;
import com.team.androidpos.ui.ListFragment;

public class ProductsFragment extends ListFragment {

    private ProductAndCategoryAdapter adapter;
    private ProductsViewModel viewModel;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        if (adapter == null) {
            adapter = new ProductAndCategoryAdapter();
            adapter.setAdapterItemClickListener(vo -> {
                if (getView() == null) return;

                Bundle args = new Bundle();
                args.putInt(ProductEditFragment.KEY_PRODUCT_ID, vo.getId());
                Navigation.findNavController(getView()).navigate(R.id.action_productsFragment_to_productEditFragment, args);
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
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        viewModel.getProducts().observe(this, list -> {
            adapter.submitList(list);
        });
    }

    @Override
    protected void onFabClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_productsFragment_to_productEditFragment);
    }

    @Override
    protected void deleteItemAt(int position) {
        viewModel.delete(adapter.getItemAt(position).getId());
    }
}
