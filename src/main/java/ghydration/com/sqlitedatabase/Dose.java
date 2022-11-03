package ghydration.com.sqlitedatabase;

import java.util.ArrayList;
import java.util.Date;
import java.time.*;

public class Dose
{
    public static ArrayList<Dose> doseArrayList = new ArrayList<>();
    public static String DOSE_EDIT_EXTRA = "doseEdit";

    private int id;
    private String title;
    private String description;
    private Date deleted;

    public Dose(int id, String title, String description, Date deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }

    public Dose(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
    }

    public static Dose getDoseForID(int passedDoseID)
    {
        for (Dose dose : doseArrayList)
        {
            if(dose.getId() == passedDoseID)
                return dose;
        }
        return null;
    }

    public static ArrayList<Dose> nonDeletedDoses()
    {
        ArrayList<Dose> nonDeleted = new ArrayList<>();
        for (Dose dose : doseArrayList)
        {
            if (dose.getDeleted() == null)
                nonDeleted.add(dose);
        }

        return nonDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

}
