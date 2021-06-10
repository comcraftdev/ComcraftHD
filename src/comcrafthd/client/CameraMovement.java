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
    }

    private static final float ROT_SPEED_X = 60f;
    private static final float ROT_SPEED_Y = 60f;

    private float currRotX = 0;
    private float currRotY = 0;

    private void rotate(float x, float y) {
        final Camera camera = ComcraftGame.instance.renderer.camera;

        currRotX += x * Time.dt * ROT_SPEED_X;
        currRotY += y * Time.dt * ROT_SPEED_Y;

        camera.setOrientation(currRotY, 0f, 1f, 0f);
        camera.postRotate(currRotX, 1f, 0f, 0f);
    }

    private static final float MOVE_SPEED = 6f;

    private void move(float right, float up, float forward) {
        final Camera camera = ComcraftGame.instance.renderer.camera;

        float forwardX = (float) -Math.sin(Math.toRadians(currRotY));
        float forwardZ = (float) -Math.cos(Math.toRadians(currRotY));

        float rightX = (float) Math.sin(Math.toRadians(currRotY) + Math.PI * 0.5);
        float rightZ = (float) Math.cos(Math.toRadians(currRotY) + Math.PI * 0.5);

        camera.translate(
                (forward * forwardX + right * rightX) * MOVE_SPEED * Time.dt,
                up * MOVE_SPEED * Time.dt,
                (forward * forwardZ + right * rightZ) * MOVE_SPEED * Time.dt);
    }

}
