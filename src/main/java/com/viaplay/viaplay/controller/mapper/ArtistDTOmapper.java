package com.viaplay.viaplay.controller.mapper;

import com.viaplay.viaplay.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class ArtistDTOmapper {

    public static ArtistDTO mapToArtistDTO(Artist artist, ArtistDescription artistDescription, List<AlbumCover> albumCovers) {

        ArtistDTO artistDTO = new ArtistDTO();

        artistDTO.setMbid(artist.getArtistId());
        artistDTO.setDescription(artistDescription.getDescription());
        artistDTO.setAlbums(mapToAlbums(albumCovers));

        return artistDTO;
    }

    private static List<AlbumDTO> mapToAlbums(List<AlbumCover> albumCovers) {

        return albumCovers.stream()
                .map(ArtistDTOmapper::mapToAlbum)
                .collect(Collectors.toList());
    }

    private static AlbumDTO mapToAlbum(AlbumCover albumCover) {

        AlbumDTO albumDTO = new AlbumDTO();

        albumDTO.setTitle(albumCover.getAlbumTitle());
        albumDTO.setId(albumCover.getAlbumId());
        albumDTO.setImage(albumCover.getAlbumCoverUrl());

        return albumDTO;
    }
}
