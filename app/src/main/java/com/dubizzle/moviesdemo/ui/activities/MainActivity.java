package com.dubizzle.moviesdemo.ui.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dubizzle.moviesdemo.BR;
import com.dubizzle.moviesdemo.R;
import com.dubizzle.moviesdemo.data.adapters.RecyclerBindingAdapter;
import com.dubizzle.moviesdemo.data.api.API;
import com.dubizzle.moviesdemo.data.api.RestCallBack;
import com.dubizzle.moviesdemo.data.models.ApiResponse;
import com.dubizzle.moviesdemo.data.models.Result;
import com.dubizzle.moviesdemo.databinding.ActivityMainBinding;
import com.dubizzle.moviesdemo.util.HAlert;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private Context context = MainActivity.this;
    private int currentPage = 1;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean requestLoading = false;
    private boolean is_last_page = false;
    private int maxYear = 2017;
    private int minYear = 1970;
    private String maxYearValue = "2017";
    private String minYearValue = "1970";
    private ArrayList<Result> moviesList = new ArrayList<>();
    private RecyclerBindingAdapter adapter;
    private BottomSheetBehavior filterSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        filterSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        filterSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        adapter = new RecyclerBindingAdapter(R.layout.item_list_movie, BR.movie, moviesList);

        final GridLayoutManager layoutManager = new GridLayoutManager(context, 4);

        binding.recyclerViewMain.setLayoutManager(layoutManager);
        binding.recyclerViewMain.setHasFixedSize(true);
        binding.recyclerViewMain.setItemViewCacheSize(20);
        binding.recyclerViewMain.setDrawingCacheEnabled(true);
        binding.recyclerViewMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recyclerViewMain.setAdapter(adapter);

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

                if (filterValid()) {
                    filterSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                    String minYearEntered = binding.editTextMin.getText().toString();
                    String maxYearEntered = binding.editTextMin.getText().toString();

                    currentPage = 1;

                    getMovies(currentPage, maxYearEntered, minYearEntered, true);

                }
            }
        });

    }

    private boolean filterValid() {

        String minYearEntered = binding.editTextMin.getText().toString();
        String maxYearEntered = binding.editTextMin.getText().toString();

        try {
            int min = Integer.parseInt(minYearEntered);
            int max = Integer.parseInt(maxYearEntered);

            if (min < 1900 || (max > 2018 && max < min) || max < 1901) {

                HAlert.showToast(context, "Enter and valid year");

            } else {

                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
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
}
