package com.ks.enums;

public enum ProjectStatus {

    //WARNING: DO NOT CHANGE THE ORDER OF THE ENUM VALUES OTHERWISE THEY WILL GET ASSIGNED A NEW NUMERIC VALUE FOR THEIR POSITION
    // WHICH WILL IMPACT WHERE THE METADATA TYPE HAS BEEN PERSISTED WITH AND THE ENUM ENTRY VALUE MAPPED TO ITS NUMERIC POSITION
    // IN THE LIST BELOW.
    NOT_VIEWED,
    VIEWED,
    ANSWERED;
    
    private ProjectStatus() {
    	
	}
}
