package cn.ehanmy.hospital.mvp.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HeightItemDecoration extends RecyclerView.ItemDecoration {

    private int height;
    public HeightItemDecoration(int height){
        this.height = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = height;
    }
}
