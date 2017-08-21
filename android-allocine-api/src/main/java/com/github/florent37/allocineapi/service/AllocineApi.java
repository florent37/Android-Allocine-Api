package com.github.florent37.allocineapi.service;

import com.github.florent37.allocineapi.model.AllocineResponse;
import com.github.florent37.allocineapi.model.AllocineResponseSmall;
import com.github.florent37.allocineapi.model.Media;
import com.github.florent37.allocineapi.model.Movie.Movie;
import com.github.florent37.allocineapi.model.News;
import com.github.florent37.allocineapi.model.Participation;
import com.github.florent37.allocineapi.model.Person.PersonFull;
import com.github.florent37.allocineapi.model.Review;
import com.github.florent37.allocineapi.model.Theater.Theater;
import com.github.florent37.allocineapi.model.Theater.TheaterShowtime;

import org.reactivestreams.Publisher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by florentchampigny on 18/04/2014.
 */
public class AllocineApi {

    public static final String FILTER_MOVIE = "movie"; //afficher les films correspondant à la recherche
    public static final String FILTER_THEATER = "theater"; //afficher les cinémas
    public static final String FILTER_PERSON = "person"; //afficher les acteurs, réalisateurs, etc. (personnes)
    public static final String PERSONLIST_FILTER_PERSON = "top"; //filtre la liste des personnalité
    public static final String FILTER_NEWS = "news"; //afficher les news
    public static final String FILTER_TVSERIES = "tvseries"; //afficher les séries TV
    public static final String MOVIELIST_FILTER_COMINGSOON = "comingsoon"; //afficher les films à paraitre
    public static final String MOVIELIST_FILTER_NOWSHOWING = "nowshowing"; //afficher les films actuellement à l'affiche
    public static final String MOVIELIST_ORDER_DATEDESC = "datedesc"; //classement anti-chronologique
    public static final String MOVIELIST_ORDER_DATEASC = "dateasc"; //classement chronologique
    public static final String MOVIELIST_ORDER_THEATERCOUNT = "theatercount"; //classement par nombre de ic_drawer_salles
    public static final String MOVIELIST_ORDER_TOPRANK = "toprank"; //classement par popularité
    public static final String PROFILE_SMALL = "small";
    public static final String PROFILE_MEDIUM = "medium";
    public static final String PROFILE_LARGE = "large";
    public static final String REVIEW_LIST_FILTER_PRESSE = "desk-press";
    public static final String REVIEW_LIST_FILTER_PUBLIC = "public";

    public static final String PROFIL_NEWS_CINEMA = "movie";
    public static final String PROFIL_NEWS_SERIE = "tvseries";
    public static final String PROFIL_NEWS_STAR = "star";
    private final AllocineService allocineService;

    public AllocineApi() {
        this(new OkHttpClient());
    }

    public AllocineApi(OkHttpClient okHttpClient) {
        allocineService = new Retrofit.Builder()
                .baseUrl(AllocineService.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(AllocineService.class);
    }

    static String format(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public AllocineService getAllocineService() {
        return allocineService;
    }

    /**
     * Recherche
     */
    public Single<AllocineResponseSmall> searchSmall(String recherche, String filter, int count, int page) {
        return searchSmall(recherche, Arrays.asList(filter), count, page);
    }

    /**
     * Recherche
     */
    public Single<AllocineResponse> search(String recherche, String filter, int count, int page) {
        return search(recherche, Arrays.asList(filter), count, page);
    }

    /**
     * Recherche
     */
    public Single<AllocineResponse> search(String recherche, List<String> filter, int count, int page) {

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.Q, "" + recherche.replace(" ", "+"),
                AllocineService.FILTER, filter,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.search(recherche, ServiceSecurity.applatir(filter), count, page, sed, sig);
    }

    /**
     * Recherche
     */
    public Single<AllocineResponseSmall> searchSmall(String recherche, List<String> filter, int count, int page) {
        final String params = ServiceSecurity.construireParams(false,
                AllocineService.Q, "" + recherche.replace(" ", "+"),
                AllocineService.FILTER, filter,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.searchSmall(recherche, ServiceSecurity.applatir(filter), count, page, sed, sig);
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur un film
     */
    public Single<Movie> movie(String idFilm, Profile profile) {
        final String filter = FILTER_MOVIE;

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.CODE, idFilm,
                AllocineService.PROFILE, profile.getValue(),
                AllocineService.FILTER, filter
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.movie(idFilm, profile.getValue(), filter, sed, sig)
                .map(new Function<AllocineResponse, Movie>() {
                    @Override
                    public Movie apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getMovie();
                    }
                });
    }

    /**
     * Critiques sur un film (presse et public)
     */
    public Single<List<Review>> reviewlist(String idFilm, String filter, int count, int page) {

        String type = "movie";

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.CODE, idFilm,
                AllocineService.TYPE, type,
                AllocineService.FILTER, filter,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.reviewlist(idFilm, type, filter, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getReview();
                    }
                });
    }

    /**
     * Informations sur un film
     */
    public Single<Theater> theater(String idCinema, String profile, String filter) {
        final String params = ServiceSecurity.construireParams(false,
                AllocineService.CODE, idCinema,
                AllocineService.PROFILE, profile,
                AllocineService.FILTER, filter
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.theater(idCinema, profile, filter, sed, sig)
                .map(new Function<AllocineResponse, Theater>() {
                    @Override
                    public Theater apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getTheater();
                    }
                });
    }

    /**
     * Horaires des cinémas
     */
    public Single<List<TheaterShowtime>> showtimelist(String zip, Date date, int count, int page) {

        if (date == null)
            date = new Date();
        String d = format(date);

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.ZIP, zip,
                AllocineService.DATE, d,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.showtimelistWithZip(zip, d, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                    @Override
                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheaterShowtimes();
                    }
                });
    }
    //---------------------------------------------------------------------------------------------

    public Single<List<TheaterShowtime>> showtimelistForTheater(String code, Date date, int count, int page) {
        if (date == null)
            date = new Date();
        final String d = format(date);

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.THEATERS, code,
                AllocineService.DATE, d,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.showtimelistForTheater(code, d, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                    @Override
                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheaterShowtimes();
                    }
                });
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<TheaterShowtime>> showtimelistForTheaterAndMovie(String code, String idFilm, Date date, int count, int page) {

        if (date == null)
            date = new Date();
        String d = format(date);

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.THEATERS, code,
                AllocineService.MOVIE, idFilm,
                AllocineService.DATE, d,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.showtimelistForTheaterAndMovie(code, idFilm, d, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                    @Override
                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheaterShowtimes();
                    }
                });
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<TheaterShowtime>> showtimelistWithMovie(String zip, String idFilm, Date date, int count, int page) {

        if (date == null)
            date = new Date();
        String d = format(date);

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.ZIP, zip,
                AllocineService.MOVIE, idFilm,
                AllocineService.DATE, d,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.showtimelistWithZipAndMovie(zip, idFilm, d, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                    @Override
                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheaterShowtimes();
                    }
                });
    }

    public Single<List<TheaterShowtime>> showtimelistWithLatLng(String lat, String lng, Date date, int count, int page) {

        if (date == null)
            date = new Date();
        String d = format(date);

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.LAT, lat,
                AllocineService.LONG, lng,
                AllocineService.DATE, d,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.showtimelistWithLatLng(lat, lng, d, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                    @Override
                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheaterShowtimes();
                    }
                });
    }

    public Single<List<TheaterShowtime>> showtimelistWithLatLngAndMovie(String lat, String lng, String radius, String idFilm, Date date, int count, int page) {

        if (date == null)
            date = new Date();
        String d = format(date);

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.LAT, lat,
                AllocineService.LONG, lng,
                AllocineService.RADIUS, radius,
                AllocineService.MOVIE, idFilm,
                AllocineService.DATE, d,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.showtimelistWithLatLngAndMovie(lat, lng, radius, idFilm, d, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                    @Override
                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheaterShowtimes();
                    }
                });
    }

    /**
     * Informations sur une vidéo : media
     */

    public void media() {
    }

    /**
     * Informations sur une personne
     */
    public Single<PersonFull> person(String idPerson, String profile, String filter) {

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.CODE, idPerson,
                AllocineService.PROFILE, profile,
                AllocineService.FILTER, filter
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.person(idPerson, profile, filter, sed, sig)
                .map(new Function<AllocineResponse, PersonFull>() {
                    @Override
                    public PersonFull apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getPerson();
                    }
                });
    }

    /**
     * Filmographie d'une personne
     */
    public Single<List<Participation>> filmography(String idPerson, String profile, String filter) {

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.CODE, idPerson,
                AllocineService.PROFILE, profile,
                AllocineService.FILTER, filter
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.filmography(idPerson, profile, filter, sed, sig).map(new Function<AllocineResponse, List<Participation>>() {
            @Override
            public List<Participation> apply(AllocineResponse allocineResponse) throws Exception {
                return allocineResponse.getPerson().getParticipation();
            }
        });

    }

    public Single<List<Movie>> movieList(MovieListFilter filter, Profile profile, MovieListOrder order, int count, int page) {
        return movieList(Arrays.asList(filter), profile, order, count, page);
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<Movie>> movieList(List<MovieListFilter> filter, Profile profile, MovieListOrder order, int count, int page) {
        final List<String> filterString = new ArrayList<>();
        for (MovieListFilter movieListFilter : filter) {
            filterString.add(movieListFilter.getValue());
        }

        final String params = ServiceSecurity.construireParams(true,
                AllocineService.FILTER, filterString,
                AllocineService.PROFILE, profile.getValue(),
                AllocineService.ORDER, order.getValue(),
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.movieList(ServiceSecurity.applatir(filterString), profile.getValue(), order.getValue(), count, page, sed, sig)
                .map(new Function<AllocineResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getMovie();
                    }
                })
                .compose(this.<List<Movie>>retry());
    }

    private <T>  SingleTransformer<T, T> retry() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.retryWhen(new Function<Flowable<Throwable>, Publisher<Object>>() {
                    @Override
                    public Publisher<Object> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
                            @Override
                            public Publisher<?> apply(Throwable throwable) throws Exception {
                                if(throwable instanceof HttpException){
                                    final HttpException httpException = (HttpException) throwable;
                                    if(httpException.code() == 403){
                                        return Flowable.timer(3, TimeUnit.SECONDS);
                                    }
                                }
                                return Flowable.error(throwable);
                            }
                        });
                    }
                });
            }
        };
    }


    //---------------------------------------------------------------------------------------------

    public Single<List<PersonFull>> starsList(PersonListFilter filter, Profile profile, int count, int page) {
        return starsList(Arrays.asList(filter), profile, count, page);
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<PersonFull>> starsList(List<PersonListFilter> filter, Profile profile, int count, int page) {
        final List<String> filterString = new ArrayList<>();
        for (PersonListFilter movieListFilter : filter) {
            filterString.add(movieListFilter.getValue());
        }

        final String params = ServiceSecurity.construireParams(true,
                AllocineService.FILTER, filterString,
                AllocineService.PROFILE, profile.getValue(),
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.personList(ServiceSecurity.applatir(filterString), profile.getValue(), count, page, sed, sig)
                .map(new Function<AllocineResponse, List<PersonFull>>() {
                    @Override
                    public List<PersonFull> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getPerson();
                    }
                });
    }

    //---------------------------------------------------------------------------------------------

    @Deprecated
    public Single<List<News>> newslist(String filter, int count, int page) {
        return newslist(Arrays.asList(filter), count, page);
    }

    @Deprecated
    public Single<List<News>> newslist(List<String> filter, int count, int page) {
        final String params = ServiceSecurity.construireParams(true,
                AllocineService.FILTER, filter,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );


        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.newsList(ServiceSecurity.applatir(filter), count, page, sed, sig)
                .map(new Function<AllocineResponse, List<News>>() {
                    @Override
                    public List<News> apply(AllocineResponse allocineResponse) throws Exception {
                        return null;
                    }
                });


    }

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur une personne
     */
    @Deprecated
    public Single<News> news(String idNews, String profile) {

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.CODE, idNews,
                AllocineService.PROFILE, profile
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.news(idNews, profile, sed, sig)
                .map(new Function<AllocineResponse, News>() {
                    @Override
                    public News apply(AllocineResponse allocineResponse) throws Exception {
                        return null;
                    }
                });
    }

    public Single<List<Theater>> theaterlist(String zip, int count, int page) {
        final String params = ServiceSecurity.construireParams(false,
                AllocineService.ZIP, zip,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.theaterlist(zip, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<Theater>>() {
                    @Override
                    public List<Theater> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheater();
                    }
                });
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<Theater>> theaterlist(String lat, String lng, int radius, int count, int page) {
        final String params = ServiceSecurity.construireParams(false,
                AllocineService.LAT, lat,
                AllocineService.LONG, lng,
                AllocineService.RADIUS, "" + radius,
                AllocineService.COUNT, "" + count,
                AllocineService.PAGE, "" + page
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.theaterlist(lat, lng, radius, count, page, sed, sig)
                .map(new Function<AllocineResponse, List<Theater>>() {
                    @Override
                    public List<Theater> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getTheater();
                    }
                });
    }

    public Single<List<Media>> videoList(String code, int count) {

        String subject = "movie:" + code;
        String mediafmt = "mp4";

        final String params = ServiceSecurity.construireParams(false,
                AllocineService.SUBJECT, subject,
                AllocineService.COUNT, "" + count,
                AllocineService.MEDIAFMT, mediafmt
        );

        final String sed = ServiceSecurity.getSED();
        final String sig = ServiceSecurity.getSIG(params, sed);

        return allocineService.videoList(subject, count, mediafmt, sed, sig)
                .map(new Function<AllocineResponse, List<Media>>() {
                    @Override
                    public List<Media> apply(AllocineResponse allocineResponse) throws Exception {
                        return allocineResponse.getFeed().getMedia();
                    }
                });
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Pour l'instant on ne s'interesse pas aux series
     */
    public void tvseries() {
    }

    //---------------------------------------------------------------------------------------------

    public void season() {
    }

    public void episode() {
    }

    //---------------------------------------------------------------------------------------------

    public enum MovieListFilter {
        COMING_SOON(MOVIELIST_FILTER_COMINGSOON),
        NOW_SHOWING(MOVIELIST_FILTER_NOWSHOWING);

        private final String value;

        MovieListFilter(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Profile {
        SMALL(PROFILE_SMALL),
        MEDIUM(PROFILE_MEDIUM),
        LARGE(PROFILE_LARGE);


        private final String value;

        Profile(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum MovieListOrder {

        DATEDESC(MOVIELIST_ORDER_DATEDESC),
        DATEASC(MOVIELIST_ORDER_DATEASC),
        THEATERCOUNT(MOVIELIST_ORDER_THEATERCOUNT),
        TOPRANK(MOVIELIST_ORDER_TOPRANK);

        private final String value;

        MovieListOrder(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PersonListFilter {
        TOP(PERSONLIST_FILTER_PERSON);

        private final String value;

        PersonListFilter(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class NetworkException extends Exception {
    }




}
