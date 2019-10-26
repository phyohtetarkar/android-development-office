package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.team.androidpos.databinding.CompleteSaleBinding;

public class CompleteSaleFragment extends Fragment {

    private CompleteSaleBinding binding;
    private SaleActionViewModel saleActionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleActionViewModel = ViewModelProviders.of(requireActivity()).get(SaleActionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CompleteSaleBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(saleActionViewModel);
        return binding.getRoot();
    }

}
