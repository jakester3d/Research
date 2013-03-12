package com.filedance.research;
 
//		This class allows our app to receive SMS messages.

//		These permissions have to be added to the AndroidManifest.xml file to receive text directly, without using a texting app.
//
//			<uses-permission android:name="android.permission.RECEIVE_SMS"/>
//
//
//		This receiver has to be added as well to tell Android we want to be notified every time a text is received.
//		The priority=100 says we want to be notified BEFORE every other app, that allows us time to run abort a message so the other apps never get it.
//
//			<receiver android:name=".SMSReceiver">
//				<intent-filter android:priority="100">
//					<action android:name="android.provider.Telephony.SMS_RECEIVED" />
//				</intent-filter>
//			</receiver>
//


import java.net.URISyntaxException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	
	//When a text is received, this routine will fire before the rest of the phone because the 
	@Override
	public void onReceive(Context context, Intent intent) {
		//---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "SMS from: ";
		if (bundle != null)	{
			//---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			String phoneNumber;

			for (int i=0; i<msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				if (i==0) {
					//---get the sender address/phone number---
					str += msgs[i].getOriginatingAddress();
					str += ": ";
					phoneNumber = msgs[i].getOriginatingAddress();
					log("phoneNumber=" + phoneNumber);
				}
				//---get the message body---
				str += msgs[i].getMessageBody().toString();
		//		body[i] = msgs[i].getMessageBody().toString();
				log("body[" + i + "]=" + msgs[i].getMessageBody().toString());
			}
			//---display the new SMS message---
			Toast.makeText(context, str, Toast.LENGTH_LONG).show();
			log(str);
			
			String body = null;
			body = msgs[0].getMessageBody();
//			Bundle extras;
//			extras = msgs[0].
			log("onReceive() key=" + body.substring(0, 7));
			if (body.substring(0, 7).equals("launch:")) {
				this.abortBroadcast();
				log("abortBroadcast!");
				int launchCode;
				launchCode = Integer.parseInt(body.substring(7, 9));
				log("onReceive() launchCode=" + launchCode);
				Launch launch = new Launch();
				switch (launchCode) {
				case 1:
					String webPage;
					webPage = body.substring(10);
					log("onReceive() webPage=" + webPage);
					
//					//---send a broadcast intent to update the SMS received in the activity---
//					Intent broadcastIntent = new Intent();
//					broadcastIntent.setAction(“SMS_RECEIVED_ACTION”);
//					broadcastIntent.putExtra(“sms”, str);
//					context.sendBroadcast(broadcastIntent);
					
					Intent webIntent = new Intent();
					webIntent = launch.webPage(context, webPage);
					webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //this is required outside of an Activity context
					context.startActivity(webIntent);
					break;
				case 2:
					String address;
					address = body.substring(10);
					log("onReceive() address=" + address);
					
					Intent mapIntent = new Intent();
					mapIntent = launch.mapAddress(context, address);
					mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //this is required outside of an Activity context
					context.startActivity(mapIntent);
					break;
				}
//				Intent newIntent;
//				Uri uri;
//				uri = Uri.parse(body);
//				try {
//					newIntent = Intent.parseUri(body, Intent.URI_INTENT_SCHEME);
//					log("Received Intent=" + newIntent.toString());
//					
//					context.startActivity(newIntent);
//					
//				} catch (URISyntaxException e) {
//					log("URISyntaxException!");
//				}

			}
			
		}

	}
	
	private void log(String text) {	Log.d("eCupcake", text);	}
	
}


