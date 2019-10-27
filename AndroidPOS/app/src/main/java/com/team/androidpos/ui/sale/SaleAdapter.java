package com.team.androidpos.ui.sale;

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
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.ui.AdapterItemClickListener;

public class SaleAdapter extends PagedListAdapter<Sale, SaleAdapter.SaleViewHolder> {

    private static final DiffUtil.ItemCallback<Sale> DIFF_CALLBACK = new DiffUtil.ItemCallback<Sale>() {
        @Override
        public boolean areItemsTheSame(@NonNull Sale oldItem, @NonNull Sale newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Sale oldItem, @NonNull Sale newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AdapterItemClickListener<Sale> adapterItemClickListener;

    SaleAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_sale, parent, false);
        return new SaleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public Sale getItemAt(int position) {
        return getItem(position);
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<Sale> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    class SaleViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public SaleViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                if (adapterItemClickListener != null) adapterItemClickListener.onClick(getItem(getAdapterPosition()));
            });
        }

        void bind(Sale sale) {
            binding.setVariable(BR.obj, sale);
        }

    }

}
