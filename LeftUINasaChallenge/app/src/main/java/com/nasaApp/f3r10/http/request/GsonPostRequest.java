package com.nasaApp.f3r10.http.request;

/**
 * Created by nando on 4/10/15.
 */
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nasaApp.f3r10.http.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Class for all gson response parsed GET requests.
 *
 * @param <T> The type of parsed response this request expects.
 *
 * @author Mostafa Gazar <eng.mostafa.gazar@gmail.com>
 */
public class GsonPostRequest<T> extends Request<T> {

    private final Gson mGson = new Gson();
    private Map<String, String> mParams;

    /**
     * Class object of generic T.
     */
    private final Class<T> mClassOfT;

    /**
     * Callback object, which is notified of delivered response.
     */
    private final Response.Listener<T> mListener;

    /**
     * Make a POST request and return a parsed object from JSON.
     *
     * @param url      URL of the request to make.
     * @param classOfT Relevant class object, for Gson's reflection.
     */

    public GsonPostRequest(String url, Class<T> classOfT, Map<String, String> params,
                           Response.Listener<T> listener, Response.ErrorListener errorListener) {

        super(Method.POST, url, errorListener);
        mParams = params;
        mClassOfT = classOfT;
        mListener = listener;
    }

    // Only works with POST and PUT.
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return mParams != null ? mParams : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            // NOTE :: Cache anyway regardless of server response cache headers.
            Cache.Entry cacheEntry = HttpHeaderParser.parseIgnoreCacheHeaders(response);

            return Response.success(mGson.fromJson(json, mClassOfT), cacheEntry);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

}
