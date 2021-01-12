package com.song.androidstudy.preferences;

import android.content.ContentProvider;
import android.content.UriMatcher;

public abstract class PreferencesProvider extends ContentProvider {

    private UriMatcher mUriMatcher;

    /**
     * 表列名
     */
    public static String COLUMNNAME = "SPCOLUMNNAME";
    /**
     * authorities key
     */
    public static String AUTHORITIES_KEY = "authorities_key";
    /**
     * authorities_spname
     */
    public static String AUTHORITIES_SPNAME = "authorities_spname";
    /**
     * string
     */
    private String mStringPath = "string/*/*/";
    public static final int STRING_CONTENT_URI_CODE = 100;
    /**
     * int
     */
    private String mIntegerPath = "integer/*/*/";
    public static final int INTEGER_CONTENT_URI_CODE = 101;
    /**
     * long
     */
    private String mLongPath = "long/*/*/";
    public static final int LONG_CONTENT_URI_CODE = 102;
    /**
     * float
     */
    private String mFloatPath = "float/*/*/";
    public static final int FLOAT_CONTENT_URI_CODE = 104;
    /**
     * boolean
     */
    private String mBooleanPath = "boolean/*/*/";
    public static final int BOOLEAN_CONTENT_URI_CODE = 105;

    /**
     *
     */
    private String mDeletePath = "delete/*/*/";
    public static final int DELETE_CONTENT_URI_CODE = 106;

    /**
     *
     */
    private String mPutsPath = "puts";
    public static final int PUTS_CONTENT_URI_CODE = 107;


}