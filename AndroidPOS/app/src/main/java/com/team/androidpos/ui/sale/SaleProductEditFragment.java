package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.team.androidpos.databinding.SaleProductEditBinding;

public class SaleProductEditFragment extends Fragment {

    private SaleProductEditBinding binding;
    private SaleActionViewModel saleActionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleActionViewModel = ViewModelProviders.of(requireActivity()).get(SaleActionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = SaleProductEditBinding.inflate(inflater, container, false);
        this.binding.setLifecycleOwner(this);
        this.binding.setViewModel(saleActionViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSave.setOnClickListener(v -> {
            saleActionViewModel.saveEditSaleProduct();
            Navigation.findNavController(view).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saleActionViewModel.editSaleProduct.setValue(null);
    }
}
