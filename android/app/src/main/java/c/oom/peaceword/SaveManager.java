package c.oom.peaceword;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class SaveManager {

    static String filename = "savemanager.sav";

    public static String Serialize() {
        String content = "";
        content += Passgenerator.GetMinLength() + "\n";
        content += Passgenerator.GetSalt() + "\n";
        content += Passgenerator.GetNeedUperCase() + "\n";
        content += Passgenerator.GetNeedSpecialChar() + "\n";
        return content;
    }

    public static boolean Deserialize(ArrayList<String> lines) {
        if (lines.size() >= 4) {
            String minlen = lines.get(0);
            String salt = lines.get(1);
            String needupercase = lines.get(2);
            String needspecialchar = lines.get(3);

            int ml = Integer.parseInt(minlen);
            boolean nuc = Boolean.parseBoolean(needupercase);
            boolean nsc = Boolean.parseBoolean(needspecialchar);

            Passgenerator.SetConfig(ml, nuc, nsc);
            Passgenerator.SetSalt(salt);
            return true;
        }
        return false;
    }

    public static void SaveConfig(Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String content = Serialize();
            outputStream.write(content.getBytes());
            outputStream.close();

        } catch (Exception e) {
            Toast.makeText(context, "Couldn't save the config to save file.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public static void LoadConfig(Context context) {
        FileInputStream inputStream;

        try {
            inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                ArrayList<String> lines = new ArrayList<>();
                while ((line = buffreader.readLine()) != null) {
                    lines.add(line);
                }
                Deserialize(lines);
                buffreader.close();
                inputreader.close();
                inputStream.close();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Couldn't load the config from save file.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static boolean SaveExisting(Context context) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}

