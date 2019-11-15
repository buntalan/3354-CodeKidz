package com.example.materialeventcalendar;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnCalendarSwipeListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;
    public OnCalendarSwipeListener(Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }
    @Override
    public boolean onTouch(View view, MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    public void onSwipeRight(){}
    public void onSwipeLeft(){}
    public void onSwipeTop(){}
    public void onSwipeBottom(){}

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e){
            return true;
        }
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velX, float velY){
            boolean result = false;
            try{
                float diffY = event2.getY() - event1.getY();
                float diffX = event2.getX() - event1.getX();
                if(Math.abs(diffX) > Math.abs(diffY)){
                    if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velX) > SWIPE_VELOCITY_THRESHOLD){
                        if(diffX>0){
                            onSwipeRight();
                        }else onSwipeLeft();
                        result=true;
                    }
                }else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velY) > SWIPE_VELOCITY_THRESHOLD){
                    if(diffY > 0){
                        onSwipeBottom();
                    }else onSwipeTop();
                }result = true;
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}
