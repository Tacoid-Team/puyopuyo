package com.tacoid.puyopuyo;

import android.content.Context;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.analytics.tracking.android.EasyTracker;

public class PuyoPuyoAndroid extends AndroidApplication
{	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initialize(PuyoPuyo.getInstance(), false);
    }
    
    protected void onStart() {
    	super.onStart();
    	EasyTracker.getInstance().activityStart(this);
    }
    protected void onStop() {
    	super.onStop();
    	EasyTracker.getInstance().activityStop(this); 
    }
    
}
