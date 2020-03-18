package com.example.assignmentapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.MainActivity;
import com.example.assignmentapplication.Movie;
import com.example.assignmentapplication.MyFragmentClass;
import com.example.assignmentapplication.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;

    private List<Movie> listOfMovies;


    public RecyclerViewAdapter(Context context, List<Movie> listOfMovies) { //Constructor
        this.context = context;

        this.listOfMovies = listOfMovies;

        Log.i("Size",String.valueOf(listOfMovies.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = listOfMovies.get(position);

        holder.title.setText(movie.getTitle());

        holder.year.setText(movie.getReleaseYear());

        holder.itemView.setId(position);

        ImageDownloader task = new ImageDownloader();

        Bitmap image;
        try {
            image= task.execute(movie.getImage()).get();

            holder.imageView.setImageBitmap(image);
        } catch (Exception e) {
            Log.i("Info","SetImageFailed");

            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;

        public TextView year;

        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            year = itemView.findViewById(R.id.year);

            title = itemView.findViewById(R.id.title);

            imageView = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) { // This method gets called when user tap on one of the item of Recycler View

            Bundle args = new Bundle();

            args.putInt("index",getAdapterPosition());

            MyFragmentClass fragment = new MyFragmentClass();

            fragment.setArguments(args);


            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,fragment).commit();
        }
    }

    public class ImageDownloader extends AsyncTask<String , Void , Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            HttpURLConnection connection = null ;
            try{
                URL url = new URL(urls[0]);

                connection =(HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream in = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(in);

                return bitmap;
            }catch (Exception e){

                e.printStackTrace();

                Log.i("Info","Internet Connection Failed");

                return null;
            }
        }
    }
}
