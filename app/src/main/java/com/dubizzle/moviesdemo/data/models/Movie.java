
package com.dubizzle.moviesdemo.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {

    @Json(name = "adult")
    private Boolean adult;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "budget")
    private Integer budget;
    @Json(name = "homepage")
    private String homepage;
    @Json(name = "id")
    private Integer id;
    @Json(name = "imdb_id")
    private String imdbId;
    @Json(name = "original_language")
    private String originalLanguage;
    @Json(name = "original_title")
    private String originalTitle;
    @Json(name = "overview")
    private String overview;
    @Json(name = "popularity")
    private Float popularity;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "revenue")
    private Integer revenue;
    @Json(name = "runtime")
    private Integer runtime;
    @Json(name = "status")
    private String status;
    @Json(name = "tagline")
    private String tagline;
    @Json(name = "title")
    private String title;
    @Json(name = "video")
    private Boolean video;
    @Json(name = "vote_average")
    private Float voteAverage;
    @Json(name = "vote_count")
    private Integer voteCount;
    private String releaseDateFormated;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    public Movie(Boolean adult, String backdropPath, Integer budget, String homepage, Integer id, String imdbId, String originalLanguage, String originalTitle, String overview, Float popularity, String posterPath, String releaseDate, Integer revenue, Integer runtime, String status, String tagline, String title, Boolean video, Float voteAverage, Integer voteCount) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.budget = budget;
        this.homepage = homepage;
        this.id = id;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getReleaseDateFormated() {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.UK);

        try {
            Date movieDate = input.parse(releaseDate);  // parse input
            releaseDateFormated = output.format(movieDate);    // format output
            return "RELEASED ON:  " + releaseDateFormated;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return releaseDate;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return  "OVERVIEW:\n\n"+overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return "RATING:  "+String.valueOf(voteAverage);
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.adult);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.budget);
        dest.writeString(this.homepage);
        dest.writeValue(this.id);
        dest.writeString(this.imdbId);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.revenue);
        dest.writeValue(this.runtime);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
        dest.writeString(this.title);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
    }

    protected Movie(Parcel in) {
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backdropPath = in.readString();
        this.budget = (Integer) in.readValue(Integer.class.getClassLoader());
        this.homepage = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imdbId = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.popularity = (Float) in.readValue(Float.class.getClassLoader());
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.revenue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.runtime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = in.readString();
        this.tagline = in.readString();
        this.title = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Float) in.readValue(Float.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
