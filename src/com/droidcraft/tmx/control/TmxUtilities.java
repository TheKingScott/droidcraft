package com.droidcraft.tmx.control;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.constants.Constants;

import android.content.Context;

/**
 * This class uses Android tmx map utilities to simplify loading of tmx maps
 * @author Ettienne
 *
 */

public class TmxUtilities{
	
	//===========================================================
	// Fields
	// ===========================================================
	public static TMXTiledMap mTMXTiledMap;
	public static TMXLoader mTMXLoader;
	
	// ===========================================================
	// Methods
	// ===========================================================
	public static void createTmxMap(final Context pContext, final TextureManager pTextureManager, final TextureOptions pTextureOptions, final Scene pScene, final String asset){
		try {
			mTMXLoader = new TMXLoader(pContext, pTextureManager, TextureOptions.BILINEAR_PREMULTIPLYALPHA, new ITMXTilePropertiesListener(){
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					if(pTMXTileProperties.containsTMXProperty("cactus", "true")) {
						//do something
					}
				}
			});
			mTMXTiledMap = mTMXLoader.loadFromAsset(pContext, asset);
			} catch (final TMXLoadException tmxle) {
				Debug.e(tmxle);
			}
			pScene.getFirstChild().attachChild(getTmxLayer(0));
			pScene.registerUpdateHandler(new IUpdateHandler(){
				@Override
				public void reset() { }

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					/* Get the scene-coordinates of the players feet. */
					//final float[] playerFootCordinates = player.convertLocalToSceneCoordinates(12, 31);

					/* Get the tile the feet of the player are currently waking on. */
					//final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
					//if(tmxTile != null) {
						// tmxTile.setTextureRegion(null); <-- Rubber-style removing of tiles =D
						//currentTileRectangle.setPosition(tmxTile.getTileX(), tmxTile.getTileY());
					//}
				}
			});
	}
	public static TMXTiledMap getTmxMap(){
		return mTMXTiledMap;
			
	}
	public static TMXLoader getTmxLoader(){
		return mTMXLoader;
			
	}
	public static TMXLayer getTmxLayer(int pLayer){
		return mTMXTiledMap.getTMXLayers().get(pLayer);
			
	}
	public static float getTmxLayerWidth(int pLayer){
		return mTMXTiledMap.getTMXLayers().get(pLayer).getWidth();
			
	}
	public static float getTmxLayerHeight(int pLayer){
		return mTMXTiledMap.getTMXLayers().get(pLayer).getHeight();
			
	}
};	
