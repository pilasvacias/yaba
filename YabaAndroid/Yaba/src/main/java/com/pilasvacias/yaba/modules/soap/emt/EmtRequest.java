package com.pilasvacias.yaba.modules.soap.emt;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pilasvacias.yaba.modules.soap.EmtEnvelopeSerializer;
import com.pilasvacias.yaba.modules.util.L;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pablo on 10/12/13.
 * welvi-android
 */
public class EmtRequest<T extends EmtResult> extends Request<T> {

    private final EmtBody body;
    private final Class<T> responseType;
    private final Response.Listener<T> listener;

    public EmtRequest(EmtBody body, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, "https://servicios.emtmadrid.es:8443/bus/servicebus.asmx", errorListener);
        this.body = body;
        this.responseType = responseType;
        this.listener = listener;
    }

    @Override protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String xml = new String(response.data);
        T data = EmtEnvelopeSerializer.getInstance().fromXML(xml, responseType);
        if (data != null)
            return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
        else
            return Response.error(new VolleyError(response));

    }

    @Override protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override public byte[] getBody() throws AuthFailureError {
        String xml = EmtEnvelopeSerializer.getInstance().toXML(body);
        L.og.d("emt body => \n%s", xml);
        try {
            return xml.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override public String getBodyContentType() {
        return "text/xml";
    }

    @Override public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("SOAPAction", "http://tempuri.org/" + body.getSoapAction());
        L.og.d("emt headers => \n%s", headers.toString());
        return headers;
    }
}
