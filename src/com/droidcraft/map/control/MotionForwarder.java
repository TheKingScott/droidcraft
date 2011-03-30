package com.droidcraft.map.control;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import android.view.GestureDetector;

/**
 * Uses {@link http://developer.android.com/reference/android/view/GestureDetector.html } 
 * to detect a gesture and forwards
 * the gesture
 * 
 * @author Frank
 * @author Br
 */
public class MotionForwarder implements IOnSceneTouchListener {

	private GestureDetector detector = null;

	public MotionForwarder(GestureDetector detector) {
		this.detector = detector;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		return this.detector.onTouchEvent(pSceneTouchEvent.getMotionEvent());
	}

}
