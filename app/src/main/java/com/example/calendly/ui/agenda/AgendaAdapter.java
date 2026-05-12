package com.example.calendly.ui.agenda;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendly.data.model.Event;
import com.example.calendly.databinding.DateHeaderItemBinding;
import com.example.calendly.databinding.EventItemBinding;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AgendaItem> items;
    private OnItemClickListener onItemClickListener;

    public AgendaAdapter(List<AgendaItem> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == AgendaItem.TYPE_DATE_HEADER) {
            DateHeaderItemBinding dateHeaderItemBinding = DateHeaderItemBinding.inflate(inflater, parent, false);
            return new DateHeaderViewHolder(dateHeaderItemBinding);
        } else {
            EventItemBinding eventItemBinding = EventItemBinding.inflate(inflater, parent, false);
            return new EventViewHolder(eventItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("agenda", items.toString());

        if (holder instanceof DateHeaderViewHolder) {
            ((DateHeaderViewHolder) holder).bind((items.get(position)).date);
        } else if (holder instanceof EventViewHolder) {
            ((EventViewHolder) holder).bind((items.get(position)).event);

            if (onItemClickListener != null){
                onItemClickListener.onItemClick(items.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(AgendaItem item);
    }

    public static class DateHeaderViewHolder extends RecyclerView.ViewHolder {
        private final DateHeaderItemBinding binding;

        public DateHeaderViewHolder(@NonNull DateHeaderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LocalDate date) {
            binding.tvDateHeader.setText(
                    date.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"))
            );
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private final EventItemBinding binding;

        public EventViewHolder(@NonNull EventItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Event event) {
            binding.tvAgendaStartTime.setText(formatTime(event.startTimeMillis));
            binding.tvAgendaEndTime.setText(formatTime(event.endTimeMillis));
            binding.tvAgendaTitle.setText(event.title);
            binding.tvAgendaLocation.setText(event.location);
        }
    }

    public static String formatTime(long timeMillis) {
        return Instant.ofEpochMilli(timeMillis)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void updateData(List<AgendaItem> newAgendaItems){
        this.items.clear();
        this.items.addAll(newAgendaItems);
        notifyDataSetChanged();
    }
}
