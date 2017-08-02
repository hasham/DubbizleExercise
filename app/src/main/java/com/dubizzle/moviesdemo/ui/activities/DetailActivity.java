package com.dubizzle.moviesdemo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.dubizzle.moviesdemo.BR;
import com.dubizzle.moviesdemo.R;
import com.dubizzle.moviesdemo.data.adapters.RecyclerBindingAdapter;
import com.dubizzle.moviesdemo.data.api.API;
import com.dubizzle.moviesdemo.data.api.RestCallBack;
import com.dubizzle.moviesdemo.data.models.ApiResponse;
import com.dubizzle.moviesdemo.data.models.Movie;
import com.dubizzle.moviesdemo.databinding.ActivityDetailBinding;

import java.util.AbstractList;

import retrofit2.Call;

public class DetailActivity extends BaseActivity implements RestCallBack {

    private ActivityDetailBinding binding;
    private Context context = DetailActivity.this;
    private Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setSupportActionBar(binding.toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewDetail.setHasFixedSize(true);
        binding.recyclerViewDetail.setItemViewCacheSize(20);
        binding.recyclerViewDetail.setNestedScrollingEnabled(false);
        binding.recyclerViewDetail.setDrawingCacheEnabled(true);
        binding.recyclerViewDetail.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bundle extras = getIntent().getExtras();
        movie = extras.getParcelable("detail");

        if (movie != null) {
            binding.setVariable(BR.detail, movie);
            setTitle(movie.getTitle());
            getRelatedMovies(movie.getId());
        }

    }

    private void getRelatedMovies(Integer id) {

        apiCall(apiService
                        .getRelatedMovies(String.valueOf(id))
                , API.ACTION_RECOMENDATION, this);
    }

    private void getVideos(Integer id) {

        apiCall(apiService
                        .getVideos(String.valueOf(id))
                , API.ACTION_VIDEOS, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onRestResponse(String action, Call<ApiResponse> call, ApiResponse response, Throwable t) {

        switch (action) {

            case API.ACTION_RECOMENDATION: {

                if (response != null && t == null) {

                    binding.textViewRelated.setVisibility(View.VISIBLE);

                    if (response.getResults().size() > 0) {

                        RecyclerBindingAdapter adapter = new RecyclerBindingAdapter(R.layout.item_list_movie_horizontal,
                                BR.movie,
                                (AbstractList) response.getResults());
                        binding.recyclerViewDetail.setAdapter(adapter);
                        adapter.setOnItemClickListener(new RecyclerBindingAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, Object item) {
                                Movie selectedMovie = (Movie) item;

                                startActivity(new Intent(context, DetailActivity.class).putExtra("detail", selectedMovie));
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            }
                        });
                    } else {
                        binding.textViewRelated.setVisibility(View.GONE);
                    }


                }
            }
            break;

            case API.ACTION_VIDEOS: {

                if (response != null && t == null) {

                }
            }
            break;
        }
    }
}
