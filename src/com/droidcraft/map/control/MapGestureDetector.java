package com.droidcraft.map.control;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
/**
 * This class uses Android gestures to move the map around
 * @author Frank Blandon
 * @author Br
 *
 */
public class MapGestureDetector extends SimpleOnGestureListener {

	private MapPanning mapPanning;
	private SelectionArea selectionArea;

	
	
	public MapGestureDetector (MapPanning mapPanning,SelectionArea selectionArea){
		this.mapPanning = mapPanning;
		this.selectionArea = selectionArea;
		
	}
	
	@Override
	public boolean onFling(MotionEvent pMotionEvent1, MotionEvent pMotionEvent2, float velocityX, float velocityY ){
			this.mapPanning.setSpeedX(velocityX * .8f);
			this.mapPanning.setSpeedY(velocityY * .8f);
		return true;	
			
	}
	
	@Override
	  public boolean onDoubleTap(MotionEvent e) {
	    this.mapPanning.cancelCurScrolling();
	    this.selectionArea.startSelection();	    
	    return true;
	  }
}
