package com.tacoid.pweekmini;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;
import com.tacoid.pweek.IActivityRequestHandler;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class PweekAndroid extends AndroidApplication implements IActivityRequestHandler, AdListener
{	
	private static AdView adView;

	private final static int PORTRAIT_ADS = 3;
	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;

	static protected Handler handler = new Handler()
	{
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case SHOW_ADS:
			{
				adView.setVisibility(View.VISIBLE);
				break;
			}
			case HIDE_ADS:
			{
				adView.setVisibility(View.GONE);
				break;
			}
			}
		}
	};

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Cr�ation du layout
		RelativeLayout layout = new RelativeLayout(this);

		// Fait ce que "initialize" est sens� faire
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useGL20 = false;

		// Cr�ation de la vue libGdx
		PweekMini.setHandler(this);
		View gameView = initializeForView(PweekMini.getInstance(), config);

		// Cr�ation de la vu adMob
		adView = new AdView(this, AdSize.BANNER, "a150a3f124cd8c4"); // Put in your secret key here
		adView.loadAd(new AdRequest());
		adView.setAdListener(this);


		// Ajout de la vu libGdx au layout
		layout.addView(gameView);     

		// Ajout de la vu adMob au layout
		RelativeLayout.LayoutParams adParams = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		adView.setLayoutParams(adParams);

		layout.addView(adView, adParams);

		// Hook it all up
		setContentView(layout);
	}

	protected void onStart() {
		super.onStart();
		TrackingManager.setTrackerType(TrackerType.GOOGLE_ANALYTICS);
		EasyTracker.getInstance().activityStart(this);
	}
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this); 
	}

	@Override
	public void showAds(boolean show) {
		// always show ads.
		handler.sendEmptyMessage(SHOW_ADS);
	}

	@Override
	public void setPortrait(boolean isPortrait) {
		handler.sendEmptyMessage(PORTRAIT_ADS);
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		TrackingManager.getTracker().trackEvent("Ads","adMob", "onDismissScreen", null);	
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		TrackingManager.getTracker().trackEvent("Ads","adMob", "onFailedToReceiveAd", null);
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		TrackingManager.getTracker().trackEvent("Ads","adMob", "onLeaveApplication", null);
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		TrackingManager.getTracker().trackEvent("Ads","adMob", "onPresentScreen", null);
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		TrackingManager.getTracker().trackEvent("Ads","adMob", "onReceiveAd", null);

	}

	@Override
	public int getVolume() {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int vol = audio.getStreamVolume(AudioManager.STREAM_MUSIC) * 100;
		
		return vol / audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

}
