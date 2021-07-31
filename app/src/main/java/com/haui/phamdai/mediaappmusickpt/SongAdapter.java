package com.haui.phamdai.mediaappmusickpt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private final List<Song> songs;

    public SongAdapter(List<Song> items) {
        songs = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    // gắn dữ liệu cho view
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String title = songs.get(position).getTitle();
        holder.getTxtTitle().setText(title);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtTitle;

        public ViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.textviewTitleSongList);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }
    }
}
