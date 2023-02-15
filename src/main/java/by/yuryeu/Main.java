package by.yuryeu;

import by.yuryeu.model.Animal;
import by.yuryeu.model.Car;
import by.yuryeu.model.Flower;
import by.yuryeu.model.House;
import by.yuryeu.model.Person;
import by.yuryeu.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.YEARS;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
    }

    private static void task1() throws IOException {
        System.out.println("============================TASK 1============================");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14).limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        System.out.println("============================TASK 2============================");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getOrigin().equals("Japanese"))
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        System.out.println("============================TASK 3============================");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        System.out.println("============================TASK 4============================");
        List<Animal> animals = Util.getAnimals();
        long amountOfFemales = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count();
        System.out.println(amountOfFemales);
    }

    private static void task5() throws IOException {
        System.out.println("============================TASK 5============================");
        List<Animal> animals = Util.getAnimals();
        boolean isThereAnyHungarian = animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));
        System.out.println(isThereAnyHungarian);
    }

    private static void task6() throws IOException {
        System.out.println("============================TASK 6============================");
        List<Animal> animals = Util.getAnimals();
        List<String> genders = List.of("Male", "Female");

        boolean isAllAnimalsMaleOrFemale = animals.stream()
                .allMatch(animal -> genders.contains(animal.getGender()));
        System.out.println(isAllAnimalsMaleOrFemale);
    }

    private static void task7() throws IOException {
        System.out.println("============================TASK 7============================");
        List<Animal> animals = Util.getAnimals();
        boolean isThereAnyAnimalFromOceania = animals.stream()
                .anyMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(isThereAnyAnimalFromOceania);
    }

    private static void task8() throws IOException {
        System.out.println("============================TASK 8============================");
        List<Animal> animals = Util.getAnimals();
        Integer maxAge = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .map(Animal::getAge)
                .max(Integer::compareTo)
                .get();
        System.out.println(maxAge);
    }

    private static void task9() throws IOException {
        System.out.println("============================TASK 9============================");
        List<Animal> animals = Util.getAnimals();
        Integer minLength = animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .map(value -> value.length)
                .min(Integer::compareTo)
                .get();
        System.out.println(minLength);
    }

    private static void task10() throws IOException {
        System.out.println("============================TASK 10============================");
        List<Animal> animals = Util.getAnimals();
        Integer animalsAgeSum = animals.stream()
                .map(Animal::getAge)
                .reduce(0, (x, y) -> x + y);
        System.out.println(animalsAgeSum);
    }

    private static void task11() throws IOException {
        System.out.println("============================TASK 11============================");
        List<Animal> animals = Util.getAnimals();
        Double averageAgeOfIndonesianAnimals = animals.stream()
                .filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .map(Animal::getAge)
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println(averageAgeOfIndonesianAnimals);
    }

    private static void task12() throws IOException {
        System.out.println("============================TASK 12============================");
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> "Male".equals(person.getGender()))
                .filter(person -> person.getDateOfBirth().until(LocalDate.now(), YEARS) >= 18 &&
                        person.getDateOfBirth().until(LocalDate.now(), YEARS) < 27)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        System.out.println("============================TASK 13============================");
        List<House> houses = Util.getHouses();
        List<Person> remainingPeople = new ArrayList<>(houses.stream()
                .map(House::getPersonList)
                .flatMap(Collection::stream)
                .toList());

        List<Person> hospitalList = houses.get(0).getPersonList();
        remainingPeople.removeAll(hospitalList);

        List<Person> youngAndOldList = remainingPeople.stream()
                .filter(person -> person.getDateOfBirth().until(LocalDate.now(), YEARS) < 18 ||
                        person.getDateOfBirth().until(LocalDate.now(), YEARS) > 63 && "Male".equals(person.getGender()) ||
                        person.getDateOfBirth().until(LocalDate.now(), YEARS) > 58 && "Female".equals(person.getGender()))
                .toList();
        remainingPeople.removeAll(youngAndOldList);

        List<Person> collect = Stream.of(hospitalList, youngAndOldList, remainingPeople)
                .flatMap(Collection::stream)
                .limit(500)
                .toList();

        collect.forEach(System.out::println);
    }

    private static void task14() throws IOException {
        System.out.println("============================TASK 14============================");
        List<Car> cars = Util.getCars();
        List<String> carMakersForSecondEchelon = List.of("BMW", "Lexus", "Chrysler", "Toyota");
        List<String> carMakersForThirdEchelon = List.of("GMC", "Dodge");
        List<String> carModelsForFourthEchelon = List.of("Civic", "Cherokee");
        List<String> carColorsForFifthEchelon = List.of("НЕ Yellow", "Red", "Green", "Blue");

        Predicate<Car> firstEchelonPredicate = car ->
                "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor());

        Predicate<Car> secondEchelonPredicate = car ->
                car.getMass() < 1500 || carMakersForSecondEchelon.contains(car.getCarMake());

        Predicate<Car> thirdEchelonPredicate = car ->
                (car.getColor().equals("Black") && car.getMass() < 4000) ||
                        carMakersForThirdEchelon.contains(car.getCarMake());

        Predicate<Car> fourthEchelonPredicate = car ->
                car.getReleaseYear() < 1982 || carModelsForFourthEchelon.contains(car.getCarModel());

        Predicate<Car> fifthEchelonPredicate = car ->
                car.getPrice() > 40000 || carColorsForFifthEchelon.contains(car.getColor());

        Predicate<Car> sixthEchelonPredicate = car -> car.getVin().contains("59");

        List<Car> firstEchelon = getAndExtractEchelonFromList(cars, firstEchelonPredicate);
        List<Car> secondEchelon = getAndExtractEchelonFromList(cars, secondEchelonPredicate);
        List<Car> thirdEchelon = getAndExtractEchelonFromList(cars, thirdEchelonPredicate);
        List<Car> fourthEchelon = getAndExtractEchelonFromList(cars, fourthEchelonPredicate);
        List<Car> fifthEchelon = getAndExtractEchelonFromList(cars, fifthEchelonPredicate);
        List<Car> sixthEchelon = getAndExtractEchelonFromList(cars, sixthEchelonPredicate);

        System.out.println("Расходы на Туркменистан: " + countCostForCountries(firstEchelon) + "$");
        System.out.println("Расходы на Узбекистан: " + countCostForCountries(secondEchelon) + "$");
        System.out.println("Расходы на Казахстан: " + countCostForCountries(thirdEchelon) + "$");
        System.out.println("Расходы на Кыргызстан: " + countCostForCountries(fourthEchelon) + "$");
        System.out.println("Расходы на Россию: " + countCostForCountries(fifthEchelon) + "$");
        System.out.println("Расходы на Монголию: " + countCostForCountries(sixthEchelon) + "$");

        Integer totalRevenue = Stream
                .of(firstEchelon, secondEchelon, thirdEchelon, fourthEchelon, fifthEchelon, sixthEchelon)
                .flatMap(Collection::stream)
                .map(Car::getPrice)
                .reduce(0, (x, y) -> x + y);
        System.out.println("Общая выручка: " + totalRevenue + "$");
    }

    private static void task15() throws IOException {
        System.out.println("============================TASK 15============================");
        List<Flower> flowers = Util.getFlowers();
        String chars = "CDEFGHIJKLMNOPQRS";
        List<String> vaseMaterials = List.of("Glass", "Aluminum", "Steel");

        BigDecimal totalCost = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed())
                .sorted(Comparator.comparing(Flower::getPrice).reversed())
                .sorted(Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> chars.contains(String.valueOf(flower.getCommonName().charAt(0))))
                .filter(Flower::isShadePreferred)
                .filter(flower -> checkFlowerVaseMaterials(flower, vaseMaterials))
                .map(flower -> countFlowerCostForFiveYears(flower))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(totalCost + "$");
    }

    private static void task16() throws IOException {
        List<Animal> animals = Util.getAnimals();
        Map<String, List<String>> everyCountryBreeds = animals.stream()
                .filter(animal -> animal.getAge() >= 7 && animal.getAge() < 17)
                .collect(Collectors.groupingBy(Animal::getOrigin))
                .entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                        e -> e.getValue().stream()
                                .map(Animal::getBread)
                                .distinct()
                                .sorted()
                                .limit(3)
                                .collect(Collectors.toList())));

        System.out.println(everyCountryBreeds);
    }

    private static List<Car> getAndExtractEchelonFromList(List<Car> list, Predicate<Car> predicate) {
        List<Car> cars = list.stream()
                .filter(predicate)
                .toList();
        list.removeAll(cars);
        return cars;
    }

    private static BigDecimal countCostForCountries(List<Car> echelon) {
        return BigDecimal.valueOf(echelon.stream()
                        .map(Car::getMass)
                        .reduce(0, (x, y) -> x + y))
                .multiply(BigDecimal.valueOf(0.00714))  //cost per kilogram
                .setScale(2, RoundingMode.FLOOR);
    }

    private static boolean checkFlowerVaseMaterials(Flower flower, List<String> vaseMaterials) {
        return vaseMaterials.stream().anyMatch(material -> flower.getFlowerVaseMaterial().contains(material));
    }

    private static BigDecimal countFlowerCostForFiveYears(Flower flower) {
        return BigDecimal.valueOf(5 * 365)
                .multiply(BigDecimal.valueOf(flower.getWaterConsumptionPerDay()))
                .multiply(BigDecimal.valueOf(0.00139))  //cost per liter
                .add(BigDecimal.valueOf(flower.getPrice()))
                .setScale(2, RoundingMode.FLOOR);
    }
}