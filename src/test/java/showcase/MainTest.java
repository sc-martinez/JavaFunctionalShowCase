package showcase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MainTest {
    //Functional Interfaces simple Samples
    @Test
    void testFunction() {
        //Function
        Function<Integer, Integer> powerBy2 = number -> number * number;
        assertEquals(25, powerBy2.apply(5));
    }

    @Test
    void testBiFunction() {
        //BiFunction
        BiFunction<Double, Double, Double> sumNumbers = (a, b) -> a + b;
        assertEquals(15, sumNumbers.apply(10.0, 5.0));
    }

    @Test
    void testPredicate() {
        //Predicate
        Person p1 = new Person(15, "Andres", "Reyes",
                Gender.MALE, MaritalStatus.SINGLE);
        Predicate<Person> isSingle = person -> Gender.MALE.equals(person.getGender());
        assertTrue(isSingle.test(p1));
    }

    @Test
    void testBiPredicate() {
        //BiPredicate
        Person pA = new Person(15, "Andres", "Reyes",
                Gender.MALE, MaritalStatus.SINGLE);
        Person pB = new Person(15, "Maria", "Fernandez",
                Gender.FEMALE, MaritalStatus.SINGLE);

        BiPredicate<Person, Person> areOfSameAge = (personA, personB) ->
                personA.getAge().equals(personB.getAge());
        assertTrue(areOfSameAge.test(pA, pB));
    }

    @Test
    void testConsumer() {
        Person pA = new Person(15, "Andres", "Reyes",
                Gender.MALE, MaritalStatus.SINGLE);
        Person pB = new Person(15, "Maria", "Fernandez",
                Gender.FEMALE, MaritalStatus.SINGLE);
        //Consumer
        PersonFactory.buildRandomFamily.accept(pA);
        PersonFactory.buildRandomFamily.accept(pB);
        assertTrue(pA.getRelatives().size() == 5 && pB.getRelatives().size() == 5);
    }

    @Test
    void testBiConsumer() {
        Person pA = new Person(15, "Andres", "Reyes",
                Gender.MALE, MaritalStatus.SINGLE);
        Person pB = new Person(15, "Maria", "Fernandez",
                Gender.FEMALE, MaritalStatus.SINGLE);
        PersonFactory.buildRandomFamily.accept(pA);
        PersonFactory.buildRandomFamily.accept(pB);
        //BiConsumer
        BiConsumer<Person, Person> showPreMarriageFamily = (personA, personB) -> {
            System.out.println("============================ Applicant ================================");
            System.out.println(personA);
            System.out.println("=============== Applicant RELATIVES ARE : =======================");
            personA.getRelatives().forEach(System.out::println);
            System.out.println("============================ Candidate ================================");
            System.out.println(personB);
            System.out.println("=============== Candidate Spouse RELATIVES ARE : =======================");
            personB.getRelatives().forEach(System.out::println);
        };
        showPreMarriageFamily.accept(pA, pB);
    }

    @Test
    void testSupplierWithStreamsAndBinaryOperator() {
        //Supplier with Streams
        Supplier<List<Person>> buildANewMarriage = () -> {
            List<Person> marriage = List.of(
                    PersonFactory.buildRandomPerson.get(),
                    PersonFactory.buildRandomPerson.get());
            marriage.forEach(p -> {
                PersonFactory.buildRandomFamily.andThen(person -> person.setMaritalStatus(MaritalStatus.MARRIED)).accept(p);
            });
            return marriage;
        };
        List<Person> marriage = buildANewMarriage.get();
        System.out.println(marriage);
        assertTrue(marriage.stream().allMatch(p -> MaritalStatus.MARRIED.equals(p.getMaritalStatus())));
        //BinaryOperator
        PersonFactory.addRelatives.apply(marriage.get(0), marriage.get(1));
        assertTrue(marriage.get(0).getRelatives().size() > 5);
    }

    @Test
    void testGroupingFamily() {
        Supplier<List<Person>> buildANewMarriage = () -> {
            List<Person> marriage = List.of(
                    PersonFactory.buildRandomPerson.get(),
                    PersonFactory.buildRandomPerson.get());
            marriage.forEach(p -> {
                PersonFactory.buildRandomFamily.andThen(person -> person.setMaritalStatus(MaritalStatus.MARRIED)).accept(p);
            });
            return marriage;
        };
        List<Person> marriage = buildANewMarriage.get();
        Map<MaritalStatus, List<Person>> grouping = marriage.get(0)
                .getRelatives()
                .stream().collect(Collectors.groupingBy(Person::getMaritalStatus));

        System.out.println(grouping);
        assertTrue(grouping.get(MaritalStatus.MARRIED).size() > 0 );

    }

    @Test
    void testParallel(){
        Optional<Long> result = Stream.iterate(1L, i -> i + 1)  // Ejemplo de rangos infinitos
                .limit(10)
                .parallel()
                .reduce((x,y) -> x + y); // Equivalente a Fold

        System.out.println(result.isPresent());
        assertEquals(55l,  result.isPresent() ? result.get() : result.orElse(0l)); // Equivalente a Optional
    }


    @Test
    void testFuture() throws InterruptedException, ExecutionException {
        CompletableFuture<Optional<Person>> randomPersonWithFamily = new CompletableFuture<>();
        new Thread( () -> {
                Person p = PersonFactory.buildRandomPerson.get();
                PersonFactory.buildRandomFamily.accept(p);
                randomPersonWithFamily.complete(Optional.of(p));
        }).start();
        assertTrue( randomPersonWithFamily.get().isPresent());
    }



}