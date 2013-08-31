package com.tacoid.pweekmini;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.games.multiplayer.realtime.Room;
import com.tacoid.pweekmini.GameHelper;
import com.tacoid.pweek.IGameService;
import com.tacoid.pweek.ScoreManager.GameType;

public class GameServiceManager implements IGameService {
	private GameHelper aHelper;
	private Context context;
	private Activity activity;

	public GameServiceManager(GameHelper aHelper, Context context, Activity activity) {
		this.aHelper = aHelper;
		this.context = context;
		this.activity = activity;
	}

	@Override
	public void unlockAchievement(Achievement a) {
		if (!PweekMini.getInstance().getGameService().getSignedIn()) {
			return;
		}
		
		if (a.incremental) {
			switch (a) {
			case FANBOY:
				aHelper.getGamesClient().incrementAchievement(context.getString(R.string.ach_FANBOY), 1);
				break;

			default:
				System.out.println("Achievement non implémenté.");
				break;
			}
		} else {
			switch (a) {
			case AFK:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_AFK));
				break;
			case CHAIN:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_CHAIN));
				break;
			case DEAF:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_DEAF));
				break;
			case FIRST_COMBO:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_FIRST_COMBO));
				break;
			case FOREVER_ALONE:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_FOREVER_ALONE));
				break;
			case MASTERSTROKE:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_MASTERSTROKE));
				break;
			case MEGA_EXPLODE:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_MEGA_EXPLODE));
				break;
			case NINJA:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_NINJA));
				break;
			case OCD:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_OCD));
				break;
			case P10K:
				aHelper.getGamesClient().unlockAchievement(context.getString(R.string.ach_P10K));
				break;
			default:
				System.out.println("Achievement non implémenté.");
				break;
			}
		}
	}

	@Override
	public void login() {
		aHelper.debugLog("=>LOGIN");
        try {
        activity.runOnUiThread(new Runnable(){
               
                //@Override
                public void run(){
                        aHelper.beginUserInitiatedSignIn();
                }
                });
        }catch (final Exception ex){
               
        }
	}

	@Override
	public void logout() {
		aHelper.debugLog("=>LOGOUT");
        try {
        activity.runOnUiThread(new Runnable(){
               
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
		return aHelper.isSignedIn();
	}

	@Override
	public void submitScore(GameType type, int score) {
		if (!PweekMini.getInstance().getGameService().getSignedIn()) {
			return;
		}
		aHelper.debugLog("=>SUBMITSCORE");
		switch(type) {
		case SOLO:
			aHelper.getGamesClient().submitScore(context.getString(R.string.solo_leaderboard), score);
			break;
		case CHRONO:
			aHelper.getGamesClient().submitScore(context.getString(R.string.chrono_leaderboard), score);
			break;
		default:
			aHelper.debugLog("Invalid game type leaderboard");
		}
	}

	@Override
	public void showLeaderboard(LeaderboardType type) {
		if (!PweekMini.getInstance().getGameService().getSignedIn()) {
			return;
		}
		aHelper.debugLog("=>GETSCORES");
		switch (type) {
		case SOLO:
			activity.startActivityForResult(aHelper.getGamesClient().getLeaderboardIntent(context.getString(R.string.solo_leaderboard)), 105);
			break;
		case CHRONO:
			activity.startActivityForResult(aHelper.getGamesClient().getLeaderboardIntent(context.getString(R.string.chrono_leaderboard)), 105);
			break;
		default:
			activity.startActivityForResult(aHelper.getGamesClient().getAllLeaderboardsIntent(), 105);
		} 
	}

	@Override
	public void showAchievements() {
		if (!PweekMini.getInstance().getGameService().getSignedIn()) {
			return;
		}
		aHelper.debugLog("=>GETACHIEVEMENTS");
		activity.startActivityForResult(aHelper.getGamesClient().getAchievementsIntent(), 106);
	}
	
	@Override
	public void getScoresData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showFriendSelector() {
		// launch the player selection screen
		// minimum: 1 other player; maximum: 3 other players
		Intent intent = aHelper.getGamesClient().getSelectPlayersIntent(1, 1);
		activity.startActivityForResult(intent, GameHelper.RC_SELECT_PLAYERS);
	}

	@Override
	public void startQuickGame() {
		
		aHelper.createMatchMakingRoom();

	    // go to game screen
	}

	@Override
	public void showWaitingRoom(Room r) {
		// TODO Auto-generated method stub
		
	}

}
