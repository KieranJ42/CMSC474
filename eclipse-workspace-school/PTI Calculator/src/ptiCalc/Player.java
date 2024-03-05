package ptiCalc;

import java.util.*;

public class Player {
	
	private static final double DEFAULT_CONF = .25;
	private static final double DEFAULT_RATING = 60.0;
	
	private static Set<Integer> usedIds = new HashSet<Integer>();
	private static int startingId = 1;
	
	private int playerId;
	private String name;
	private double confidence;
	private double rating;
	
	public Player() {
		this(0, "N/A", DEFAULT_CONF, DEFAULT_RATING);
	}
	
	public Player(int pid) {
		this(pid, "N/A", DEFAULT_CONF, DEFAULT_RATING);
	}
	
	public Player(String name) {
		this(0, name, DEFAULT_CONF, DEFAULT_RATING);
	}
	
	public Player(int pid, String name) {
		this(pid, name, DEFAULT_CONF, DEFAULT_RATING);
	}
	
	public Player(int pid, String name, double conf, double rat) {
		if (pid < 1 || usedIds.contains(pid)) {
			while (startingId < Integer.MAX_VALUE) {
				if (!usedIds.contains(startingId)) {
					playerId = startingId;
					break;
				} 
				startingId++;
			}
		} else
			playerId = pid;
		usedIds.add(playerId);
		this.name = name;
		confidence = conf;
		rating = rat;
	}
	
	public void setConf(double conf) {
		confidence = conf;
	}
	
	public double getConf() {
		return confidence;
	}
	
	public void setRat(double rat) {
		rating = rat;
	}
	
	public double getRat() {
		return rating;
	}
	
	public String toString() {
		return "Name: " + name + ", Player ID: " + playerId + ", Rating: " +
				String.format("%.2f", rating) + ", Confidence: " + 
				String.format("%.3f", confidence);
	}
	
}
