package org.yanzi.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.res.Resources;

import android.graphics.Bitmap;

import android.graphics.Bitmap.CompressFormat;

import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.os.Bundle;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.text.Editable;

import android.text.format.Time;

import android.util.Log;
import android.util.Log;

import android.view.Menu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.view.View.OnClickListener;

import android.view.ViewGroup.LayoutParams;

import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.yanzi.camera.CameraInterface;
import org.yanzi.camera.CameraInterface.CamOpenOverCallback;
import org.yanzi.camera.preview.CameraSurfaceView;

import com.inksci.inker.R;

import org.yanzi.util.DisplayUtil;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.inksci.function.InkFunction;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.util.Base64;

import com.inksci.function.*;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.inksci.inker.*;
import com.inksci.speech.ApkInstaller;
import com.inksci.speech.ClearEditText;
import com.inksci.speech.JsonParser;
public class CameraActivity extends Activity implements CamOpenOverCallback, SpeechSynthesizerListener {
    static String app_path_name = "InkerRobot";
    
    /////////// speech
	private ImageView start2;
	private Context mContext;
	
	//初始化识别对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog iatDialog;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	// 语音+安装助手类
	//ApkInstaller mInstaller ;
    /// speech end
    
	///////////////////
	private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = app_path_name+"/baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license_without";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
	/////// TTS Finish
	
		
    //AudioName裸音频数据文件
    private static final String AudioName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name+"/recorder-recognize-audio.pcm";
    AudioRecorder myAudioRecorder = new AudioRecorder(app_path_name, AudioName);
    
    
	boolean new_visit;
	ImageView master_control;
	
    static boolean get_bitmap;
    private static final String TAG = "yanzi";

    //private EditText editText;
    private static final int MSG_CameraPost = 1;
    private static final int EXCEPTION = 2;
    MultipartEntity global_entity;
    HttpPost global_post;
    int global_MSG;
    String new_face_token;
    String master_face_token;
    Bitmap up_bitmap;
    ImageButton image1;
    CameraSurfaceView surfaceView = null;
    ImageButton shutterBtn;
    float previewRate = -1f;
    String up_image_file = "zx-01.jpg"; // initial value
    private HttpParams httpParams;
    private HttpClient httpClient;


    private Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
               
                case 902: // doGet
                	//Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                	String content = "...";
                	try {
						content = new JSONObject( (String) msg.obj ).getString("content");
						content = content.replace("{br}", "\r\n");
						content = content.replace("菲菲", "小墨");
						if(content.indexOf("404 Not Found")!=-1){
							content ="抱歉，找不到相关内容";
						}
						
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
                	//Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                	mSpeechSynthesizer.speak(content);
                	
                	TextView tv1=(TextView)findViewById(R.id.textView1);
                	tv1.setText("小墨： "+content);
                	
                	break;
                case 901: // WavPost
                	//Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();

                	
                	
                	String result0 = (String) msg.obj;
                		
                	if(result0.equals("")){
                		// xf speech will submit "" second time
                		break;
                	}	
                	
                	
                		//Toast.makeText(getApplicationContext(), "result0: "+result0, Toast.LENGTH_SHORT).show();
                		
                		TextView tv01=(TextView)findViewById(R.id.TextView01);
                    	tv01.setText("我： "+result0);
                    	
                    	if(result0.equals("人脸识别")){
							logo_page();
							break;
						}
                    	
                    	if(result0.equals("百度语音")){
                    		Button Start;
                		Button Stop;

                		Start = (Button)findViewById(R.id.start);
                		Stop = (Button) findViewById(R.id.stop);
                    	
                    		Start.setVisibility(View.VISIBLE);
        					Stop.setVisibility(View.VISIBLE);
							break;
						}
                    	
                		
						try {
							result0 = java.net.URLEncoder.encode(result0,   "utf-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                		
						final String voice_result = result0 ;
						
                		 new Thread() {
     			            public void run() {
     			            	 Map<String, String> map = new HashMap<String, String>();  
     			                 
     			                 map.put("key", "free");  
     			                 map.put("appid", "0");  
     			                 map.put("msg", voice_result);  
    			            	
     			            	 Message msg = new Message();
     			                 msg.what = 902;
     			                 try {
     								msg.obj = doGet("http://api.qingyunke.com/api.php",map);
     				            	
     							} catch (Exception e) {
     								// TODO Auto-generated catch block
     								e.printStackTrace();
     							}
     			                 handler.sendMessage(msg);
     			            }
     			        }.start();
                	
              
                	
                	
                	
                	break;
                case 701: // 
                    // Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();

                    Time t = new Time("GMT+8"); // or Time t=new Time("GMT+8"); ??Time Zone???  
                    t.setToNow(); // ???????  

                    String str_master = "{\"face_token\": \"" + new_face_token +
                        "\", " + "\"last_use\": " + "{ 	\"year\":" + t.year +
                        ", 	\"month\":" + t.month + ", 	\"day\":" + t.monthDay +
                        " }" + "}";

                    writeFileToSD("master.data", str_master);

                    try {
                        global_entity = new MultipartEntity();
                        global_entity.addPart("T", new StringBody("inker"));
                        global_entity.addPart("V", new StringBody(str_master));
                        global_post = new HttpPost(
                                "http://www.inksci.com/inker/post.php");
                        global_MSG = 705;
                        new Thread() {
                                @Override
                                public void run() {
                                    ink_post();
                                }
                            }.start();
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    //setContentView(R.layout.over_05);

                    
                    // have not master
                    
                    master_control = (ImageView) findViewById(R.id.imageView2);
                    master_control.setImageBitmap( ((BitmapDrawable)(getResources().getDrawable(R.drawable.make_not))).getBitmap() );

                	master_control.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Toast.makeText(getApplicationContext(), "click...", Toast.LENGTH_SHORT).show();
                                File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/" +
                                        "master.data");
                                if (f.exists()){
                                
                                	f.delete();
                                	master_control.setImageBitmap( ((BitmapDrawable)(getResources().getDrawable(R.drawable.make_yes))).getBitmap() );
                                }else{
                                	new Thread() {
                                        @Override
                                        public void run() {
                        					create_master_thread();
                                        }
                                    }.start();
                                }
                            }
                    });                 
                    
                    
                    
                    
                    
                    
                    
                    
                    break;

                case 702:

                    String txt_data_702 = (String) msg.obj;
                    //writeFileToSD("702.ink", txt_data_702);

                    // decode json
                    JSONObject jsonObject_702;

                    try {
                        // Toast.makeText(getApplicationContext(), "JSON ...", Toast.LENGTH_SHORT).show();

                        jsonObject_702 = new JSONObject(txt_data_702);

                        String confidence = jsonObject_702.getString(
                                "confidence");

                        if (Float.parseFloat(confidence) > 90) {
                            // Toast.makeText(getApplicationContext(), "Love you, my master!", Toast.LENGTH_SHORT).show();
                            
                            // you are master
                        	if(new_visit){
                        		( (TextView) findViewById(R.id.textView2) ).setText("主人，小墨好想你！");
                        		new_visit=false;
                        	}
                            master_control = (ImageView) findViewById(R.id.imageView2);
                            Resources res=getResources();  
                            Drawable drawable = res.getDrawable(R.drawable.make_not);  
                            //实际上这是一个BitmapDrawable对象  
                            BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;  
                            //可以在调用getBitmap方法，得到这个位图  
                            Bitmap bitmap=bitmapDrawable.getBitmap();   
                        	master_control.setImageBitmap(bitmap); 

                        	master_control.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(getApplicationContext(), "click...", Toast.LENGTH_SHORT).show();
                                        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/" +
                                                "master.data");
                                        if (f.exists()){
                                        
                                        	f.delete();
                                        	master_control.setImageBitmap( ((BitmapDrawable)(getResources().getDrawable(R.drawable.make_yes))).getBitmap() );
                                        }else{
                                        	master_control.setImageBitmap( ((BitmapDrawable)(getResources().getDrawable(R.drawable.no_make))).getBitmap() );
                                        	new Thread() {
                                                @Override
                                                public void run() {
                                					create_master_thread();
                                                }
                                            }.start();
                                        }
                                    }
                            });
                            
                            
                            
                        } else {
                           // Toast.makeText(getApplicationContext(), "Hi, friend!", Toast.LENGTH_SHORT).show();
                        	if(new_visit){   
                        		( (TextView) findViewById(R.id.textView2) ).setText("嗨，很高兴认识你！");
                        		new_visit=false;
                        	}
                            master_control = (ImageView) findViewById(R.id.imageView2);
                            Resources res=getResources();  
                            Drawable drawable = res.getDrawable(R.drawable.no_make);  
                            //实际上这是一个BitmapDrawable对象  
                            BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;  
                            //可以在调用getBitmap方法，得到这个位图  
                            Bitmap bitmap=bitmapDrawable.getBitmap();   
                        	master_control.setImageBitmap(bitmap); 

                        	master_control.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(getApplicationContext(), "click...", Toast.LENGTH_SHORT).show();
                                    }
                            });
                            
                            
                        }

                        Time t_702 = new Time("GMT+8"); // or Time t=new Time("GMT+8"); ??Time Zone???  
                        t_702.setToNow(); // ???????  

                        String str_master_702 = "{\"face_token\": \"" +
                            master_face_token + "\", " + "\"last_use\": " +
                            "{ 	\"year\":" + t_702.year + ", 	\"month\":" +
                            t_702.month + ", 	\"day\":" + t_702.monthDay +
                            " }" + "}";

                        writeFileToSD("master.data", str_master_702);

                        try {
                            global_entity = new MultipartEntity();
                            global_entity.addPart("T", new StringBody("inker"));
                            global_entity.addPart("V",
                                new StringBody(str_master_702));
                            global_post = new HttpPost(
                                    "http://www.inksci.com/inker/post.php");
                            global_MSG = 706;
                            new Thread() {
                                    @Override
                                    public void run() {
                                        ink_post();
                                    }
                                }.start();
                        } catch (UnsupportedEncodingException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;

               

                case MSG_CameraPost:
                   // Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    //writeFileToSD("up.html.txt.ink", (String) msg.obj);
                    //3.???? ??????
                    String txt_data = (String) msg.obj;

                    //editText.setText(txt_data);

                    // String -> JSON -> display it
                    JSONObject jsonObject;

                    try {
                        //editText.setText("...");
                        //Toast.makeText(getApplicationContext(), "JSON ...", Toast.LENGTH_SHORT).show();

                        jsonObject = new JSONObject(txt_data);

                        JSONArray jsonArray = jsonObject.getJSONArray("faces");

                        //Toast.makeText(getApplicationContext(), jsonArray.length()+"", Toast.LENGTH_LONG).show();
                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

                            String face_token = jsonObject2.getString(
                                    "face_token");
                            JSONObject attributes = jsonObject2.getJSONObject(
                                    "attributes");
                            String gender = attributes.getJSONObject("gender")
                                                      .getString("value");
                            String age = attributes.getJSONObject("age")
                                                   .getString("value");
                            String glass = attributes.getJSONObject("glass")
                                    .getString("value");
                            int int_smile = attributes.getJSONObject("smile")
                                    .getInt("value");
                            if(glass.equals("None")){glass="无眼镜";}else if(glass.equals("Dark")){glass="佩戴墨镜";}else if(glass.equals("Normal")){glass="普通眼镜";}
                            String smile;
                            if(int_smile<20){
                            	smile="表情严肃";}else if(int_smile<40){smile="嫣然一笑";}else if(int_smile<60){smile="笑逐颜开";}else if(int_smile<80){smile="笑容可掬";}else{smile="捧腹大笑";}
                            
                            //editText.setText("gender: "+gender+", age: "+age+", face_token: "+face_token);
                            //Toast.makeText(getApplicationContext(),
                                //"gender: " + gender + ", age: " + age+ ", glass: " + glass+ ", smile: " + smile +
                               // ", face_token: " + face_token, Toast.LENGTH_LONG)
                                // .show();

                            new_face_token = face_token;
                            jump_reslut_04(gender, age, glass, smile);
                        } else {
                            Toast.makeText(getApplicationContext(), "抱歉，没有检测到人脸！", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    System.out.println("76799679");

                    break;

                case EXCEPTION:
                   // Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();

                    break;
                }

                super.handleMessage(msg);
            }
        };
public void relativeLayout_onClick(View v){
	//Toast.makeText(getApplicationContext(), "click me", Toast.LENGTH_SHORT).show();
	mSpeechSynthesizer.stop();
	
	setParam();
	
	showIatDialog();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }  
    private void ink_post() {
        String strResult = "";

        /* ??HTTPPost?? */
        HttpPost httpRequest = global_post;

        //String strResult = "doPostError";  
        try {
            /* ??????????? */
            MultipartEntity entity = global_entity;

            httpRequest.setEntity(entity);

            /* ????????? */
            HttpResponse httpResponse = httpClient.execute(httpRequest);

            /* ?????200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ????? */
                strResult = EntityUtils.toString(httpResponse.getEntity());

                //??ui ???????
                Message msg = new Message(); //????
                msg.what = global_MSG;
                msg.obj = strResult; //????
                handler.sendMessage(msg); //?handler???????
            } else {
                strResult = "Error Response: " +
                    httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);
    }

    protected void jump_reslut_04(String gender, String age, String glass, String smile) {
    	/*
    	 * Display the massage received.
    	 * Whether master has been set?
    	 * true:
    	 *     judge whether this face belongs to the master?
    	 * false:
    	 *     supply a control menu for being the new master.
    	 * */
    	
        setContentView(R.layout.result_04_02);
        new_visit=true;
        
        // Display the massage
        TextView tv01=(TextView)findViewById(R.id.textView1);
        if(gender.equals("Male")){gender="男";}else{gender="女";}
        tv01.setText("         性别："+gender+"\n         年龄："+age+"\n         "+glass+"，"+smile);

        //////////////////////
        ImageView iv_04_02 = (ImageView) findViewById(R.id.imageView1);
        iv_04_02.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                }
            });
    	
    	
        
        
    	
    	boolean date_valid=false;
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/" +
                "master.data";
        File f = new File(fileName);
        if (f.exists()){
            String res = InkFunction.read_file(fileName);

            try {
                JSONObject jsonObject = new JSONObject(res);
                master_face_token = jsonObject.getString("face_token");
                JSONObject last_use = jsonObject.getJSONObject("last_use");
    			int y=last_use.getInt("year");
    			int m=last_use.getInt("month");
    			int d=last_use.getInt("day");
    			Time t = new Time("GMT+8"); // or Time t=new Time("GMT+8"); Time Zone
                t.setToNow(); // System Time  
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d1;
    			try {
    				d1 = sdf.parse(y+"-"+m+"-"+d+" 00:00:00");
    				Date d2=sdf.parse(t.year+"-"+t.month+"-"+t.monthDay+" 00:00:00");
    				daysBetween(d1,d2);
    				//Toast.makeText(getApplicationContext(), "daysBetween(d1,d2): "+daysBetween(d1,d2), Toast.LENGTH_LONG).show();
    				
    				if(daysBetween(d1,d2)<31){
    					date_valid=true;
    				}
    				
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}  
                
            } catch (JSONException e) {
                e.printStackTrace();
            }	

            /////////////////       	
        }
        
        if(!date_valid){// file is invalid, or not exist
        	if(new_visit){
            	( (TextView) findViewById(R.id.textView2) ).setText("小墨还没有主人");
            	new_visit=false;
            }
        	// master control
        	master_control = (ImageView) findViewById(R.id.imageView2);
            Resources res=getResources();  
            Drawable drawable = res.getDrawable(R.drawable.make_yes);  
            //实际上这是一个BitmapDrawable对象  
            BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;  
            //可以在调用getBitmap方法，得到这个位图  
            Bitmap bitmap=bitmapDrawable.getBitmap();   
        	master_control.setImageBitmap(bitmap); 

        	master_control.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "click...", Toast.LENGTH_SHORT).show();
                        new Thread() {
                            @Override
                            public void run() {
            					create_master_thread();
                            }
                        }.start();
                    }
            });
        	
        	
        	
        	
        }else{
        	// judge whether is master
        	new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    compare_two_image();
                }
            }.start();
        }
    }

    private boolean date_is_ok(File f) {
		// TODO Auto-generated method stub
        /////////////////
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/" +
            "master.data";

        //????String fileName = "mnt/sdcard/Y.txt";
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

        try {
            JSONObject jsonObject = new JSONObject(res);
            JSONObject last_use = jsonObject.getJSONObject("last_use");
			int y=last_use.getInt("year");
			int m=last_use.getInt("month");
			int d=last_use.getInt("day");
			Time t = new Time("GMT+8"); // or Time t=new Time("GMT+8"); Time Zone
            t.setToNow(); // System Time  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1;
			try {
				d1 = sdf.parse(y+"-"+m+"-"+d+" 00:00:00");
				Date d2=sdf.parse(t.year+"-"+t.month+"-"+t.monthDay+" 00:00:00");
				daysBetween(d1,d2);
				//Toast.makeText(getApplicationContext(), "daysBetween(d1,d2): "+daysBetween(d1,d2), Toast.LENGTH_LONG).show();
				
				if(daysBetween(d1,d2)<31){
					return true;
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            
        } catch (JSONException e) {
            e.printStackTrace();
        }	

        ///////////////// 	
		return false;
	}

	protected void compare_two_image() {
        String strResult = "";

        /* ??HTTPPost?? */
        HttpPost httpRequest = new HttpPost(
                "https://api-cn.faceplusplus.com/facepp/v3/compare");

        //String strResult = "doPostError";  
        try {
            /* ??????????? */
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("api_key",
                new StringBody("Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN"));
            entity.addPart("api_secret",
                new StringBody("EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f"));
            entity.addPart("face_token1", new StringBody(master_face_token));
            entity.addPart("face_token2", new StringBody(new_face_token));

            httpRequest.setEntity(entity);

            /* ????????? */
            HttpResponse httpResponse = httpClient.execute(httpRequest);

            /* ?????200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ????? */
                strResult = EntityUtils.toString(httpResponse.getEntity());

                //??ui ???????
                Message msg = new Message(); //????
                msg.what = 702;
                msg.obj = strResult; //????
                handler.sendMessage(msg); //?handler???????
            } else {
                strResult = "Error Response: " +
                    httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);
    }

    private void create_master_thread() {
        String strResult = "";

        /* ??HTTPPost?? */
        HttpPost httpRequest = new HttpPost(
                "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface");

        //String strResult = "doPostError";  
        try {
            /* ??????????? */
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("api_key",
                new StringBody("Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN"));
            entity.addPart("api_secret",
                new StringBody("EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f"));
            entity.addPart("outer_id", new StringBody("20161205"));
            entity.addPart("face_tokens", new StringBody(new_face_token));

            httpRequest.setEntity(entity);

            /* ????????? */
            HttpResponse httpResponse = httpClient.execute(httpRequest);

            /* ?????200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ????? */
                strResult = EntityUtils.toString(httpResponse.getEntity());

                //??ui ???????
                Message msg = new Message(); //????
                msg.what = 701;
                msg.obj = strResult; //????
                handler.sendMessage(msg); //?handler???????
            } else {
                strResult = "Error Response: " +
                    httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        ///////////// TTS Begin
        initialEnv();
        startTTS();
        // TTS Finish
        initChat();
       speech_page();
       
	
        
    }
    
    
    
    
    private void speech_page(){
		mContext = this;
		//创建语音识别对象
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		//mInstaller = new  ApkInstaller(CameraActivity.this);
		initView();
		
		
		

    }
	private void initView() {
		;
		
	}
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码："+code);
			}
		}
	};
	private void showTip(String str) {
		Toast.makeText(mContext, str, 1).show();
	}
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener2 = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码："+code);
			}
		}
	};
	/**
	 * 参数设置
	 * @param param
	 * @return 
	 */
	public void setParam(){
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// 设置听写引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置语言区域
		mIat.setParameter(SpeechConstant.ACCENT,"zh_cn");
		// 设置语音前端点
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
		// 设置语音后端点
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
		// 设置标点符号
		mIat.setParameter(SpeechConstant.ASR_PTT, "0");
		// 设置音频保存路径
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/iflytek/wavaudio.pcm");
	}
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = JsonParser.parseIatResult(results.getResultString());
			
			//Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			  Message msg = new Message();
              msg.what = 901;
              msg.obj = text;
              handler.sendMessage(msg);
			
		}

		

		@Override
		public void onError(com.iflytek.cloud.SpeechError arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	

	/**
	 * 显示听写对话框.
	 * @param
	 */
	public void showIatDialog()
	{
		if(null == iatDialog) {
			//初始化听写Dialog	
			iatDialog =new RecognizerDialog(this, mInitListener2);
		}
		//显示听写对话框
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
	}  
    
    
	private void initChat() {
		setContentView(R.layout.recorder);
		Button Start;
		Button Stop;

		Start = (Button) this.findViewById(R.id.start);
		Stop = (Button) this.findViewById(R.id.stop);
		Start.setOnClickListener(new OnClickListener() {
			 
		      @Override
		      public void onClick(View v) {
		        // TODO Auto-generated method stub
		    	  mSpeechSynthesizer.stop();
		    	  
		    	  myAudioRecorder.startRecord();
					TextView tv1=(TextView)findViewById(R.id.textView1);
	            	tv1.setText("");
	            	TextView tv01=(TextView)findViewById(R.id.TextView01);
	            	tv01.setText("聆听中...");
		        
		 
		      }
		    });
		Stop.setOnClickListener(new OnClickListener() {
			 
		      @Override
		      public void onClick(View v) {
		        // TODO Auto-generated method stub
		    	  myAudioRecorder.stopRecord();
					TextView tv1=(TextView)findViewById(R.id.textView1);
	            	tv1.setText("");
	            	TextView tv01=(TextView)findViewById(R.id.TextView01);
	            	tv01.setText("联网识别中...");
	        		
					
					 new Thread() {
				            public void run() {
				            	 Message msg = new Message();
				                 msg.what = 901;
				                 try {
									msg.obj = WavPost();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				                 
				                 
				                 JSONObject jsonObject_901;
								try {
									jsonObject_901 = new JSONObject( (String) msg.obj );
JSONArray resultArr = jsonObject_901.getJSONArray("result");
			                		
			                		String result0 = (String) resultArr.opt(0);
			                		
			                		result0 = result0.substring(0,result0.length()-1);
			                		msg.obj=result0;
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

			                		
				                 
				                 
				                 
				                 handler.sendMessage(msg);
				            }
				        }.start();
		 
		      }
		    });		
		Start.setVisibility(View.GONE);
		Stop.setVisibility(View.GONE);
	}



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static String getToken(String apiKey, String secretKey) throws Exception {
        String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" + 
            "&client_id=" + apiKey + "&client_secret=" + secretKey;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        String token = new JSONObject(printResponse(conn)).getString("access_token");
        return token;
    }

    public String WavPost() throws Exception {
        File pcmFile = new File(AudioName);
        HttpURLConnection conn = (HttpURLConnection) new URL("http://vop.baidu.com/server_api").openConnection();


        JSONObject params = new JSONObject();
        params.put("format", "pcm");
        params.put("rate", 16000);
        params.put("channel", "1");        
        params.put("token", getToken("yzGVe4oXOdflbEICtnYbhe7A", "c9644b9b942a565dbf633d2581cb89f2"));
        params.put("cuid", "inksci");
        params.put("len", pcmFile.length());
        params.put("speech", android.util.Base64.encodeToString(loadFile(pcmFile),Base64.NO_WRAP));

        String pathName=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/";
        
        String tst="";
        for(int i=0;i<10000;i++){
        	tst+="abcde1234567890";
        }
        
        InkFunction.write_file(pathName,"encode.txt",tst);
        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(params.toString());
        wr.flush();
        wr.close();

        
        
        return printResponse(conn);
    }
    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
 
    
    
    protected void logo_page(){
        setContentView(R.layout.logo_01);
        
        ImageView iv01=(ImageView)findViewById(R.id.imageView1);
        iv01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	String pathName=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/";
            	String fileName=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/" + "visit.data";
            	File f = new File(fileName);
            	
            	// initialize: if file is not exist create it.
                if (!f.exists()){
                	
                	InkFunction.write_file(pathName,"visit.data","{visit_count:0}");
                }
                
                // read visit count and update file
                int visit_count=-1;
            	try {
					JSONObject jsonObject  = new JSONObject(InkFunction.read_file(fileName));
					visit_count=jsonObject.getInt("visit_count");
					jsonObject.remove("visit_count");
					jsonObject.put("visit_count", visit_count+1);
					InkFunction.write_file(pathName,"visit.data",jsonObject.toString());
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
                
            	
            	if(visit_count==0||
						visit_count==2||
						visit_count==5||
						visit_count==9||
						visit_count==13||
						visit_count==20){
	                setContentView(R.layout.introduce_02);

	                ImageView iv01 = (ImageView) findViewById(R.id.imageView1);
	                iv01.setOnClickListener(new OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                            run_camera();
	                        }
	                    });
				}else{
					run_camera();
				}
            }
        });
    }

    protected void run_camera() {
        Log.v(TAG, "protected void onStart()");

        Thread openThread = new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    CameraInterface.getInstance()
                                   .doOpenCamera(CameraActivity.this);
                }
        };
        openThread.start();
        setContentView(R.layout.activity_camera);
        initUI();
        initViewParams();

        shutterBtn.setOnClickListener(new BtnListeners());

        image1 = (ImageButton) findViewById(R.id.btn_shutter);
        
    }
    private class BtnListeners implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.btn_shutter:
                get_bitmap = false;

                CameraInterface.getInstance().doTakePicture();
               
                break;

            default:
                break;
            }

            new Thread() {
                    public void run() {
                        while (get_bitmap == false) {
                            ;
                        }

                        if (get_bitmap == true) {
                           
                        	

                            Message msg = new Message();
                            msg.what = MSG_CameraPost;
                            msg.obj = CameraPost();
                            handler.sendMessage(msg);
                        }
                    }
                }.start();
        }
    }
    
    public String CameraPost() {
        String strResult = "doPostError";
        getHttpClient(); // it`s important!
        try {
        	HttpPost httpRequest = new HttpPost(
                    "https://api-cn.faceplusplus.com/facepp/v3/detect");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bm = CameraInterface.getInstance().get_ok_bitmap();
            //BitmapFactory.decodeFile("/storage/sdcard1/InkerRobot/"+up_image_file);
            bm.compress(CompressFormat.JPEG, 60, bos);

            ContentBody mimePart = new ByteArrayBody(bos.toByteArray(),
                    "image/jpeg");

            FileBody fileBody = new FileBody(new File(
                        "/storage/sdcard1/InkerRobot/" + up_image_file),
                    "image/jpeg");
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("image_file", mimePart);
            entity.addPart("api_key",
                new StringBody("Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN"));
            entity.addPart("api_secret",
                new StringBody("EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f"));
            entity.addPart("return_landmark", new StringBody("1"));
            entity.addPart("return_attributes", new StringBody("gender,age,smiling,glass"));

            httpRequest.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(httpRequest);

            /* 200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else {
                strResult = "Error Response: " +
                    httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);

        return strResult;
    }
    private Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);

            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "protected void onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "protected void onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "protected void onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "protected void onDestroy()");
        
        	// 退出时释放连接
     		mIat.cancel();
     		mIat.destroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "protected void onRestart()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.camera, menu);

    	if(true){
    	return true;
    	}
        menu.add(Menu.NONE, Menu.FIRST + 1, 5, "delete")
            .setIcon(android.R.drawable.ic_menu_delete);

        // setIcon()???????????????????????????????,?

        // android.R???????????????????????R???
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "edit")
            .setIcon(android.R.drawable.ic_menu_edit);

        menu.add(Menu.NONE, Menu.FIRST + 3, 6, "help")
            .setIcon(android.R.drawable.ic_menu_help);

        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "add")
            .setIcon(android.R.drawable.ic_menu_add);

        menu.add(Menu.NONE, Menu.FIRST + 5, 4, "info")
            .setIcon(android.R.drawable.ic_menu_info_details);

        menu.add(Menu.NONE, Menu.FIRST + 6, 3, "send")
            .setIcon(android.R.drawable.ic_menu_send);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case Menu.FIRST + 1:
            //Toast.makeText(this, "????????", Toast.LENGTH_LONG).show();

            break;

        case Menu.FIRST + 2:
            //Toast.makeText(this, "????????", Toast.LENGTH_LONG).show();

            break;

        case Menu.FIRST + 3:
           // Toast.makeText(this, "????????", Toast.LENGTH_LONG).show();

          /*  final EditText editText = new EditText(this);
            new AlertDialog.Builder(this).setTitle("???").setView(editText).setPositiveButton("??",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub  
                        Toast.makeText(CameraActivity.this,
                            "????????" + editText.getText(), Toast.LENGTH_SHORT)
                             .show();
                        up_image_file = editText.getText().toString();
                    }
                }).setNegativeButton("??", null).show();
*/
            break;

        case Menu.FIRST + 4:
            
            break;

        case Menu.FIRST + 5:
            //Toast.makeText(this, "????????", Toast.LENGTH_LONG).show();

            break;

        case Menu.FIRST + 6:
            //Toast.makeText(this, "????????", Toast.LENGTH_LONG).show();

            break;
        }

        return false;
    }

    private void initUI() {
        surfaceView = (CameraSurfaceView) findViewById(R.id.camera_surfaceview);
        shutterBtn = (ImageButton) findViewById(R.id.btn_shutter);
    }

    private void initViewParams() {
        LayoutParams params = surfaceView.getLayoutParams();
        Point p = DisplayUtil.getScreenMetrics(this);
        params.width = p.x;
        params.height = p.y;
        previewRate = DisplayUtil.getScreenRate(this); //?????????
        surfaceView.setLayoutParams(params);

        //??????ImageButton????120dip?120dip,??????64?64
        LayoutParams p2 = shutterBtn.getLayoutParams();
        p2.width = DisplayUtil.dip2px(this, 80);
        p2.height = DisplayUtil.dip2px(this, 80);
        ;
        shutterBtn.setLayoutParams(p2);
    }

    @Override
    public void cameraHasOpened() {
        // TODO Auto-generated method stub
        SurfaceHolder holder = surfaceView.getSurfaceHolder();
        CameraInterface.getInstance().doStartPreview(holder, previewRate);
    }

    private void writeFileToSD(String fileName, String s) {
        String sdStatus = Environment.getExternalStorageState();

        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("TestFile", "SD card is not avaiable/writeable right now.");

            return;
        }

        try {
            String pathName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+app_path_name + "/";

            //String fileName="file.txt";  
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
    public HttpClient getHttpClient() {
        // ?? HttpParams ????? HTTP ?????????????  
        this.httpParams = new BasicHttpParams();

        // ??????? Socket ????? Socket ????  
        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

        // ????????? true  
        HttpClientParams.setRedirecting(httpParams, true);

        // ?? user agent  
        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);

        // ???? HttpClient ??  

        // ?? HttpClient httpClient = new HttpClient(); ?Commons HttpClient  

        // ?????? Android 1.5 ??????? Apache ????? DefaultHttpClient  
        httpClient = new DefaultHttpClient(httpParams);

        return httpClient;
    }

    public static void get_bitmap_ok() {
        // TODO Auto-generated method stub
        get_bitmap = true;
    }
    
    
    public String doGet(String url, Map params) {
    	getHttpClient();
    	
        /* ??HTTPGet?? */
        String paramStr = "";

        Iterator iter = params.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            paramStr += (paramStr = "&" + key + "=" + val);
        }

        if (!paramStr.equals("")) {
            paramStr = paramStr.replaceFirst("&", "?");
            url += paramStr;
        }

        HttpGet httpRequest = new HttpGet(url);

        String strResult = "doGetError";

        try {
            /* ????????? */
            HttpResponse httpResponse = httpClient.execute(httpRequest);

            /* ?????200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ????? */
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else {
                strResult = "Error Response: " +
                    httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);

        return strResult;
    }		

    
    /////////////////////////////////////////// TTS START
    // 初始化语音合成客户端并启动
    private void startTTS() {
/*查看Key×
App ID: 9150869

API Key: yzGVe4oXOdflbEICtnYbhe7A

Secret Key: c9644b9b942a565dbf633d2581cb89f2*/    	
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(this);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("yzGVe4oXOdflbEICtnYbhe7A", "c9644b9b942a565dbf633d2581cb89f2");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("9150869");
        /*
        // 设置语音合成文本模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, "your_txt_file_path");
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, "your_speech_file_path");
        // 设置语音合成声音授权文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "your_licence_path");
        */
        
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        
        // 获取语音合成授权信息
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            //toPrint("auth success");
            mSpeechSynthesizer.initTts(TtsMode.MIX);
            //mSpeechSynthesizer.speak("百度语音合成示例程序正在运行");
            
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            //toPrint("auth failed errorMsg=" + errorMsg);
        }

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        //toPrint("loadEnglishModel result=" + result);

        //打印引擎信息和model基本信息
        //printEngineInfo();
        
        int tt = this.mSpeechSynthesizer.speak("您好");
        
    }    
    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }
    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }   
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  



	@Override
	public void onError(String arg0, SpeechError arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSpeechFinish(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSpeechProgressChanged(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSpeechStart(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSynthesizeFinish(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSynthesizeStart(String arg0) {
		// TODO Auto-generated method stub
		
	}
    ///////////////////// TTS END

}
