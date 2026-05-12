package com.example.calendly.ui.agenda;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendly.databinding.YearItemBinding;

import java.util.List;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.YearViewHolder> {
    private List<Integer> years;
    private OnYearClickListener onYearClickListener;
    private int selectedPosition = -1;

    public interface OnYearClickListener {
        void onYearClick(int year);
    }

    public YearAdapter(List<Integer> years, OnYearClickListener onYearClickListener) {
        this.years = years;
        this.onYearClickListener = onYearClickListener;
    }

    public void setSelectedPosition(int selectedPosition){
        int old = this.selectedPosition;
        this.selectedPosition = selectedPosition;
        notifyItemChanged(old);
        notifyItemChanged(selectedPosition);
    }

    @NonNull
    @Override
    public YearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YearItemBinding binding = YearItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new YearViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull YearViewHolder holder, int position) {
        int year = years.get(position);
        holder.binding.tvYear.setText(String.valueOf(year));
        holder.binding.getRoot().setSelected(position == selectedPosition);

        holder.binding.tvYear.setOnClickListener(view -> {
            if (onYearClickListener != null) {
                onYearClickListener.onYearClick(year);
            }
        });
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    public static class YearViewHolder extends RecyclerView.ViewHolder {
        private final YearItemBinding binding;

        public YearViewHolder(@NonNull YearItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateData(List<Integer> newYearItems){
        this.years.clear();
        this.years.addAll(newYearItems);
        notifyDataSetChanged();
    }
}
