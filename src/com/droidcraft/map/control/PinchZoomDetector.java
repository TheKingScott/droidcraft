package com.droidcraft.map.control;

import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import android.util.FloatMath;
import android.view.MotionEvent;
 
/**
 * PinchZoomDetector is only usable on multitouch-enalbed versions (2.1+)
 * @author Frank Blandon
 * @author BrR
 */
public class PinchZoomDetector implements IOnSceneTouchListener
{      
        private float lastSpacing;
        private ZoomCamera cam;
        private final MapPanning panner;
       
        public PinchZoomDetector(ZoomCamera cam, MapPanning panner)
        {
                this.cam = cam;
                this.panner = panner;
        }
       
        /**
         * Determines whether there are two fingers on the screen or not. If not, then it returns false and the {@linkplain MapPanning} will 
         * take of it
         */
        @Override
        public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
        {
                int action = pSceneTouchEvent.getAction();
                if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_1_UP || action == MotionEvent.ACTION_POINTER_2_UP)
                {
                        this.lastSpacing = 0;
                        return false;
                }
                if(pSceneTouchEvent.getMotionEvent().getPointerCount() == 2)
                {
                        this.handleMultitouchGesture(pSceneTouchEvent.getMotionEvent());
                        return true;
                }
                return false;
        }
       
        /**
         * This will handle the zooming, the amount of zooming depends on the distance between the two fingers
         * Thus, the greater the spacing, the faster the zooming.
         * Note: There is a limit as to how much the camera can zoom: 0.2f < zoom < 1
         * 
         */
        private void handleMultitouchGesture(MotionEvent evt)
        {
                this.panner.cancelCurScrolling();
                final float curSpacing = this.spacing(evt);
                float zoomFactor = this.cam.getZoomFactor();
                if(this.lastSpacing > 0)
                {
                	zoomFactor = this.cam.getZoomFactor() * (curSpacing / this.lastSpacing);
                	if(zoomFactor > 1)
                    {
                		zoomFactor = 1;
                        this.cam.setZoomFactor(zoomFactor);
                    }
                    if(zoomFactor < .2f)
                    {
                    	zoomFactor = .2f;
                    }
                    this.cam.setZoomFactor(zoomFactor);
                }
                this.lastSpacing = curSpacing;
        }
       
        /**
         * Calculates the spacing between the two fingers
         */
        private float spacing(MotionEvent evt)
        {
                float x = evt.getX(0) - evt.getX(1);
                float y = evt.getY(0) - evt.getY(1);
                return FloatMath.sqrt(x * x + y * y);
        }      
}