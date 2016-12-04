package org.yanzi.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final  String TAG = "FileUtil";
	private static final File parentPath = Environment.getExternalStorageDirectory();
	private static   String storagePath = "";
	private static final String DST_FOLDER_NAME = "PlayCamera";

	/**��ʼ������·��
	 * @return
	 */
	private static String initPath(){
		if(storagePath.equals("")){
			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
			File f = new File(storagePath);
			if(!f.exists()){
				f.mkdir();
			}
		}
		return storagePath;
	}

	/**����Bitmap��sdcard
	 * @param b
	 */
	public static void saveBitmap(Bitmap b){
		////////////////////////
		//��ȡ���ͼƬ�Ŀ�͸�
        int width = b.getWidth();
        int height = b.getHeight();
        
        //����Ԥת���ɵ�ͼƬ�Ŀ�Ⱥ͸߶�
        int newWidth = width;
        int newHeight = height;
        
        //���������ʣ��³ߴ��ԭʼ�ߴ�
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        
        // ��������ͼƬ�õ�matrix����
        Matrix matrix = new Matrix();
        
        // ����ͼƬ����
        matrix.postScale(scaleWidth, scaleHeight);
        
        //��תͼƬ ����
        matrix.postRotate(0);
        
        // �����µ�ͼƬ
        b = Bitmap.createBitmap(b, 0, 0,
        width, height, matrix, true);
		////////////////////////

		String path = initPath();
		long dataTake = System.currentTimeMillis();
		String jpegName = path + "/" + dataTake +".jpg";
		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Log.i(TAG, "saveBitmap�ɹ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "saveBitmap:ʧ��");
			e.printStackTrace();
		}

	}


}
