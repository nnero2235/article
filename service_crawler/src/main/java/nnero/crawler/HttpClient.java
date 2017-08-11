package nnero.crawler;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.omg.PortableServer.RequestProcessingPolicy;
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

    public static final String[] USER_AGENTS = {
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER"
    };

    public String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        Request request = new Request.Builder()
                .url(buildURLWithParams(url, params))
                .headers(Headers.of(headers))
                .addHeader("User-Agent", USER_AGENTS[new Random().nextInt(USER_AGENTS.length)])
                .get()
                .build();
        Response response = sClient.newCall(request).execute();
        return response.body().string();
    }

    public String get(String url, Map<String, String> headers) throws IOException {
        return get(url,null,headers);
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
}
