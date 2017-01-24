package com.inksci.speech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.inksci.inker.*;

import com.iflytek.cloud.SpeechUtility;


/**
 * ������ʾ�����ط������
 */
public class ApkInstaller {
	private Activity mActivity ;
	
	public ApkInstaller(Activity activity) {
		mActivity = activity;
	}

	@SuppressWarnings("deprecation")
	public void install(){
		final Dialog dialog=new Dialog(mActivity,R.style.dialog);
		LayoutInflater inflater = mActivity.getLayoutInflater();
		View alertDialogView = inflater.inflate(R.layout.superman_alertdialog, null);
		dialog.setContentView(alertDialogView);
		Button okButton = (Button) alertDialogView.findViewById(R.id.ok);
		Button cancelButton = (Button) alertDialogView.findViewById(R.id.cancel);
		TextView comeText=(TextView) alertDialogView.findViewById(R.id.title);
		comeText.setTypeface(Typeface.MONOSPACE,Typeface.ITALIC);
		//ȷ��
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				String url = SpeechUtility.getUtility().getComponentUrl();
				String assetsApk="SpeechService.apk";
				processInstall(mActivity, url,assetsApk);
			}
		});
		//ȡ��
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();			
		WindowManager windowManager = mActivity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int)(display.getWidth()); //���ÿ��
		dialog.getWindow().setAttributes(lp);
		return;
	}
	/**
	 * ����������û�а�װ�����������������ҳ�棬�������غ�װ��
	 */
	private boolean processInstall(Context context ,String url,String assetsApk){
		//ֱ�����ط�ʽ
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
		return true;		
	}
}
