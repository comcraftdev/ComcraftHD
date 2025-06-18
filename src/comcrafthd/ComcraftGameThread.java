package comcrafthd;

import comcrafthd.client.*;

public final class ComcraftGameThread implements Runnable {

    public boolean gamePaused = false;
    public boolean gameStopped = false;

    private Thread gameThread;

    private final ComcraftGame game;

    public ComcraftGameThread(ComcraftGame game) {
        this.game = game;

        gameThread = new Thread(this);
    }

    private boolean gameStarted = false;

    public void start() {
        if (!gameStarted) {
            gameStarted = true;
            gameThread.start();
        }
    }

    public synchronized void stop() {
        gameStopped = true;
        gamePaused = false;

        notify();

        try {
            gameThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void pause() {
        gamePaused = true;
    }

    public synchronized void resume() {
        gamePaused = false;
        notify();
    }

    public void run() {
        Log.info(this, "run() entered");

        Time.reset();

        game.initialize();

        while (!gameStopped) {
            synchronized (this) {
                while (gamePaused) {
                    Log.info(this, "run() gamePaused");
                    Time.reset();
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                if (gameStopped) {
                    Log.info(this, "run() gameStopped break");
                    break;
                }
            }

            Time.tick();

            game.tick();

            Thread.yield();
        }

        game.stop();
        game.clear();

        this.gameThread = null;

        Log.info(this, "run() finished");
    }

}
