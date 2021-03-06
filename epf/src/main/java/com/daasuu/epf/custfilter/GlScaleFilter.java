package com.daasuu.epf.custfilter;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.daasuu.epf.R;
import com.daasuu.epf.filter.FilterType;
import com.daasuu.epf.filter.GlFilter;

public class GlScaleFilter extends GlFilter {

    float mScale = 0f;
    float mOffset = 0f;
    private int mScaleHandle;
    private boolean plus = false;

    public GlScaleFilter(Context context) {
        super(context, R.raw.def_vertext, R.raw.fragment_scale);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPX_SCALE;
    }

    @Override
    public void initProgramHandle() {
        super.initProgramHandle();
        mScaleHandle = GLES30.glGetUniformLocation(mProgramHandle, "scale");
    }

    @Override
    public void onDraw() {
        mOffset += plus ? +0.06f : -0.06f;
        if (mOffset >= 1.0f) {
            plus = false;
        } else if (mOffset <= 0.0f) {
            plus = true;
        }
        mScale = 1.0f + 0.5f * getInterpolation(mOffset);
        GLES20.glUniform1f(mScaleHandle, mScale);


    }

    private float getInterpolation(float input) {
        return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }

}

