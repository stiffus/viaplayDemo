<h3>Viaplay demo</h3>
Is designed to provide users with artist information through a REST api that collects data from three different sources.

The handling of all the GET requests for the different sources (MusicBrainz, CoverArt Arvhieve, Discogs API) are being done in ArtistService, through calls from our ArtistController.

To be able to use this api, you need to have:

- maven 
- spring-boot

<h4>Information</h4>
To be able to fetch artist information you need to have a mbid (MusicBrainz id), that Id is what the basis for api since the first call we do is to MusicBrainz and we base the other calls on the information we recieve from here.
