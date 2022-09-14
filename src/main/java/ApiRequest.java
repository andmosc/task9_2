import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class ApiRequest {
    private static String apiKey = null;
    private static String urlApiKey = "?api_key=" + getApiKey(apiKey);
    private static final String urlBase = "https://api.nasa.gov/planetary/apod";

    protected static CloseableHttpResponse getResponse() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(urlBase + urlApiKey);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());// отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);// вывод полученных заголовков
        return response;
    }
    private static String getApiKey(String apiKey) {
        if (apiKey == null || apiKey.equals("")) {
            apiKey = "DEMO_KEY";
        }
        return apiKey;
    }
    public static void setUrlApiKey(final String newApiKey) {
        urlApiKey = "?api_key=" + newApiKey;
    }
}
