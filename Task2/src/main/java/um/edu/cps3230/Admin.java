package um.edu.cps3230;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Admin {
    public void uploadAlertOfType(String alertType) throws IOException {
        int type = Integer.parseInt(alertType);

        String data1 = "{\n  \"alertType\": ";
        String data2 = " ," +
                "\n  \"heading\":\"Test Heading\"," +
                "\n  \"description\":\"Test Description\"," +
                "\n  \"url\":\"Test Url\"," +
                "\n  \"imageUrl\":\"https://upload.wikimedia.org/wikipedia/commons/1/11/Test-Logo.svg\"," +
                "\n  \"postedBy\":\"c2f7a631-9e15-43b3-82ee-b63f2b8e194f\"," +
                "\n  \"priceInCents\":10000\n}";

        String data = data1.concat(String.valueOf(type)).concat(data2);

        DELETE();

        POST(data);
    }

    public void uploadAlerts(int i) throws IOException {
        String data = "{\n  \"alertType\":1," +
                "\n  \"heading\":\"Test Heading\"," +
                "\n  \"description\":\"Test Description\"," +
                "\n  \"url\":\"Test Url\"," +
                "\n  \"imageUrl\":\"https://upload.wikimedia.org/wikipedia/commons/1/11/Test-Logo.svg\"," +
                "\n  \"postedBy\":\"c2f7a631-9e15-43b3-82ee-b63f2b8e194f\"," +
                "\n  \"priceInCents\":10000\n}";

        DELETE();

        for (int j = 0; j < i; j++) {
            POST(data);
        }
    }

    public void POST(String data) throws IOException {
        URL url = new URL("https://api.marketalertum.com/Alert");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setRequestMethod("POST");
        http.setDoOutput(true);

        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();

        stream.write(out);

        System.out.println(http.getResponseCode() + " " + data + " " + http.getResponseMessage());

        http.disconnect();
    }

    public void DELETE() throws IOException {
        URL url = new URL("https://api.marketalertum.com/Alert?userId=c2f7a631-9e15-43b3-82ee-b63f2b8e194f");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setRequestMethod("DELETE");

        http.setRequestProperty("Accept", "*/*");

        System.out.println(http.getResponseCode() + " "  + http.getResponseMessage());

        http.disconnect();
    }
}