package server;

import javax.xml.ws.Endpoint;
import service.DocTime;

public class ServeurJWS {
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8084/";
        Endpoint.publish(url, new DocTime());
        System.out.println("Web service déployé sur " + url);
    }
}
