package com.team.androidpos.ui.category;

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
import com.team.androidpos.model.vo.CategoryAndProductCountVO;
import com.team.androidpos.ui.AdapterItemClickListener;

public class CategoryAndProductCountAdapter extends ListAdapter<CategoryAndProductCountVO, CategoryAndProductCountAdapter.CategoryAndProductCountViewHolder> {

    private AdapterItemClickListener<CategoryAndProductCountVO> adapterItemClickListener;

    private static final DiffUtil.ItemCallback<CategoryAndProductCountVO> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategoryAndProductCountVO>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoryAndProductCountVO oldItem, @NonNull CategoryAndProductCountVO newItem) {
            return oldItem.getCategory().getId() == newItem.getCategory().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoryAndProductCountVO oldItem, @NonNull CategoryAndProductCountVO newItem) {
            return oldItem.equals(newItem);
        }
    };

    CategoryAndProductCountAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CategoryAndProductCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_category, parent, false);
        return new CategoryAndProductCountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAndProductCountViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public CategoryAndProductCountVO getItemAt(int position) {
        return getItem(position);
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<CategoryAndProductCountVO> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    class CategoryAndProductCountViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        CategoryAndProductCountViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });
        }

        void bind(CategoryAndProductCountVO obj) {
            binding.setVariable(BR.obj, obj);
            binding.executePendingBindings();
        }

    }

}
