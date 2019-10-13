package ru.mail.park.rk1;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public int LastNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        RecyclerFragment numbers = (RecyclerFragment) fragmentManager.findFragmentById(R.id.number_fragment);
        if (numbers == null)
            transaction.replace(R.id.container, RecyclerFragment.newInstance(LastNumber));

        transaction.commit();

    }

    public void SetLastNumber(int i) {
        LastNumber = i;
    }
}
