package showcase;

import java.util.HashSet;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PersonFactory {
    /**
     * Generates a Random Person
     */
    public static Supplier<Person> buildRandomPerson = () -> {
        try {
            return RandomObjectFiller.createAndFill(Person.class);
        } catch (Exception e) {
            return null;
        }
    };

    /**
     * Builds a Random Family for a specified Person
     */
    public static Consumer<Person> buildRandomFamily =
            person -> person
                    .setRelatives(IntStream.range(0,5)
                            .mapToObj((x)-> buildRandomPerson.get())
                            .collect(Collectors.toCollection(HashSet::new)
                            )
                    );

    public static BinaryOperator<Person> addRelatives = (a, b) -> {
        a.setRelatives(Stream.concat(a.getRelatives().stream(), b.getRelatives().stream()).collect(Collectors.toCollection(HashSet::new)));
        return a;
    };

}
