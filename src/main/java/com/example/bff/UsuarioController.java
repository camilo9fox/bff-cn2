package com.example.bff;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final String QUERY_URL = "https://cn2cruduser.azurewebsites.net/api/graphql-query";
    private static final String MUTATION_URL = "https://cn2cruduser.azurewebsites.net/api/graphql-mutation";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();

    public UsuarioController() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @GetMapping
    public ResponseEntity<String> getUsuarios() {
        try {
            String queryBody = "{\"query\": \"query { usuarios { id username email rol { nombreRol } } }\"}";
            HttpEntity<String> request = new HttpEntity<>(queryBody, headers);
            return restTemplate.postForEntity(QUERY_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/byId")
    public ResponseEntity<String> getUsuarioById(@RequestParam String id) {
        try {
            String queryBody = String.format(
                "{\"query\": \"query { usuario(id: \\\"%s\\\") { id username email rol { nombreRol } } }\"}", 
                id
            );
            HttpEntity<String> request = new HttpEntity<>(queryBody, headers);
            return restTemplate.postForEntity(QUERY_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody String usuarioJson) {
        try {
            String mutationBody = String.format(
                "{\"query\": \"mutation CreateUser($input: UsuarioInput!) { crearUsuario(input: $input) { id username email } }\", " +
                "\"variables\": {\"input\": %s}}", 
                usuarioJson
            );
            HttpEntity<String> request = new HttpEntity<>(mutationBody, headers);
            return restTemplate.postForEntity(MUTATION_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateUsuario(@RequestBody String usuarioJson) {
        try {
            String mutationBody = String.format(
                "{\"query\": \"mutation UpdateUser($input: UsuarioUpdateInput!) { actualizarUsuario(input: $input) { id username email rol { nombreRol } } }\", " +
                "\"variables\": {\"input\": %s}}", 
                usuarioJson
            );
            HttpEntity<String> request = new HttpEntity<>(mutationBody, headers);
            return restTemplate.postForEntity(MUTATION_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUsuario(@RequestParam String id) {
        try {
            String mutationBody = String.format(
                "{\"query\": \"mutation { eliminarUsuario(id: \\\"%s\\\") }\"}", 
                id
            );
            HttpEntity<String> request = new HttpEntity<>(mutationBody, headers);
            return restTemplate.postForEntity(MUTATION_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}