import java.time.LocalDate;
import java.time.LocalTime;

public record Match(
        Gruppe gruppe,
        Integer spieltag,
        LocalDate datum,
        LocalTime uhrzeit,
        String heimMannschaft,
        String gastMannschaft,
        Integer toreHeim,
        Integer toreGast,
        Integer toreHeimHZ,
        Integer toreGastHZ) {
    @Override
    public String toString() {
        return String.format(
                "%s %d %s %s %s vs. %s %d:%d %d:%d",
                gruppe.getName(),
                spieltag,
                datum,
                uhrzeit,
                heimMannschaft,
                gastMannschaft,
                toreHeim,
                toreGast,
                toreHeimHZ,
                toreGastHZ);
    }

    public String getWinner() {
        return toreHeim > toreGast ? heimMannschaft
                : toreGast > toreHeim ? gastMannschaft
                        : "Unentschieden";
    }
}
