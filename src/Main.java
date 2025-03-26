public class Main {
    public static void main(String[] args) {
        // Пример использования
        Person person = new Person.PersonBuilder()
                .setName("Антон")
                .setSurname("Лопатов")
                .setAge(48)
                .setCity("Москва")
                .build();

        System.out.println("Имя: " + person.getName());
        System.out.println("Фамилия: " + person.getSurname());
        System.out.println("Возраст: " + person.getAge());
        System.out.println("Возраст известен: " + person.hasAge());
        System.out.println("Город: " + person.getCity());
        System.out.println("Адрес известен: " + person.hasAddress());

        person.happyBirthday();
        System.out.println("Возраст после дня рождения: " + person.getAge());

        Person child = person.newChildBuilder()
                .setName("Петя")
                .build();

        System.out.println("\nРебенок:");
        System.out.println("Имя: " + child.getName());
        System.out.println("Фамилия: " + child.getSurname());
        System.out.println("Возраст: " + child.getAge());
        System.out.println("Город: " + child.getCity());


        //Пример обработки исключений:
        try {
            Person invalidPerson = new Person.PersonBuilder()
                    .setName("Вася")
                    .setAge(-5)
                    .build();
        } catch (IllegalArgumentException e) {
            System.err.println("\nОшибка при создании человека: " + e.getMessage());
        }

        try {
            Person incompletePerson = new Person.PersonBuilder()
                    .setName("Оля")
                    .build();
        } catch (IllegalStateException e) {
            System.err.println("\nОшибка при создании человека: " + e.getMessage());
        }
    }
}