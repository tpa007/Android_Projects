package example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.lang.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
        EditText t1;
        EditText t2;
        MainActivity activity;
        ImageView plus;
        ImageView minus;
        ImageView multiply;
        ImageView divide;

        TextView displayResult;


        String oper = "";
        Socket socket;
        String response = "";
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // find the EditText elements (defined in res/layout/activity_main.xml
            t1 = (EditText) findViewById(R.id.t1);
            t2 = (EditText) findViewById(R.id.t2);

            plus = (ImageView) findViewById(R.id.plus);
            minus = (ImageView) findViewById(R.id.minus);
            multiply = (ImageView) findViewById(R.id.multiply);
            divide = (ImageView) findViewById(R.id.divide);

            displayResult = (TextView) findViewById(R.id.displayResult);

            // set  listeners
            plus.setOnClickListener( this );
            minus.setOnClickListener( this);
            multiply.setOnClickListener( this);
            divide.setOnClickListener( this);

        }

        // @Override
        public void onClick( View view ) {

            // check if the fields are empty
            if (TextUtils.isEmpty(t1.getText().toString())
                    || TextUtils.isEmpty(t2.getText().toString())) {
                return;
            }
            // perform operations
            // save operator in oper for later use
            switch ( view.getId() ) {
                case R.id.plus:
                    oper = "+";
                    break;
                case R.id.minus:
                    oper = "-";

                    break;
                case R.id.multiply:
                    oper = "*";
                    break;
                case R.id.divide:
                    oper = "/";
                    break;
                default:
                    break;
            }


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        response = "";
                        socket = new Socket("10.0.2.2", 4444);
                        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dIn = new DataInputStream(socket.getInputStream());
                        dOut.writeUTF(t1.getText() + " " + t2.getText() + " " + oper);
                        dOut.flush();
                        response = dIn.readUTF();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayResult.setText(response);
                            }
                        });

                        dIn.close();
                        dOut.close();
                        socket.close();
                    }
                    catch (UnknownHostException e) {
                        e.printStackTrace();
                        displayResult.setText("UnknownHostException: " + e.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        displayResult.setText("IOException: " + e.toString());
                    }

                }
            }).start();
        }
}
