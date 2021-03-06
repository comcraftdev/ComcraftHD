/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comcrafthd;

import comcrafthd.client.Time;

/**
 *
 * @author quead
 */
public final class ComcraftGameThread implements Runnable {

    public static ComcraftGameThread instance;
    
    public boolean gamePaused = false;
    public boolean gameStopped = false;

    private Thread gameThread;
    
    private final ComcraftGameConfiguration gameConfiguration;
    
    public ComcraftGameThread(ComcraftGameConfiguration gameConfiguration) {
        if (instance != null) {
            throw new IllegalStateException("ComcraftGameThread");
        }
        
        instance = this;
        
        this.gameConfiguration = gameConfiguration;
        
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

        final ComcraftGame game = new ComcraftGame(gameConfiguration);
        
        Log.info(this, "run() game created");
        
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
        
        instance = null;
        
        Log.info(this, "run() finished");
    }
    
}
