package c.oom.peaceword;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText dt = null;
    TextView ft = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button but5 = findViewById(R.id.button5);
        dt = findViewById(R.id.editText);
        ft = findViewById(R.id.textView4);
        Typeface custom = ResourcesCompat.getFont(this, R.font.custom);
        but5.setTypeface(custom);
        dt.setTypeface(custom);

        dt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                GenPassword();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        GenPassword();
    }

    private void GenPassword()
    {
        String input = dt.getText().toString();
        if (input.trim().length() == 0)
            ft.setText("?");
        else
            ft.setText(Passgenerator.Generate(input));
    }

    public void copyPassword(View v)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("salt", ft.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Password successfully copied in your clipboard !", Toast.LENGTH_LONG).show();
    }

    public void openConfig(View v)
    {
        Intent myIntent = new Intent(this, ConfigActivity.class);
        this.startActivity(myIntent);
    }
}
