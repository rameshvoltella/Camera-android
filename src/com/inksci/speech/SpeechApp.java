package com.inksci.speech;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.inksci.inker.*;

public class SpeechApp extends Application{
  
	@Override
	public void onCreate() {
		// Ӧ�ó�����ڴ�����,�����ֻ��ڴ��С��ɱ����̨����,���SpeechUtility����Ϊnull
		// ע�⣺�˽ӿ��ڷ������̵��û᷵��null���������ڷ�������ʹ���������ܣ������Ӳ�����SpeechConstant.FORCE_LOGIN+"=true" ������ʹ�á�,���ָ���
		// �����������Ӧ��appid
		SpeechUtility.createUtility(SpeechApp.this, "appid="+getString(R.string.app_id));
		super.onCreate();
	}
}
