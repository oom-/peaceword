package c.oom.peaceword;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button but5 = findViewById(R.id.button5);
        EditText dt = findViewById(R.id.editText);
        Typeface custom = ResourcesCompat.getFont(this, R.font.custom);
        but5.setTypeface(custom);
        dt.setTypeface(custom);

    }

    public void openConfig(View v)
    {
        Intent myIntent = new Intent(this, ConfigActivity.class);
        this.startActivity(myIntent);
    }
}
