package com.droidcraft.map.control;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
/**
 * This class uses Android gestures to move the map around
 * @author Frank
 * @author Br
 *
 */
public class MapFlinger extends SimpleOnGestureListener {

	private MapPanning mapPanning;

	
	
	public MapFlinger (MapPanning mapPanning){
		this.mapPanning = mapPanning;
		
	}
	
	@Override
	public boolean onFling(MotionEvent pMotionEvent1, MotionEvent pMotionEvent2, float velocityX, float velocityY ){
			this.mapPanning.setSpeedX(velocityX * .8f);
			this.mapPanning.setSpeedY(velocityY * .8f);
		return true;	
			
	}
}
