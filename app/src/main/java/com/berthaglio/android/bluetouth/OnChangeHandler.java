package com.berthaglio.android.bluetouth;

import android.widget.TextView;

import java.io.IOException;

import fr.iutvalence.android.BTConnectionHandlerLib.BTConnectionHandler;
import fr.iutvalence.android.BTConnectionHandlerLib.exceptions.BTHandlingException;

/**
 * Created by berthmat on 07/03/16.
 */
public class OnChangeHandler implements JoystickView.ValueChangedHandler {
    private static final boolean ONLY_LETTERS = true;
    BTConnectionHandler btHandler;

    public OnChangeHandler(BTConnectionHandler BTHandler){
        this.btHandler = BTHandler;
    }

    public void onValueChanged(int left, int right){
        String str;
        if(ONLY_LETTERS){
            if(left-right > 20) {
                str = "d";
            }else if(right - left > 20){
                str = "g";
            }else{
                if(left == 0){
                    str = "s";
                }else if(left < 0){
                    str = "r";
                }else{
                    str = "a";
                }
            }
        }else{
            str = "J"+left+","+right+"\n\r";
        }
        try {
            this.btHandler.sendData(str);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (BTHandlingException e) {
            e.printStackTrace();
            return;
        }
    }
}
