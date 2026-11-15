package com.daw.superfiesta.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class ExternalApiService implements AnimeExternalProvider {

    private final RestClient restClient;

    public ExternalApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String obtenerTituloAnime(Long animeId) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri("/anime/{id}", animeId)
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.get("data") instanceof Map<?, ?> data) {
                Object title = data.get("title");
                if (title != null) {
                    return title.toString();
                }
            }
        } catch (Exception e) {
            // Fallback por tolerancia a fallos si Jikan cae o el ID no existe en MAL
        }
        return "Anime Externo #" + animeId + " (Modo Offline)";
    }
}
