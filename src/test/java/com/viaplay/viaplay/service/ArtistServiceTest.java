package com.viaplay.viaplay.service;

import com.viaplay.viaplay.ViaplayApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ViaplayApplication.class)
public class ArtistServiceTest {

    @Autowired
    private ArtistService artistService;

    @Test
    public void getDiscIdTest() {
        String getId = artistService.getDiscId("ettTest/999");
        assertThat(getId).isEqualTo("999");
    }
}
