package com.municipality.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.municipality.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmirG on 10/28/2017.
 */
public class ContriesAdapter extends RecyclerView.Adapter<ContriesAdapter.ViewHolder> {

    private List<String> items = new ArrayList<String>();
    private ContriesAdapterListener contriesAdapterListener;

    public ContriesAdapter(List<String> items, ContriesAdapterListener contriesAdapterListener) {
        this.items = items;
        this.contriesAdapterListener = contriesAdapterListener;
    }

    public interface ContriesAdapterListener {
        public void onItemClicked(String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;

        public ViewHolder(View v) {
            super(v);
            itemTextView = (TextView) v.findViewById(R.id.item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = items.get(position);
        holder.itemTextView.setText(name);
        holder.itemTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(contriesAdapterListener != null) {
                    contriesAdapterListener.onItemClicked(name);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
