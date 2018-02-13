package com.ks.ui;

import java.util.ArrayList;
import java.util.List;

public class IndexVM {
	List _tweets = new ArrayList();
	List _connects = new ArrayList();
	List _stories = new ArrayList();
	List _profiles = new ArrayList();
	static String[] authors = {"Alien", "Astronauta", "Bombero", "Comisario", "Hiphopper", "Mimo", "Mounstruo"};
	static String[] comments = {
		"ZK 6.5's responsive design enables you to use the same set of components & have it run everywhere, 	desktop & tablet!",
		"Top Contributor Simon Massey talks about implementing event-driven GUI patterns with ZK on IBM developerWorks",
		"ZK 6.5 & Tablet UI Design: Design UI that take advantage of tablet-specific user interaction methods.See Demo Now!",
		"Check out this small talk to know how to apply MVVM pattern in Java if you prefer to create application UI using pure Java ...",
		"This small talk will show how to use MVVM pattern within a Richlet to create your application UI using pure Java",
		"Follow us on the ZK official G+ page to get newest updates!",
		"If you haven't already, come check out ZK 6.5 & responsive design Demo!! Don't miss out on this one!!!",
		"ZK 6.0.2 Released! Supporting development in OSGi environment with few changes in ZK Bind specifications"
	};
	public IndexVM() {
		for (int i = 0; i < 30; i++) {
			_tweets.add(new Tweet(authors[i%7], comments[i%7]));
			_connects.add(new Tweet(authors[(i+3)%7], comments[(i+3)%7]));
			_stories.add(new Tweet(authors[(i+5)%7], comments[(i+5)%7]));
		}
		for (int i = 0; i < 4; i++) {
			_profiles.add(new Tweet(authors[i%7], comments[i%7]));
		}
	}
	public List getProfiles() {
		return _profiles;
	}
	public List getTweets() {
		return _tweets;
	}
	public List getConnects() {
		return _connects;
	}
	public List getStories() {
		return _stories;
	}
}
