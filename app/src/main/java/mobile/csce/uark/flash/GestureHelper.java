package mobile.csce.uark.flash;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * Created by Jonathan on 12/7/14.
 */
public class GestureHelper extends GestureDetector.SimpleOnGestureListener{
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    System.out.println("TEST LEFT");
                    return true;


                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    System.out.println("TEST RIGHT");
                    return true;
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }


        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
    }


