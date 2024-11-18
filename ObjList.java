
/* A class that stores a list of objects.
 * Contains many methods to manipulate the list, without depending on existing libraries */
public class ObjList {

    // -- ATTRIBUTES --
    
    private Obj[] list;


    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Default constructor */
    public ObjList() {
        this.list = null;
    }

    // DEVELOPED BY: Sheldon
    /* Constructor that initializes the list with an array of objects
     * @param list - the array of objects to initialize the list with */
    public ObjList(Obj[] list) {
        this.list = list;
    }

    // -- GETTER AND SETTER --

    // DEVELOPED BY: Sheldon
    /* Inserts an object at a specified index
     * @param index - the index to insert the object at
     * @param obj - the object to insert
     * @return - true if the object was inserted, false otherwise */
    public void SetItem(int index, Obj obj) {
        if (index >= list.length)
            return ;
        if (index < 0)
            index = list.length + index;
        list[index] = obj;
    }

    // DEVELOPED BY: Sheldon
    /* Gets an object at a specified index
     * @param index - the index to get the object from
     * @return - the object at the specified index */
    public Obj GetItem(int index) {
        if (index < 0)
            index = list.length + index;
        return list[index];
    }

    // DEVELOPED BY: Sheldon
    /* Gets the list of objects
     * @return - the list of objects */
    public Obj[] GetList() {
        return this.list;
    }


    // -- PUBLIC METHODS --

    // DEVELOPED BY: Sheldon
    /* Appends an object to the list
     * @param obj - the object to append
     * @return - true if the object was appended, false otherwise */
    public boolean append(Obj obj) {
        if (list == null) {
            this.list = new Obj[1];
            this.list[0] = obj;
            return true;
        }

        Obj[] newList = new Obj[list.length + 1];
        for (int i = 0; i < list.length; i++)
            newList[i] = list[i];

        newList[list.length] = obj;
        this.list = newList;
        return true;
    }

    // DEVELOPED BY: Sheldon
    /* Finds the index of an object in the list
     * @param objRef - the object to find
     * @return - the index of the object in the list */
    public int FindItem(Obj objRef) {
        for (int i = 0; i < list.length; i ++) {
            if (list[i] == objRef)
                return i;
        }
        return -1;
    }

    // DEVELOPED BY: Sheldon
    /* Removes an object from the list at a specified index
     * @param index - the index to remove the object from */
    public void Pop(int index) {

        // if the index is a negative, get the index starting from the end of the list
        if (index < 0)
            index = list.length + index;

        Obj[] newList = new Obj[list.length - 1];

        // iterate through the list
        for (int i = 0; i < list.length; i++) {

            // if before the index to pop
            if (i < index)
                newList[i] = list[i];

            // if after the index to pop
            else if (i > index)
                newList[i - 1] = list[i];

        }

        // set the list to the new list
        this.list = newList;
    }

    // DEVELOPED BY: Sheldon
    /* Removes an object from the list
     * @param object - the object to remove */
    public void Pop(Obj object) {
        // search for the object in the list
        int index = this.FindItem(object);

        // if object doesn' exist
        if (index == -1)
            return ;

        this.Pop(index);
    }        

    // DEVELOPED BY: Sheldon
    /* Gets the length of the list
     * @return - the length of the list */
    public int GetLen() {

        if (list == null)
            return 0;

        return list.length;
    }
}
