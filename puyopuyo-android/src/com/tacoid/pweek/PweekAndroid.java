package com.tacoid.pweek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
import com.tacoid.pweek.GameHelper.GameHelperListener;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class PweekAndroid extends AndroidApplication implements IActivityRequestHandler, AdListener, ShareLauncher, IGameService, GameHelperListener
{	
	private static AdView adView;
	
	/* Google Game Service attributes */
	private GameHelper aHelper;
	private OnLeaderboardScoresLoadedListener theLeaderboardListener;

	private final static int PORTRAIT_ADS = 3;
	private final static int LANDSCAPE_ADS = 2;
	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;
	private final static int SHARE = 4;
	
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
			case PORTRAIT_ADS: 
			{
				RelativeLayout.LayoutParams adParams = 
						new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
								RelativeLayout.LayoutParams.WRAP_CONTENT);
				adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				adParams.addRule(RelativeLayout.CENTER_VERTICAL);
				adView.setLayoutParams(adParams);
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					adView.setRotation(-90.0f);
					if (adView.getWidth() > 0) {
						adView.setTranslationX(adView.getWidth() / 2 - adView.getHeight() / 2);
					} else {
						adView.setTranslationX(AdSize.BANNER.getWidth() / 2 - AdSize.BANNER.getHeight() / 2);
					}
				}
				break;
			}
			case LANDSCAPE_ADS:
			{
				RelativeLayout.LayoutParams adParams = 
						new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
								RelativeLayout.LayoutParams.WRAP_CONTENT);
				adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
				adView.setLayoutParams(adParams);
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					adView.setRotation(0.0f);
					adView.setTranslationX(0.0f);
				}
				break;
			}
			}
		}
	};

	private boolean isPortrait;

	private GamesClient mGamesClient;

    public PweekAndroid() {
	    aHelper = new GameHelper(this);
	    aHelper.enableDebugLog(true, "GAME");
	   
	    //create a listener for getting raw data back from leaderboard
	    theLeaderboardListener = new OnLeaderboardScoresLoadedListener() {
	    				@Override
	                    public void onLeaderboardScoresLoaded(int arg0, LeaderboardBuffer arg1,
	                                    LeaderboardScoreBuffer arg2) {
	                                   
	                            System.out.println("In call back");
	                           
	                            for(int i = 0; i < arg2.getCount(); i++){
	                                    System.out.println(arg2.get(i).getScoreHolderDisplayName() + " : " + arg2.get(i).getDisplayScore());
	                            }
	                    }

	            };
    }
	
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
		
		aHelper.setup(this);
		Pweek.setGameService(this);
		
		Pweek.setHandler(this);
		Pweek.setShareLauncher(this);
		View gameView = initializeForView(Pweek.getInstance(), config);
		
		
		// Cr�ation de la vu adMob
		adView = new AdView(this, AdSize.BANNER, "a150a3f124cd8c4"); // Put in your secret key here
		//adView = new AdView(this, AdSize.BANNER, "aaaaaaaaaaaaaaa"); // Put in your secret key here
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
		aHelper.onStart(this);
		TrackingManager.setTrackerType(TrackerType.GOOGLE_ANALYTICS);
		EasyTracker.getInstance().activityStart(this);
	}
	protected void onStop() {
		super.onStop();
		aHelper.onStop();
		EasyTracker.getInstance().activityStop(this); 
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage((show || isPortrait) ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void setPortrait(boolean isPortrait) {
		this.isPortrait = isPortrait;
		handler.sendEmptyMessage(isPortrait ? PORTRAIT_ADS : LANDSCAPE_ADS);
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
	public void share(String subject, String body) {
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);

		//set the type
		shareIntent.setType("text/plain");
	
		//add a subject
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
		 subject);
	
		//build the body of the message to be shared
		String shareMessage = body;
	
		//add the message
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
		 shareMessage);
	
		//start the chooser for sharing
		startActivity(Intent.createChooser(shareIntent, 
		 "Share Pweek!"));
	}

	@Override
	public void Login() {
		aHelper.debugLog("=>LOGIN");
        try {
        runOnUiThread(new Runnable(){
               
                //@Override
                public void run(){
                        aHelper.beginUserInitiatedSignIn();
                }
                });
        }catch (final Exception ex){
               
        }
	}

	@Override
	public void LogOut() {
		aHelper.debugLog("=>LOGOUT");
        try {
        runOnUiThread(new Runnable(){
               
                //@Override
                public void run(){
                        aHelper.signOut();
                }
                });
        }catch (final Exception ex){
               
        }
          
	}

	@Override
	public boolean getSignedIn() {
		aHelper.debugLog("=>GETSIGNEDIN");
		return aHelper.isSignedIn();
	}

	@Override
	public void submitScore(int score) {
		aHelper.debugLog("=>SUBMITSCORE");
        aHelper.getGamesClient().submitScore(getString(R.string.leaderBoardID), score);
	}

	@Override
	public void getScores() {
		aHelper.debugLog("=>GETSCORES");
		startActivityForResult(aHelper.getGamesClient().getLeaderboardIntent(getString(R.string.leaderBoardID)), 105); 
		
	}

	@Override
	public void getScoresData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInFailed() {
		aHelper.debugLog("=>SIGNINFAILED");
		System.out.println("sign in failed");
		
	}

	@Override
	public void onSignInSucceeded() {
		aHelper.debugLog("=>SIGNINSUCCEEDED");
		System.out.println("sign in succeeded");
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		aHelper.debugLog("onActivityResult");
		aHelper.onActivityResult(requestCode, resultCode, data);
	}
}
