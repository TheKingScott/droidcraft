package com.droidcraft.map.control;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.Log;
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
	Line lines[] = new Line[4];
	private float fHeight;
	private float fWidth;
	private float fTouchX;
	private float fTouchY;
	

	public SelectionArea(Scene pScene) {
		this.scene = pScene;
		this.selectionRectangle = new Rectangle(0, 0, 0, 0);		
		this.selectionRectangle.setColor(1.0f, 0.980392f, 0.980392f, 0.2f);
		this.selectionRectangle.setVisible(true);
		this.scene.getLastChild().attachChild(selectionRectangle);
		for(int i=0; i <lines.length; i++){
			lines[i] = new Line(0,0,0,0,2);
			this.scene.getLastChild().attachChild(lines[i]);
			lines[i].setColor(0f, 1f ,0f);
		}
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

		this.fWidth = Math
				.abs(pSceneTouchEvent.getX() - this.selectionX);
		this.fHeight = Math.abs(pSceneTouchEvent.getY()
				- this.selectionY);
		this.fTouchX = pSceneTouchEvent.getX();
		this.fTouchY = pSceneTouchEvent.getY();

		if (this.fTouchX > this.selectionX && this.fTouchY < this.selectionY) {
			  /*     _______
             *      |       |
             *      |       |
             *      X_______|
             */
			this.selectionRectangle.setPosition(this.selectionX,
					this.selectionY - this.fHeight);
		} else if (this.fTouchX < this.selectionX && this.fTouchY > this.selectionY) {
			/*       _______
             *      |       X
             *      |       |
             *      |_______|
             */
			this.selectionRectangle.setPosition(this.selectionX - this.fWidth,
					this.selectionY);
		} else if (this.fTouchX < this.selectionX && this.fTouchY < this.selectionY) {
			/*       _______
             *      |       |
             *      |       |
             *      |_______X
             */
			this.selectionRectangle.setPosition(this.selectionX - this.fWidth,
					this.selectionY - this.fHeight);
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
		
		this.fHeight = fTouchY - this.selectionY;
		this.fWidth = fTouchX - this.selectionX;
		
		//Add border to the rectangle
		lines[0].setPosition(this.selectionX, this.selectionY, this.fTouchX, this.selectionY);
		lines[1].setPosition(this.selectionX, this.selectionY, this.selectionX, this.selectionY + this.fHeight);
		lines[2].setPosition(this.fTouchX, this.selectionY, fTouchX, this.selectionY + this.fHeight);
		lines[3].setPosition(this.selectionX, this.selectionY + this.fHeight, this.selectionX + this.fWidth, this.selectionY + this.fHeight);
		
		for(int i=0; i < lines.length; i++)
			lines[i].setVisible(true);
	}

	protected void selectRectangleActionUp(TouchEvent pSceneTouchEvent) {


		if (this.selectionRectangle.isVisible()) {

			// set rectangle invisible
			this.selectionRectangle.setPosition(0.f, 0.f);
			this.selectionRectangle.setWidth(0.f);
			this.selectionRectangle.setHeight(0.f);
			for(int i=0; i < lines.length; i++){
				lines[i].setVisible(false);
				lines[i].setPosition(0,0,0,0);
				}
		}
		this.selection = false;
	}

	public void startSelection() {
		this.selection = true;
	}

}
