package com.lxz;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static void main(String[] args) throws Exception{


        testMerge();
    }

    private static void testMerge() throws Exception{
        Observable<Long> fast = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> slow = Observable.interval(3, TimeUnit.SECONDS);

        Observable<Long> clock = Observable.merge(fast,slow
        );

        clock.subscribe(tick-> System.out.println(new Date()));

        TimeUnit.MINUTES.sleep(10);
    }

    private static void testJust1() {
        Observable<String> hello = Observable.just("Hello", "World");
        hello.subscribe((item) ->
                log.info("{}", item));
    }

    private static void testJust2(){
        List<String> words = Arrays.asList(
                "the",
                "quick",
                "brown",
                "fox",
                "jumped",
                "over",
                "the",
                "lazy",
                "dog"
        );

        Observable.fromIterable(words)
                .flatMap(word -> Observable.fromArray(word.split("")))
                .distinct().sorted()
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println);

        Observable.fromIterable(words)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count)->String.format("%2d. %s", count, string))
                .subscribe(System.out::println);
    }

    private static void testJust() {
        List<String> words = Arrays.asList(
                "the",
                "quick",
                "brown",
                "fox",
                "jumped",
                "over",
                "the",
                "lazy",
                "dog"
        );

        Observable.fromIterable(words)
                .subscribe((item) -> log.info("{}",item));
    }

    private static boolean isSlowTickTime() {
        return LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY ||
                LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
