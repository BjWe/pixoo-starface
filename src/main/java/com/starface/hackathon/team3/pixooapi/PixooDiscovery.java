package com.starface.hackathon.team3.pixooapi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PixooDiscovery {

    private static final String ENDPOINT = "https://app.divoom-gz.com/Device/ReturnSameLANDevice";

    public static List<Device> discover() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IllegalStateException("API antwortete mit Status " + resp.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        DeviceResponse dr = mapper.readValue(resp.body(), DeviceResponse.class);

        if (dr.ReturnCode != 0) {
            throw new IllegalStateException("Fehler von API: " + dr.ReturnMessage);
        }

        return dr.DeviceList;
    }
}
