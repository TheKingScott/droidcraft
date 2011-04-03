package com.droidcraft.map.control;

import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.entity.scene.Scene;

/**
 * Takes care of creating a multi-touch controller using the singleton pattern to avoid 'Force Closing' in
 * those devices that don't support multi-touch
 * @author Frank
 * @author Br
 */
public class MultitouchController {

	public static Scene.IOnSceneTouchListener getPinchZoomDetector(ZoomCamera cam, MapPanning panning){
		return SingletonHolder.getPinchZoomDetector(cam, panning);
	}
	
	static class SingletonHolder{
		private static Scene.IOnSceneTouchListener getPinchZoomDetector(ZoomCamera cam, MapPanning panning){
			return new PinchZoomDetector(cam, panning);
		}
	}
}
