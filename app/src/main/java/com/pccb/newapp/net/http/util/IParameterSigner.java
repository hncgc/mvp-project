package com.pccb.newapp.net.http.util;

import java.util.Map;

/**
 * ${name} ${description}
 *
 * @author yanjun
 * @since 0.0.1
 */

public interface IParameterSigner {

    String sign(Map<String, String> params);

}
