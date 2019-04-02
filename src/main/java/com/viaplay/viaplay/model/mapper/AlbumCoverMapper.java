package com.viaplay.viaplay.model.mapper;

import com.viaplay.viaplay.model.AlbumCover;
import com.viaplay.viaplay.model.Image;
import com.viaplay.viaplay.model.ReleaseGroup;

public class AlbumCoverMapper {

    public static AlbumCover mapToAlbumCover(ReleaseGroup releaseGroup, Image image) {

        AlbumCover albumCover = new AlbumCover();

        albumCover.setAlbumId(releaseGroup.getAlbumId());
        albumCover.setAlbumTitle(releaseGroup.getAlbumTitle());
        albumCover.setAlbumCoverUrl(image.getImage());

        return albumCover;
    }
}
