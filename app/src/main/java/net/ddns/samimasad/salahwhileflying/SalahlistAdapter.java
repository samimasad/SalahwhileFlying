package net.ddns.samimasad.salahwhileflying;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SalahlistAdapter extends RecyclerView.Adapter<SalahlistAdapter.ViewHolder>  {

private String[] mDataset;
private String[] Salahname ;
private String[] Salahtimes ;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView textView_salah_time;
    public TextView textView_salah_name;
    public ViewHolder(View v) {
        super(v);
        textView_salah_name =  v.findViewById(R.id.textView_salahname);
        textView_salah_time =  v.findViewById(R.id.textViewsalah_time);

    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public SalahlistAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SalahlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_salah, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.textView_salah_time.setText(mDataset[0]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}