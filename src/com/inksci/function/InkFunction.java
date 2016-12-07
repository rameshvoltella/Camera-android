package com.inksci.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;
import android.util.Log;

public class InkFunction {
    public static String read_file(String fileName){
        //String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/" + "master.data";
            String res = "";

            try {
                FileInputStream fin = new FileInputStream(fileName);

                //FileInputStream fin = openFileInput(fileName);  

                //???????????FileInputStream
                int length = fin.available();

                byte[] buffer = new byte[length];

                fin.read(buffer);

                res = EncodingUtils.getString(buffer, "UTF-8");

                fin.close();
            } catch (Exception e) {
                e.printStackTrace();
            } 	
		return res;
    }

	public static void write_file(String pathName, String fileName, String s) {
        String sdStatus = Environment.getExternalStorageState();

        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("TestFile", "SD card is not avaiable/writeable right now.");

            return;
        }

        try {
              
            File path = new File(pathName);
            File file = new File(pathName + fileName);

            if (!path.exists()) {
                Log.d("TestFile", "Create the path:" + pathName);
                path.mkdir();
            }

            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }

            FileOutputStream stream = new FileOutputStream(file);

            //String s = "this is a test string writing to file.";  
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

}
