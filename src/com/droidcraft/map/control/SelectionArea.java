package com.droidcraft.map.control;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import android.view.MotionEvent;

/**
 * Draws the selection area rectangle to select units
 * @author Frank Blandon
 * @author Wiese82
 *
 */
public class SelectionArea implements IOnSceneTouchListener {

	private float selectionX;
	private float selectionY;
	private Rectangle selectionRectangle;
	private boolean selection = false;
	private Scene scene;

	public SelectionArea(Scene pScene) {
		this.scene = pScene;
		this.selectionRectangle = new Rectangle(0, 0, 0, 0);
		this.selectionRectangle.setColor(1.0f, 0.f, 0.f, 0.3f);
		this.selectionRectangle.setVisible(true);
		this.scene.getLastChild().attachChild(selectionRectangle);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {

			this.selectRectangleActionDown(pSceneTouchEvent);
			return true;
		}
		if (selection) {
			if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {

				this.selectRectangleActionMove(pSceneTouchEvent);
				return true;
			} else if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP) {

				this.selectRectangleActionUp(pSceneTouchEvent);
				return true;
			}
		}
		return false;
	}

	protected void selectRectangleActionDown(TouchEvent pSceneTouchEvent) {
		this.selectionX = pSceneTouchEvent.getX();
		this.selectionY = pSceneTouchEvent.getY();
	}

	protected void selectRectangleActionMove(TouchEvent pSceneTouchEvent) {

		final float fWidth = Math
				.abs(pSceneTouchEvent.getX() - this.selectionX);
		final float fHeight = Math.abs(pSceneTouchEvent.getY()
				- this.selectionY);
		final float fTouchX = pSceneTouchEvent.getX();
		final float fTouchY = pSceneTouchEvent.getY();

		if (fTouchX > this.selectionX && fTouchY < this.selectionY) {
			  /*     _______
             *      |       |
             *      |       |
             *      X_______|
             */
			this.selectionRectangle.setPosition(this.selectionX,
					this.selectionY - fHeight);
		} else if (fTouchX < this.selectionX && fTouchY > this.selectionY) {
			/*       _______
             *      |       X
             *      |       |
             *      |_______|
             */
			this.selectionRectangle.setPosition(this.selectionX - fWidth,
					this.selectionY);
		} else if (fTouchX < this.selectionX && fTouchY < this.selectionY) {
			/*       _______
             *      |       |
             *      |       |
             *      |_______X
             */
			this.selectionRectangle.setPosition(this.selectionX - fWidth,
					this.selectionY - fHeight);
		} else {
			 /*      _______
             *      X       |
             *      |       |
             *      |_______|
             */
			this.selectionRectangle.setPosition(this.selectionX,
					this.selectionY);
		}
		this.selectionRectangle.setWidth(fWidth);
		this.selectionRectangle.setHeight(fHeight);
		this.selectionRectangle.setVisible(true);
	}

	protected void selectRectangleActionUp(TouchEvent pSceneTouchEvent) {


		if (this.selectionRectangle.isVisible()) {

			// set rectangle invisible
			this.selectionRectangle.setVisible(false);
			this.selectionRectangle.setPosition(0.f, 0.f);
			this.selectionRectangle.setWidth(0.f);
			this.selectionRectangle.setHeight(0.f);
		}
		this.selection = false;
	}

	public void startSelection() {
		this.selection = true;
	}

}
