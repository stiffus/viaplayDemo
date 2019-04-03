package com.viaplay.viaplay.controller;

import com.viaplay.viaplay.model.*;
import com.viaplay.viaplay.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.viaplay.viaplay.controller.mapper.ArtistDTOmapper.mapToArtistDTO;

@RestController
@RequestMapping("artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(value = "/artistInfo/{mbid}", method = RequestMethod.GET)
    public ArtistDTO getArtist(@PathVariable("mbid") String mbid) {

        Artist artist = artistService.getArtist(mbid);
        ArtistDescription description = artistService.getDiscoInfo(artist);
        List<AlbumCover> albumCovers = artistService.getCoverInfo(artist.getReleaseGroups());

        return mapToArtistDTO(artist, description, albumCovers);

    }


}
