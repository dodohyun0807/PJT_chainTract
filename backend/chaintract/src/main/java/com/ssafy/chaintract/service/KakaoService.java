package com.ssafy.chaintract.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.chaintract.domain.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class KakaoService {

    public User getUserInfoByToken(String accessToken){

        final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);

        // add header
        post.addHeader("Authorization", "Bearer " + accessToken);

        JsonNode returnNode = null;

        try {
            final HttpResponse response = client.execute(post);
            final int responseCode = response.getStatusLine().getStatusCode();

            System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
            System.out.println("Response Code : " + responseCode);

            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }

        String id = returnNode.path("id").asText();
        String name = returnNode.path("properties").path("nickname").asText();
        String email = returnNode.path("kakao_account").path("email").asText();

        return User.builder()
                .socialId(id)
                .name(name)
                .email(email)
                .build();

    }
}
