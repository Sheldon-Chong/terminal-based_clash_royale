

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

    public ObjList() {
        this.list = null;
    }

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

    public Obj[] GetItem() {
        return this.list;
    }

    public Obj Pop(int index) {
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
        return obj;
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

    public int GetSize() {
        if (list == null)
            return 0;
        return list.length;
    }

}
