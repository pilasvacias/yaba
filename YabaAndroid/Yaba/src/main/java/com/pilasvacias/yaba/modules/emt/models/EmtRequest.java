package com.pilasvacias.yaba.modules.emt.models;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.pilasvacias.yaba.modules.emt.EmtEnvelopeSerializer;
import com.pilasvacias.yaba.modules.playa.handlers.ErrorHandler;
import com.pilasvacias.yaba.modules.playa.models.PlayaRequest;
import com.pilasvacias.yaba.util.L;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pablo on 10/12/13.
 * welvi-android
 */
public class EmtRequest<T> extends PlayaRequest<EmtData<T>> {

    private final Class<T> responseType;
    private EmtBody body;

    public EmtRequest(ErrorHandler errorHandler, Class<T> responseType) {
        super(errorHandler);
        this.responseType = responseType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String xml = EmtEnvelopeSerializer.getInstance().toXML(body);
        if (isVerbose())
            L.og.d("emt sent action %s with body => \n%s", body.getSoapAction(), xml);
        try {
            return xml.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return "text/xml";
    }

    @Override
    public void setBody(Object body) {
        this.body = (EmtBody) body;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("SOAPAction", "http://tempuri.org/" + body.getSoapAction());
        return headers;
    }

    @Override
    public EmtData<T> getParsedData(NetworkResponse response) {
        String xml = new String(response.data);
        try {
            return EmtEnvelopeSerializer.getInstance().fromXML(xml, responseType, body);
        } catch (Exception e) {
            //Parse exception
            return null;
        }
    }

    @Override
    public VolleyError generateErrorResponse(NetworkResponse response, EmtData<T> data) {
        return new EmtError(data, response);
    }

    @Override
    public boolean responseIsOk(NetworkResponse response, EmtData<T> data) {
        return data != null && data.getEmtInfo().getResultCode() == 0;
    }

    @Override
    public String getCacheKey() {
        return body.getSoapAction() + "." + super.getCacheKey();
    }
}
