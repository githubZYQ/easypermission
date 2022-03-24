package com.zyq.easypermission.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * 使用的实现View点击改变透明度
 * 在ListView中只响应了ACTION_DOWN，先不要用于ListView
 * RecycleView可以用
 *
 * @author Zhang YanQiang
 * @date 2019/10/9　20:39.
 */
public class EasyAlphaTouchListener implements View.OnTouchListener {
    private final float F_SCACLE_MIN = 0.5f;
    private final float F_SCACLE_MAX = 1f;

    @Override
    public boolean onTouch(final View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(F_SCACLE_MIN);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(F_SCACLE_MAX);
                break;
            case MotionEvent.ACTION_CANCEL:
                v.setAlpha(F_SCACLE_MAX);
                if (isOutterMotionEvent(event, v)) {
                    if (v.getParent() != null) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                } else {
                    break;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 事件触发是否超出View边界
     *
     * @param event
     * @param v
     * @return
     */
    private boolean isOutterMotionEvent(MotionEvent event, View v) {
        float touchX = event.getX();
        float touchY = event.getY();
        float maxX = v.getWidth();
        float maxY = v.getHeight();

        return touchX < 0 || touchX > maxX || touchY < 0 || touchY > maxY;
    }
}
