package com.snk.wolly.wolly;

import android.widget.ImageView;
import android.widget.TextView;

interface PickRecyclableClickListener {
    public void onItemClick(int position, ImageView imageView, TextView textView, Recyclable recyclable);
}
