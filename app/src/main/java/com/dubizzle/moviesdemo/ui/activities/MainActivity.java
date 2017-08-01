package com.dubizzle.moviesdemo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.dubizzle.moviesdemo.BR;
import com.dubizzle.moviesdemo.R;
import com.dubizzle.moviesdemo.data.adapters.RecyclerBindingAdapter;
import com.dubizzle.moviesdemo.data.api.API;
import com.dubizzle.moviesdemo.data.api.RestCallBack;
import com.dubizzle.moviesdemo.data.models.ApiResponse;
import com.dubizzle.moviesdemo.data.models.Movie;
import com.dubizzle.moviesdemo.databinding.ActivityMainBinding;
import com.dubizzle.moviesdemo.util.HAlert;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends BaseActivity implements RecyclerBindingAdapter.OnItemClickListener {

    private ActivityMainBinding binding;
    private Context context = MainActivity.this;
    private int currentPage = 1;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean requestLoading = false;
    private boolean is_last_page = false;
    private String maxYearValue = "2017";
    private String minYearValue = "1970";
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private RecyclerBindingAdapter adapter;
    private BottomSheetBehavior filterSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        adapter = new RecyclerBindingAdapter(R.layout.item_list_movie, BR.movie, moviesList);

        final GridLayoutManager layoutManager = new GridLayoutManager(context, getResources().getInteger(R.integer.columns));

        binding.recyclerViewMain.setLayoutManager(layoutManager);
        binding.recyclerViewMain.setHasFixedSize(true);
        binding.recyclerViewMain.setItemViewCacheSize(20);
        binding.recyclerViewMain.setDrawingCacheEnabled(true);
        binding.recyclerViewMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recyclerViewMain.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        binding.recyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (!requestLoading && !is_last_page) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            requestLoading = true;

                            currentPage++;
                            getMovies(currentPage, maxYearValue, minYearValue, false);

                        }
                    }
                }
            }
        });

        binding.buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // hide keyboard
                View v = MainActivity.this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if (filterValid()) {

                    filterSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                    minYearValue = binding.editTextMin.getText().toString();
                    maxYearValue = binding.editTextMin.getText().toString();

                    moviesList.clear();
                    currentPage = 1;
                    getMovies(currentPage, maxYearValue, minYearValue, true);



                } else {
                    HAlert.showToast(context, "Enter and valid year");
                }
            }
        });


        filterSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        // prevent bottom sheet dismiss
        filterSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    filterSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        filterSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    private boolean filterValid() {

        String minYearEntered = binding.editTextMin.getText().toString();
        String maxYearEntered = binding.editTextMax.getText().toString();

        try {
            int min = Integer.parseInt(minYearEntered);
            int max = Integer.parseInt(maxYearEntered);

            if (min >= 1900 && max <= 2018 && max >= min) {

                return true;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }

        return false;
    }

    private void showLoading(boolean show) {

        if (show) {
            binding.recyclerViewMain.setVisibility(View.GONE);
            binding.loadingView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewMain.setVisibility(View.VISIBLE);
            binding.loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!requestLoading) {
            moviesList.clear();
            currentPage = 1;
            getMovies(currentPage, maxYearValue, minYearValue, true);
        }


    }

    private void getMovies(int page, String maxDate, String minDate, boolean showLoading) {

        if (showLoading) {
            showLoading(true);
        }

        Map<String, String> params = API.getParams(page, maxDate, minDate);

        apiCall(apiService
                        .getMovies(params)
                , API.ACTION_DISCOVER, new RestCallBack() {
                    @Override
                    public void onRestResponse(String action, Call<ApiResponse> call, ApiResponse response, Throwable t) {

                        if (response != null && t == null) {

                            int startPosition = moviesList.size() - 1;

                            moviesList.addAll(response.getResults());

                            if (startPosition > 0) {

                                adapter.notifyItemRangeInserted(startPosition, response.getResults().size());
                            } else {
                                adapter.notifyDataSetChanged();

                            }
                        }
                        requestLoading = false;
                        showLoading(false);

                    }
                });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                if (filterSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    filterSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    filterSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (filterSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            super.onBackPressed();
        } else {
            filterSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

    }

    @Override
    public void onItemClick(int position, Object item) {

        Movie selectedMovie = (Movie) item;

        startActivity(new Intent(context, DetailActivity.class).putExtra("detail", selectedMovie));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }
}
