package fr.univ_orleans.a2048.Layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatButton;


public class SquareButton extends AppCompatButton {


    public SquareButton(Context context) {
        super(context);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredHeight(), getMeasuredWidth());

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }

    private void init() {
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (getWidth() != getHeight()) {
                    //Get the smaller dimension of height and width
                    int squareSize = Math.min(getWidth(), getHeight());

                    //Set the dimensions to a Square
                    ViewGroup.LayoutParams lp = getLayoutParams();
                    lp.width = squareSize;
                    lp.height = squareSize;
                    requestLayout();
                    return false;
                }
                return true;

            }
        });
    }

}
