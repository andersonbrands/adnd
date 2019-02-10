package com.adnd.xyzreader.adapters;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter(value = {"imgURL", "placeholderRes", "errorRes"})
    public static void setImgURL(ImageView imageView, String imgURL, Drawable placeholderRes, Drawable errorRes) {
        if (TextUtils.isEmpty(imgURL)) {
            imgURL = "Path must not be empty";
        }
        Picasso.get()
                .load(imgURL)
                .placeholder(placeholderRes)
                .error(errorRes)
                .into(imageView);
    }

}
