package com.inksci.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioRecorder {
	String	app_path_name;
	String	AudioName;
/* ��Ƶ��ȡԴ */
	private int audioSource = MediaRecorder.AudioSource.MIC;
/* ������Ƶ�����ʣ�44100��Ŀǰ�ı�׼������ĳЩ�豸��Ȼ֧��22050��16000��11025 */
	private static int sampleRateInHz = 8000;
/* ������Ƶ��¼�Ƶ�����CHANNEL_IN_STEREOΪ˫������CHANNEL_CONFIGURATION_MONOΪ������ */
	private static int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
/* ��Ƶ���ݸ�ʽ:PCM 16λÿ����������֤�豸֧�֡�PCM 8λÿ����������һ���ܵõ��豸֧�֡� */
	private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
/* �������ֽڴ�С */
	private int		bufferSizeInBytes = 0;
	private AudioRecord	audioRecord;
	private boolean		isRecord = false; /* ��������¼�Ƶ�״̬ */

	public AudioRecorder( String app, String audio )
	{
		app_path_name	= app;
		AudioName	= audio;
	}


	private void creatAudioRecord()
	{
		/* ��û������ֽڴ�С */
		bufferSizeInBytes = AudioRecord.getMinBufferSize( sampleRateInHz,
								  channelConfig, audioFormat );
		/* ����AudioRecord���� */
		audioRecord = new AudioRecord( audioSource, sampleRateInHz,
					       channelConfig, audioFormat, bufferSizeInBytes );
	}


	public void startRecord()
	{
		creatAudioRecord();
		audioRecord.startRecording();
		/* ��¼��״̬Ϊtrue */
		isRecord = true;
		/* ������Ƶ�ļ�д���߳� */
		new Thread( new AudioRecordThread() ).start();
	}


	public void stopRecord()
	{
		close();
	}


	private void close()
	{
		if ( audioRecord != null )
		{
			System.out.println( "stopRecord" );
			isRecord = false;       /* ֹͣ�ļ�д�� */
			audioRecord.stop();
			audioRecord.release();  /* �ͷ���Դ */
			audioRecord = null;
		}
	}


	class AudioRecordThread implements Runnable {
		@Override
		public void run()
		{
			writeDateTOFile(); /* ���ļ���д�������� */
			/* copyWaveFile(AudioName, NewAudioName);//�������ݼ���ͷ�ļ� */
		}
	}


	private void writeDateTOFile()
	{
		/* newһ��byte����������һЩ�ֽ����ݣ���СΪ��������С */
		byte[] audiodata = new byte[bufferSizeInBytes];
		FileOutputStream	fos		= null;
		int			readsize	= 0;
		try {
			File file = new File( AudioName );
			if ( file.exists() )
			{
				file.delete();
			}
			fos = new FileOutputStream( file ); /* ����һ���ɴ�ȡ�ֽڵ��ļ� */
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		while ( isRecord == true )
		{
			readsize = audioRecord.read( audiodata, 0, bufferSizeInBytes );
			if ( AudioRecord.ERROR_INVALID_OPERATION != readsize )
			{
				try {
					fos.write( audiodata );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
		try {
			fos.close(); /* �ر�д���� */
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
