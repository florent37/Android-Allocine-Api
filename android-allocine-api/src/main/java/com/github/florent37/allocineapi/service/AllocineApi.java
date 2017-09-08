package com.github.florent37.allocineapi.service;

import android.util.Pair;

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
import com.github.florent37.allocineapi.model.pair.Triple;

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
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
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
    public Single<AllocineResponse> search(final String recherche, final List<String> filter, final int count, final int page) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.Q, "" + recherche.replace(" ", "+"),
                                AllocineService.FILTER, filter,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends AllocineResponse>>() {
                    @Override
                    public SingleSource<? extends AllocineResponse> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.search(recherche, ServiceSecurity.applatir(filter), count, page, pair.first, pair.second);
                    }
                })
                .compose(this.<AllocineResponse>retry());
    }

    /**
     * Recherche
     */
    public Single<AllocineResponseSmall> searchSmall(final String recherche, final List<String> filter, final int count, final int page) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.Q, "" + recherche.replace(" ", "+"),
                                AllocineService.FILTER, filter,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<AllocineResponseSmall>>() {
                    @Override
                    public SingleSource<AllocineResponseSmall> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.searchSmall(recherche, ServiceSecurity.applatir(filter), count, page, pair.first, pair.second);
                    }
                })
                .compose(this.<AllocineResponseSmall>retry());
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Informations sur un film
     */
    public Single<Movie> movie(final String idFilm, final Profile profile) {
        final String filter = FILTER_MOVIE;

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {

                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.CODE, idFilm,
                                AllocineService.PROFILE, profile.getValue(),
                                AllocineService.FILTER, filter
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends Movie>>() {
                    @Override
                    public SingleSource<? extends Movie> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.movie(idFilm, profile.getValue(), filter, pair.first, pair.second)
                                .map(new Function<AllocineResponse, Movie>() {
                                    @Override
                                    public Movie apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getMovie();
                                    }
                                });
                    }
                })
                .compose(this.<Movie>retry());
    }

    /**
     * Critiques sur un film (presse et public)
     */
    private Single<List<Review>> reviewlist(String idFilm, String filter, int count, int page) {
        final String type = "movie";

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
                })
                .compose(this.<List<Review>>retry());
    }

    /**
     * Informations sur un film
     */
    public Single<Theater> theater(final String idCinema, final String profile, final String filter) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.CODE, idCinema,
                                AllocineService.PROFILE, profile,
                                AllocineService.FILTER, filter
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends Theater>>() {
                    @Override
                    public SingleSource<? extends Theater> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.theater(idCinema, profile, filter, pair.first, pair.second)
                                .map(new Function<AllocineResponse, Theater>() {
                                    @Override
                                    public Theater apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getTheater();
                                    }
                                });
                    }
                })
                .compose(this.<Theater>retry());
    }

    /**
     * Horaires des cinémas
     */
    public Single<List<TheaterShowtime>> showtimeList(final String zip, final Date date, final int count, final int page) {
        return Single
                .create(new SingleOnSubscribe<Triple<String, String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Triple<String, String, String>> e) throws Exception {
                        Date dateToSend = date;
                        if (dateToSend == null)
                            dateToSend = new Date();
                        final String d = format(dateToSend);

                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.ZIP, zip,
                                AllocineService.DATE, d,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Triple.create(sed, sig, d));
                    }
                })
                .flatMap(new Function<Triple<String, String, String>, SingleSource<? extends List<TheaterShowtime>>>() {
                    @Override
                    public SingleSource<? extends List<TheaterShowtime>> apply(Triple<String, String, String> triple) throws Exception {
                        return allocineService.showtimelistWithZip(zip, triple.third, count, page, triple.first, triple.second)
                                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                                    @Override
                                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheaterShowtimes();
                                    }
                                });
                    }
                })
                .compose(this.<List<TheaterShowtime>>retry());
    }
    //---------------------------------------------------------------------------------------------

    public Single<List<TheaterShowtime>> showtimelistForTheater(final String code, final Date date, final int count, final int page) {

        return Single
                .create(new SingleOnSubscribe<Triple<String, String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Triple<String, String, String>> e) throws Exception {
                        Date dateToSend = date;
                        if (dateToSend == null)
                            dateToSend = new Date();
                        final String d = format(dateToSend);

                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.THEATERS, code,
                                AllocineService.DATE, d,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Triple.create(sed, sig, d));
                    }
                })
                .flatMap(new Function<Triple<String, String, String>, SingleSource<? extends List<TheaterShowtime>>>() {
                    @Override
                    public SingleSource<? extends List<TheaterShowtime>> apply(Triple<String, String, String> triple) throws Exception {
                        return allocineService.showtimelistForTheater(code, triple.third, count, page, triple.first, triple.second)
                                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                                    @Override
                                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheaterShowtimes();
                                    }
                                });
                    }
                })
                .compose(this.<List<TheaterShowtime>>retry());
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<TheaterShowtime>> showtimelistForTheaterAndMovie(final String code, final String idFilm, Date date, final int count, final int page) {

        if (date == null)
            date = new Date();
        final String d = format(date);

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.THEATERS, code,
                                AllocineService.MOVIE, idFilm,
                                AllocineService.DATE, d,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<List<TheaterShowtime>>>() {
                    @Override
                    public SingleSource<List<TheaterShowtime>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.showtimelistForTheaterAndMovie(code, idFilm, d, count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                                    @Override
                                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheaterShowtimes();
                                    }
                                });
                    }
                })
                .compose(this.<List<TheaterShowtime>>retry());
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<TheaterShowtime>> showtimeListWithMovie(final String zip, final String idFilm, Date date, final int count, final int page) {
        if (date == null)
            date = new Date();
        final String d = format(date);

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.ZIP, zip,
                                AllocineService.MOVIE, idFilm,
                                AllocineService.DATE, d,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<List<TheaterShowtime>>>() {
                    @Override
                    public SingleSource<List<TheaterShowtime>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.showtimelistWithZipAndMovie(zip, idFilm, d, count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                                    @Override
                                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheaterShowtimes();
                                    }
                                });
                    }
                })
                .compose(this.<List<TheaterShowtime>>retry());
    }

    public Single<List<TheaterShowtime>> showtimeListWithLatLng(final String lat, final String lng, Date date, final int count, final int page) {

        if (date == null)
            date = new Date();
        final String d = format(date);

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.LAT, lat,
                                AllocineService.LONG, lng,
                                AllocineService.DATE, d,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends List<TheaterShowtime>>>() {
                    @Override
                    public SingleSource<? extends List<TheaterShowtime>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.showtimelistWithLatLng(lat, lng, d, count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                                    @Override
                                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheaterShowtimes();
                                    }
                                });
                    }
                })
                .compose(this.<List<TheaterShowtime>>retry());
    }

    public Single<List<TheaterShowtime>> showtimeListWithLatLngAndMovie(final String lat, final String lng, final String radius, final String idFilm, Date date, final int count, final int page) {
        if (date == null)
            date = new Date();
        final String d = format(date);

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
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

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<List<TheaterShowtime>>>() {
                    @Override
                    public SingleSource<List<TheaterShowtime>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.showtimelistWithLatLngAndMovie(lat, lng, radius, idFilm, d, count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<TheaterShowtime>>() {
                                    @Override
                                    public List<TheaterShowtime> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheaterShowtimes();
                                    }
                                });
                    }
                })
                .compose(this.<List<TheaterShowtime>>retry());
    }

    /**
     * Informations sur une vidéo : media
     */

    public void media() {
    }

    /**
     * Informations sur une personne
     */
    public Single<PersonFull> person(final String idPerson, final String profile, final String filter) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.CODE, idPerson,
                                AllocineService.PROFILE, profile,
                                AllocineService.FILTER, filter
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<PersonFull>>() {
                    @Override
                    public SingleSource<PersonFull> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.person(idPerson, profile, filter, pair.first, pair.second)
                                .map(new Function<AllocineResponse, PersonFull>() {
                                    @Override
                                    public PersonFull apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getPerson();
                                    }
                                });
                    }
                })
                .compose(this.<PersonFull>retry());
    }

    /**
     * Filmographie d'une personne
     */
    public Single<List<Participation>> filmography(final String idPerson, final String profile, final String filter) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.CODE, idPerson,
                                AllocineService.PROFILE, profile,
                                AllocineService.FILTER, filter
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<List<Participation>>>() {
                    @Override
                    public SingleSource<List<Participation>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.filmography(idPerson, profile, filter, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<Participation>>() {
                                    @Override
                                    public List<Participation> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getPerson().getParticipation();
                                    }
                                });
                    }
                })
                .compose(this.<List<Participation>>retry());
    }

    public Single<List<Movie>> movieList(MovieListFilter filter, Profile profile, MovieListOrder order, int count, int page) {
        return movieList(Arrays.asList(filter), profile, order, count, page);
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<Movie>> movieList(List<MovieListFilter> filter, final Profile profile, final MovieListOrder order, final int count, final int page) {
        final List<String> filterString = new ArrayList<>();
        for (MovieListFilter movieListFilter : filter) {
            filterString.add(movieListFilter.getValue());
        }

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(true,
                                AllocineService.FILTER, filterString,
                                AllocineService.PROFILE, profile.getValue(),
                                AllocineService.ORDER, order.getValue(),
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends List<Movie>>>() {
                    @Override
                    public SingleSource<? extends List<Movie>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.movieList(ServiceSecurity.applatir(filterString), profile.getValue(), order.getValue(), count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<Movie>>() {
                                    @Override
                                    public List<Movie> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getMovie();
                                    }
                                });
                    }
                })
                .compose(this.<List<Movie>>retry());
    }


    //---------------------------------------------------------------------------------------------

    public Single<List<PersonFull>> starsList(PersonListFilter filter, Profile profile, int count, int page) {
        return starsList(Arrays.asList(filter), profile, count, page);
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<PersonFull>> starsList(final List<PersonListFilter> filter, final Profile profile, final int count, final int page) {
        final List<String> filterString = new ArrayList<>();
        for (PersonListFilter movieListFilter : filter) {
            filterString.add(movieListFilter.getValue());
        }

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(true,
                                AllocineService.FILTER, filterString,
                                AllocineService.PROFILE, profile.getValue(),
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends List<PersonFull>>>() {
                    @Override
                    public SingleSource<? extends List<PersonFull>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.personList(ServiceSecurity.applatir(filterString), profile.getValue(), count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<PersonFull>>() {
                                    @Override
                                    public List<PersonFull> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getPerson();
                                    }
                                });
                    }
                })
                .compose(this.<List<PersonFull>>retry());
    }

    //---------------------------------------------------------------------------------------------

    private Single<List<News>> newslist(String filter, int count, int page) {
        return newslist(Arrays.asList(filter), count, page);
    }

    private Single<List<News>> newslist(List<String> filter, int count, int page) {
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
    private Single<News> news(String idNews, String profile) {

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

    public Single<List<Theater>> theaterList(final String zip, final int count, final int page) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.ZIP, zip,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends List<Theater>>>() {
                    @Override
                    public SingleSource<? extends List<Theater>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.theaterlist(zip, count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<Theater>>() {
                                    @Override
                                    public List<Theater> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheater();
                                    }
                                });
                    }
                })
                .compose(this.<List<Theater>>retry());
    }

    //---------------------------------------------------------------------------------------------

    public Single<List<Theater>> theaterList(final String lat, final String lng, final int radius, final int count, final int page) {
        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.LAT, lat,
                                AllocineService.LONG, lng,
                                AllocineService.RADIUS, "" + radius,
                                AllocineService.COUNT, "" + count,
                                AllocineService.PAGE, "" + page
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);


                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<? extends List<Theater>>>() {
                    @Override
                    public SingleSource<? extends List<Theater>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.theaterlist(lat, lng, radius, count, page, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<Theater>>() {
                                    @Override
                                    public List<Theater> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getTheater();
                                    }
                                });
                    }
                })
                .compose(this.<List<Theater>>retry());
    }

    public Single<List<Media>> videoList(final String code, final int count) {
        final String subject = "movie:" + code;
        final String mediafmt = "mp4";

        return Single
                .create(new SingleOnSubscribe<Pair<String, String>>() {
                    @Override
                    public void subscribe(SingleEmitter<Pair<String, String>> e) throws Exception {
                        final String params = ServiceSecurity.construireParams(false,
                                AllocineService.SUBJECT, subject,
                                AllocineService.COUNT, "" + count,
                                AllocineService.MEDIAFMT, mediafmt
                        );

                        final String sed = ServiceSecurity.getSED();
                        final String sig = ServiceSecurity.getSIG(params, sed);

                        e.onSuccess(Pair.create(sed, sig));
                    }
                })
                .flatMap(new Function<Pair<String, String>, SingleSource<List<Media>>>() {
                    @Override
                    public SingleSource<List<Media>> apply(Pair<String, String> pair) throws Exception {
                        return allocineService.videoList(subject, count, mediafmt, pair.first, pair.second)
                                .map(new Function<AllocineResponse, List<Media>>() {
                                    @Override
                                    public List<Media> apply(AllocineResponse allocineResponse) throws Exception {
                                        return allocineResponse.getFeed().getMedia();
                                    }
                                });
                    }
                })
                .compose(this.<List<Media>>retry());


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


    private <T> SingleTransformer<T, T> retry() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.retryWhen(new Function<Flowable<Throwable>, Publisher<Object>>() {

                    private final int MAX_COUNT = 3;
                    private int count = 0;

                    private final int DELAY_SECOND = 10;

                    @Override
                    public Publisher<Object> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
                            @Override
                            public Publisher<?> apply(Throwable throwable) throws Exception {
                                if (count++ < MAX_COUNT && throwable instanceof HttpException) {
                                    final HttpException httpException = (HttpException) throwable;
                                    if (httpException.code() == 403) {
                                        return Flowable.timer(DELAY_SECOND, TimeUnit.SECONDS);
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
}
