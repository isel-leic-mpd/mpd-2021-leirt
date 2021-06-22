package mappers;

import html.Element;
import isel.leirt.mpd.weather6.model.Location;

import java.time.LocalDate;
import java.time.Month;

import static html.Dsl.*;

public class LocationMappers {

    public static Element mapToUnorderedList(Location loc)  {
        return ul(
                li( "Name: " +loc.getName()),
                li( "Country: " + loc.getCountry()),
                li( "Latitude: " + Double.toString(loc.getLatitude())),
                li("Longitude" + Double.toString(loc.getLongitude()))
        );
    }

    public static Element mapToTableRaw(Location l) {
        LocalDate now = LocalDate.now();
        Month month = now.getMonth().minus(1);
        int monthDays = month.length(now.isLeapYear());
        int year = now.getYear();
        LocalDate day1 = LocalDate.of(year, month.getValue(),1);
        LocalDate day2 = LocalDate.of(year, month.getValue(),monthDays);
        double latitude = l.getLatitude();
        double longitude = l.getLongitude();
        String urlTodayInfo = String.format("/weather/%f:%f/forecast?loc=%s",
                latitude, longitude, l.getName());

        return
                tr (
                    td(l.getName()),
                    td(l.getCountry()),
                    td(Double.toString(l.getLatitude())).align("right"),
                    td(Double.toString(l.getLongitude())).align("right"),
                    td(
                        a("Forecast", urlTodayInfo)
                    )
                );
    }
}
