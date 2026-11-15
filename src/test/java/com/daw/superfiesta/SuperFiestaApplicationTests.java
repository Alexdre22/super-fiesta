
package com.daw.superfiesta;

import com.daw.superfiesta.service.AnimeExternalProvider;
import com.daw.superfiesta.service.PerfilDataProvider;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class SuperFiestaApplicationTests {

    @MockitoBean
    private PerfilDataProvider perfilProvider;

    @MockitoBean
    private AnimeExternalProvider animeProvider;

    @Test
    void contextLoads() {
    }
}
