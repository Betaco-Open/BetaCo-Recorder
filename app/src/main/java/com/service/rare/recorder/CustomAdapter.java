package com.service.rare.recorder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final String[] localDataSet;
    private final String[] localDataSet2;

    private final String[] localDataSet3;
    public static Integer LastAdapterPos = -1;

    public static ImageView prev_image;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private final TextView title;

        private final CardView cardview;
        private final ImageView play_image;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.textView);
            textView2 = view.findViewById(R.id.textView2);
            title = view.findViewById(R.id.title);
            play_image = view.findViewById(R.id.imageView);

            play_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick: "+getAdapterPosition());

                    String filepath = MainActivity2.path +"/"+MainActivity2.files[getAdapterPosition()]+".mp3" ;
                    try {
                        boolean isPlaying = MainActivity2.playRecording(filepath, v, getAdapterPosition(), prev_image, play_image);

                        if(isPlaying){
                            play_image.setImageResource(R.drawable.baseline_stop_24);
                            prev_image=play_image;
                            LastAdapterPos=getAdapterPosition();
                        }else {
//                            LastAdapterPos = -1;
                            prev_image=play_image;
                            play_image.setImageResource(R.drawable.baseline_play_arrow_24);
                        }

                    }catch (IOException e){
                        play_image.setImageResource(R.drawable.baseline_play_arrow_24);
                        LastAdapterPos=getAdapterPosition();
                        Toast.makeText(v.getContext(),"Error Occured with Media Playback"+filepath,Toast.LENGTH_LONG).show();
                    }


                }
            });

            cardview = view.findViewById(R.id.cardView);
            cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("TAG", "onLongClick: ");
                    return true;
                }
            });

        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getTextView2() {
            return textView2;
        }
        public TextView getTitle() {
            return title;
        }


        public ImageView getPlay_image() {
            return play_image;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataset1  String[] containing the data to populate views to be used
     *                 by RecyclerView
     * @param dataset2
     *
     * @param dataset3
     *
     */
    public CustomAdapter(String[] dataset1, String[] dataset2, String[] dataset3) {
        localDataSet = dataset1;
        localDataSet2 = dataset2;
        localDataSet3 = dataset3;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);
        viewHolder.getTextView2().setText(localDataSet2[position]);
        viewHolder.getTitle().setText(localDataSet3[position].toUpperCase(Locale.ROOT));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
