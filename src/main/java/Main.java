/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/

//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

class WrongStudentName extends Exception { }
class WrongStudentAge extends Exception { }
class WrongStudentDate extends Exception { }
class WrongMenuOption extends Exception { }

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                int ex = menu();
                switch (ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    case 0: return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wybór z menu musi być liczbą całkowitą od 0 do 3");
                scan.nextLine();
            } catch (WrongMenuOption e) {
                System.out.println("Wybierz liczbę z zakresu 0-3");
            } catch (WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch (WrongStudentAge e) {
                System.out.println("Wiek musi być liczbą z przedziału 1-99!");
            } catch (WrongStudentDate e) {
                System.out.println("Błędny format daty! Poprawny to DD-MM-YYYY.");
            } catch (IOException e) {
                System.out.println("Wystąpił błąd wejścia/wyjścia: " + e.getMessage());
            }
        }
    }

    public static int menu() throws WrongMenuOption {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");

        int choice = scan.nextInt();
        if (choice < 0 || choice > 3) {
            throw new WrongMenuOption();
        }

        return choice;
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static int ReadAge() throws WrongStudentAge {
        System.out.println("Podaj wiek: ");
        if (!scan.hasNextInt()) {
            scan.next();
            throw new WrongStudentAge();
        }
        int age = scan.nextInt();
        scan.nextLine();
        if (age < 1 || age > 99) {
            throw new WrongStudentAge();
        }
        return age;
    }

    public static String ReadDate() throws WrongStudentDate {
        System.out.println("Podaj datę urodzenia DD-MM-YYYY");
        String date = scan.nextLine();
        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new WrongStudentDate();
        }
        return date;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongStudentAge, WrongStudentDate {
        var name = ReadName();
        var age = ReadAge();
        var date = ReadDate();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}








