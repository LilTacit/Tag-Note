package com.mobile.kiril.tagnote;

import android.app.Application;
import android.content.Context;

import org.solovyev.android.checkout.Billing;

public class Tagnote extends Application {
    private String result = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxZyh6uzTnlelfZXUOG01kvYIc1pLXmyaHiG6ucnI4J9WTM/3Xu77UtV9FL146YB4cjrPQzDFzTJuNjUDG7Ju1pme6U46vGeOiZYgHB3E5XpGvL4EbNAO7xhAB7Hxk/3X4ey681QtKq9OyMYryc4eDGWE1SSIWYheNCyPnG9pQSAEZgeMC0xcQgcn9IaLfLm5Z6sYTQowABisqCSPpt6jzU+qMU+EiLhWL2m74aiQI5zBzgcOJORu77DfJTG7FWhMfy1rI2buUbWbWdtF2S50BDY99xiz/JnEBdWJoXgmL2VnIhQCAi8RYBYpJZZyjDrDn33po5tR+JE7Nv2GYERjMQIDAQAB";
    private static Tagnote sInstance;

    public Tagnote() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @Override public String getPublicKey() {
            return result;
        }
    });

    public Billing getBilling() {
        return mBilling;
    }

    public static Tagnote get() {
        return sInstance;
    }
}
