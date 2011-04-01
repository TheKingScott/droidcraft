package com.droidcraft.tmx.control;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.Debug;

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
	
	// ===========================================================
	// Methods
	// ===========================================================
	public static TMXLayer createTmxMap(final Context pContext, final TextureManager pTextureManager, final TextureOptions pTextureOptions, String asset){
		try {
			final TMXLoader tmxLoader = new TMXLoader(pContext, pTextureManager, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mTMXTiledMap = tmxLoader.loadFromAsset(pContext, asset);
			} catch (final TMXLoadException tmxle) {
				Debug.e(tmxle);
			}
			return  mTMXTiledMap.getTMXLayers().get(0);
	}	
};	
