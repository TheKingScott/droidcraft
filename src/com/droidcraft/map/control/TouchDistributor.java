package com.droidcraft.map.control;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

/**
 * This takes care of storing every type touch on the scene.
 * @author Frank Blandon
 *
 */
public class TouchDistributor implements Scene.IOnSceneTouchListener {
	
	private ArrayList<IOnSceneTouchListener> touchListeners;
	
	public TouchDistributor(){
		ArrayList<IOnSceneTouchListener> touchListeners = new ArrayList<IOnSceneTouchListener>();
		this.touchListeners = touchListeners;
	}
	
	public void addTouchListener(Scene.IOnSceneTouchListener IOsceneTouchListener){
		this.touchListeners.add(IOsceneTouchListener);
	}
	
	public void removeTouchListener(Scene.IOnSceneTouchListener IOsceneTouchListener){
		this.touchListeners.remove(IOsceneTouchListener);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int size = this.touchListeners.size();
		boolean touch = false;
		for(int i=0; i < size ; i++){
			if (((Scene.IOnSceneTouchListener)this.touchListeners.get(i)).onSceneTouchEvent(pScene, pSceneTouchEvent))
				touch = true;
		}
		return touch;
	}
}

