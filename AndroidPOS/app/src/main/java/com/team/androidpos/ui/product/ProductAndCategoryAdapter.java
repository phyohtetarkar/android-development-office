package com.team.androidpos.ui.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.BR;
import com.team.androidpos.R;
import com.team.androidpos.model.vo.ProductAndCategoryVO;
import com.team.androidpos.ui.AdapterItemClickListener;

public class ProductAndCategoryAdapter extends PagedListAdapter<ProductAndCategoryVO, ProductAndCategoryAdapter.ProductAndCategoryViewHolder> {

    private static final DiffUtil.ItemCallback<ProductAndCategoryVO> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProductAndCategoryVO>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductAndCategoryVO oldItem, @NonNull ProductAndCategoryVO newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductAndCategoryVO oldItem, @NonNull ProductAndCategoryVO newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AdapterItemClickListener<ProductAndCategoryVO> adapterItemClickListener;

    public ProductAndCategoryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ProductAndCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_product, parent, false);
        return new ProductAndCategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAndCategoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<ProductAndCategoryVO> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public ProductAndCategoryVO getItemAt(int position) {
        return getItem(position);
    }

    class ProductAndCategoryViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ProductAndCategoryViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });
        }

        void bind(ProductAndCategoryVO obj) {
            binding.setVariable(BR.obj, obj);
            binding.executePendingBindings();
        }
    }

}
