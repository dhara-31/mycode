package com.example.demodata_2_2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class MyDividerItemDecoration  extends RecyclerView.ItemDecoration
{

    public static final int[] Attrs=new int[]{
            android.R.attr.listDivider
    };

    public static final int Horizontal_list= LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST=LinearLayoutManager.VERTICAL;

    private Drawable drawable;
    private int morientation;
    private Context context;
    private  int margin;


    public MyDividerItemDecoration( Context context,int orientation, int margin) {
        this.context = context;
        this.margin = margin;
        final TypedArray array=context.obtainStyledAttributes(Attrs);
        drawable=array.getDrawable(0);
        array.recycle();
        setorientation(orientation);
    }

    private void setorientation(int orientation) {
        if (orientation!=Horizontal_list && orientation !=VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");
        }
        morientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (morientation==VERTICAL_LIST){
            drawVertical(c,parent);
        }else {
            drawHorizontal(c,parent);
        }
    }
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left=parent.getPaddingLeft();
        final int right=parent.getWidth()-parent.getPaddingRight();

        final int childCount=parent.getChildCount();
        for (int i=0;i<childCount;i++){
            final View child=parent.getChildAt(i);
            final RecyclerView.LayoutParams params=(RecyclerView.LayoutParams)child.getLayoutParams();
            final int top=child.getBottom()+params.bottomMargin;
            final int bottom=top+drawable.getIntrinsicHeight();
            drawable.setBounds(left+dbTopx(margin),top,right-dbTopx(margin),bottom);
            drawable.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top =parent.getPaddingTop();
        final int bottom=parent.getHeight()-parent.getPaddingBottom();

        final int childcount=parent.getChildCount();
        for (int i=0;i<childcount;i++){
            final View child=parent.getChildAt(i);
            final RecyclerView.LayoutParams params=(RecyclerView.LayoutParams) child.getLayoutParams();
            final int left=child.getRight()+params.rightMargin;
            final int right=left+drawable.getIntrinsicHeight();
            drawable.setBounds(left,top + dbTopx(margin),right,bottom-dbTopx(margin));
            drawable.draw(c);
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (morientation==VERTICAL_LIST){
            outRect.set(0,0,0,drawable.getIntrinsicHeight());
        }else {
            outRect.set(0,0,drawable.getIntrinsicWidth(),0);
        }
    }

    private int dbTopx(int margin) {
        Resources resources=context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,margin,resources.getDisplayMetrics()));
    }

}