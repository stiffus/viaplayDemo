package com.viaplay.viaplay.service;

import com.viaplay.viaplay.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.viaplay.viaplay.model.mapper.AlbumCoverMapper.mapToAlbumCover;


@Service
public class ArtistService {

    @Value("${api.url.musicbrainz}")
    String musicBrainzApi;

    @Value("${api.url.discogs}")
    String discogsApi;

    @Value("${api.url.cover}")
    String coverArtApi;

    private RestTemplate restTemplate;

    private final String RELATION = "discogs";
    private final String TYPE = "Album";

    private final Logger LOGGER = LoggerFactory.getLogger(ArtistService.class);

    public ArtistService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable("artist")
    public Artist getArtist(String mbid) {

        String url = musicBrainzApi + mbid + "?&fmt=json&inc=url-rels+release-groups";

        Artist artist = new Artist();
        try {
            artist = restTemplate.getForObject(url, Artist.class);
        } catch (HttpClientErrorException.BadRequest br) {
            LOGGER.error("Not a valid mbid " + mbid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return artist;
    }

    public ArtistDescription getDiscoInfo(Artist artist) {

        String resource = artist.getRelations().stream()
                .filter(e -> e.getRelationType().equals(RELATION))
                .map(uri -> uri.getUrl().getResource())
                .findFirst().orElse("");

        String discUrl = discogsApi + getDiscId(resource);
        ResponseEntity<ArtistDescription> albumDescription;

        try {
            albumDescription = restTemplate.getForEntity(discUrl, ArtistDescription.class);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Did not find any matching artist-description for id " + getDiscId(resource));
            return new ArtistDescription();
        } catch (Exception e) {
            LOGGER.error("There was an error with message " + e.getMessage());
            return new ArtistDescription();
        }

        return albumDescription.getBody();
    }

    @Cacheable("albumCover")
    public List<AlbumCover> getCoverInfo(List<ReleaseGroup> releaseGroups) {

        return releaseGroups.stream()
                .filter(type -> type.getType().equals(TYPE))
                .map(releaseGroup -> callCoverArt(releaseGroup))
                .collect(Collectors.toList());
    }

    private AlbumCover callCoverArt(ReleaseGroup releaseGroup) {

        String url = coverArtApi + releaseGroup.getAlbumId();
        AlbumCoverDTO albumCoverDTO = new AlbumCoverDTO();
        ResponseEntity<AlbumCoverDTO> response = new ResponseEntity<>(albumCoverDTO, HttpStatus.NO_CONTENT);

        try {
            response = restTemplate.getForEntity(url, AlbumCoverDTO.class);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Did not find any album-cover for id " + releaseGroup.getAlbumId());
        } catch (Exception e) {
            LOGGER.error("Error " + e.getMessage() + " is thrown for mbid " + releaseGroup.getAlbumId());
        }

        Image image = new Image();
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            response.getBody().getImages()
                    .stream()
                    .filter(Image::isFrontCover)
                    .map(Image::getImage)
                    .forEach(image1 -> image.setImage(image1));
        }


        return mapToAlbumCover(releaseGroup, image);
    }

    protected String getDiscId(String resource) {
        String[] split = resource.split("/");
        return split[split.length - 1];
    }
}
