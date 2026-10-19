package org.iesteis;

import org.iesteis.model.Character;
import org.iesteis.model.Response;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    static void readAPIRest() {
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://rickandmortyapi.com/api/character/?page=1"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() / 100 != 2) {
                throw new IOException("La API respondió con HTTP " + response.statusCode());
            }

            Response r = mapper.readValue(response.body(), Response.class);
            // r.getResults().forEach(System.out::println);
            GenderAndOrigin(r.getResults());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La petición fue interrumpida", e);
        }
    }
    public static void main(String[] args) {
        readAPIRest();
    }

    static void GenderAndOrigin(List<Character> characters) {
        Map<String, Map<String, Long>> GenderAndOrigin = characters.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getOrigin().get("name"),
                        Collectors.groupingBy(
                                c -> c.getGender(),
                                Collectors.counting()
                        )
                ));

        GenderAndOrigin.forEach((origen, generos) -> {
            System.out.println(origen);

            generos.forEach((genero, cantidad) ->
                    System.out.println("  " + genero + ": " + cantidad)
            );
        });
    }
}
