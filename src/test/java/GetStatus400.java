import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetStatus400 {
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

    @DataProvider
    private Object[][] endpoints() {
        return new Object[][]{
                {"/user"}, {"/user/followers"}, {"/notifications"}
        };
    }

    @Test(dataProvider = "endpoints")
    public void userReturns401(String endpoint) throws IOException {
        HttpGet get = new HttpGet(BASE_URL + endpoint);
        closeableHttpResponse = closeableHttpClient.execute(get);
        Assert.assertEquals(closeableHttpResponse.getStatusLine().getStatusCode(), 401);
    }

}