package com.inksci.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import com.inksci.inker.R;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AudioRecorder {
	static String app_path_name = "InkerRobot";
	
//////////////////////////////////////////
// 音频获取源
private int audioSource = MediaRecorder.AudioSource.MIC;
// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
private static int sampleRateInHz = 8000;
// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
private static int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
// 缓冲区字节大小
private int bufferSizeInBytes = 0;
private Button Start;
private Button Stop;
private AudioRecord audioRecord;
private boolean isRecord = false;// 设置正在录制的状态


private void creatAudioRecord() {
	// 获得缓冲区字节大小
	bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
			channelConfig, audioFormat);
	// 创建AudioRecord对象
	audioRecord = new AudioRecord(audioSource, sampleRateInHz,
			channelConfig, audioFormat, bufferSizeInBytes);
}


private void startRecord() {
	creatAudioRecord();
	audioRecord.startRecording();
	// 让录制状态为true
	isRecord = true;
	// 开启音频文件写入线程
	new Thread(new AudioRecordThread()).start();
}

private void stopRecord() {
	close();
}

private void close() {
	if (audioRecord != null) {
		System.out.println("stopRecord");
		isRecord = false;//停止文件写入
		audioRecord.stop();
		audioRecord.release();//释放资源
		audioRecord = null;
	}
}


class AudioRecordThread implements Runnable {
	@Override
	public void run() {
		writeDateTOFile();//往文件中写入裸数据
		//copyWaveFile(AudioName, NewAudioName);//给裸数据加上头文件
	}
}


//AudioName裸音频数据文件
private static final String AudioName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name+"/recorder-recognize-audio.pcm";
////////////// END
	private void writeDateTOFile() {
		// new一个byte数组用来存一些字节数据，大小为缓冲区大小
		byte[] audiodata = new byte[bufferSizeInBytes];
		FileOutputStream fos = null;
		int readsize = 0;
		try {
			File file = new File(AudioName);
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);// 建立一个可存取字节的文件
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (isRecord == true) {
			readsize = audioRecord.read(audiodata, 0, bufferSizeInBytes);
			if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
				try {
					fos.write(audiodata);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			fos.close();// 关闭写入流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
