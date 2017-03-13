# Project 1 - Flickster

Flickster shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: 4 hours spent for base requirement

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current movies** from the Movie Database API
* [X] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [X] For each movie displayed, user can see the following details:
  * [X] Title, Poster Image, Overview (Portrait mode)
  * [X] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [X] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
       As the screen is scrolled up, the "image" space is first filled with a blue on white "flowery" image for a brief period.
* [X] Improved the user interface through styling and coloring.
        The more popular movies have a red background with green/yellow and white texts.

The following **bonus** features are implemented:

* [X] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
        A new activity is spawned to show these features. The rating is mapped from 1 to 10 to 5 stars.
        The background color is grey.
* [X] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
        Heterogenous view holders have been implemented.
* [X] Allow video trailers to be played in full-screen using the YouTubePlayerView.
        Included youTube player has a purple border.
    * [X] Overlay a play icon for videos that can be played.
        Included play icon.
    * [X] More popular movies should start a separate activity that plays the video immediately.
        A separate activity kicks off the video play -- issue with youtube key fetch due to ASYNC OkHTTP access
    * [X] Less popular videos rely on the detail page should show ratings and a YouTube preview.
        A different activity is first called to show the ratings and backdrop image, (no play icon) which is clickable
        to take the viewer to Youtube preview in yet another activity & screen
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
        Did not want to do this a gradle update seemed to break my setup.
* [X] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
        All posters and background images, as well as borders on "Title buttons" are rounded corners
* [X] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) networking libraries.
        Have used OkHttp everywhere ( with an if -- boolean-- for switch (set to OkHttp) to Async if needed)

The following **additional** features are implemented:

* [X] List anything else that you can get done to improve the app functionality!

Improve one issue with in thread (ASYNC) executions for youtube key fetch.
(Unable to implement the client request execut function with youtube key access in spite
of researching with code path documentation and other similar documents on line. )

Add Butterknife annotation, much earlier in the project so that the gradle environment
does not need to update at the 11 th hour.

Genymotion keeps crashing frequently. I used my phone for a lot of debugging and it went a bit faster.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

.
http://i.imgur.com/pfL5Frw.gif

<iframe class="imgur-embed" width="100%" height="654" frameborder="0" src="http://i.imgur.com/pfL5Frw.gifv#embed"></iframe>

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Video guide provided by codepath is very useful. 
Did not have any problems implementing base requirements plus a couple of optionals.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [2017] [HK]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
