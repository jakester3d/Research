package com.filedance.research;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemSelectedListener {
 
	//Constants
    private final static int BUTTON_COUNT = 8;
    
	//Reference our custom view
    private StackView mStackView;
    private int mLayerCurrent;
    private ImageView[] mImageButtons = new ImageView[BUTTON_COUNT];
    private String mPhrase;
    
    //Controls
    private Spinner mPhraseSpinner;
    private EditText mPhraseEditText;
    
    private ShareActionProvider mShareActionProvider;
    
    private int mBuildVersion = new Build.VERSION().SDK_INT;
    
    private String mImageFilePath;
    private File mImageFile;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Inflate our UI from its XML layout description.
		setContentView(R.layout.activity_main);
        
		//Stack View
        mStackView = (StackView) findViewById(R.id.main_stack_view);
        
        //Image Buttons
        mImageButtons[0] = (ImageView) findViewById(R.id.main_button00);
        mImageButtons[1] = (ImageView) findViewById(R.id.main_button01);
        mImageButtons[2] = (ImageView) findViewById(R.id.main_button02);
        mImageButtons[3] = (ImageView) findViewById(R.id.main_button03);
        mImageButtons[4] = (ImageView) findViewById(R.id.main_button04);
        mImageButtons[5] = (ImageView) findViewById(R.id.main_button05);
        mImageButtons[6] = (ImageView) findViewById(R.id.main_button06);
        mImageButtons[7] = (ImageView) findViewById(R.id.main_button07);
 
        //Set default radio button choice
        RadioButton radioButtonDefault = (RadioButton) findViewById(R.id.main_radio0);
        radioButtonDefault.setChecked(true);

        //Fill buttons with image choices
        updateImageButtons();
        
        //Phrase
        mPhraseSpinner = (Spinner) findViewById(R.id.main_spinner_phrase);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.phrases_array, android.R.layout.simple_spinner_item);  // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // Apply the adapter to the spinner
        mPhraseSpinner.setAdapter(adapter);
        
        mPhraseSpinner.setOnItemSelectedListener(this);  //Setup listener
        
        //Phrase Edit
        mPhraseEditText = (EditText) findViewById(R.id.main_edit_phrase);

        
     //   processRequestingIntent();  //Look at the Intent that started this activity
        
	}
	
	//Clicked Radio Button
	public void onRadioButtonClicked(View view) {
		mLayerCurrent = Integer.decode(view.getTag().toString());  //Get the layer # from the tag
		updateImageButtons();
	}
	
	//Update Image Buttons
	private void updateImageButtons() {
    	for (int choice = 0; choice < StackView.CHOICE_COUNT; choice++) {
    		mImageButtons[choice].setImageDrawable(mStackView.getImage(mLayerCurrent, choice));
    	}
	}
	
	//Clicked Image Button
	public void onImageViewClicked(View view) {
	    mStackView.setImage(mLayerCurrent, Integer.decode(view.getTag().toString()));  //Get the choice # from the tag
	}
	
	//Clicked Update Button
	public void onUpdateButtonClicked(View view) {
		mPhrase = mPhraseEditText.getText().toString();
	    mStackView.setPhraseText(mPhrase);
//		Toast.makeText(this, "Update!", Toast.LENGTH_LONG).show();
	}
	
	//Phrase Spinner Item selected
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    mPhrase =  (String) parent.getItemAtPosition(pos);
	    if (mPhrase.equals(" ")) {
	    	
	    	
	    }
	    else {
	    	mPhraseEditText.setText(mPhrase);	    	
	    	mStackView.setPhraseText(mPhrase);
	    }

	//f (mPhrase.equals("(custom)")) {
	//		mPhraseEditText.setVisibility(View.VISIBLE);
	//		mPhrase = mPhraseEditText.getText().toString();
	//	}

	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(getApplicationContext(), "Nothing Selected!", Toast.LENGTH_LONG).show();
	}
	
	
	//Action bar (menu) option chosen
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_settings:
            break;

        case R.id.menu_share:   //This won't fire because it shows a submenu instead of this call back!!!!!!!!!!!
            break;
            
        case R.id.menu_send_sms:
            MessageUtils.sendSMS("9192724385", "Hey sexy!");
            break;
            
        case R.id.menu_send_web:
        	MessageUtils.sendWebPage("9192724385", "www.android.com");
            break;
            
        case R.id.menu_send_map:
    		MessageUtils.sendMapAddress("9192724385", "617 Torry Hill Ct, Wake Forest, NC");
            break;
        }	
    return super.onOptionsItemSelected(item);
	}
	
	
	
	
	//Clicked Sex Button
	public void onSexButtonClicked(View view) {
	//	Toast.makeText(this, "sex!", Toast.LENGTH_SHORT).show();

		
//		Launch launch = new Launch();
	//	startActivity(launch.webPage(getApplicationContext(), "www.android.com"));
	//	startActivity(launch.callPhoneNumber(getApplicationContext(), "19192724605"));
	//	startActivity(launch.mapAddress(getApplicationContext(), "617 Torry Hill Ct","Wake Forest","NC"));
	//	startActivity(launch.sendEmail(getApplicationContext(), "bryan_aldrich@hotmail.com", "Email subject", "Email message text", ""));
	//	startActivityForResult(launch.takePicture(getApplicationContext()), 0);	
	//	returnResultIntent();
		
		
//		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//		sharingIntent.setType("text/plain");
//		String shareBody = "Here is the share content body";
//		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//		startActivity(Intent.createChooser(sharingIntent, "Share via"));

		
    	saveImage();
		
	}

	
	public void saveImage() {
//		String strFolderName = "Pictures";
//		String strFileName = "test4";
//		String strFileExt = "PNG";
//		
//		Bitmap bitmap;
//		
//		//Get a bitmap from a resource
//	//	bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.background03);
//		
//		//Get a bitmap from a View
//		bitmap = getBitmapFromView(mStackView);
//		
//		String folder = "";
//		
//		//External storage location
//		if (isExternalStorageWritable()) {
//			folder = Environment.getExternalStorageDirectory().toString() + "/" + strFolderName;
//		}
//			
//		//Private, internal storage just for this app (will be deleted when app is uninstalled)
//      //folder = this.getFilesDir().toString();
//	    
//	    OutputStream outStream = null;
//	    mImageFile = new File(folder, strFileName + "." + strFileExt);
//	    try {
//			outStream = new FileOutputStream(mImageFile);
//			if (strFileExt.equals("PNG")) {
//				bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//			}
//			else if (strFileExt.equals("JPEG")) {
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//			}
//			
//		//	mImageFilePath = mImageFile.getAbsolutePath();
//			
//		//	Toast.makeText(this, "Image saved to=" + mImageFilePath, Toast.LENGTH_LONG).show();
//
//	    }
//	    catch(Exception e) {
//			Toast.makeText(this, "saveImage() Error", Toast.LENGTH_LONG).show();
//			mImageFilePath = "";
//			return;
//	    }
//	    
//	    try {
//	    	mImageFilePath = MediaStore.Images.Media.insertImage(getContentResolver(), mImageFile.getAbsolutePath(), mImageFile.getName(), mImageFile.getName());
//	  //  	Toast.makeText(this, "Save to Media Store=" + mImageFilePath, Toast.LENGTH_LONG).show();
//	    }
//	    catch (Exception e) {
//			Toast.makeText(this, "MediaStore Error", Toast.LENGTH_LONG).show();
//			return;
//        }
	    

		
		
		
		
    	mImageFilePath = MediaStore.Images.Media.insertImage(getContentResolver(), getBitmapFromView(mStackView), "eCupcake", "A Cupcake for you!");
	    
//    	Uri uri =null;
//    	uri = addToTouchActiveAlbum(this,"eCupcake Title", mImageFilePath);
//		Toast.makeText(this, "Image uri=" + uri.toString(), Toast.LENGTH_LONG).show();
	    
		//Update the share menu intent
		updateShareIntent();

		
//		bitmap = ((BitmapDrawable)draw).getBitmap();
		
		//Image from a web site:
//		String str_url = "http:www.image.png"
//		String name = this.getString(str_url);
//		URL url_value = new URL(name);
//		ImageView profile = (ImageView)v.findViewById(R.id.vdo_icon);
//		if (profile != null) {
//		    Bitmap mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
//		    profile.setImageBitmap(mIcon1);
//		}
	}
	
	public static Uri addToTouchActiveAlbum( Context context, String title, String filePath ) {
	    ContentValues values = new ContentValues(); 
	    values.put( Media.TITLE, title ); 
	    values.put( Images.Media.DATE_TAKEN, System.currentTimeMillis() );
	    values.put( Images.Media.BUCKET_ID, filePath.hashCode() );
	    values.put( Images.Media.BUCKET_DISPLAY_NAME, "eCupcake Bucket" );

	    values.put( Images.Media.MIME_TYPE, "image/png" );
	    values.put( Media.DESCRIPTION, context.getResources().getString( R.string.app_name ) ); 
	    values.put( MediaStore.MediaColumns.DATA, filePath );
	    Uri uri = context.getContentResolver().insert( Media.EXTERNAL_CONTENT_URI , values );

	    return uri;
	}
	
	
	public static Bitmap getBitmapFromView(View view) {
	    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable =view.getBackground();
	    if (bgDrawable!=null) 
	        bgDrawable.draw(canvas);
	    else 
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    return returnedBitmap;
	}

	
	//Create menus
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mBuildVersion >= 14) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.activity_main, menu);
			
		    // Get the menu item.
		    MenuItem menuItem = menu.findItem(R.id.menu_share);
		    
		    // Get the provider and hold onto it to set/change the share intent.
		    mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
	
			Launch launch = new Launch();
			mShareActionProvider.setShareIntent(launch.shareImage(this, "", "Email subject", "Email message text", ""));
	//	    updateShareIntent();
		}
		else {
			// Inflate the menu; use old compatible menu file
			getMenuInflater().inflate(R.menu.activity_main_compatible, menu);
		}

			
		
		return true;
	}
	
	@SuppressLint("NewApi")
	private void updateShareIntent() {
		if (mBuildVersion >= 14) {
		    // Attach an intent to this ShareActionProvider.  You can update this at any time,
		    // like when the user selects a new piece of data they might like to share.
			Launch launch = new Launch();
			Intent intent;
			
			intent = launch.shareImage(this, "", "A cupcake for you!", "Here is the image: " + mImageFilePath, mImageFilePath);
			
			
			
	//		Bitmap bitmap;
	//		bitmap = getBitmapFromView(mStackView);
	//		intent = launch.shareBitmap(this, "", "A cupcake for you!", "Here is the image: " + mImageFilePath, bitmap);
	//		intent = launch.shareImage(this, "", "A cupcake for you!", "Here is the image: " + mImageFile.getAbsolutePath(), mImageFile.getAbsolutePath());  //This doesn't work!!!!!!!
			
//			String subject = "A cupcake man!";
//			String body = "File Path: " + mImageFile.getAbsolutePath();
//			
//			Intent intent = new Intent(Intent.ACTION_SEND);
//			intent.setType("text/plain");
//			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//			intent.putExtra(Intent.EXTRA_TEXT, body);
//			Uri uri = Uri.fromFile(mImageFile);
//			intent.putExtra(Intent.EXTRA_STREAM, uri);
			
			mShareActionProvider.setShareIntent(intent);
			
	//		Toast.makeText(this, "Share intent updated: " + mImageFilePath, Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(this, "Android version " + mBuildVersion + " does not support this command!", Toast.LENGTH_LONG).show();
		}
			
	}
	

	//Look at the Intent that started with Activity
	private void processRequestingIntent() {
		//This routine crashes!!!!!!!!!!!
		
        // Get the intent that started this activity
        Intent requestingIntent = getIntent();
        Uri data = requestingIntent.getData();
        // Figure out what to do based on the intent type
        if (requestingIntent.getType().indexOf("image/") != -1) {
        	// Handle intents with image data ...   
        }
        else if (requestingIntent.getType().equals("text/plain")) {
        	// Handle intents with text ...
        }
	}
	
	//Return image via Intent
	private void returnResultIntent() {
		//this routine crashes too!
		
        // Create intent to deliver some kind of result data
        Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse(mImageFilePath));
        setResult(Activity.RESULT_OK, result);
	}
	
	@Override
	public void finish() {
	//	returnResultIntent();
		
		super.finish();
	}
	
	//A result has been received from a previous Intent request for result
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == 0) {
			String result = data.toUri(Intent.URI_INTENT_SCHEME).toString();
			Toast.makeText(this, "An eCupcake result has been returned: " + result, Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "An eCupcake result has been returned: Error!", Toast.LENGTH_LONG).show();
		}
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	// Create a folder in the external user's public Pictures folder
	public File getAlbumStorageDir(String albumName) {
	    // Get the directory for the user's public pictures directory. 
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES), albumName);
	    if (!file.mkdirs()) {
			Toast.makeText(this, "Directory no created!", Toast.LENGTH_LONG).show();
	    }
	    return file;
	}
	
	//Create a folder on external storage, that is stil private to this app (and will be removed upon uninstall)
	public File getAlbumStorageDir(Context context, String albumName) {
	    // Get the directory for the app's private pictures directory. 
	    File file = new File(context.getExternalFilesDir(
	            Environment.DIRECTORY_PICTURES), albumName);
	    if (!file.mkdirs()) {
	    	Toast.makeText(this, "Directory no created!", Toast.LENGTH_LONG).show();
	    }
	    return file;
	}

	
	private void log(String text) {	Log.d("eCupcake", text);	}
	
}

