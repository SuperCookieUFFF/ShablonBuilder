import java.util.OptionalInt;

public class Person {

    private final String name;
    private final String surname;
    private int age;
    private boolean ageKnown;
    private String city;

    private Person(String name, String surname, int age, boolean ageKnown, String city) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.ageKnown = ageKnown;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public OptionalInt getAge() {
        if (!ageKnown) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(age);
    }

    public boolean hasAge() {
        return ageKnown;
    }

    public void happyBirthday() {
        if (ageKnown) {
            age++;
        } else {
            throw new IllegalStateException("Возраст неизвестен, нельзя увеличить возраст.");
        }
    }

    public String getCity() {
        return city;
    }

    public boolean hasAddress() {
        return city != null && !city.isEmpty();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PersonBuilder newChildBuilder() {
        return new PersonBuilder()
                .setSurname(this.getSurname())
                .setAge(0)
                .setCity(this.getCity())
                .setName(null);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", ageKnown=" + ageKnown +
                ", city='" + city + '\'' +
                '}';
    }

    public static class PersonBuilder {
        private String name;
        private String surname;
        private Integer age;
        private String city;

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public PersonBuilder setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Возраст не может быть отрицательным.");
            }
            this.age = age;
            return this;
        }

        public PersonBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public Person build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("Необходимо указать имя.");
            }
            if (surname == null || surname.isEmpty()) {
                throw new IllegalStateException("Необходимо указать фамилию.");
            }

            boolean ageKnown = age != null;
            int builtAge = age != null ? age : 0;

            return new Person(name, surname, builtAge, ageKnown, city);
        }
    }

    public static void main(String[] args) {
        Person mom = new Person.PersonBuilder()
                .setName("Анна")
                .setSurname("Волкова")
                .setAge(31)
                .setCity("Москва")
                .build();

        System.out.println("Mom's age (using OptionalInt): " + mom.getAge().orElse(0));
        if(mom.getAge().isPresent()) {
            System.out.println("Mom's actual age is: " + mom.getAge().getAsInt());
        }


        Person son = mom.newChildBuilder()
                .setName("Антошка")
                .build();
        System.out.println("Son: " + son);

        System.out.println("Son's age (using OptionalInt): " + son.getAge().orElse(0));
        if(!son.getAge().isPresent()){
            System.out.println("Son's age is unknown");
        }



        try {
            new Person.PersonBuilder().build();
        } catch (IllegalStateException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        try {
            new Person.PersonBuilder().setAge(-100).build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Person personWithoutAge = new Person.PersonBuilder()
                .setName("John")
                .setSurname("Doe")
                .setCity("New York")
                .build();

        System.out.println("Person without Age: " + personWithoutAge);
        try {
            personWithoutAge.happyBirthday();
        } catch (IllegalStateException e) {
            System.out.println("Exception: " + e.getMessage());
        }


    }
}
