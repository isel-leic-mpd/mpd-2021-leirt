package mappers;

import html.Element;
import isel.leirt.mpd.weather6.model.DayInfo;
import isel.leirt.mpd.weather6.model.DayInfo;


import static html.Dsl.*;

public class DaysMappers {
    private static String[] phase_names = {
        "new",
        "young",
        "waxing_crescent",
        "waxing_quarter",
        "waxing_gibbous",
        "full",
        "waning_gibbous",
        "waning_crescent",
        "waning_quarter",
        "old"
    };


    private static String phase_to_img(double phase) {
       return phase_names[(int) (phase*10)];
    }

    public static Element mapToTableRaw(DayInfo d) {
        return
            tr (
                td(d.getDate().toString()),
                td(
                    img("https://openweathermap.org/img/w/" + d.icon() + ".png", "Weather icon")
                ),

                td(toStr(d.getMinTemp())).align("right"),
                td(toStr(d.getMaxTemp())).align("right"),

                td(
                    img("/images/moon_phases/" + phase_to_img(d.getMoonPhase()) + ".png", "Moon phase")
                        .addAtribute("width", "30px")
                        .addAtribute("height", "30px")
                    ),

                // td(toStr(d.getMoonPhase())),

                td(d.getSunrise().toString()).align("right"),
                td(d.getSunset().toString()).align("right")
            );
    }
}
