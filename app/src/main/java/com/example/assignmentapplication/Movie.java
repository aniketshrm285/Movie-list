package com.example.assignmentapplication;

public class Movie { // Class for parsing json data using retrofit

    private String title ;
    private String image;
    private String rating;
    private String releaseYear;
    private String[] genre;

    public Movie(String title, String image, String rating, String releaseYear, String[] genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String[] getGenre() {
        return genre;
    }
}
