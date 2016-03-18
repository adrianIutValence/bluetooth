package com.berthaglio.android.bluetouth;

import android.app.Application;

import fr.iutvalence.android.BTConnectionHandlerLib.BTConnectionHandler;

/**
 * Created by berthmat on 14/03/16.
 */
public class RobotApplication extends Application {
    public BTConnectionHandler btHandler;

    public RobotApplication(){
        super();
        this.btHandler = new BTConnectionHandler(this);
    }

    public BTConnectionHandler getBTConnectionHandler(){return this.btHandler; }
}
