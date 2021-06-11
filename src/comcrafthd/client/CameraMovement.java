/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.ComcraftGame;
import javax.microedition.m3g.Camera;

/**
 *
 * @author quead
 */
public final class CameraMovement {

    public void tick() {
        final KeyboardMapping mapping = ComcraftGame.instance.keyboardMapping;
        final Keyboard keyboard = Keyboard.instance;

        if (keyboard.isPressed(mapping.cameraUp)) {
            rotate(1f, 0);
        }
        if (keyboard.isPressed(mapping.cameraDown)) {
            rotate(-1f, 0);
        }
        if (keyboard.isPressed(mapping.cameraLeft)) {
            rotate(0, 1f);
        }
        if (keyboard.isPressed(mapping.cameraRight)) {
            rotate(0, -1f);
        }

        if (keyboard.isPressed(mapping.moveBack)) {
            move(0, 0, -1f);
        }
        if (keyboard.isPressed(mapping.moveFront)) {
            move(0, 0, 1f);
        }
        if (keyboard.isPressed(mapping.moveUp)) {
            move(0, 1f, 0);
        }
        if (keyboard.isPressed(mapping.moveDown)) {
            move(0, -1f, 0);
        }
        if (keyboard.isPressed(mapping.moveLeft)) {
            move(-1f, 0, 0);
        }
        if (keyboard.isPressed(mapping.moveRight)) {
            move(1f, 0, 0);
        }
        
        updateCamera(ComcraftGame.instance.renderer.camera);
    }

    private static final float ROT_SPEED_X = 60f;
    private static final float ROT_SPEED_Y = 60f;

    public float rotationX = 0;
    public float rotationY = 0;

    private void rotate(float x, float y) {
        rotationX += x * Time.dt * ROT_SPEED_X;
        rotationY += y * Time.dt * ROT_SPEED_Y;
    }

    private static final float MOVE_SPEED = 6f;

    public float positionX;
    public float positionY = 10;
    public float positionZ;
    
    private void move(float right, float up, float forward) {
        final float forwardX = (float) -Math.sin(Math.toRadians(rotationY));
        final float forwardZ = (float) -Math.cos(Math.toRadians(rotationY));

        final float rightX = (float) Math.sin(Math.toRadians(rotationY) + Math.PI * 0.5);
        final float rightZ = (float) Math.cos(Math.toRadians(rotationY) + Math.PI * 0.5);

        positionX += (forward * forwardX + right * rightX) * MOVE_SPEED * Time.dt;
        positionY += up * MOVE_SPEED * Time.dt;
        positionZ += (forward * forwardZ + right * rightZ) * MOVE_SPEED * Time.dt;
    }
    
    public void updateCamera(final Camera camera) {
        camera.setOrientation(rotationY, 0f, 1f, 0f);
        camera.postRotate(rotationX, 1f, 0f, 0f);
        
        camera.setTranslation(positionX, positionY, positionZ);
    }

}
