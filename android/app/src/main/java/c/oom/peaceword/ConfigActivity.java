package c.oom.peaceword;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

import static c.oom.peaceword.ScannerQrActivity.REQUEST_CAMERA_PERMISSION;


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

        dt2.setText("" + Passgenerator.GetMinLength());
        sw2.setChecked(Passgenerator.GetNeedUperCase());
        sw3.setChecked(Passgenerator.GetNeedSpecialChar());
    }

    public void onExportSaltQRcode(View v) {
        Intent myIntent = new Intent(this, QRCodeActivity.class);
        this.startActivity(myIntent);
    }

    public void onImport(View v) {
        String salt = dt3.getText().toString().trim();
        if (salt.length() >= 0) {
            Passgenerator.SetSalt(salt);
            Toast.makeText(this, "Salt successfully imported !", Toast.LENGTH_LONG).show();
            dt3.setText("");
        } else
            Toast.makeText(this, "Write a salt first !", Toast.LENGTH_LONG).show();

    }

    public void onSaveConfig(View v) {
        String lenmin = dt2.getText().toString();
        if (android.text.TextUtils.isDigitsOnly(lenmin)) {
            int lenmint = Integer.parseInt(lenmin);
            if (lenmint >= 8) {
                Passgenerator.SetConfig(lenmint, sw2.isChecked(), sw3.isChecked());
                Toast.makeText(this, "Config successfully saved !", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Min length should be superior or equal to 8 !", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Min length should be a numeric value !", Toast.LENGTH_LONG).show();
        }
        SaveManager.SaveConfig(this);
        finish();

    }

    public void onCopySaltInClipboard(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("salt", Passgenerator.GetSalt());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Salt successfully copied in your clipboard !", Toast.LENGTH_LONG).show();
    }

    public void onScanQRCode(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent myIntent = new Intent(this, ScannerQrActivity.class);
            this.startActivity(myIntent);
        } else {
            Toast.makeText(this, "You need to allow the app to access to your camera first.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }
}

