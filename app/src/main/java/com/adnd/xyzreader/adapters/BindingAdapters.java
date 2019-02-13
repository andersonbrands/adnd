package com.adnd.xyzreader.adapters;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.adnd.xyzreader.custom_views.AspectRatioImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class BindingAdapters {

    @BindingAdapter(value = {"imgURL", "placeholderRes", "errorRes"}, requireAll = false)
    public static void setImgURL(ImageView imageView, String imgURL, Drawable placeholderRes, Drawable errorRes) {
        if (TextUtils.isEmpty(imgURL)) {
            imgURL = "Path must not be empty";
        }
        RequestCreator requestCreator = Picasso.get().load(imgURL);
        if (placeholderRes != null) {
            requestCreator.placeholder(placeholderRes);
        }
        if (errorRes != null) {
            requestCreator.error(errorRes);
        }
        requestCreator.into(imageView);
    }

    @BindingAdapter("aspectRatio")
    public static void setAspectRatio(AspectRatioImageView aspectRatioImageView, float aspectRatio) {
        aspectRatioImageView.setAspectRatio(aspectRatio);
    }
}
