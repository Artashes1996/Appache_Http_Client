import org.apache.http.Header;
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
import java.util.Arrays;
import java.util.List;

public class Headers {

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
    public void checkServer() throws IOException {
        HttpGet get = new HttpGet(BASE_URL);
        closeableHttpResponse = closeableHttpClient.execute(get);
        String headerValue = getHeader(closeableHttpResponse, "Server");
        Assert.assertEquals(headerValue,"Github");
    }

    private String getHeader(CloseableHttpResponse closeableHttpResponse, String headerName) {
        //Get all headers
        Header[] headers = closeableHttpResponse.getAllHeaders();
        List<Header> httpHeaders = Arrays.asList(headers);
        String returnHeader = "";

        //Loop for over a
        for (Header header : httpHeaders) {
            if (headerName.equalsIgnoreCase(header.getName())) {
                returnHeader = header.getValue();
            }

         //If no header - found exception
            if (returnHeader.isEmpty()) {
                throw new RuntimeException("Didn't find the following header " + headerName);
            }
        }
        return returnHeader;
    }


private String getHeaderOtherWay(HttpResponse response, final String headerName) {
    List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

    Header matchedHeader = httpHeaders.stream()
            .filter(header -> headerName.equalsIgnoreCase(header.getName()))
            .findFirst().orElseThrow(() ->  new RuntimeException("Didn't find the following header " + headerName));
    return matchedHeader.getValue();
}
}