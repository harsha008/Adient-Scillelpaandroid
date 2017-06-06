package lt.adient.mobility.eLPA.ws;

import java.io.IOException;

import lt.adient.mobility.eLPA.exception.InvalidWebServiceUrl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BaseUrlInterceptor implements Interceptor {
    private String baseUrl;

    public BaseUrlInterceptor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = HttpUrl.parse(baseUrl);
        if (url != null) {
            HttpUrl newUrl = request.url().newBuilder()
                    .scheme(url.scheme())
                    .host(url.host())
                    .port(url.port())
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        } else {
            throw new InvalidWebServiceUrl("invalid web service url: " + baseUrl);
        }
        return chain.proceed(request);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
