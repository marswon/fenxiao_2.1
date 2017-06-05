package com.kedang.fenxiao.service.trans;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

/**
 *
 */
public class RedirectStrategyImpl extends DefaultRedirectStrategy {

    public static final RedirectStrategyImpl INSTANCE = new RedirectStrategyImpl();
    public RedirectStrategyImpl() {
        super();
    }

    public boolean isRedirected(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws ProtocolException {
        Args.notNull(request, "HTTP request");
        Args.notNull(response, "HTTP response");

        final Header locationHeader = response.getFirstHeader("location");



        final int statusCode = response.getStatusLine().getStatusCode();
        final String method = request.getRequestLine().getMethod();
        if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                (statusCode == HttpStatus.SC_SEE_OTHER) ||
                (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {


            if (locationHeader != null) {
                String rdurl= locationHeader.getValue();
                if(StringUtils.isNotBlank(rdurl)){
                    if(StringUtils.startsWith(rdurl,HttpClientService.LOGOUT_URL)||StringUtils.startsWith(rdurl,HttpClientService.LOGIN_URL)){
                        response.addHeader("isTimeout","1");
                        return false;
                    }
                }
            }
        }
        switch (statusCode) {
            case HttpStatus.SC_MOVED_TEMPORARILY:
                return isRedirectable(method) && locationHeader != null;
            case HttpStatus.SC_MOVED_PERMANENTLY:
            case HttpStatus.SC_TEMPORARY_REDIRECT:
                return isRedirectable(method);
            case HttpStatus.SC_SEE_OTHER:
                return true;
            default:
                return false;
        } //end of switch
    }

}
