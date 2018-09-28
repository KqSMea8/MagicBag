package org.github.helixcs.java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Java11NewFeatures {
    private static void  java11HttpClient() throws  Exception{
        var requests = HttpRequest.newBuilder()
                .uri(URI.create("http://helixcs.me"))
                .GET()
                .build();
        var client = java.net.http.HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(requests,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }


    private static void asyncHttpClient() throws Exception{
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://helixcs.me"))
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        var futureResponse  = client.sendAsync(httpRequest,HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);

        System.out.println(futureResponse.get());
    }
    public static void main(String[] args) throws Exception{
//        java11HttpClient();
        asyncHttpClient();
    }
}
