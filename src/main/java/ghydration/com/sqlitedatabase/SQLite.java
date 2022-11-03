package ghydration.com.sqlitedatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLite extends SQLiteOpenHelper {

    private static SQLite sqLite;

    private static final String DATABASE_NAME = "Dose08";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Dose";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DESC_FIELD = "desc";
    private static final String DELETED_FIELD = "deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLite instanceOfDatabase(Context context) {
        if (sqLite == null)
            sqLite = new SQLite(context);

        return sqLite;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append("Dose")
                .append(" (")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(DESC_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        switch (oldVersion)
//        {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }

    public void addDoseToDatabase(Dose dose)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, dose.getId());
        contentValues.put(TITLE_FIELD, dose.getTitle());
        contentValues.put(DESC_FIELD, dose.getDescription());
        contentValues.put(DELETED_FIELD, getStringFromDate(dose.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateDoseListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if (result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String stringDeleted = result.getString(4);
                    Date deleted = getDateFromString(stringDeleted);
                    Dose dose = new Dose(id,title,desc,deleted);
                    Dose.doseArrayList.add(dose);
                }
            }
        }
    }

    public void updateDoseInDB(Dose dose) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, dose.getId());
        contentValues.put(TITLE_FIELD, dose.getTitle());
        contentValues.put(DESC_FIELD, dose.getDescription());
        contentValues.put(DELETED_FIELD, getStringFromDate(dose.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf((dose.getId()))});
    }

    private String getStringFromDate(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}
