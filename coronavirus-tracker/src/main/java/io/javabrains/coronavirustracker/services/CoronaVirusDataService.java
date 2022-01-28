package io.javabrains.coronavirustracker.services;

import io.javabrains.coronavirustracker.models.LocationStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String Corona_Data_Url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStat> allStat = new ArrayList<LocationStat>();

    public List<LocationStat> getAllStat() {
        return allStat;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCoronaData() throws IOException, InterruptedException {
        List<LocationStat> newStat = new ArrayList<LocationStat>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Corona_Data_Url)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStat locationStat = new LocationStat();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCaseCount = Integer.parseInt(record.get(record.size()-1));
            int previousDayCount = Integer.parseInt(record.get(record.size()-2));
            locationStat.setLatestCases(latestCaseCount);
            locationStat.setDiffFromPast(latestCaseCount- previousDayCount);
            //System.out.println(state);
            //System.out.println(locationStat);
            newStat.add(locationStat);
        }
        this.allStat = newStat;
    }
}
