package com.viaplay.viaplay.service;

import com.viaplay.viaplay.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import static com.viaplay.viaplay.model.mapper.AlbumCoverMapper.mapToAlbumCover;


@Service
public class ArtistService {

    @Value("${api.url.musicbrainz}")
    String musicBrainzApi;

    @Value("${api.url.discogs}")
    String discogsApi;

    @Value("${api.url.cover}")
    String coverArtApi;

    private final String RELATION = "discogs";
    private final static Logger LOGGER = Logger.getLogger(ArtistService.class.getName());

    public Artist getArtist(String mbid) {

        RestTemplate restTemplate = new RestTemplate();
        String url = musicBrainzApi + mbid + "?&fmt=json&inc=url-rels+release-groups";

        Artist artist;
        try {
            artist = restTemplate.getForObject(url, Artist.class);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
           throw e;
        }

        return artist;
    }

    public ArtistDescription getDiscoInfo(Artist artist) {

        String resource = artist.getRelations().stream()
                .filter(e -> e.getRelationType().equals(RELATION))
                .map(uri -> uri.getUrl().getResource())
                .findFirst().orElse("");

        RestTemplate restTemplate = new RestTemplate();

        String discUrl = discogsApi + getDiscId(resource);
        ResponseEntity<ArtistDescription> albumDescription;

        try {
            albumDescription = restTemplate.getForEntity(discUrl, ArtistDescription.class);
        } catch (Exception e) {
            throw e;
        }

        return albumDescription.getBody();
    }

    public List<AlbumCover> getCoverInfo(List<ReleaseGroup> releaseGroups) {

        //Map<String, AlbumCover> albumCoverMap = releaseGroups.parallelStream()
        //        .collect(Collectors.toMap(ReleaseGroup::getAlbumTitle, id -> callCoverArt(id.getAlbumId()), (x1, x2) -> x1));

        List<AlbumCover> albumCovers = releaseGroups.parallelStream()
                .distinct()
                .map(releaseGroup -> callCoverArt(releaseGroup))
                .collect(Collectors.toList());

        return albumCovers;
    }

    private AlbumCover callCoverArt(ReleaseGroup releaseGroup) {

        RestTemplate restTemplate = new RestTemplate();

        String url = coverArtApi + releaseGroup.getAlbumId();
        ResponseEntity<AlbumCoverDTO> response;

        try {
            response = restTemplate.getForEntity(url, AlbumCoverDTO.class);
        } catch (Exception e) {
            LOGGER.warning("not found");
            return new AlbumCover();
        }

        Image image = response.getBody().getImages()
                .stream()
                .findFirst().get();

        return mapToAlbumCover(releaseGroup, image);
    }



    private String getDiscId(String resource) {
        String[] split = resource.split("/");
        return split[split.length - 1];
    }
}
