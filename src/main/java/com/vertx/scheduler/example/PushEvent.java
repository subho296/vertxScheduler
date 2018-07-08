package com.vertx.scheduler.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;


public class PushEvent {

    private static final int MILLION = 10;

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/api/addschedule";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        int count = 0;
        while (count < MILLION) {
            HttpResponse response = client.execute(request);
            if (count % 500 == 0)
                System.out.println(count);
            count++;
        }

    }
}
