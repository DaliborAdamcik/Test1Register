package register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User interface of the application.
 */
public class ConsoleUI {
    /** register.Register of persons. */
    private Register register;
    
    /**
     * In JDK 6 use Console class instead.
     * @see readLine()
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Menu options.
     */
    private enum Option {
        PRINT, ADD, UPDATE, REMOVE, FIND, FIND_AND_UPDATE, FIND_AND_REMOVE, EXIT
    };
    
    public ConsoleUI(Register register) {
        this.register = register;
    }
    
    public void run() {
        while (true) {
            switch (showMenu()) {
                case PRINT:
                    printRegister();
                    break;
                case ADD:
                    addToRegister();
                    break;
                case UPDATE:
                    updateRegister(this.requestValidInt(1, register.getCount(), "Enter index")-1);
                    break;
                case REMOVE:
                    removeFromRegister(this.requestValidInt(1, register.getCount(), "Enter index")-1);
                    break;
                case FIND:
                    findInRegister();
                    break;
                case FIND_AND_UPDATE:
                    updateRegister(findInRegister());
                    break;
                case FIND_AND_REMOVE:
                    removeFromRegister(findInRegister());
                    break;
                case EXIT:
                	System.out.println("Bye bye :-)");
                    return;
            }
        }
    }
    
    private String readLine() {
        //In JDK 6.0 and above Console class can be used
        //return System.console().readLine();
        
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    private Option showMenu() {
        System.out.println("Main menu.");
        for (Option option : Option.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option);
        }
        System.out.println("-----------------------------------------------");
        
        int selection = requestValidInt(1, Option.values().length, "Option");
        
        return Option.values()[selection - 1];
    }
    
    private void printRegister() {
    	if(register.getCount()==0)
    	{
    		System.out.println("\tList of persons is empty.");
    		return;
    	}
    	
    	System.out.println(">>List of persons (sorted ascending)");
    	register.SortRegister(true);
    	for(int i = 0;i<this.register.getCount();i++)
        {
        	Person person = this.register.getPerson(i);
        	System.out.println(String.format("%02d. %s (%s)", i+1, person.getName(), person.getPhoneNumber()));
        }
    	System.out.println(">>---------------------------------------------");
    }
    
    private void addToRegister() {
        System.out.println("Enter Name: ");
        String name = readLine().trim();
        
        if(name.length()==0)
        {
        	System.out.println("*** Field name is required.");
        	return;
        }
        	
        
        System.out.println("Enter Phone Number: ");
        String phoneNumber = readLine().trim();

        try
        {
        	register.addPerson(new Person(name, phoneNumber));
        }
        catch(Exception e)
        {
        	System.out.println("** Error during create person: "+e.getMessage());
        }
        
    }
    
    private void updateRegister(int index) {
    	if(index<0)
    		return;
    	
        Person person = register.getPerson(index);
	        
        String inp;
        System.out.println("Update person "+person.getName()+": \n\t(press enter to leave current values for fields)");
        System.out.print("Enter new name: ");
        inp= readLine().trim();
        if(inp.length()>0)
        {
        	if(register.findPersonByName(inp)!=null && person.getName().compareToIgnoreCase(inp)!=0)
        	{
        		System.out.println("*** Person with name "+inp+" already exists.");
        		return;
        	}
        	System.out.println("... change name to: "+inp);
        	person.setName(inp);
        }
        
        System.out.print("Enter new telephone number: ");
        inp= readLine().trim();
        if(inp.length()>0)
        {
        	try
        	{
            	if(register.findPersonByPhoneNumber(inp)!=null)
            	{
            		System.out.println("*** Person with phone num "+inp+" already exists.");
            		return;
            	}
	        	person.setPhoneNumber(inp);
	        	System.out.println("... change phone number to: "+inp);
        	}
        	catch(Exception e)
        	{
	        	System.out.println("*** Cant set phone number to "+inp+". Error: "+e.getMessage());
        	}
        }
    }
    
    private int findInRegister() {
    	System.out.println("------Find menu------\n1. by Name (case insetive)\n2. by phone number\n3. exit to main menu");
    	int menuitem = requestValidInt(1, 3, "Select");
        
        if (menuitem==3)
        	return -1;
        
        System.out.println(String.format("Enter %s to find: ", (menuitem==1?"name":"phone number")));
        String toSeach = readLine().trim();
        if(toSeach.length()==0)
        {
        	System.out.println("Nothing to search.");
        	return -1;
        }
        
        
        Person found;
        if(menuitem==1)
        	found = this.register.findPersonByName(toSeach);
        else
        	found = this.register.findPersonByPhoneNumber(toSeach);
        
        if(found==null)
        {
        	System.out.println("** No person found");
        	return -1;
        }
        
        int index = this.register.getPersonIndex(found);
        System.out.println(String.format("Found person: %s (%s), index %d", found.getName(), found.getPhoneNumber(), 
        		index+1));

        return index;
    }
    
    private void removeFromRegister(int index) {
    	if(index<0)
    		return;
    	
        Person person = register.getPerson(index);
    	if(this.requestValidInt(0, 1, "Remove person "+person.getName()+"(NO=0 / yes=1)")==1)
    		register.removePerson(person);
    }
    
    private int requestValidInt(int from, int to, String message)
    {
        int selection = -1;
        int tryes = 0;
        do {
            System.out.print(message+": ");
            try
            {
            	selection = Integer.parseInt(readLine());
            }
            catch(Exception e)
            {
            	selection =-1;
            }
            if(++tryes%4==0)
            	System.out.println(String.format("*i* An valid range for \"%s\" is from %d to %d.", message, from, to));
        } while (selection < from || selection > to);
    	return selection;
    }

}
