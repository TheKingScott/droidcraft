package com.droidcraft.ui;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.droidcraft.map.control.MapFlinger;
import com.droidcraft.map.control.MapPanning;
import com.droidcraft.map.control.MotionForwarder;
import com.droidcraft.map.control.MultitouchController;
import com.droidcraft.map.control.TouchDistributor;

import android.os.Build;
import android.view.Display;
import android.view.GestureDetector;


public class DroidCraft extends BaseGameActivity {

	private static int CAMERA_HEIGHT;
	private static int CAMERA_WIDTH;
	private ZoomCamera mZoomCamera;
	private Texture mTexture;
	private TextureRegion mBoxFaceTextureRegion;
	
	@Override
	public void onLoadComplete() {
		// Nothing for now
		
	}

	@Override
	public Engine onLoadEngine() {
		Display d = getWindowManager().getDefaultDisplay();
		CAMERA_WIDTH = d.getWidth();
		CAMERA_HEIGHT = d.getHeight();
		this.mZoomCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final Engine engine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mZoomCamera));
		return engine;
	}

	@Override
	public void onLoadResources() {
		this.mTexture = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegionFactory.setAssetBasePath("gfx/");
		this.mBoxFaceTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "face_box.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(1);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		initMultiTouch(scene);
		final Sprite box = new Sprite(CAMERA_HEIGHT/2,CAMERA_WIDTH/2, this.mBoxFaceTextureRegion);
		scene.getLastChild().attachChild(box);
		return scene;
	}
	
	/**
	 * Initializes all the multi-touch functionalities in the phone
	 * Panning, Zooming-in-out, Pinch-Zoom...etc
	 * Fling!
	 */
	private void initMultiTouch(Scene scene) {
		TouchDistributor touchDistributor = new TouchDistributor();
		MapPanning panner = new MapPanning(this.mZoomCamera);
		
		if(Build.VERSION.SDK_INT >= 5){
			Scene.IOnSceneTouchListener listener = MultitouchController.getPinchZoomDetector(this.mZoomCamera, panner);
        	touchDistributor.addTouchListener(listener); 
		}
		this.mEngine.registerUpdateHandler(panner);
		scene.setOnSceneTouchListener(touchDistributor);
        touchDistributor.addTouchListener(panner);               
        GestureDetector gestureDetector = new GestureDetector(new MapFlinger(panner));
        MotionForwarder motionForwarder = new MotionForwarder(gestureDetector);
        touchDistributor.addTouchListener(motionForwarder);	
	}
	
}