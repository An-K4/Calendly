package com.example.calendly.ui.calendar;

import static com.example.calendly.ui.agenda.AgendaAdapter.formatTime;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendly.data.model.Event;
import com.example.calendly.databinding.EventItemBinding;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventItemBinding eventItemBinding = EventItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EventViewHolder(eventItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(events.get(position));

        if (onItemClickListener != null){
            onItemClickListener.onItemClick(events.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public EventAdapter(List<Event> events, OnItemClickListener onItemClickListener) {
        this.events = events;
        this.onItemClickListener = onItemClickListener;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        private EventItemBinding binding;

        public EventViewHolder(@NonNull EventItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Event event) {
            binding.tvAgendaStartTime.setText(formatTime(event.startTimeMillis));
            binding.tvAgendaEndTime.setText(formatTime(event.endTimeMillis));
            binding.tvAgendaTitle.setText(event.title);
            binding.tvAgendaLocation.setText(event.location.isBlank() ? "Không có địa điểm." : event.location);
        }
    }

    public void updateData(List<Event> newEvents) {
        this.events.clear();
        this.events.addAll(newEvents);
        notifyDataSetChanged();
    }
}
