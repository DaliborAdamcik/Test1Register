package register;

/**
 * Created by jaro on 3.2.2014.
 * Modified by Dalibor Adamcik 14.6.2016
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Register register = new ArrayRegister(6);

        register.addPerson(new Person("Janko Hrasko", "421090012345"));
        register.addPerson(new Person("Peter Flak", "1234"));
        register.addPerson(new Person("Zuzana Vymyslena", "433528"));
        register.addPerson(new Person("Rudy", "05155"));
        register.addPerson(new Person("Daniel Hevier", "05533458"));

        ConsoleUI ui = new ConsoleUI(register);
        ui.run();
    }
}
