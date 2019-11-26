package redhat.org.ipark;

import android.app.Application;

public class MyApplication extends Application {

    private boolean loggedIn;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
