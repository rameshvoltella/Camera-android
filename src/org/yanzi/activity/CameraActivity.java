package org.yanzi.activity;

import android.R.string;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.res.Resources;

import android.graphics.Bitmap;

import android.graphics.Bitmap.CompressFormat;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

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

import org.yanzi.playcamera.R;

import org.yanzi.util.DisplayUtil;

import com.inksci.function.InkFunction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class CameraActivity extends Activity implements CamOpenOverCallback {
	boolean new_visit;
	ImageView master_control;
	
    static boolean get_bitmap;
    private static final String TAG = "yanzi";

    //private EditText editText;
    private static final int MSG_OK = 1;
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
    String app_path_name = "InkerRobot";
    String up_image_file = "zx-01.jpg"; // initial value
    private HttpParams httpParams;
    private HttpClient httpClient;


    private Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
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

               

                case MSG_OK:
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
        
        logo_page();
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
                           
                            http_use();
                        }
                    }
                }.start();
        }
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

    private void http_use() {
        /*
         * =====================================
        curl -X POST "https://api-cn.faceplusplus.com/facepp/v3/detect" \
        -F "api_key=Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN" \
        -F "api_secret=EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f" \
        -F "image_url=http://inksci.com/w/tmp/sg-67698.jpg" \
        -F "return_landmark=1" \
        -F "return_attributes=gender,age"*/
        try {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("api_key",
                    "Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN"));
            params.add(new BasicNameValuePair("api_secret",
                    "EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f"));
            params.add(new BasicNameValuePair("image_url",
                    "http://inksci.com/w/tmp/sg-67698.jpg"));
            params.add(new BasicNameValuePair("return_landmark", "1"));
            //params.add(new BasicNameValuePair("return_attributes", "gender,age,smile,glass"));

            Map params2 = new HashMap();

            params2.put("hl", "zh-CN");

            params2.put("source", "hp");

            params2.put("q", "haha");

            params2.put("aq", "f");

            params2.put("aqi", "g10");

            params2.put("aql", "");

            params2.put("oq", "");

            String url2 = "http://www.google.cn/search";

            String url = "http://inksci.com/~tstbox/upload/up-index.php";
            //"https://api-cn.faceplusplus.com/facepp/v3/detect"; //"http://www.inksci.com/app/th-week.php";  
            getHttpClient();

            //??ui ???????
            Message msg = new Message(); //????
            msg.what = MSG_OK;
            msg.obj = doPost(url, params); //????
            handler.sendMessage(msg); //?handler???????
        } catch (Exception e) {
            e.printStackTrace();

            //?????????
            //Toast.makeText(this, "?????????", 0).show();
            Message msg = new Message();
            msg.what = EXCEPTION;
            handler.sendMessage(msg);
        }
    }

    public String doGet(String url, Map params) {
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

    public String doPost(String url, List<NameValuePair> params) {
        /* ??HTTPPost?? */
        HttpPost httpRequest = new HttpPost(
                "https://api-cn.faceplusplus.com/facepp/v3/detect");

        String strResult = "doPostError";

        try {
            /* ??????????? */
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bm = CameraInterface.getInstance().get_ok_bitmap();
            //BitmapFactory.decodeFile("/storage/sdcard1/InkerRobot/"+up_image_file);
            bm.compress(CompressFormat.JPEG, 60, bos);

            ContentBody mimePart = new ByteArrayBody(bos.toByteArray(),
                    "image/jpeg");

            // ????????????faces??
            FileBody fileBody = new FileBody(new File(
                        "/storage/sdcard1/InkerRobot/" + up_image_file),
                    "image/jpeg");
            StringBody stringBody = new StringBody("description of file");
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("image_file", mimePart);
            //entity.addPart("image_url", new StringBody("http://inksci.com/w/tmp/sg-67698.jpg")); 
            entity.addPart("api_key",
                new StringBody("Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN"));
            entity.addPart("api_secret",
                new StringBody("EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f"));
            entity.addPart("return_landmark", new StringBody("1"));
            entity.addPart("return_attributes", new StringBody("gender,age,smiling,glass"));

            httpRequest.setEntity(entity);

            //httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
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


}
