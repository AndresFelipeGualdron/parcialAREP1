package edu.eci.arep;

import edu.eci.arep.services.CacheService;
import edu.eci.arep.services.impl.CacheServiceImpl;
import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Spark Mean And Standard Web App
 */
public class App {

    private static final CacheService cacheService = new CacheServiceImpl();

    /**
     * Main function of the Spark App.
     *
     * @param args List of special arguments, do not have functionality.
     */
    public static void main(String[] args) {
        port(getPort());
        get("/clima", App::resultPage);
    }

    /**
     * Return the HTML of the Result Page.
     *
     * @param req The request made for that page.
     * @param res The response of that page.
     * @return A String that represents the HTML of the Result Page.
     */
    private static String resultPage(Request req, Response res) throws Exception {
        String rta;
        rta = cacheService.returnKey(req.queryParams("lugar"));
        System.out.println("rrr " + rta);
        if (rta == null) {
            rta = getResult(req.queryParams("lugar"));
            cacheService.saveKey(req.queryParams("lugar"), rta);
        }
        return rta;
    }

    /**
     * Get the Mean And Standard Deviation Of A Set Of Numbers.
     *
     * @param city city to find.
     * @return A message with the mean And standard deviation of the set of numbers or a error message in case of error.
     */
    private static String getResult(String city){
        StringBuilder rta = new StringBuilder();
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=f031a987545358c1c419c4827e0f7779");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rta.append(line);
            }
            bufferedReader.close();
        }catch (Exception e){

        }

        return rta.toString();
    }

    /**
     * Get the Port Of The Web Application.
     *
     * @return The value of the port configured in the system environment, returns 4567 by default.
     */
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}