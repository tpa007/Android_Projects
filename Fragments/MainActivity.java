package example.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button fragA;
    Button fragB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragA = (Button) findViewById(R.id.fragA);
        fragB = (Button) findViewById(R.id.fragB);
        fragA.setOnClickListener(this);
        fragB.setOnClickListener(this);
        if (findViewById(R.id.main_frame) != null) {
            if (savedInstanceState != null)
                return;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame, new FragmentA());
            ft.commit();
        }
    }
    public void onClick( View view ) {
        Fragment fragment;
        if(view == findViewById(R.id.fragA)){
            fragment = new FragmentA();
            FragmentManager fm = getSupportFragmentManager();
            Toast.makeText(this, "Fragment A selected...", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.commit();
        }
        if(view == findViewById(R.id.fragB)){
            fragment = new FragmentB();
            FragmentManager fm = getSupportFragmentManager();
            Toast.makeText(this, "Fragment B selected...", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.commit();
        }
    }
}
