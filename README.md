<h3>Viaplay demo</h3>
Is designed to provide users with artist information through a REST api that collects data from three different sources.

The handling of all the GET requests for the different sources (MusicBrainz, CoverArt Arvhieve, Discogs API) are being done in ArtistService, through calls from our ArtistController.

To be able to use this api, you need to have:

- maven 
- spring-boot
- Java 10

## Information
To be able to fetch artist information you need to have a mbid (MusicBrainz id),
that Id is what the basis for api since the first call we do is to MusicBrainz and we base the other calls on the information we recieve from here.

## Build and Run
After cloning the repository, make sure that you are standing in the project root folder (viaplay/viaplay) and then you do a
``` mvn clean install``` to run the tests and download the necessary packages.

Start the application by typing
``` mvn spring-boot:run ```

Now you should be able to either use postman or you web-browser to access the api.

```http://localhost:8090/artist/artistInfo/{mbid}```

## My thoughts while building the api

I thought about several different ways to structure the project, like creating several services, one for each api call and have some sort of "aggregator" service calling each one of them and collecting data from the sources.
I also wanted to do async calls for cover and create different threads for the CoverArt Archive call since that one was the slowest, I tried this but couldn't manage to get it to work the way I wanted so I scipped that idea and relied more on a cache to hopefully get the speed right at least the second time someone calls for a particular artist.

## Problems

So the real problem was to find a way to get some speed in the application to be able to handle all the requests, to
be be able to to that in a real world situation with current solution we would probably need to "warm up" the cache by create a program that loads alot of artist before the application become available to the users.

## Shortcuts

The biggest shortcut I took while building this application was not using multi threading in combination with some sort of async operation to get more "speedy" calls, the reason was simply because I would need a bit more time to get it to work the way I wanted.

I feel like I should have provided more tests, integration-tests and also some more unit tests since I found a few bugs while doing this i feel that if I had more time I probably could find more bugs.
And also to feel confident in the future about refactor the api.

## Tools of choice

So I choosed to use spring boot, mostly since I have been writing a few applications with it before.
I feels very easy to have some sort of structure in a spring boot project, and I thought that the we build things with services gave alot of good opotions for this type of application.

Maven for building, wasn't really important for this project, the reason I use it is because i feel more comfortable with it than gradle for examlple.

## Different artist mbid's to use
```
f27ec8db-af05-4f36-916e-3d57f91ecf5e
deb2225c-3781-46ac-9014-bbe94a1d86ca
53b106e7-0cc6-42cc-ac95-ed8d30a3a98e
0383dadf-2a4e-4d10-a46a-e9e041da8eb3
f90e8b26-9e52-4669-a5c9-e28529c47894
