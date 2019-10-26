package com.team.androidpos.ui.sale;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.BR;
import com.team.androidpos.R;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.ui.AdapterItemClickListener;

import java.util.Objects;

public class SaleProductAdapter extends ListAdapter<SaleProduct, SaleProductAdapter.SaleProductViewHolder> {

    private static final DiffUtil.ItemCallback<SaleProduct> DIFF_CALLBACK = new DiffUtil.ItemCallback<SaleProduct>() {
        @Override
        public boolean areItemsTheSame(@NonNull SaleProduct oldItem, @NonNull SaleProduct newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SaleProduct oldItem, @NonNull SaleProduct newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AdapterItemClickListener<SaleProduct> adapterItemClickListener;

    SaleProductAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SaleProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_sale_product, parent, false);
        return new SaleProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleProductViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<SaleProduct> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public SaleProduct getItemAt(int position) {
        return getItem(position);
    }

    class SaleProductViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        SaleProductViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                if (adapterItemClickListener != null) adapterItemClickListener.onClick(getItem(getAdapterPosition()));
            });
        }

        void bind(SaleProduct obj) {
            binding.setVariable(BR.obj, obj);
        }

    }

}
