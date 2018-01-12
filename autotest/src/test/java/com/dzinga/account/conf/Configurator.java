package com.dzinga.account.conf;

public class Configurator {

    public String setTestZone(String zone){

        if (zone.equals("test")) {
          zone = "https://dzinga.com/";

        } else if (zone.equals("prod")) {
            zone = "http://plp.smarttelplus.eu";

        }
        return zone;
    }
}
