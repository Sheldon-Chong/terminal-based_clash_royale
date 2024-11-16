
/*
 * A class that stores a list of objects.
 * Contains many methods to manipulate the list, without depending on existing libraries.
 */

public class ObjList {
    private Obj[] list;

    public static void main(String [] args) {
        Obj obj1 = new Troop();
        Obj obj2 = new Troop();

        obj1.SetPos(1,1);
        obj2.SetPos(2,2);

        ObjList objList = new ObjList();
        objList.append(obj1);
        objList.append(obj2);

        System.out.println(objList.GetItem(0).GetPos().x);
        System.out.println(objList.GetItem(1).GetPos().x);
        System.out.println(objList.GetItem(-2).GetPos().x);

        objList.PrintList();
        objList.Pop(-1);
        
        objList.PrintList();

        System.out.println(objList.FindItem(obj1));

    }

    // -- CONSTRUCTORS --

    public ObjList() {
        this.list = null;
    }

    public ObjList(Obj[] list) {
        this.list = list;
    }

    // -- PUBLIC METHODS --

    public Obj []SelfCopy() {
        Obj []newList = new Obj[list.length];

        for (int i = 0; i < list.length; i++)
            newList[i] = list[i];
        return newList;
    }

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

    public void SetItem(int index, Obj obj) {
        if (index >= list.length)
            return ;
        if (index < 0)
            index = list.length + index;
        list[index] = obj;
    }

    public Obj GetItem(int index) {
        if (index < 0)
            index = list.length + index;
        return list[index];
    }

    public int FindItem(Obj objRef) {
        for (int i = 0; i < list.length; i ++) {
            if (list[i] == objRef)
                return i;
        }
        return -1;
    }

    public Obj[] GetList() {
        return this.list;
    }

    public void Pop(int index) {
        if (index < 0)
            index = list.length + index;

        Obj obj = list[index];
        Obj[] newList = new Obj[list.length - 1];
        for (int i = 0; i < list.length; i++) {
            if (i < index)
                newList[i] = list[i];
            else if (i > index)
                newList[i - 1] = list[i];
        }
        this.list = newList;
        return ;
    }

    public void Pop(Obj object) {
        int index = this.FindItem(object);

        if (index == -1)
            return ;

        this.Pop(index);
    }        

    public void PrintList() {
        System.out.print("{ ");
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i]);
            if (i < list.length - 1)
                System.out.print(", ");
        }
        System.out.print(" }\n");
    }

    public void ClearList() {
        this.list = null;
    }

    public int GetLen() {
        if (list == null)
            return 0;
        return list.length;
    }

}
