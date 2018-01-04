# Android Allocine Api
Allocine Api for Android
Made with Retrofit2, usable with RxJava2


<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>


```java
allocineApi.movieList(AllocineApi.MovieListFilter.NOW_SHOWING, AllocineApi.Profile.SMALL, AllocineApi.MovieListOrder.TOPRANK, 20, 1)
                
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        //display now showing movies
                    }
                });
```

# Download

[ ![Download](https://api.bintray.com/packages/florent37/maven/android-allocine-api/images/download.svg) ](https://bintray.com/florent37/maven/android-allocine-api/_latestVersion)
```java
dependencies {
    compile 'com.github.florent37:android-allocine-api:1.0.0'
}
```

# Init

```
final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
final AllocineApi allocineApi = new AllocineApi(okHttpClient);
```

# Methods

## Movie List

```java
allocineApi.movieList(AllocineApi.MovieListFilter.NOW_SHOWING, AllocineApi.Profile.SMALL, AllocineApi.MovieListOrder.TOPRANK, numberElementsPerPage, page)
allocineApi.movieList(AllocineApi.MovieListFilter.COMING_SOON, AllocineApi.Profile.SMALL, AllocineApi.MovieListOrder.DATEDESC, numberElementsPerPage page)
```

## Movie (movieId)

```java
allocineApi.movie(movieId, AllocineApi.PROFILE_SMALL)
allocineApi.movie(movieId, AllocineApi.PROFILE_MEDIUM)
allocineApi.movie(movieId, AllocineApi.PROFILE_LARGE)
```

## Search 

### Movie
### Star

## Star List

```java
allocineApi.starsList(PersonListFilter.TOP, AllocineApi.PROFILE_SMALL, numberElementsPerPage, page)
```

## Filmography (starId)

## Theater

## TheaterShowtime 

# Disclaimer

This library is just a fork of the PHP library [https://github.com/gromez/allocine-api](https://github.com/gromez/allocine-api)

I did not openend Allocine APK to get the sources and get the API

# Credits   

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

Blog : [http://www.tutos-android-france.com/](http://www.www.tutos-android-france.com/)


<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>


License
--------

    Copyright 2017 Florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
