package c.oom.peaceword;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class ConfigActivity extends AppCompatActivity {

    EditText dt2, dt3;
    Switch sw2, sw3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Typeface custom = ResourcesCompat.getFont(this, R.font.custom);

        dt2 = findViewById(R.id.editText2);
        dt3 = findViewById(R.id.editText3);
        dt3.setTypeface(custom);
        dt2.setTypeface(custom);
        Button b1, b2, b3, b4, b8;
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b8 = findViewById(R.id.button8);
        b1.setTypeface(custom);
        b2.setTypeface(custom);
        b3.setTypeface(custom);
        b4.setTypeface(custom);
        sw2 = findViewById(R.id.switch2);
        sw3 = findViewById(R.id.switch3);
        sw2.setTypeface(custom);
        sw3.setTypeface(custom);
        b8.setTypeface(custom);
    }

    public void onExportSaltQRcode(View v)
    {
        Intent myIntent = new Intent(this, QRCodeActivity.class);
        this.startActivity(myIntent);
    }

    public void onImport(View v)
    {

    }

    public void onSaveConfig(View v)
    {

    }

    public  void onCopySaltInClipboard(View v)
    {

    }

    public void onScanQRCode(View v)
    {

    }
}

