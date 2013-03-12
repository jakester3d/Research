package com.filedance.research;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Launch {
	
	
	
	
	/**********
	 * Open a web page!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.webPage(getApplicationContext(), "www.android.com"));
	 */
	public Intent webPage(Context context, String webAddress) {
	//	Toast.makeText(context, "Launching web page: " + webAddress, Toast.LENGTH_LONG).show();
		log("webPage() webAddress=" + webAddress);
		
		Uri webpage = Uri.parse("http://" + webAddress);  //Create URI to hold the data
		Intent intent = new Intent(Intent.ACTION_VIEW, webpage);  //Create the Intent with Action and Data
		
		if (isFound(context, intent)) {
			return intent;
		}
		else {
			return null;
		}
	}
	
	
	/**********
	 * Call a telephone number!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.callPhoneNumber(getApplicationContext(), "19192724605"));
	 */
	public Intent callPhoneNumber(Context context, String phoneNumber) {
		Toast.makeText(context, "Calling Telephone Number: " + phoneNumber, Toast.LENGTH_LONG).show();
		
		Uri number = Uri.parse("tel:" + phoneNumber);
		Intent intent = new Intent(Intent.ACTION_DIAL, number);
		
		if (isFound(context, intent)) {
			return intent;
		}
		else {
			return null;
		}
	}

	/**********
	 * Map an address!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.mapAddress(getApplicationContext(), "1600 Amphitheatre Parkway", "Mountain View", "CA"));
	 */
	public Intent mapAddress(Context context, String street, String city, String state) {
		String address = street + ", " + city + ", " + state;
		Toast.makeText(context, "Mapping this address: " + address, Toast.LENGTH_LONG).show();
		
		// Map point based on address
		Uri location = Uri.parse("geo:0,0?q=" + address);
		// Or map point based on latitude/longitude
		// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
		// z param is zoom level
		Intent intent = new Intent(Intent.ACTION_VIEW, location);
		
		if (isFound(context, intent)) {
			return intent;
		}
		else {
			return null;
		}
	}
	public Intent mapAddress(Context context, String address) {
		Toast.makeText(context, "Mapping this address: " + address, Toast.LENGTH_LONG).show();
		
		// Map point based on address
		Uri location = Uri.parse("geo:0,0?q=" + address);
		// Or map point based on latitude/longitude
		// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
		// z param is zoom level
		Intent intent = new Intent(Intent.ACTION_VIEW, location);
		
		if (isFound(context, intent)) {
			return intent;
		}
		else {
			return null;
		}
	}
	
	/**********
	 * Send an email with an attachment!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.sendEmail(getApplicationContext(), "jon@example.com", "Email subject", "Email message text", "//path/to/email/attachment"));
	 */
	public Intent sendEmail(Context context, String toRecipients, String subject, String body, String attachmentPath) {
		Toast.makeText(context, "Sending email to: " + toRecipients, Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		// The intent does not have a URI, so declare the "text/plain" MIME type  (the SENDTO does us a URI, but I think it only does simple text, not images or attachments
		intent.setType(HTTP.PLAIN_TEXT_TYPE);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {toRecipients});	// recipients
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);
		if (!attachmentPath.isEmpty()) {
			intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content:" + attachmentPath));
			// You can also attach multiple items by passing an ArrayList of Uris			
		}
		
		if (isFound(context, intent)) {
			return intent;
		}
		else {
			return null;
		}
	}
	
	
	/**********
	 * Share an image!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.shareImage(getApplicationContext(), "jon@example.com", "Email subject", "Email message text", "//path/to/email/attachment"));
	 */
	public Intent shareImage(Context context, String toRecipients, String subject, String body, String attachmentPath) {
	//	Toast.makeText(context, "Sending email to: " + toRecipients, Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(Intent.ACTION_SEND);
	//	intent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");  //android mms app, still won't attach though!
		// The intent does not have a URI, so declare the "text/plain" MIME type  (the SENDTO does us a URI, but I think it only does simple text, not images or attachments
	//	intent.setType(HTTP.PLAIN_TEXT_TYPE); //works the same as "text/plain"
		intent.setType("text/plain");
	//	intent.setType("image/png"); 
		if (!toRecipients.isEmpty()) {
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] {toRecipients});	// recipients
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);
		if (!attachmentPath.isEmpty()) {
			intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(attachmentPath));
		//	intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content:/" + attachmentPath));
			// You can also attach multiple items by passing an ArrayList of Uris			
		}

		return intent;
	}
	
	/**********
	 * Share a Bitmap!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.shareImage(getApplicationContext(), "jon@example.com", "Email subject", "Email message text", "//path/to/email/attachment"));
	 */
	public Intent shareBitmap(Context context, String toRecipients, String subject, String body, Bitmap bitmap) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
	//	intent.setType("image/png"); 
		if (!toRecipients.isEmpty()) {
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] {toRecipients});	// recipients
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);
		if (bitmap != null) {
			//Convert bitmap to byte array
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			intent.putExtra("image",byteArray);
		}

		return intent;
	}
	
	/**********
	 * Take a picture with the camera!
	 * 
	 * Sample Calling Code:
	 * 		Launch launch = new Launch();
	 * 		startActivity(launch.takePicture(getApplicationContext(), "//path/to/email/attachment"));
	 */
	public Intent takePicture(Context context) {
		Toast.makeText(context, "Taking a picture!", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		if (isFound(context, intent)) {
			return intent;
		}
		else {
			return null;
		}
	}
	
	private boolean isFound(Context context, Intent intent) {
		// Verify the Intent resolves to an actual Activity
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		boolean isIntentSafe = activities.size() > 0;
		
		return isIntentSafe;
	}
	
	public String intentToText(Intent intent) {
		String text;
		text = "($IntentAction)" + intent.getAction();


		
		return text;
	}
	
	public Intent textToIntent(String text) {
		Intent intent = null;
		
		
		return intent;
	}
	
	private static void log(String text) {	Log.d("eCupcake", text);	}
	
}
