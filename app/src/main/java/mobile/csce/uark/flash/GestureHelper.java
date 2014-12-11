package mobile.csce.uark.flash;

import android.view.GestureDetector;
import android.view.MotionEvent;


/**
 * Created by Jonathan on 12/7/14.
 */
public class GestureHelper extends GestureDetector.SimpleOnGestureListener{
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    public static final String DIRECTION_UP = "UP";
    public static final String DIRECTION_DOWN = "DOWN";
    public static final String DIRECTION_LEFT = "LEFT";
    public static final String DIRECTION_RIGHT = "RIGHT";
    public static boolean IsSwiping = false;

    public static String Direction;



    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                //if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                //    return false;

                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    System.out.println("TEST LEFT");
                    Direction = DIRECTION_LEFT;
                    return true;


                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    System.out.println("TEST RIGHT");
                    Direction = DIRECTION_RIGHT;
                    return true;
                }
                else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    System.out.println("TEST DOWN");
                    Direction = DIRECTION_DOWN;
                    return true;
                }
                else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    System.out.println("TEST UP");
                    Direction = DIRECTION_UP;
                    return true;
                }
                else {
                    Direction = "";
                    return false;
                }

            } catch (Exception e) {
                // nothing
            }
            return true;
        }


        @Override
        public boolean onDown(MotionEvent e) {
            Direction = "";
            return true;
        }

    }


