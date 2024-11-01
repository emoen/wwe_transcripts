package no.wwe.transcripts.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private static final String SOLR_URL = "https://fi.solrcluster.com/solr/wrestling_test/select?q=";
    private static final String USERNAME = "ben";
    private static final String PASSWORD = "test123";


    /*@GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello, Spring Boot with Gradle!";
    }*/
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = SOLR_URL + query + "&wt=json";

        // Create headers with basic authentication
        HttpHeaders headers = new HttpHeaders();
        String auth = USERNAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the HTTP request with the headers
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode docsNode = rootNode.path("response").path("docs");
            if (docsNode.isArray()) {
                for (JsonNode docNode : docsNode) {
                    Map<String, Object> docMap = objectMapper.convertValue(docNode, Map.class);
                    results.add(docMap);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error processing JSON response");
            return "result";
        }

        model.addAttribute("results", results);
        return "result";
    }
}