package org.yanzi.activity;

import org.yanzi.camera.CameraInterface;
import org.yanzi.camera.CameraInterface.CamOpenOverCallback;
import org.yanzi.camera.preview.CameraSurfaceView;
import org.yanzi.playcamera.R;
import org.yanzi.util.DisplayUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import android.R.string;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
 
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.params.HttpClientParams;  
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.params.BasicHttpParams;  
import org.apache.http.params.HttpConnectionParams;  
import org.apache.http.params.HttpParams;  
import org.apache.http.params.HttpProtocolParams;  
import org.apache.http.protocol.HTTP;  
import org.apache.http.util.EntityUtils;  
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
 
import android.app.Activity;  
import android.content.DialogInterface;
import android.os.Bundle;  
import android.util.Log;  
import android.widget.EditText;  
import android.widget.Toast;


import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.mime.MultipartEntity;  
import org.apache.http.entity.mime.content.FileBody;  
import org.apache.http.entity.mime.content.StringBody;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;

public class CameraActivity extends Activity implements CamOpenOverCallback {
	private static final String TAG = "yanzi";
	CameraSurfaceView surfaceView = null;
	ImageButton shutterBtn;
	float previewRate = -1f;
	
	String app_path_name="PlayCamera";
	String up_image_file="zx-01.jpg"; // initial value
	

	
	   private HttpParams httpParams;  
	    
	   private HttpClient httpClient; 
	   
	   //private EditText editText;
	 
	   
	   private static final int MSG_OK = 1;
	   private static final int EXCEPTION = 2;
	   //1.在主线程里面声明消息处理器 handler
	   private Handler handler = new Handler(){
	       //处理消息的
	       @Override
	       public void handleMessage(Message msg) {
	    	   writeFileToSD("up.html.txt.ink", (String)msg.obj);
	           switch (msg.what) {
	               case MSG_OK:
	            	   
	            	   Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_SHORT).show();
	            	   
	                   //3.处理消息 运行在主线程
	            	   String txt_data=(String)msg.obj;
	            	   //editText.setText(txt_data);
					   
	            	   // String -> JSON -> display it
				JSONObject jsonObject;
				try {
					//editText.setText("...");
					Toast.makeText(getApplicationContext(), "JSON ...", Toast.LENGTH_SHORT).show();
					
					jsonObject = new JSONObject(txt_data);
					JSONArray jsonArray = jsonObject.getJSONArray("faces"); 
					//Toast.makeText(getApplicationContext(), jsonArray.length()+"", Toast.LENGTH_LONG).show();
					if(jsonArray.length()>0){
					JSONObject jsonObject2 = (JSONObject)jsonArray.opt(0);
					 
					String face_token=jsonObject2.getString("face_token");
					JSONObject attributes = jsonObject2.getJSONObject("attributes");
					String gender = attributes.getJSONObject("gender").getString("value");
					String age = attributes.getJSONObject("age").getString("value");
					//editText.setText("gender: "+gender+", age: "+age+", face_token: "+face_token);
					Toast.makeText(getApplicationContext(), "gender: "+gender+", age: "+age+", face_token: "+face_token, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Notice: jsonArray.length() is 0.", Toast.LENGTH_LONG).show();
							
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                   
	            	      	   
	            	   
	                   System.out.println("76799679");
	                   break;
	               case EXCEPTION:
	                   Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_SHORT).show();
	                   break;
	           }

	           super.handleMessage(msg);
	       }
	   };

	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		

	}
	
	
    @Override  
    protected void onStart() {  
        super.onStart();  
        Log.v(TAG, "protected void onStart()");  
        Thread openThread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
			}
		};
		Toast.makeText(getApplicationContext(), "Camera: Welcome!", Toast.LENGTH_SHORT).show();
		openThread.start();
		setContentView(R.layout.activity_camera);
		initUI();
		initViewParams();
		
		shutterBtn.setOnClickListener(new BtnListeners());
		
		
		ImageButton image1 = (ImageButton) findViewById(R.id.btn_shutter);
        Bitmap bitmap = getLoacalBitmap("/storage/sdcard1/123.jpg"); //从本地取图片
        image1.setImageBitmap(bitmap);	//设置Bitmap
        
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
		
		
		/*
         * 
         * add()方法的四个参数，依次是：
         * 
         * 1、组别，如果不分组的话就写Menu.NONE,
         * 
         * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
         * 
         * 3、顺序，那个菜单现在在前面由这个参数的大小决定
         * 
         * 4、文本，菜单的显示文本
         */

        menu.add(Menu.NONE, Menu.FIRST + 1, 5, "删除").setIcon(

        android.R.drawable.ic_menu_delete);

        // setIcon()方法为菜单设置图标，这里使用的是系统自带的图标，同学们留意一下,以

        // android.R开头的资源是系统提供的，我们自己提供的资源是以R开头的

        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "保存").setIcon(

        android.R.drawable.ic_menu_edit);

        menu.add(Menu.NONE, Menu.FIRST + 3, 6, "帮助").setIcon(

        android.R.drawable.ic_menu_help);

        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "添加").setIcon(

        android.R.drawable.ic_menu_add);

        menu.add(Menu.NONE, Menu.FIRST + 5, 4, "详细").setIcon(

        android.R.drawable.ic_menu_info_details);

        menu.add(Menu.NONE, Menu.FIRST + 6, 3, "[菜单]发送").setIcon(

        android.R.drawable.ic_menu_send);
		return true;
	}


	@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {

	        case Menu.FIRST + 1:

	            Toast.makeText(this, "删除菜单被点击了", Toast.LENGTH_LONG).show();

	            break;

	        case Menu.FIRST + 2:

	            Toast.makeText(this, "保存菜单被点击了", Toast.LENGTH_LONG).show();
	        
	        	setContentView(R.layout.activity_camera);
	        	initUI();
	    		initViewParams();

	            break;

	        case Menu.FIRST + 3:

	            Toast.makeText(this, "帮助菜单被点击了", Toast.LENGTH_LONG).show();

	        final EditText editText = new EditText(this);  
	        new AlertDialog.Builder(this).setTitle("请输入").setView(editText).setPositiveButton("确定",   
	        new DialogInterface.OnClickListener() {                
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                // TODO Auto-generated method stub  
	                Toast.makeText(CameraActivity.this, "您输入的内容是："+editText.getText(), Toast.LENGTH_SHORT).show();
	                up_image_file=editText.getText().toString();
	            }  
	        }).setNegativeButton("取消", null).show();
	        
	        
	            break;

	        case Menu.FIRST + 4:

	            Toast.makeText(this, "添加菜单被点击了", Toast.LENGTH_LONG).show();
	        
	        setContentView(R.layout.view_02);
	        
			Button btn = (Button) findViewById(R.id.button1);
			btn.setOnClickListener(new OnClickListener() {
				 
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "222222222", Toast.LENGTH_SHORT).show();
			    writeFileToSD("note.txt", "添加菜单被点击了");
			}
			
			});
			
			final EditText et=(EditText) findViewById(R.id.editText1);
			
			Button btn02 = (Button) findViewById(R.id.button2);
			btn02.setOnClickListener(new OnClickListener() {
				 
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_SHORT).show();
				up_image_file=et.getText().toString();
			}
			
			});
			
	            break;

	        case Menu.FIRST + 5:

	            Toast.makeText(this, "详细菜单被点击了", Toast.LENGTH_LONG).show();

	            break;

	        case Menu.FIRST + 6:

	            Toast.makeText(this, "发送菜单被点击了", Toast.LENGTH_LONG).show();

	            break;

	        }

	        return false;

	    }
	private void initUI(){
		surfaceView = (CameraSurfaceView)findViewById(R.id.camera_surfaceview);
		shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
	}
	private void initViewParams(){
		LayoutParams params = surfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		params.width = p.x;
		params.height = p.y;
		previewRate = DisplayUtil.getScreenRate(this); //默认全屏的比例预览
		surfaceView.setLayoutParams(params);

		//手动设置拍照ImageButton的大小为120dip×120dip,原图片大小是64×64
		LayoutParams p2 = shutterBtn.getLayoutParams();
		p2.width = DisplayUtil.dip2px(this, 80);
		p2.height = DisplayUtil.dip2px(this, 80);;		
		shutterBtn.setLayoutParams(p2);	

	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		SurfaceHolder holder = surfaceView.getSurfaceHolder();
		CameraInterface.getInstance().doStartPreview(holder, previewRate);
	}
	private class BtnListeners implements OnClickListener{

		@Override
		public void onClick(View v) {
			new Thread(){
	            public void run() {
	                http_use();
	            }
	        }.start();
			
			switch(v.getId()){
			case R.id.btn_shutter:
				CameraInterface.getInstance().doTakePicture();
				break;
			default:break;
			}


		}

	}
	
    private void writeFileToSD(String fileName, String s) {  
        String sdStatus = Environment.getExternalStorageState();  
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {  
            Log.d("TestFile", "SD card is not avaiable/writeable right now.");  
            return;  
        }  
        try {  
            String pathName="/storage/sdcard1/"+app_path_name+"/";  
            //String fileName="file.txt";  
            File path = new File(pathName);  
            File file = new File(pathName + fileName);  
            if( !path.exists()) {  
                Log.d("TestFile", "Create the path:" + pathName);  
                path.mkdir();  
            }  
            if( !file.exists()) {  
                Log.d("TestFile", "Create the file:" + fileName);  
                file.createNewFile();  
            }  
            FileOutputStream stream = new FileOutputStream(file);  
            //String s = "this is a test string writing to file.";  
            byte[] buf = s.getBytes();  
            stream.write(buf);            
            stream.close();  
              
        } catch(Exception e) {  
            Log.e("TestFile", "Error on writeFilToSD.");  
            e.printStackTrace();  
        }  
    }  	
	private void http_use() {
		/*
		 * =====================================
		curl -X POST "https://api.megvii.com/facepp/v3/detect" \
		-F "api_key=Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN" \
		-F "api_secret=EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f" \
		-F "image_url=http://inksci.com/w/tmp/sg-67698.jpg" \
		-F "return_landmark=1" \
		-F "return_attributes=gender,age"*/		
				
				
				
				
				try{
				// TODO Auto-generated method stub
		        List<NameValuePair> params = new ArrayList<NameValuePair>();  
		        params.add(new BasicNameValuePair("api_key", "Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN"));  
		        params.add(new BasicNameValuePair("api_secret", "EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f"));  
		        params.add(new BasicNameValuePair("image_url", "http://inksci.com/w/tmp/sg-67698.jpg"));  
		        params.add(new BasicNameValuePair("return_landmark", "1"));  
		        params.add(new BasicNameValuePair("return_attributes", "gender,age"));   
  
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
        		//"https://api.megvii.com/facepp/v3/detect"; //"http://www.inksci.com/app/th-week.php";  
  
        getHttpClient();  
    
        //更新ui 不能写在子线程
        Message msg = new Message();//声明消息
        msg.what = MSG_OK;
        msg.obj = doPost(url, params);//设置数据
        handler.sendMessage(msg);//让handler帮我们发送数据
  		
	} catch (Exception e) {
            e.printStackTrace();
            //土司不能写在子线程
            //Toast.makeText(this, "发生异常，请求失败", 0).show();
            Message msg = new Message();
            msg.what = EXCEPTION;
            handler.sendMessage(msg);
    }
		
	};
	
    public String doGet(String url, Map params) {  
  	  
        /* 建立HTTPGet对象 */  
  
        String paramStr = "";  
  
        Iterator iter = params.entrySet().iterator();  
        while (iter.hasNext()) {  
            Map.Entry entry = (Map.Entry) iter.next();  
            Object key = entry.getKey();  
            Object val = entry.getValue();  
            paramStr += paramStr = "&" + key + "=" + val;  
        }  
  
        if (!paramStr.equals("")) {  
            paramStr = paramStr.replaceFirst("&", "?");  
            url += paramStr;  
        }  
        HttpGet httpRequest = new HttpGet(url);  
  
        String strResult = "doGetError";  
  
        try {  
  
            /* 发送请求并等待响应 */  
            HttpResponse httpResponse = httpClient.execute(httpRequest);  
            /* 若状态码为200 ok */  
            if (httpResponse.getStatusLine().getStatusCode() == 200) {  
                /* 读返回数据 */  
                strResult = EntityUtils.toString(httpResponse.getEntity());  
  
            } else {  
                strResult = "Error Response: "  
                        + httpResponse.getStatusLine().toString();  
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
  
        /* 建立HTTPPost对象 */  
        HttpPost httpRequest = new HttpPost("https://api.megvii.com/facepp/v3/detect");  
  
        String strResult = "doPostError";  
  
        try {  
            /* 添加请求参数到请求对象 */  
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	Bitmap bm = BitmapFactory.decodeFile("/storage/sdcard1/PlayCamera/"+up_image_file);
        	bm.compress(CompressFormat.JPEG, 60, bos);
        	
        	ContentBody mimePart = new ByteArrayBody(bos.toByteArray(), "image/jpeg");        	
        	// 头像不能颠倒，否则返回的faces为空
        	FileBody fileBody = new FileBody(new File("/storage/sdcard1/PlayCamera/"+up_image_file), "image/jpeg");
        	StringBody stringBody = new StringBody("description of file");
        	MultipartEntity entity = new MultipartEntity(); 
            entity.addPart("image_file", mimePart);
        	//entity.addPart("image_url", new StringBody("http://inksci.com/w/tmp/sg-67698.jpg")); 
        	entity.addPart("api_key", new StringBody("Iwe59oTUN5GFG39IPUQVbOJ7iCA_hmaN")); 
            entity.addPart("api_secret", new StringBody("EzzLLQB8wFvFObPEVRjYb0S-_UnUZf2f")); 
            entity.addPart("return_landmark", new StringBody("1")); 
            entity.addPart("return_attributes", new StringBody("gender,age"));
            
            httpRequest.setEntity(entity); 
            //httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
            /* 发送请求并等待响应 */  
            HttpResponse httpResponse = httpClient.execute(httpRequest);  
            /* 若状态码为200 ok */  
            if (httpResponse.getStatusLine().getStatusCode() == 200) {  
                /* 读返回数据 */  
                strResult = EntityUtils.toString(httpResponse.getEntity());  
  
            } else {  
                strResult = "Error Response: "  
                        + httpResponse.getStatusLine().toString();  
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
  
        // 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）  
  
        this.httpParams = new BasicHttpParams();  
  
        // 设置连接超时和 Socket 超时，以及 Socket 缓存大小  
  
        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  
  
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);  
  
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);  
  
        // 设置重定向，缺省为 true  
  
        HttpClientParams.setRedirecting(httpParams, true);  
  
        // 设置 user agent  
  
        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
        HttpProtocolParams.setUserAgent(httpParams, userAgent);  
  
        // 创建一个 HttpClient 实例  
  
        // 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient  
  
        // 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient  
  
        httpClient = new DefaultHttpClient(httpParams);  
  
        return httpClient;  
    }  


}
