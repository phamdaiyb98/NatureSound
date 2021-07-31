package com.haui.phamdai.mediaappmusickpt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 */
public class SongFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SongFragment newInstance(int columnCount) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewSong);
        Context context = recyclerView.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        // đổ dữ liệu vào recyclerView
        recyclerView.setAdapter(new SongAdapter(MainActivity.songs));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((parent, view12, position, id) -> {
            MainActivity.position = position;
            MainActivity.getInstance().changeSong();
        });

        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener((parent, view1, position, id) -> {
            Toast.makeText(context, MainActivity.songs.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            return false;
        });

        return view;
    }
}