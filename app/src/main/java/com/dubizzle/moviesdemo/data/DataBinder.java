package com.dubizzle.moviesdemo.data;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Patterns;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Developed by Hasham.Tahir on 1/17/2017.
 */

public class DataBinder {

    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {

        if (url != null && !url.equals("") && Patterns.WEB_URL.matcher(url).matches()) {
            Context context = imageView.getContext();
            Picasso.with(context).load(url).fit().into(imageView);
        }
    }

}
