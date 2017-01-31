package zoomkart.paykart;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.ListItems;

public class SaveState implements Serializable {
    static public List<ListItems> items = new ArrayList<ListItems>();
    static public Customer customer = new Customer();
    static public Context context;
    static SaveState instance=null;

    public static SaveState getInstance(){
        if( instance == null )
            instance = new SaveState();
        return instance;
    }

    public static void saveData(SaveState instance){
        ObjectOutput out;
        try {
            FileOutputStream fos = context.openFileOutput("payKartSaveData", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(instance);
            os.close();
            fos.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static SaveState loadData(){
        ObjectInput in;
        SaveState ss=null;
        try {
            FileInputStream fis = context.openFileInput("payKartSaveData");
            ObjectInputStream is = new ObjectInputStream(fis);
            ss = (SaveState) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {e.printStackTrace();}
        return ss;
    }
}