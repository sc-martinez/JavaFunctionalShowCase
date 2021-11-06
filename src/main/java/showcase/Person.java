package showcase;

import java.util.HashSet;

/**
 * Define el Genero de una persona
 */
enum Gender {
    MALE,
    FEMALE,
    UNDEFINED
}

enum MaritalStatus {
    SINGLE,
    MARRIED,
    FREE_UNION
}

public class Person {
    private Integer age;
    private String name;
    private String lastName;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private HashSet<Person> relatives;

    public Person() {
    }

    /**
     * Constructor
     * @param age
     * @param name
     * @param lastName
     * @param gender
     * @param maritalStatus
     */
    public Person(Integer age, String name, String lastName, Gender gender, MaritalStatus maritalStatus) {
        this.age = age;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.relatives = new HashSet<>();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public HashSet<Person> getRelatives() {
        return relatives;
    }

    public void setRelatives(HashSet<Person> relatives) {
        this.relatives = relatives;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", maritalStatus=" + maritalStatus +
                '}';
    }


}


