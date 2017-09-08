package com.github.florent37.allocineapi.service;

import com.github.florent37.allocineapi.model.*;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllocineService {

    String ALLOCINE_PARTNER_KEY = "100043982026";
    String ALLOCINE_SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";
    String APP_ID = "27405";

    String FORMAT_JSON = "json";

    String URL = "http://api.allocine.fr/";
    String USER_AGENT = "Dalvik/1.6.0 (Linux; U; Android 4.2.2; Nexus 4 Build/JDQ39E)";

    String PARTNER = "partner";
    String CODE = "code";
    String COUNT = "count";
    String ORDER = "order";
    String FILTER = "filter";
    String FORMAT = "format";
    String DEFAULT_PARAMS = PARTNER + "=" + ALLOCINE_PARTNER_KEY + "&" + CODE + "=" + APP_ID + "&" + FORMAT + "=" + FORMAT_JSON;
    String DEFAULT_PARAMS_MINUS_CODE = PARTNER + "=" + ALLOCINE_PARTNER_KEY + "&" + FORMAT + "=" + FORMAT_JSON;
    String SED = "sed";
    String SIG = "sig";
    String Q = "q";
    String PAGE = "page";
    String PROFILE = "profile";
    String ZIP = "zip";
    String LAT = "lat";
    String LONG = "long";
    String RADIUS = "radius";
    String THEATER = "theater";
    String LOCATION = "location";
    String TYPE = "type";
    String MOVIE = "movie";
    String DATE = "date";
    String THEATERS = "theaters";
    String SUBJECT = "subject";
    String MEDIAFMT = "mediafmt";

    //---------------------------------------------------------------------------------------------

    /**
     * Recherche
     */
    @GET("rest/v3/search?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> search(@Query(Q) String recherche,
                                    @Query(FILTER) String filer,
                                    @Query(COUNT) int count,
                                    @Query(PAGE) int page,
                                    @Query(SED) String sed,
                                    @Query(SIG) String sig
    );


    //---------------------------------------------------------------------------------------------

    /**
     * Recherche Small
     */
    @GET("rest/v3/search?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponseSmall> searchSmall(@Query(Q) String recherche,
                                      @Query(FILTER) String filer,
                                      @Query(COUNT) int count,
                                      @Query(PAGE) int page,
                                      @Query(SED) String sed,
                                      @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur un film
     */
    @GET("rest/v3/movie?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> movie(@Query(CODE) String code,
                           @Query(PROFILE) String profile,
                           //@Query(mediafmt)
                           @Query(FILTER) String fitler,
                           @Query(SED) String sed,
                           @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    /**
     * Critiques sur un film (presse et public)
     */
    @GET("rest/v3/reviewlist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> reviewlist(@Query(CODE) String code,
                                @Query(TYPE) String type,
                                @Query(FILTER) String fitler,
                                @Query(COUNT) int count,
                                @Query(PAGE) int page,
                                @Query(SED) String sed,
                                @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur un theater
     */
    @GET("rest/v3/theater?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> theater(@Query(CODE) String code,
                             @Query(PROFILE) String profile,
                             //@Query(mediafmt)
                             @Query(FILTER) String fitler,
                             @Query(SED) String sed,
                             @Query(SIG) String sig
    );

    //pour showtime
    //radius : rayon autour du point désigné (entre 1 et 500 km)
    //theaters : liste de codes de cinémas (séparé par une virgule, exemple: P0728,P0093)
    //location : chaîne représentant le cinéma

    /**
     * Horaires des cinémas
     */
    @GET("rest/v3/showtimelist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> showtimelistWithZip(@Query(ZIP) String zip,
                                         @Query(DATE) String date, //YYYY-MM-DD
                                         @Query(COUNT) int count,
                                         @Query(PAGE) int page,
                                         @Query(SED) String sed,
                                         @Query(SIG) String sig
    );

    @GET("rest/v3/showtimelist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> showtimelistForTheater(@Query(THEATERS) String code,
                                            @Query(DATE) String date, //YYYY-MM-DD
                                            @Query(COUNT) int count,
                                            @Query(PAGE) int page,
                                            @Query(SED) String sed,
                                            @Query(SIG) String sig
    );

    @GET("rest/v3/showtimelist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> showtimelistForTheaterAndMovie(@Query(THEATERS) String code,
                                                    @Query(MOVIE) String idFilm,
                                                    @Query(DATE) String date, //YYYY-MM-DD
                                                    @Query(COUNT) int count,
                                                    @Query(PAGE) int page,
                                                    @Query(SED) String sed,
                                                    @Query(SIG) String sig
    );

    @GET("rest/v3/showtimelist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> showtimelistWithZipAndMovie(@Query(ZIP) String zip,
                                                 @Query(MOVIE) String idFilm,
                                                 @Query(DATE) String date, //YYYY-MM-DD
                                                 @Query(COUNT) int count,
                                                 @Query(PAGE) int page,
                                                 @Query(SED) String sed,
                                                 @Query(SIG) String sig
    );

    @GET("rest/v3/showtimelist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> showtimelistWithLatLng(@Query(LAT) String lat,
                                            @Query(LONG) String lng,
                                            @Query(DATE) String date, //YYYY-MM-DD
                                            @Query(COUNT) int count,
                                            @Query(PAGE) int page,
                                            @Query(SED) String sed,
                                            @Query(SIG) String sig
    );

    @GET("rest/v3/showtimelist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> showtimelistWithLatLngAndMovie(@Query(LAT) String lat,
                                                    @Query(LONG) String lng,
                                                    @Query(RADIUS) String radius,
                                                    @Query(MOVIE) String idFilm,
                                                    @Query(DATE) String date, //YYYY-MM-DD
                                                    @Query(COUNT) int count,
                                                    @Query(PAGE) int page,
                                                    @Query(SED) String sed,
                                                    @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur une vidéo : media
     */

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur une personne
     */

    @GET("rest/v3/person?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> person(@Query(CODE) String idPerson,
                            @Query(PROFILE) String profile,
                            @Query(FILTER) String filter,
                            @Query(SED) String sed,
                            @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur une news
     */

    @GET("rest/v3/news?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> news(@Query(CODE) String idNews,
                          @Query(PROFILE) String profile,
                          @Query(SED) String sed,
                          @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    /**
     * Filmographie d'une personne
     */

    @GET("rest/v3/filmography?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> filmography(@Query(CODE) String idPerson,
                                 @Query(PROFILE) String profile,
                                 @Query(FILTER) String filter,
                                 @Query(SED) String sed,
                                 @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    @GET("rest/v3/movielist?" + DEFAULT_PARAMS)
    Single<AllocineResponse> movieList(@Query(FILTER) String filer,
                               @Query(PROFILE) String profile,
                               @Query(ORDER) String order,
                               @Query(COUNT) int count,
                               @Query(PAGE) int page,
                               @Query(SED) String sed,
                               @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------
    @GET("rest/v3/personList?" + DEFAULT_PARAMS)
    Single<AllocineResponse> personList(@Query(FILTER) String filer,
                                @Query(PROFILE) String profile,
                                @Query(COUNT) int count,
                                @Query(PAGE) int page,
                                @Query(SED) String sed,
                                @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

    @GET("rest/v3/videolist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> videoList(@Query(SUBJECT) String sbubject,
                               @Query(COUNT) int count,
                               @Query(MEDIAFMT) String mediafmt,
                               @Query(SED) String sed,
                               @Query(SIG) String sig
    );


    //---------------------------------------------------------------------------------------------

    @GET("rest/v3/newslist?" + DEFAULT_PARAMS)
    Single<AllocineResponse> newsList(@Query(FILTER) String filer,
                              @Query(COUNT) int count,
                              @Query(PAGE) int page,
                              @Query(SED) String sed,
                              @Query(SIG) String sig
    );


    //---------------------------------------------------------------------------------------------

    @GET("rest/v3/theaterlist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> theaterlist(@Query(ZIP) String zip,
                                 @Query(COUNT) int count,
                                 @Query(PAGE) int page,
                                 @Query(SED) String sed,
                                 @Query(SIG) String sig
    );

    @GET("rest/v3/theaterlist?" + DEFAULT_PARAMS_MINUS_CODE)
    Single<AllocineResponse> theaterlist(@Query(LAT) String lat,
                                 @Query(LONG) String lng,
                                 @Query(RADIUS) int radius,
                                 @Query(COUNT) int count,
                                 @Query(PAGE) int page,
                                 @Query(SED) String sed,
                                 @Query(SIG) String sig
    );

    //---------------------------------------------------------------------------------------------

}
