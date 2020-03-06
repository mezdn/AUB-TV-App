package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.myapplication.vimeonetworking.NetworkingLogger;
import com.example.myapplication.vimeonetworking.TestAccountStore;
import com.example.myapplication.vimeonetworking.NetworkingLogger;
import com.example.myapplication.vimeonetworking.TestAccountStore;
import com.vimeo.networking.Configuration;
import com.vimeo.networking.Vimeo.LogLevel;
import com.vimeo.networking.VimeoClient;

/**
 * Created by kylevenn on 1/27/16.
 */
public class TestApp extends Application {

    private static final String SCOPE = "private public create edit delete interact";

    private static final boolean IS_DEBUG_BUILD = false;
    // Switch to true to see how access token auth works.
    private static final boolean ACCESS_TOKEN_PROVIDED = true;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        AccountPreferenceManager.initializeInstance(sContext);

        // <editor-fold desc="Vimeo API Library Initialization">
        Configuration.Builder configBuilder;
        // This check is just as for the example. In practice, you'd use one technique or the other.
        if (ACCESS_TOKEN_PROVIDED) {
            configBuilder = getAccessTokenBuilder();
        } else {
            configBuilder = getClientIdAndClientSecretBuilder();
        }
        if (IS_DEBUG_BUILD) {
            // Disable cert pinning if debugging (so we can intercept packets)
            configBuilder.enableCertPinning(false);
            configBuilder.setLogLevel(LogLevel.VERBOSE);
        }
        configBuilder
                .setCacheDirectory(this.getCacheDir())
                .setUserAgentString(getUserAgentString(this))
                .setDebugLogger(new NetworkingLogger());
        VimeoClient.initialize(configBuilder.build());
        // </editor-fold>
    }

    public Configuration.Builder getAccessTokenBuilder() {
        // The values file is left out of git, so you'll have to provide your own access token
        String accessToken = "XXXXXXXXXX3fb1";
        return new Configuration.Builder(accessToken);
    }

    public Configuration.Builder getClientIdAndClientSecretBuilder() {
        // The values file is left out of git, so you'll have to provide your own id and secret
        String clientId = "dc62dc72e8c7cf4d0b7bb025d41e2a7649ddc0a4";
        String clientSecret = "SRbzt7mLfbA0l5aEV8lXINE54nRGkSp8S2i+wOp9paOqQsEKkwC5MtdwFPHiVqX7DTCooPVWAxPVjG8/pjjPbl+7LNTXTuSDit1oZGtCjkBYFqahb95J4pAHzEumdNhs";
        String codeGrantRedirectUri = getString(R.string.deeplink_redirect_scheme) + "://" +
                                      getString(R.string.deeplink_redirect_host);
        TestAccountStore testAccountStore = new TestAccountStore(this.getApplicationContext());
        Configuration.Builder configBuilder =
                new Configuration.Builder(clientId, clientSecret, SCOPE);
        configBuilder
                // Used for oauth flow
                .setCodeGrantRedirectUri(codeGrantRedirectUri);

        return configBuilder;
    }

    public static String getUserAgentString(Context context) {
        String packageName = context.getPackageName();

        String version = "unknown";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println("Unable to get packageInfo: " + e.getMessage());
        }

        String deviceManufacturer = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        String deviceBrand = Build.BRAND;

        String versionString = Build.VERSION.RELEASE;
        String versionSDKString = String.valueOf(Build.VERSION.SDK_INT);

        return packageName + " (" + deviceManufacturer + ", " + deviceModel + ", " + deviceBrand +
               ", " + "Android " + versionString + "/" + versionSDKString + " Version " + version +
               ")";
    }
}