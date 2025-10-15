package com.hooper.ugg.lightview.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LightSpeedFetcher {

    @Value("${lightspeed.login-url}")
    private String loginUrl;

    @Value("${lightspeed.data-url}")
    private String dataApi;

    @Value("${lightspeed.username}")
    private String username;

    @Value("${lightspeed.password}")
    private String password;

    @Value("${lightspeed.domain-prefix}")
    private String domainPrefix;

    private final ObjectMapper objectMapper;

//    @PostConstruct
//    public void fetch() throws Exception {
//        CookieStore cookieStore = new BasicCookieStore();
//
//        try (CloseableHttpClient httpClient = HttpClients.custom()
//                .setDefaultCookieStore(cookieStore)
//                .build()) {
//
//            // 登录
//            HttpPost loginPost = new HttpPost(loginUrl);
//            loginPost.setHeader("Content-Type", "application/json");
//
//            Map<String, String> payload = Map.of(
//                    "username", username,
//                    "password", password,
//                    "domainPrefix", domainPrefix
//            );
//            loginPost.setEntity(new StringEntity(objectMapper.writeValueAsString(payload), StandardCharsets.UTF_8));
//
//            httpClient.execute(loginPost).close();
//
//            // 请求数据
//            HttpGet getData = new HttpGet(dataApi);
//            getData.setHeader("Accept", "application/json");
//
//            String response = httpClient.execute(getData, httpResponse ->
//                    EntityUtils.toString(httpResponse.getEntity()));
//
//            System.out.println("API返回: " + response);
//
//            // TODO: 软解成实体 -> 写入数据库
//        }
//    }
}
