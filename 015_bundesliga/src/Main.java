import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        var matches = FileReader.readMatchesFromFile(Path.of("./files/bundesliga_23_24.csv"));
        var favourite = "RB Salzburg";

        Predicate<Match> isFavoriteTeam = match -> List.of(match.heimMannschaft(), match.gastMannschaft())
                .contains(favourite);
        Predicate<Match> isGesamtGruppe = match -> match.gruppe() == Gruppe.GESAMT;
        Predicate<Match> isMeisterGruppe = match -> match.gruppe() == Gruppe.MEISTER;
        Predicate<Match> isDraw = match -> match.getWinner().equals("Unentschieden");
        Predicate<Match> isWin = match -> match.getWinner().equals(favourite);
        Predicate<Match> isZeroZero = match -> match.toreHeim() == 0 && match.toreGast() == 0;
        Predicate<Match> isHighScoring = match -> Math.max(match.toreHeim(), match.toreGast()) > 5;
        Predicate<Match> isComeback = match -> (match.toreHeimHZ() > match.toreGastHZ()
                && match.toreHeim() < match.toreGast())
                || (match.toreHeimHZ() < match.toreGastHZ() && match.toreHeim() > match.toreGast());

        System.out.println("a) Alle Spiele des Lieblingsvereins:");
        matches.stream()
                .filter(isFavoriteTeam)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("b) Alle Spiele des Vereins in der Gesamtgruppe:");
        matches.stream()
                .filter(isFavoriteTeam.and(isGesamtGruppe))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("c) Hat der Verein in der Meistergruppe gespielt?");
        System.out.println(matches.stream()
                .anyMatch(isFavoriteTeam.and(isMeisterGruppe)));
        System.out.println();

        System.out.println("d) Alle in der Gesamtgruppe gewonnenen Spiele des Vereins:");
        matches.stream()
                .filter(isFavoriteTeam.and(isGesamtGruppe).and(isWin))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("e) Alle Unentschieden in der Gesamtgruppe des Vereins:");
        matches.stream()
                .filter(isFavoriteTeam.and(isGesamtGruppe).and(isDraw))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("f) Alle 0:0-Spiele des Vereins:");
        matches.stream()
                .filter(isFavoriteTeam.and(isZeroZero))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("g) Anzahl der gewonnenen Spiele der Mannschaft:");
        System.out.println(matches.stream()
                .filter(isFavoriteTeam.and(isWin))
                .count());
        System.out.println();

        System.out.println("h) Anzahl der erzielten Tore der Mannschaft:");
        System.out.println(matches.stream()
                .filter(isFavoriteTeam)
                .mapToInt(match -> match.heimMannschaft().equals(favourite) ? match.toreHeim() : match.toreGast())
                .sum());
        System.out.println();

        System.out.println("i) Gesamte Torbilanz der Mannschaft:");
        System.out.println(matches.stream()
                .filter(isFavoriteTeam)
                .mapToInt(match -> match.heimMannschaft().equals(favourite)
                        ? match.toreHeim() - match.toreGast()
                        : match.toreGast() - match.toreHeim())
                .sum());
        System.out.println();

        System.out.println("j) Punkte in der Gesamtgruppe:");
        System.out.println(matches.stream()
                .filter(isFavoriteTeam.and(isGesamtGruppe))
                .mapToInt(match -> match.getWinner().equals(favourite)
                        ? 3
                        : match.getWinner().equals("Unentschieden")
                                ? 1
                                : 0)
                .sum());
        System.out.println();

        System.out.println("k) Spiele am 20. Spieltag:");
        matches.stream()
                .filter(match -> match.spieltag() == 20)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("l) Spiele im April:");
        matches.stream()
                .filter(match -> match.datum().getMonthValue() == 4)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("m) Spiele mit Tordifferenz größer als 5:");
        matches.stream()
                .filter(match -> Math.abs(match.toreHeim() - match.toreGast()) > 5)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("n) Anzahl Spiele mit mehr als 5 Toren einer Mannschaft in der Gesamtgruppe:");
        System.out.println(matches.stream()
                .filter(isGesamtGruppe.and(isHighScoring))
                .count());
        System.out.println();

        System.out.println("o) Spiele mit gedrehtem Ergebnis nach der 1. Halbzeit:");
        matches.stream()
                .filter(isComeback)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("p) Mannschaften der Qualifikationsgruppe:");
        matches.stream()
                .filter(match -> match.gruppe() == Gruppe.QUALIFIKATION)
                .flatMap(match -> List.of(match.heimMannschaft(), match.gastMannschaft()).stream())
                .distinct()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("q) Mannschaften der Meistergruppe:");
        matches.stream()
                .filter(match -> match.gruppe() == Gruppe.MEISTER)
                .map(Match::heimMannschaft)
                .distinct()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("r) Welche Mannschaft hat in der Gesamtgruppe die meisten Spiele gewonnen?");
        matches.stream()
                .filter(isGesamtGruppe)
                .map(Match::getWinner)
                .filter(winner -> !winner.equals("Unentschieden"))
                .reduce((team1, team2) -> {
                    var count1 = matches.stream()
                            .filter(isGesamtGruppe)
                            .filter(match -> match.getWinner().equals(team1))
                            .count();
                    var count2 = matches.stream()
                            .filter(isGesamtGruppe)
                            .filter(match -> match.getWinner().equals(team2))
                            .count();
                    return count1 >= count2 ? team1 : team2;
                })
                .ifPresent(System.out::println);
        System.out.println();

        System.out.println("s) Spiele, bei denen beide Mannschaften in beiden Halbzeiten getroffen haben:");
        matches.stream()
                .filter(match -> match.toreHeimHZ() > 0 && match.toreGastHZ() > 0
                        && (match.toreHeim() - match.toreHeimHZ()) > 0
                        && (match.toreGast() - match.toreGastHZ()) > 0)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("t) Mannschaften, die nie ein Heimspiel in der Gesamtgruppe verloren haben:");
        matches.stream()
                .filter(isGesamtGruppe)
                .map(Match::heimMannschaft)
                .distinct()
                .filter(team -> matches.stream()
                        .filter(isGesamtGruppe)
                        .filter(match -> match.heimMannschaft().equals(team))
                        .noneMatch(match -> match.toreHeim() < match.toreGast()))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("u) Anzahl Spiele, bei denen der Halbzeitführende nicht gewonnen hat:");
        matches.stream()
                .filter(match -> (match.toreHeimHZ() > match.toreGastHZ() && match.toreHeim() <= match.toreGast())
                        || (match.toreHeimHZ() < match.toreGastHZ() && match.toreHeim() >= match.toreGast()))
                .count();
        System.out.println();

        System.out.println("v) Spiele mit den meisten geschossenen Toren:");
        matches.stream()
                .sorted((m1, m2) -> Integer.compare(
                        m2.toreHeim() + m2.toreGast(),
                        m1.toreHeim() + m1.toreGast()))
                .limit(3)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("w) Teams, die in jedem ihrer Spiele mindestens ein Tor erzielten:");
        matches.stream()
                .flatMap(m -> Stream.of(m.heimMannschaft(), m.gastMannschaft()))
                .distinct()
                .filter(team -> matches.stream()
                        .filter(m -> m.heimMannschaft().equals(team) || m.gastMannschaft().equals(team))
                        .allMatch(m -> (m.heimMannschaft().equals(team) ? m.toreHeim() : m.toreGast()) > 0))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("x) Spiele mit genau 3 Toren insgesamt:");
        matches.stream()
                .filter(match -> match.toreHeim() + match.toreGast() == 3)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("y) Teams ohne 0:0 Unentschieden:");
        matches.stream()
                .flatMap(m -> Stream.of(m.heimMannschaft(), m.gastMannschaft()))
                .distinct()
                .filter(team -> matches.stream()
                        .filter(m -> m.heimMannschaft().equals(team) || m.gastMannschaft().equals(team))
                        .noneMatch(m -> m.toreHeim() == 0 && m.toreGast() == 0))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("z) Anzahl Spiele mit mehr Toren in der zweiten Halbzeit:");
        matches.stream()
                .filter(match -> (match.toreHeim() - match.toreHeimHZ() + match.toreGast()
                        - match.toreGastHZ()) > (match.toreHeimHZ() + match.toreGastHZ()))
                .count();
        System.out.println();

        System.out.println("aa) Spiele, in denen genau eine Mannschaft nicht getroffen hat:");
        matches.stream()
                .filter(match -> (match.toreHeim() == 0 && match.toreGast() > 0)
                        || (match.toreHeim() > 0 && match.toreGast() == 0))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("ab) Spiele, in denen beide Teams in beiden Halbzeiten gleich viele Tore erzielten:");
        matches.stream()
                .filter(match -> match.toreHeimHZ() == match.toreGastHZ()
                        && (match.toreHeim() - match.toreHeimHZ()) == (match.toreGast() - match.toreGastHZ()))
                .forEach(System.out::println);
        System.out.println();
    }
}
