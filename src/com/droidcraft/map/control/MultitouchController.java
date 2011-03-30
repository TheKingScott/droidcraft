package com.droidcraft.map.control;

import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.entity.scene.Scene;

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
