package brickset;

import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /**
     * Visszaadja a leggyakrabban használt címkét, és annak mennyiségét 
     *
     * @return Egy Stringet ad vissza ami a nevet és egy Long-ot ami a mennyiséget tartalmazza
     */
    public Optional<Map.Entry<String, Long>> getTagWithTheMostUsage() {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null)
                .flatMap(legoSet -> legoSet.getTags().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }

    /**
     * Kiíratja a Most
     */
    public void printTagWithTheMostUsage() {
        Map.Entry<String, Long> mostUsedTag = getTagWithTheMostUsage().get();
        System.out.printf("\t- %s %d times.", mostUsedTag.getKey(), mostUsedTag.getValue());
    }

    /**
     * Visszaadja azokat a LegoSet-eket aminek a neve az adott paraméterrel végződik
     *
     * @param endWith egy Stringet vár, amivel a LegoSet-ek neve végződik
     *
     * @return Egy listát ad vissza ami kért LegoSet nevekkel
     */

    public List<LegoSet> getLegoSetsThatNameEndWith(String endWith) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getName() != null)
                .filter(legoSet -> legoSet.getName().endsWith(endWith))
                .collect(Collectors.toList());
    }

    /**
     * Kinyomtatja azoknak a LEGO-készleteknek a listáját, amelyek neve a megadottal végződik.
     *
     * @param endWith Ezzel végződik a LegoSet neve
     */
    public void printLegoSetsThatNameEndWith(String endWith) {
        getLegoSetsThatNameEndWith(endWith)
                .forEach(legoSet -> System.out.printf("\t- %s\n", legoSet.getName()));
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();
        System.out.println("\nLEGO Sets that name ends with 'Set':");
        repository.printLegoSetsThatNameEndWith("Set");
        System.out.println("\nTag with most usage:");
        repository.printTagWithTheMostUsage();
    }
}