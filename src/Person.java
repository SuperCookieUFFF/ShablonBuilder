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

    public int getAge() {
        if (!ageKnown) {
            throw new IllegalStateException("Возраст неизвестен.");
        }
        return age;
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
        return new PersonBuilder().setSurname(this.getSurname()).setAge(0) // Дети, как правило, не рождаются пожилыми
                .setCity(this.getCity());
    }

    public static class PersonBuilder {
        private String name;
        private String surname;
        private Integer age; // используем Integer для возможности null
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

            boolean ageKnown = age != null;  // Возраст известен, если он был установлен билдером
            int builtAge = age != null ? age : 0;

            return new Person(name, surname, builtAge, ageKnown, city);
        }
    }
}