package ptiCalc;

public class Match {
	
	private boolean win;
	private int gamesPlayed, gamesWon;
	private int[] setScores;
	private double ptiSpread, ptiSpreadScale;
	
	public enum matchType {
		CASUAL(1), LEAGUE(2), TOURNAMENT(3);
		private int value;
		
		private matchType(int value) {
			this.value = value;
		}
	}
	
	private static final matchType DEFAULTTYPE = matchType.LEAGUE;
	private matchType type;
	private Player[] players;
	private double[] multipliers;
	
	public Match(Player p1, Player p2, Player p3, Player p4) {
		type = DEFAULTTYPE;
		players = new Player[4];
		multipliers = new double[4];
		
		
		players[0] = p1;
		players[1] = p2;
		players[2] = p3;
		players[3] = p4;
		
		calcMultipliers();
	}
	
	public void calcMultipliers() {
		multipliers[0] = Math.cbrt(players[1].getConf()*players[2].getConf()
				*players[3].getConf()) / players[0].getConf();
		multipliers[1] = Math.cbrt(players[0].getConf()*players[2].getConf()
				*players[3].getConf()) / players[1].getConf();
		multipliers[2] = Math.cbrt(players[0].getConf()*players[1].getConf()
				*players[3].getConf()) / players[2].getConf();
		multipliers[3] = Math.cbrt(players[0].getConf()*players[1].getConf()
				*players[2].getConf()) / players[3].getConf();
	}
	
	public Match(Player[] players) /*throws Exception*/ {
		this(players[0], players[1], players[2], players[3]);
		//if (players.length != 4)
		//	throw new Exception();
	}
	
	public Match(Player[] players, int[] score) /*throws Exception*/ {
		this(players);
		switch (score.length) {
		case 4:
			win = score[0]>score[1];
			gamesWon = score[0] + score[2];
			gamesPlayed = gamesWon + score[1] + score[3];
			break;
		case 6:
			win = score[4]>score[5];
			gamesWon = score[0] + score[2] + score[4];
			gamesPlayed = gamesWon + score[1] + score[3] + score[5];
			break;
		default:
			//throw new Exception();
		}
		setScores = score;
		
		System.out.println(this);
		ptiAdjustment();
		System.out.println(this);
	}
	
	/*public Player[] getPlayers() {
		return players;
	}
	
	public double[] getMultipliers() {
		return multipliers;
	}
	
	public int[] getScores() {
		return setScores;
	}
	
	public int getGamesWon() {
		return gamesWon;
	}
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	public boolean getWin() {
		return win;
	}
	*/
	
	/*function pti_actual(win,winGames, loseGames) {
		  if(win) 
		    return (1+2*winGames/(winGames+loseGames))/3;
		  else
		    return (2*winGames/(winGames+loseGames))/3;
		}
		function pti_expected() {
		  return 1 - (1 / (1 + 10 ** ((PTISpread) / 24)))
		}

		function pti_adjustment(expected_result,actual_result) {
		  if (((expected_result>.5) && (actual_result < .5)) || ((expected_result<.5) && (actual_result > .5)))
		    return 2.8*MatchTypeScale* (expected_result-actual_result);
		  else
		    return PTISpreadScale*2.8*MatchTypeScale* (expected_result-actual_result);
		}
		if(Math.abs(PTISpread)>=21) {
    PTISpreadScale=0;
  	}
  	else if (Math.abs(PTISpread)>17){
    	PTISpreadScale=(21-Math.abs(PTISpread))/4;
  	} else {
    	PTISpreadScale=1;
  	}*/
	
	public double ptiActual() {
		return (2.0*gamesWon/gamesPlayed+(win?1.0:0))/3.0;
	}
	
	public double ptiExpected() {
		return (1/(1+Math.pow(10, ptiSpread/24)));
	}
	
	public void ptiAdjustment() {
		ptiSpread = players[0].getRat() + players[1].getRat()
				- (players[2].getRat() + players[3].getRat());	
		double expRes = ptiExpected();
		double actRes = ptiActual();
		if (((expRes>.5) && (actRes<.5)) || ((expRes<.5) && (actRes>.5)))
			ptiSpreadScale = 1;
		else
			ptiSpreadScale = Math.abs(ptiSpread)>=21?0:Math.abs(ptiSpread)>17
			 	 ?(21-Math.abs(ptiSpread))/4:1;
		double adj = ptiSpreadScale*1.4*type.value*(expRes-actRes);
		players[0].setRat(players[0].getRat()+(adj*multipliers[0]));
		players[1].setRat(players[1].getRat()+(adj*multipliers[1]));
		players[2].setRat(players[2].getRat()-(adj*multipliers[2]));
		players[3].setRat(players[3].getRat()-(adj*multipliers[3]));

		System.out.println ("Expected Result: " + expRes + "\nActual Result: "
					+ actRes + "\nAdjustment: " + adj + "\n");
		multAdjustment(expRes, actRes);
	}
	
	public void multAdjustment(double expRes, double actRes) {
		double lowSlide = expRes<.2?expRes-.1:expRes>.8?expRes-.4:expRes/2;
		double highSlide = expRes<.2?expRes+.4:expRes>.8?expRes+.1:(1+expRes)/2;
		System.out.println ("Low end: " + lowSlide + "\nHigh end: " +
							highSlide + "\n");
		for (int i = 0; i < 4; i++) {
			if (actRes < lowSlide) {
				if (players[i].getConf() > 1-(lowSlide-actRes))
					players[i].setConf(1-(lowSlide-actRes));
				if (i==0)
					System.out.println("Lowest new confidence: " + 
							(1-(lowSlide-actRes)) + "\n");
			} else if (actRes > highSlide) {
				if (players[i].getConf() > 1-(actRes-highSlide))
					players[i].setConf(1-(actRes-highSlide));
				if (i==0)
					System.out.println("Lowest new confidence: " + 
						(1-(actRes-highSlide)) + "\n");
			} else {
				players[i].setConf(players[i].getConf()+.25-Math.abs(actRes-
						(lowSlide+highSlide)/2));
				if (players[i].getConf()>1)
					players[i].setConf(1);
				if (i==0)
					System.out.println("Max confidence increase: " + 
						(.25-Math.abs(actRes-(lowSlide+highSlide)/2)) + "\n");
			}
		}
			
		calcMultipliers();
	}
	
	public String toString() {
		return "Team 1:\n" + players[0].toString() + ", Rating Multiplier: " + 
				String.format("%.2f", multipliers[0]) + "\n" +
				players[1].toString() + ", Rating Multiplier: " + 
				String.format("%.2f", multipliers[1]) + "\n" +
				"\nTeam 2:\n" + players[2].toString() + ", Rating Multiplier: " + 
				String.format("%.2f", multipliers[2]) + "\n" +
				players[3].toString() + ", Rating Multiplier: " + 
				String.format("%.2f", multipliers[3]) + "\n";
	}
	

}
