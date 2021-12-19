package com.scoredev.scores;

import java.security.*;

public final class HighScorePermission extends BasicPermission {

    public HighScorePermission(String name)
    {
	super(name);
    }

    // note that actions is ignored and not used,
    // but this constructor is still needed
    public HighScorePermission(String name, String actions) 
    {
	super(name, actions);
    }
}
