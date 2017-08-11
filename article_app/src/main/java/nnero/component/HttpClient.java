package nnero.component;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * 2017/8/3 下午2:45 created by NNERO
 */
@Component
public class HttpClient {

    private static final OkHttpClient sClient = new OkHttpClient();

    public static final String USER_AGENT = "ArticleWeb";

    public String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        Request request = new Request.Builder()
                .url(buildURLWithParams(url, params))
                .headers(Headers.of(headers))
                .addHeader("User-Agent", USER_AGENT)
                .get()
                .build();
        Response response = sClient.newCall(request).execute();
        return response.body().string();
    }


    public String get(String url, Map<String, String> params) throws IOException {
        Request request = new Request.Builder()
                .url(buildURLWithParams(url, params))
                .addHeader("User-Agent", USER_AGENT)
                .get()
                .build();
        Response response = sClient.newCall(request).execute();
        return response.body().string();
    }

    public String post(String url,Map<String,String> formData,Map<String,String> headers) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .addHeader("User-Agent",USER_AGENT)
                .post(buildFormData(formData))
                .build();
        Response response = sClient.newCall(request).execute();
        return response.body().string();
    }

    public String post(String url,Map<String,String> formData) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent",USER_AGENT)
                .post(buildFormData(formData))
                .build();
        Response response = sClient.newCall(request).execute();
        return response.body().string();
    }

    public static String buildURLWithParams(String url, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(url).append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
        return url;
    }

    public static RequestBody buildFormData(Map<String,String> formData){
        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String,String> entry : formData.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        return builder.build();
    }
}
