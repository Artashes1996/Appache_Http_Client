import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetStatus200 {

    private static final String BASE_URL = "https://api.github.com";
    private CloseableHttpClient closeableHttpClient;
    private CloseableHttpResponse closeableHttpResponse;

    @BeforeMethod
    public void setup() {
        closeableHttpClient = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResources() throws IOException {
        closeableHttpClient.close();
        closeableHttpResponse.close();
    }

    @Test
    public void baseUrlReturns200() throws IOException {
        HttpGet get = new HttpGet(BASE_URL);
        HttpResponse response = closeableHttpClient.execute(get);
        int actualStatus = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatus, 200);
    }
}
