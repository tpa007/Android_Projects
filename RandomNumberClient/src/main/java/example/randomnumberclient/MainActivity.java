package example.randomnumberclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.lang.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText n;
    EditText max;
    EditText min;
    TextView displayResult;
    Button submit;
    MainActivity activity;



    String oper = "";
    Socket socket;
    String response = "";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the EditText elements (defined in res/layout/activity_main.xml
        n = (EditText) findViewById(R.id.editText);
        min = (EditText) findViewById(R.id.editText2);
        max = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.button);


        displayResult = (TextView) findViewById(R.id.displayResult);

        // set  listeners
        submit.setOnClickListener(this);


    }

    // @Override
    public void onClick( View view ) {

        // check if the fields are empty
        if (TextUtils.isEmpty(n.getText().toString())
                || TextUtils.isEmpty(min.getText().toString()) || TextUtils.isEmpty(max.getText().toString())) {
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = "";
                    socket = new Socket("10.0.2.2", 4455);
                    DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dIn = new DataInputStream(socket.getInputStream());
                    dOut.writeUTF(n.getText() + " " + min.getText() + " " + max.getText());
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

