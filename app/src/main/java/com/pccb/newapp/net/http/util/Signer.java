package com.pccb.newapp.net.http.util;

/**
 * ${name} ${description}
 *
 * @author yanjun
 * @since 0.0.1
 */

public class Signer {

    static {
        setSigner(new DefaultParameterSigner());
    }

    private static IParameterSigner signer;

    public static IParameterSigner getSigner() {
        return signer;
    }

    public static void setSigner(IParameterSigner signer) {
        Signer.signer = signer;
    }
}
