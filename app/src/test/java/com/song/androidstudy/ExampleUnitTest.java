package com.song.androidstudy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() {

        // 拿到测试长度
        String entryption = "{\"data\":{\"pri\":\"kP33we3Dtr8J7LMGdbwwrKripSBzrF+qLUYVWILFqlfBBkJDM4SURXC2Vpa0EpTedQ9oz+fpsyOGr\\/IN9gcTbNO3z6ZqUQ84EPK8IjgjN+j\\/c0k\\/ksT74ySxQQOMyyxLa96jZ0GF8I1zhNAfFACU0xGpx0P1RJXVjT2NvgxxRsXc9QRgz70FM+OEWhwC9OD3sEJiEweiWEBw9e\\/q+WvXJlW2jspgzE5SDW\\/ysGJQrKDchhqTy9LU+klayIJ22DPFdOY5VhkbOGgxrZtkuJsa7jtlXvO9aH4pt20u1M3VzS8ikLRE41xcoYDsEF9\\/u+mNFlA1KwSUVEVTG56k5kNw5Q==\",\"fingerprint\":\"CZvPvIxNyxf0XPNz5U5HdMydN98cKN2SQiVZ85P2b1Uqn7Zhp\\/P0PVzn\\/qBRetZLEMW+lCMSIFXWdFuyQT+VUZ2iwgQuvgIA\\/5G3\\/W7wE8ldsooFT7LSH28QO3dMZ+fCKir\\/ZEYq+UCUVaAm+HeIEdMSfBWvfGDaSBFQy2oZ9jfL0v3\\/fire3W1jiEiCAzh2eb+CxiIaLEzBnozeMkb1x8MzLh0xfmmbb1I54F\\/3oQwSdkWmV1YoEnvRJSqpZrPOQvSvO6dMS3aouEBBOJX2xhKGsqsV+1M6COuhBzazciKtHoHWYLhQll8zQTjFx4f5erlhuAd3pO\\/NNgjCs9QHUWE0LADc+QgnS4Pp9tx2mWO28KmwV3wvBCZwpJGxsPSXrgNVG2vCzSY9Xcm69S+kA\\/OO8PDXkULrFk8EY0AZTi1FTSqIAwlMiJUY+A0e\\/qZO1IxhNI63DRmfeJZXiPoQtzt1aahP9VDChbBCcg8c15aFfv5HiG2EqkopDdU6glm2zUp7MGrJix8AiVguDv+TMgT2KPeVp8nS2+H5t3IBvfm5cww6P4OCxukXa6qY9v+FChQ15GhWHoHVJb2HiGfqfATiLApCX3t88bouU+FurrQfdBKeMHswaUfpXzPPJWYWHJ+qa0brwMQLP7RA7aLDqd5MjUfG\\/vYyQiZ4XqNkb5q8WzS5Plv5Tqfh6aV61sws6CI1IsP+HI3B3ap1d3Bdkh01W1CqWaagxtTJcAms7zsNoUTE9ueDO+T4VDz3TziDsG\\/52EbMEmaPc+0ykIEmFagmL0UZMmDhW+Fy70+NmWvXCz3j80p3XiHZt6PN4pj\\/SDnDWnllEDs2mm2ExV1Hx4VRYGjWypYT\\/xUyvcsGumf8sVoE6j7tF4XH7XL+xLnkDf1f3PzsX5Ywadw09v4AwTZeQVnAW8TPhQTfXk+PxudKtu2ZNOWjbZFmLdzvb3Y\\/Qu4dFuXBcxTDMI7nsY27qbaVvFhpQ+YN5yZYAQU4zadm3\\/br2c5bSfv3t3qQkxjr3OkS5g8L+aFfjyhZMfHMKo+ITRqiubhPfSKSKojeuVOIL2X\\/aVkKqZ6GgqV8adJ339aCqhNdC05gvDTAcUCaIMQClWm7okcLX4Oi91MNQzDkRnOJhHNKnPwXSWs0vl3yUrMNIK7pLTEsIUG2RvbFcsEojeGVDTvPB8JTSuRYayiEdNLROePYa3eu+daudDgBKpqipxKKE6dmEv4\\/Ulp\\/HAQAff5DpQduH+Q1S8mJ4EvC\\/5qLL20JqGprSGISw4049ZpuK4IBxk4J9Xhymw\\/YA80vXCCYJpfy7PhlfPgBs2GpExTVNV41ZmBRCNoREOCLCoipBPOSlmhJFfsu4U6FkeokIEmBp0EEcDTuJKMxSL82toHvco0MzUX5\\/PaCt3IIhM+bs8eEWXRsQfvj4HEN4baCjQoGsjk37Htl6fYlIOmvStnRSfF\\/BTa7J60Fg+iZ5RaQg8KqkeNV7G2LFTOsooobyRGV25x7k3a7M721H9EHbmBJONGVied3Gwif1nD\\/dZr3\\/gu4XUQadKGGQMoxoccHDnM7kDqy+FMrte7o117TVtdNLnl7plKJ5nLVYYvH7I18\\/kVc5IM+hHyPAGTqCtAb8aifR7YKgizp4JLgbCQUtIpBOBNvrjXvErFvtvga0vEt+re1rd35H03KBjTpm74kzxTCrjqlTxtBkv69DdvKQA0gROi7RCwLOBRsDF3OFWvJEAgygEradtwac9XORXi55brzyD\\/GqhNGu0L4Eoz\\/Lp\\/lbZQCbB4ce0tlpsQdFZb1624PavNY4\\/mWKSofhXb0bNw86X8exGL1yX7l\\/rb\\/\\/X+iFNX00MkwmrXWodMXseqm+MEW2vyAQsBa1Oif5iTTSI3etZHlUVI4doorcsraafFVm1enb9Qm7MC9bI+1ONYpR3IaFmWpELi571DqpUOqwUCMVh+bnStForDd8vMUVGzQ6KxD2JFAlZgSBFskaasjUCmmgjXnxvNuo0bZgIuZWY+9LrWoRmTmdBh66BHzskCAeHA2x7dhsSFJHWOBrm5EngUADYkRvmrpg+QxfzZxPiEOj3kRmy\\/p2DFud8hgqKxJVYVJPbhL8s8WKALWVJMIF0\\/Fo0tv0bdsEU5c0i3qRHZpO3foVktR2EMyjoaCZeq6U4JHrCpfS90GBlnXI7TgaXz4SUw9YpUwNQG0Ahr+is2f52frQmoCMeIbV7I4mgZDobV+oUSXy8\\/pZXvPLxkbBM9ry7ctgWemj4a1K6+0JLLWI7LEsg0hmxe+ScA6oo+rbs0HssZ8xD0nMTonCfXaO1hiw76B66kP2VRE7yUBQUHEu\\/a+FHdtgPBdMWRpEqBPMHNYR2KbNJAcn18sBoM0gDHFuz5bUt8TiUnw\\/\\/8ydnQtNU2n49g7AxoTlDQeg34ah8U1XT+8NwQzkIWnreQiJWza82UatH1bK8BYHIsgrySEemANhmaeKnXsWHOufuUbZpCM4IkCrqNWK7mmTTDHbCl5efTh8is4qs0MV+DdWuFLZP4caQSUXI+\\/OfhztVae09wOWf4OGzcFD\\/Jh0bOL9qFxudnVHSWc20qBKRqwaYaklyd0bmcr4n5MXS11PbYIAXANEZrd65vLGJhX+NTKOVybDtAnRXQbJQvx14ouGwIsj9Udf9rNpR6FvUXkoC+jJzYWxi8ssRwUyEVZIz3ZubvGDnYwGyA01URC+QKtB8y8v39XFGameTAfbjbRt\\/cUNzYD9vHjtimACz4G8y861SGQPWwXXvv1vrneTRBlu9oCgiBC\\/bfOMDaJdrwg7A06Myvx\\/ufNHtXYdsvelEzFd8LQyAV5UV2BrAJAyPiMlQXqKimsvQOhEN6\\/Hd5N9FMJ2y0R\\/Z7SB6L919wJeT85\\/fdEop9GJ\\/C+WmG6ac97FeXFZ3GMgfyXGsxjdzof7GBLyvxrTeJ56MYzKw4E\\/ZIdARQJ8090XF5nPShLoPggau98OQQOF9wILD0AVVghZLyM3pfu+IWxnmGW+RYjViKQbL0mSIBCO0qSKZbTvhceSFMy1ecn2KwlQq9Ua2SzqTGUhG9lBE3OZDjdRbQaQ8\\/pMOa14PF3tu3jzNUt6WDq4p1XTs3GnNUVq8P4fEooKJ8cfJjRhucYKDjkY8PpH8iURnbnFGLNveorHZKv\\/26bYmsI1fCSn+MQcqNMdakX9K416IbJmG+MZkzmaCdjnBTaT91Oavl75MnoItK1auh7la+\\/AWk0Y9eIYku3eGZ1PK3u74WNHSey4ftMI8h6CMRhHUv6+RkR1vdkPhhGLk0fVKwa\\/xsSXpYLvC+lB\\/TXQVscRZvX2dTodyGXeKTIWN0Rem\\/RBEknIHxmGmkQkdqoQHcBQzPC3eG0Y9AJaaDNah4VcDv6ke8DTJuYQpceEB34XgZrbdu+eVLwwXJvhc8HVv4RMf\\/g+Ymh3ep7EglwJT9tS2wKPTY3T9LMHfF3oUqzYerOzzAgkh7Lsv2LYFU8M0s9+cxAKEnZq7kxoGtHD2K0tkqAb4llv1dbWXPTzgqhxff9XR3R9izwfeuAyAoQbZC5YWpcl\\/VoGjqpR3S45ZEgsfqh\\/xIHhnpe\\/HKE9Kl";

        System.out.println(entryption.length());

        assertEquals(4, 2 + 2);





    }
}