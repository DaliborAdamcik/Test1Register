package register;

/**
 * register.Person register.
 */
public class ArrayRegister implements Register {
    /** register.Person array. */
    private Person[] persons;
    
    /** Number of persons in this register. */
    private int count;
    
    /**
     * Constructor creates an empty register with maximum size specified.
     * @param size maximum size of the register
     */
    public ArrayRegister(int size) {
        persons = new Person[size];
        count = 0;
    }
    
    /* (non-Javadoc)
	 * @see register.Register#getCount()
	 */
    @Override
	public int getCount() {
        return count;
    }
    
    /* (non-Javadoc)
	 * @see register.Register#getSize()
	 */
    @Override
	public int getSize() {
        return persons.length;
    }
    
    /* (non-Javadoc)
	 * @see register.Register#getPerson(int)
	 */
    @Override
	public Person getPerson(int index) {
        return persons[index];
    }

    /* (non-Javadoc)
	 * @see register.Register#addPerson(register.Person)
	 */
    @Override
	public void addPerson(Person person) {

    	if(this.findPersonByName(person.getName()) != null)
        	throw new RuntimeException("Person named \""+person.getName()+"\" already exist in register.");

    	if(this.findPersonByName(person.getPhoneNumber()) != null)
        	throw new RuntimeException("Person with phone number \""+person.getPhoneNumber()+"\" already exist in register.");

    	if(count+1 == this.getSize())
    		throw new RuntimeException("Cant add: register exeeds maximum size of "+this.getSize()+" Persons.");
    		
        persons[count] = person;
        count++;
    }       
    
    /* (non-Javadoc)
	 * @see register.Register#findPersonByName(java.lang.String)
	 */
    @Override
	public Person findPersonByName(String name) {
    	for(int i=0;i<this.getCount();i++)
        {
        	if(this.getPerson(i).getName().compareToIgnoreCase(name)==0)
        		return this.getPerson(i);
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see register.Register#findPersonByPhoneNumber(java.lang.String)
	 */
    @Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
        for(int i=0;i<this.getCount();i++)
        {
        	if(this.getPerson(i).getPhoneNumber().compareTo(phoneNumber)==0)
        		return this.getPerson(i);
        }
        return null;
    }
    
    /* (non-Javadoc)
	 * @see register.Register#removePerson(register.Person)
	 */
    @Override
	public void removePerson(Person person) {
    	int i = getPersonIndex(person);
        
    	if(i<0)
        {
        	System.out.println("Person not found.");
        	return;
        }
    	
    	System.out.println("Removing \""+this.persons[i].getName()+"\"...");
    	for(;i<this.getCount()-1;i++)
    	{
    		this.persons[i]=this.persons[i+1];
    	}
    	this.persons[i]= null;
    	this.count--;
    }
    
    /* (non-Javadoc)
	 * @see register.Register#getPersonIndex(register.Person)
	 */
    @Override
	public int getPersonIndex(Person person)
    {
        if(person==null)
        	return -1;
        
    	int i = 0;
    	boolean found = false;
        
    	do
        {
        	found|= person.equals(this.persons[i]); 
        }
        while(!found && (i++<this.getCount())); 

        if(!found)
        	return-1;
        
        return i;
    }
    
    /* (non-Javadoc)
	 * @see register.Register#SortRegister(boolean)
	 */
    @Override
	public void SortRegister(boolean asc)
    {
    	for(int i=0;i<this.getCount()-1;i++)
        	for(int j=i+1;j<this.getCount();j++)
        	{
        		int comRes =persons[i].getName().compareTo(persons[j].getName()); 
        		if(comRes>0 && asc || comRes<0 && !asc)
        		{
            		Person tmp= persons[i];
            		persons[i]=persons[j];
            		persons[j]=tmp;
        		}
        	}
    }
}
