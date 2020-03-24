package com.example.a4_myflickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;


/**  ItemClickListener reacts to diff. gestures "SingleClick ; Swipe ; LongPress ; ...."
 *      - set interface let "activity" decide further action after "gestures being listened".
 * */


class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG = "RecyclerItemClickListen";

    interface OnRecyclerClickListener {
        // Called below and need activity to implement
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView,
                                     OnRecyclerClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && mListener != null) {
                    Log.d(TAG, "onSingleTapUp: calling listener.onItemClick");
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                /** Return True for SingleClick  ==> says yes, the gesture's been properly handled.
                 *  ==>  Thus, `onInterceptTouchEvent()` will intercept (cancel) any following action.
                 */
                return true;
            }

            /** Most Gesture Events return BOOLEAN (onSingleTapUp, onScroll, etc.),
             *          so the Listener Knows what happened.
             *  But onLongPress return nothing void, then other things listening to LongPress would
             *          also try to react to LongPress.
             *  The reason could be ==> longPress and you then start to Scroll(swipe) the screen.
             *          Then if return LongPress=True, the following Scroll would be ignored!     */
            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && mListener != null) {
                    Log.d(TAG, "onLongPress: calling listener.onItemLongClick");
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    /** boolean onInterceptTouchEvent():
     *  - return true: tell system we have "handled the gesture event", so nothing further needed.
     *    All child views handling the gesture receive an ACTION_CANCEL!
     *          ==> For Example: if return true to ALL Event including "swipe event"
     *          ==> Swiping Can'T Move the Screen Anymore! (no reaction)
     *  - return false: then this Intercept method only spying what gesture happened.     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        // return true;

        Log.d(TAG, "onInterceptTouchEvent: starts Intercept Method.");
        if (mGestureDetector != null) {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned: " + result);
            return result;
        } else {
            /** here for `SimpleOnItemTouchListener`: any other events that we didn't override
             *  (swipe, double click, etc.) will just let thro. ( mGestureDetector being NULL ) */
            Log.d(TAG, "onInterceptTouchEvent: returned: false");
            return false;
        }
    }
}
