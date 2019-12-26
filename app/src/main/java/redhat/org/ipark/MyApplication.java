package redhat.org.ipark;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    private boolean loggedIn;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
