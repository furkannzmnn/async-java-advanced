import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/*
    * Bu sınıf birden fazla web sayfasını asenkron olarak indirmek için CompletableFuture kullanımını gösterir.
    * İlk olarak hangi sunucu cevap dönerse onun cevabını alır.
    * Diğer sunucuların cevaplarına bakmaz.
 */
public class WebPageQueryCompletableFuture {
    public static String getWeatherFromServerA(){ // returning result in 300 ms
        sleep(300);
        return "Partly-Sunny" + " server-A";
    }
    public static String getWeatherFromServerB(){ // returning result in 500 ms
        sleep(500);
        return "Partly-Sunny" + " server-B";
    }

    public static String getWeatherFromServerC(){ // returning result in 100 ms
        sleep(100);
        return "Partly-Sunny" + " server-C";
    }

    public static void sleep(int millis){    // extracted sleep here, and making
        try{                             // catch empty to avoid propagation of
            Thread.sleep(millis);        // exception
        }
        catch (InterruptedException ex){
            // do nothing
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<String> weatherA = CompletableFuture.supplyAsync(WebPageQueryCompletableFuture::getWeatherFromServerA)
                .applyToEither(CompletableFuture.supplyAsync(WebPageQueryCompletableFuture::getWeatherFromServerB), Function.identity())
                .applyToEither(CompletableFuture.supplyAsync(WebPageQueryCompletableFuture::getWeatherFromServerC), Function.identity());

        System.out.println("Weather information from server: " + weatherA.join());
        System.out.println("Time taken: " + (System.currentTimeMillis() - startTime));
    }
}
