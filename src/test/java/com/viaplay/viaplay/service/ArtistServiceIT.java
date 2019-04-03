package com.viaplay.viaplay.service;

import com.viaplay.viaplay.ViaplayApplication;
import com.viaplay.viaplay.model.AlbumCover;
import com.viaplay.viaplay.model.Artist;
import com.viaplay.viaplay.model.ReleaseGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ViaplayApplication.class)
public class ArtistServiceIT {

    @Autowired
    private ArtistService artistService;

    @Test
    public void getArtistInformation() {

        String mbid = "deb2225c-3781-46ac-9014-bbe94a1d86ca";

        Artist artist = artistService.getArtist(mbid);

        assertThat(artist.getArtistId()).isNotNull();
        assertThat(artist.getArtistId()).isEqualTo(mbid);
        assertThat(artist.getArtistName()).isEqualTo("Adjusted");
        assertThat(artist.getRelations()).isNotNull();
        assertThat(artist.getReleaseGroups().size()).isEqualTo(2);

    }

    @Test
    public void getCoverInfoShouldReturnAlbumCover() {

        String albumTitle = "Anthology";
        String albumId = "37906983-1005-36fb-b8e7-3a04687e6f4f";
        String type = "Album";

        ReleaseGroup releaseGroup = new ReleaseGroup();
        releaseGroup.setAlbumId(albumId);
        releaseGroup.setAlbumTitle(albumTitle);
        releaseGroup.setType(type);

        List<AlbumCover> coverInfo = artistService.getCoverInfo(Collections.singletonList(releaseGroup));

        assertThat(coverInfo.get(0).getAlbumCoverUrl()).isNotNull();
        assertThat(coverInfo.get(0).getAlbumId()).isEqualTo(albumId);
        assertThat(coverInfo.get(0).getAlbumTitle()).isEqualTo(albumTitle);
    }

}
