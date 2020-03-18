package com.example.assignmentapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignmentapplication.adapter.RecyclerViewAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class MyFragmentClass extends Fragment implements OnClickListener{ // Class that governs the working of fragment

    private TextView title ;
    private TextView details;
    private ImageView imageView;

    private ImageView cross;

    public MyFragmentClass(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_layout,container,false);

        title = view.findViewById(R.id.title);

        details = view.findViewById(R.id.details);

        imageView = view.findViewById(R.id.imageView2);

        cross = view.findViewById(R.id.imageView3);

        cross.setOnClickListener(this);

        if(getArguments()!=null){ // Getting arguments from Onclick Recycler View

            Movie movie = MainActivity.movies.get(getArguments().getInt("index"));

            title.setText(movie.getTitle());

            ImageDownloader task = new ImageDownloader();

            Bitmap image;

            try {
                image= task.execute(movie.getImage()).get();

                imageView.setImageBitmap(image);

            } catch (Exception e) {

                Log.i("Info","SetImageFailed");

                e.printStackTrace();
            }
            String genre ="";

            String[] genres = movie.getGenre(); // Getting all genres and concatenating them in a new String

            for(String i:genres){
                genre+=i+", ";
            }
            details.setText("Year of release: "+movie.getReleaseYear()+"\n"+"Rating: "+movie.getRating()+"\n"+"Genere: " + genre);
        }

        return view;

    }

    @Override
    public void onClick(View v) { // On click method for closing fragment when cross image touched

        Objects.requireNonNull(getActivity()).onBackPressed();

    }

    public class ImageDownloader extends AsyncTask<String , Void , Bitmap> { // New thread to download image from source
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
