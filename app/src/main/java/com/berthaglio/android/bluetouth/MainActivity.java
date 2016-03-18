package com.berthaglio.android.bluetouth;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import fr.iutvalence.android.BTConnectionHandlerLib.BTConnectionHandler;
import fr.iutvalence.android.BTConnectionHandlerLib.exceptions.BTHandlingException;


public class MainActivity extends ActionBarActivity {
    public BTConnectionHandler btHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btHandler = ((RobotApplication)this.getApplication()).getBTConnectionHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connect(View view){
        try {
            this.btHandler.connectToBTDevice(((EditText) findViewById(R.id.deviceName)).getText().toString());
            ((TextView) findViewById(R.id.status)).setText("Connected.");
        } catch (BTHandlingException e) {
            e.printStackTrace();
            ((TextView) findViewById(R.id.status)).setText("Error. Check android studio. Don't have ? Too bad...");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            ((TextView) findViewById(R.id.status)).setText("Error. Check android studio. Don't have ? Too bad...");
            return;
        }
    }

    public void onSend(View view){
       this.send(((TextView) findViewById(R.id.command)).getText().toString());
    }

    public void disconnect(View view){
        this.btHandler.closeConnection();
        ((TextView) findViewById(R.id.status)).setText("Disconected");
    }

    public void send(String str){
        try {
            this.btHandler.sendData(str);
            ((TextView) findViewById(R.id.status)).setText("Sent");
            ((TextView) findViewById(R.id.command)).setText("");
        } catch (IOException e) {
            e.printStackTrace();
            ((TextView) findViewById(R.id.status)).setText("Error. Check android studio. Don't have ? Too bad...");
            return;
        } catch (BTHandlingException e) {
            e.printStackTrace();
            ((TextView) findViewById(R.id.status)).setText("Error. Check android studio. Don't have ? Too bad...");
            return;
        }
    }

    public void openJoystick(View view){
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
    }

    /**
     * Transform two speeds into a valid MagicalLagrezeBot protocol string.
     * @param left Left wheel speed
     * @param right Right wheel speed
     */
    private void toRobot(int left, int right)
    {
        this.send("J"+String.valueOf(left)+','+String.valueOf(right)+"\n\r");
    }

    public void onLeft(View view)
    {
        this.send("G");
        //this.toRobot(-100, 100);
    }

    public void onRight(View view)
    {
        this.send("D");
        //this.toRobot(100, -100);
    }

    public void onFront(View view)
    {
        this.send("A");
        //this.toRobot(100, 100);
    }

    public void onBack(View view)
    {
        this.send("R");
        //this.toRobot(-100, -100);
    }

    public void onStop(View view)
    {
        this.send("S");
        //this.toRobot(0, 0);
    }
}
