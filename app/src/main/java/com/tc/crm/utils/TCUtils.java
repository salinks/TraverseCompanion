package com.tc.crm.utils;

public class TCUtils {

   public static  boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
