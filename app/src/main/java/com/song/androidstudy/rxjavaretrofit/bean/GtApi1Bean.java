package com.song.androidstudy.rxjavaretrofit.bean;

/**
 * Created by chensongsong on 2018/10/13.
 */
public class GtApi1Bean {
    /**
     * success : 1
     * challenge : 4e62fd6d3ec98898a350936bf70aa042
     * gt : e52c06c937981b90b275d0aff1d40076
     * new_captcha : true
     */

    private int success;
    private String challenge;
    private String gt;
    private boolean new_captcha;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public boolean isNew_captcha() {
        return new_captcha;
    }

    public void setNew_captcha(boolean new_captcha) {
        this.new_captcha = new_captcha;
    }

    @Override
    public String toString() {
        return "GtApi1Bean{" +
                "success=" + success +
                ", challenge='" + challenge + '\'' +
                ", gt='" + gt + '\'' +
                ", new_captcha=" + new_captcha +
                '}';
    }
}
