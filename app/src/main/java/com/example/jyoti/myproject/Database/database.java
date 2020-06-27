package com.example.jyoti.myproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jyoti.myproject.Bean_Class.EmailBean;
import com.example.jyoti.myproject.Bean_Class.ItemBean;
import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.Bean_Class.SMSBean;
import com.example.jyoti.myproject.Bean_Class.TodoListBean;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by Jyoti on 7/10/2017.
 */

public class database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "REMINDER_DATABASE";
    private static final int DATABASE_VERSION = 1;
    private static final String REMINDER_TYPE = "REMINDER_TYPE";
    private static final String REPEAT_TYPE = "REPEAT_TYPE";
    private static final String REMINDER_TABLE = "REMINDER_TABLE";
    private static final String SMS_TABLE = "SMS_TABLE";
    private static final String EMAIL_TABLE = "EMAIL_TABLE";
    private static final String EMAIL_TABLE_INTERNET = "EMAIL_TABLE_INTERNET";
    private static final String TODOLIST_TABLE = "TODOLIST_TABLE";
    private static final String LISTITEM_TABLE = "LISTITEM_TABLE";

    private static final String ID_REMINDER_TABLE = "ID_REMINDER_TABLE";
    private static final String TITLE_REMINDER_TABLE = "TITLE_REMINDER_TABLE";
    private static final String TEXT_REMINDER_TABLE = "TEXT_REMINDER_TABLE";
    private static final String TYPE_REMINDER_TABLE = "TYPE_REMINDER_TABLE";
    private static final String REPEAT_REMINDER_TABLE = "REPEAT_REMINDER_TABLE";
    private static final String DATE_REMINDER_TABLE = "DATE_REMINDER_TABLE";
    private static final String TIME_REMINDER_TABLE = "TIME_REMINDER_TABLE";
    private static final String STATUS_REMINDER_TABLE = "STATUS_REMINDER_TABLE";
    private static final String DELETE_REMINDER_TABLE = "DELETE_REMINDER_TABLE";
    private static final String TONE_REMINDER_TABLE = "TONE_REMINDER_TABLE";

    private static final String ID_REMINDER_TYPE = "ID_REMINDER_TYPE";
    private static final String TYPE_REMINDER_TYPE = "TYPE_REMINDER_TYPE";

    private static final String ID_REPEAT_TYPE = "ID_REPEAT_TYPE";
    private static final String TYPE_REPEAT_TYPE = "TYPE_REPEAT_TYPE";

    private static final String ID_SMS_TABLE = "ID_SMS_TABLE";
    private static final String REM_ID_SMS_TABLE = "REM_ID_SMS_TABLE";
    private static final String TO_SMS_TABLE = "TO_SMS_TABLE";
    private static final String FROM_SMS_TABLE = "FROM_SMS_TABLE";
    private static final String TEXT_SMS_TABLE = "TEXT_SMS_TABLE";

    private static final String ID_EMAIL_TABLE = "ID_EMAIL_TABLE";
    private static final String REM_ID_EMAIL_TABLE = "REM_ID_EMAIL_TABLE";
    private static final String EMAIL_EMAIL_TABLE = "EMAIL_EMAIL_TABLE";
    private static final String NAME_EMAIL_TABLE = "NAME_EMAIL_TABLE";
    private static final String SUBJECT_EMAIL_TABLE = "SUBJECT_EMAIL_TABLE";
    private static final String MESSAGE_EMAIL_TABLE = "MESSAGE_EMAIL_TABLE";
    private static final String NUMBER_EMAIL_TABLE = "NUMBER_EMAIL_TABLE";
    private static final String TYPE_EMAIL_TABLE = "TYPE_EMAIL_TABLE";

    /*private static final String ID_EMAIL_TABLE_INTERNET  = "ID_EMAIL_TABLE";
    private static final String REM_ID_EMAIL_TABLE_INTERNET  = "REM_ID_EMAIL_TABLE";
    private static final String EMAIL_EMAIL_TABLE_INTERNET  = "EMAIL_EMAIL_TABLE";
    private static final String NAME_EMAIL_TABLE_INTERNET  = "NAME_EMAIL_TABLE";
    private static final String SUBJECT_EMAIL_TABLE_INTERNET  = "SUBJECT_EMAIL_TABLE";
    private static final String MESSAGE_EMAIL_TABLE_INTERNET  = "MESSAGE_EMAIL_TABLE";
    private static final String NUMBER_EMAIL_TABLE_INTERNET  = "NUMBER_EMAIL_TABLE";
    private static final String TYPE_EMAIL_TABLE_INTERNET  = "TYPE_EMAIL_TABLE";
*/

    private static final String ID_TODOLIST_TABLE = "ID_TODOLIST_TABLE";
    private static final String TITLE_TODOLIST_TABLE = "TITLE_TODOLIST_TABLE";
    private static final String DELETE_TODOLIST_TABLE = "DELETE_TODOLIST_TABLE";

    private static final String ID_LISTITEM_TABLE= "ID_LISTITEM_TABLE";
    private static final String ID_TODOLIST_LISTITEM_TABLE= "ID_TODOLIST_LISTITEM_TABLE";
    private static final String ITEM_LISTITEM_TABLE= "ITEM_LISTITEM_TABLE";
    private static final String STATUS_LISTITEM_TABLE= "STATUS_LISTITEM_TABLE";

    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String reminder = "CREATE TABLE " + REMINDER_TABLE + " (" + ID_REMINDER_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_REMINDER_TABLE + " VARCHAR(20), " + TEXT_REMINDER_TABLE + " VARCHAR(20), " + TYPE_REMINDER_TABLE + " INTEGER, " + REPEAT_REMINDER_TABLE + " INTEGER, " + DATE_REMINDER_TABLE + " VARCHAR(20)," + TIME_REMINDER_TABLE + " VARCHAR(20)," + STATUS_REMINDER_TABLE + " INTEGER," + DELETE_REMINDER_TABLE + " INTEGER," + TONE_REMINDER_TABLE + " VARCHAR(50))";
        String remin_type = "CREATE TABLE " + REMINDER_TYPE + " (" + ID_REMINDER_TYPE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE_REMINDER_TYPE + " VARCHAR(20)) ";
        String repeat_type = "CREATE TABLE " + REPEAT_TYPE + " (" + ID_REPEAT_TYPE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE_REPEAT_TYPE + " VARCHAR(20) )";
        String sms = "CREATE TABLE " + SMS_TABLE + " (" + ID_SMS_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REM_ID_SMS_TABLE + " INTEGER, " + TO_SMS_TABLE + " VARCHAR(20), " + FROM_SMS_TABLE + " INTEGER, " + TEXT_SMS_TABLE + " VARCHAR(20)) ";
        String email = "CREATE TABLE " + EMAIL_TABLE + " (" + ID_EMAIL_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REM_ID_EMAIL_TABLE + " INTEGER, " + EMAIL_EMAIL_TABLE + " VARCHAR(20), " + NAME_EMAIL_TABLE + " VARCHAR(20), " + MESSAGE_EMAIL_TABLE+ " VARCHAR(2000), "+SUBJECT_EMAIL_TABLE+" VARCHAR(20),"+NUMBER_EMAIL_TABLE+" VARCHAR(20),"+TYPE_EMAIL_TABLE+" VARCHAR(20) )";
        String emailInternet = "CREATE TABLE " + EMAIL_TABLE_INTERNET + " (" + ID_EMAIL_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REM_ID_EMAIL_TABLE + " INTEGER, " + EMAIL_EMAIL_TABLE + " VARCHAR(20), " + NAME_EMAIL_TABLE + " VARCHAR(20), " + MESSAGE_EMAIL_TABLE+ " VARCHAR(2000), "+SUBJECT_EMAIL_TABLE+" VARCHAR(20),"+NUMBER_EMAIL_TABLE+" VARCHAR(20),"+TYPE_EMAIL_TABLE+" VARCHAR(20) )";

        String ToDoList= "CREATE TABLE " + TODOLIST_TABLE + " (" + ID_TODOLIST_TABLE+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_TODOLIST_TABLE+ " VARCHAR(20), " + DELETE_TODOLIST_TABLE+ " INTEGER)  ";
        String items= "CREATE TABLE " + LISTITEM_TABLE+ " (" + ID_LISTITEM_TABLE+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_TODOLIST_LISTITEM_TABLE+ " INTEGER, " + ITEM_LISTITEM_TABLE+ " VARCHAR(20)  ,  "+STATUS_LISTITEM_TABLE+" INTEGER) ";


        Log.e("reminder ", "" + reminder);
        Log.e("remintype", "" + remin_type);
        Log.e("repeattype", "" + repeat_type);
        Log.e("sms", "" + sms);
        Log.e("email", "" + email);
        Log.e("email Internet", ""+emailInternet);
        Log.e("todolist",""+ToDoList);
        Log.e("items",""+items);


        db.execSQL(reminder);
        db.execSQL(remin_type);
        db.execSQL(repeat_type);
        db.execSQL(sms);
        db.execSQL(email);
        db.execSQL(emailInternet);
        db.execSQL(ToDoList);
        db.execSQL(items);

        String[] remType = new String[]{"SMS TYPE", "EMAIL TYPE", "SIMPLE REMINDER"};
        for (int i = 0; i < 3; i++) {
            ContentValues values = new ContentValues();
            values.put(ID_REMINDER_TYPE, i + 1);
            values.put(TYPE_REMINDER_TYPE, remType[i]);
            Log.e("Values: ", "" + values);
            db.insert(REMINDER_TYPE, null, values);
            //db.close();
        }

        String[] repeattype = new String[]{"ONCE", "DAILY", "MONTHLY", "YEARLY"};
        for (int i = 0; i < 4; i++) {
            ContentValues values = new ContentValues();
            values.put(ID_REPEAT_TYPE, i + 1);
            values.put(TYPE_REPEAT_TYPE, repeattype[i]);
            Log.e("Values: ", "" + values);
            db.insert(REPEAT_TYPE, null, values);

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getReminderType() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  " + TYPE_REMINDER_TYPE + " FROM " + REMINDER_TYPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        //db.close();

        // returning lables
        return labels;
    }


    public List<String> getRepeatType() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  " + TYPE_REPEAT_TYPE + " FROM " + REPEAT_TYPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        //db.close();

        // returning lables
        return labels;
    }


    public void createReminder(ReminderBean reminder) {
        SQLiteDatabase db = getWritableDatabase();
          /*  String query="INSERT INTO "+REMINDER_TABLE+" ("+TITLE_REMINDER_TABLE+","+TEXT_REMINDER_TABLE+","+TYPE_REMINDER_TABLE+","+REPEAT_REMINDER_TABLE+","+DATE_REMINDER_TABLE+","+TIME_REMINDER_TABLE+","+STATUS_REMINDER_TABLE+","+DELETE_REMINDER_TABLE+","+TONE_REMINDER_TABLE+") VALUES(\""+reminder.getTitle()+"\",\""+reminder.getText()+"\","+reminder.getType()+","+reminder.getRepeat()+",\""+reminder.getDate()+"\",\""+reminder.getTime()+"\","+reminder.getStatus()+","+reminder.getDelete()+",\""+reminder.getTone()+"\")";
            Log.e("Create Rem:",""+query);
            db.rawQuery(query,null);
        //int a=
        Log.e("inset",""+db.rawQuery(query,null));*/
        ContentValues values = new ContentValues();
        values.put(TITLE_REMINDER_TABLE, reminder.getTitle());
        values.put(TEXT_REMINDER_TABLE, reminder.getText());
        values.put(STATUS_REMINDER_TABLE, reminder.getStatus());
        values.put(DELETE_REMINDER_TABLE, reminder.getDelete());
        values.put(DATE_REMINDER_TABLE, reminder.getDate());
        values.put(TIME_REMINDER_TABLE, reminder.getTime());
        values.put(TYPE_REMINDER_TABLE, reminder.getType());
        values.put(REPEAT_REMINDER_TABLE, reminder.getRepeat());
        values.put(TONE_REMINDER_TABLE, reminder.getTone());

        Log.e("Values: ", "" + values);
        db.insert(REMINDER_TABLE, null, values);

        showReminder();
        //db.close();
    }


    public void showReminder() {
        Log.e("reminder1", "");
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + REMINDER_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("reminder", "" + cursor.getString(0));
                Log.e("reminder", "" + cursor.getString(1));
            } while (cursor.moveToNext());
        }
    }

    public ReminderBean getReminderDetailsById(String reminder_id)
    {
        ReminderBean reminderBean=null;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(REMINDER_TABLE,new String[]{ID_REMINDER_TABLE,TITLE_REMINDER_TABLE,TEXT_REMINDER_TABLE,TYPE_REMINDER_TABLE,REPEAT_REMINDER_TABLE,DATE_REMINDER_TABLE,TIME_REMINDER_TABLE,STATUS_REMINDER_TABLE,DELETE_REMINDER_TABLE,TONE_REMINDER_TABLE},ID_REMINDER_TABLE+"=?",new String[]{reminder_id},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                reminderBean = new ReminderBean(parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), parseInt(cursor.getString(3)),parseInt(cursor.getString(4)),cursor.getString(5),cursor.getString(6),cursor.getString(9),cursor.getShort(8),cursor.getShort(7));
            } while (cursor.moveToNext());
        }
        return reminderBean;

    }

    public EmailBean getMailByID(String reminder_id)
    {
        EmailBean reminderBean=null;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(EMAIL_TABLE,new String[]{ID_EMAIL_TABLE,NAME_EMAIL_TABLE,EMAIL_EMAIL_TABLE,TYPE_EMAIL_TABLE,NUMBER_EMAIL_TABLE,MESSAGE_EMAIL_TABLE,SUBJECT_EMAIL_TABLE},REM_ID_EMAIL_TABLE+"=?",new String[]{reminder_id},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                reminderBean = new EmailBean(parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            } while (cursor.moveToNext());
        }
        return reminderBean;

    }

    public SMSBean getSMSByID(String reminder_id)
    {
        SMSBean reminderBean=null;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(SMS_TABLE,new String[]{ID_SMS_TABLE,TO_SMS_TABLE,FROM_SMS_TABLE,TEXT_SMS_TABLE},REM_ID_SMS_TABLE+"=?",new String[]{reminder_id},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                reminderBean = new SMSBean(parseInt(cursor.getString(0)),
                        cursor.getString(1), parseInt(cursor.getString(2)), cursor.getString(3));
            } while (cursor.moveToNext());
        }
        Log.e("remidssd",""+reminderBean.getReciever());
        return reminderBean;

    }



    public int updateReminder(ReminderBean reminder,String reminderid)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TEXT_REMINDER_TABLE,""+reminder.getText());
        values.put(TITLE_REMINDER_TABLE,""+reminder.getTitle());
        values.put(TONE_REMINDER_TABLE,""+reminder.getTone());
        values.put(DATE_REMINDER_TABLE,""+reminder.getDate());
        values.put(TIME_REMINDER_TABLE,""+reminder.getTime());
        values.put(REPEAT_REMINDER_TABLE,""+reminder.getRepeat());

        return db.update(REMINDER_TABLE, values, ID_REMINDER_TABLE+"=?",
                new String[]{reminderid});

    }

    public int updateMessage(SMSBean sms,String reminderid)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TO_SMS_TABLE,""+sms.getReciever());
        values.put(TEXT_SMS_TABLE,""+sms.getMessage());

        return db.update(SMS_TABLE, values, REM_ID_SMS_TABLE+"=?",
                new String[]{reminderid});

    }

    public int updateMail(EmailBean email,String reminderid)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(EMAIL_EMAIL_TABLE,""+(email.getEmail_email()));
        values.put(SUBJECT_EMAIL_TABLE,""+email.getSubject_email());
        values.put(NAME_EMAIL_TABLE,""+email.getName_email());
        values.put(SUBJECT_EMAIL_TABLE,""+email.getSubject_email());

        return db.update(EMAIL_TABLE, values, REM_ID_EMAIL_TABLE+"=?",
                new String[]{reminderid});

    }



    public Cursor getLastReminderId() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT MAX(" + ID_REMINDER_TABLE + ") FROM " + REMINDER_TABLE;
        Log.e("getid", "" + query);
        return db.rawQuery(query, null);
        //return db.query(REMINDER_TABLE, new String[] { MAX(ID_REMINDER_TABLE) }, null, null, null, null, null, null);
    }

    public Cursor getSimpleReminderOnTime(String time) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + REMINDER_TABLE + " WHERE " + DELETE_REMINDER_TABLE+ " = 0 and " + TIME_REMINDER_TABLE + " = \"" + time+"\" and " + STATUS_REMINDER_TABLE + " = 1 and " + TYPE_REMINDER_TABLE + " = 2";
        Log.e("getreminder ", "" + query);
        return db.rawQuery(query, null);
    }

    public Cursor getSMSReminderOnTime(String time) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + REMINDER_TABLE + " WHERE " + DELETE_REMINDER_TABLE+ " = 0 and " + TIME_REMINDER_TABLE + " = \"" + time+"\" and " + STATUS_REMINDER_TABLE + " = 1 and " + TYPE_REMINDER_TABLE + " = 0";
        Log.e("getreminder ", "" + query);
        return db.rawQuery(query, null);
    }

    public Cursor getEmailReminderOnTime(String time) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + REMINDER_TABLE + " WHERE " + DELETE_REMINDER_TABLE+ " = 0 and " + TIME_REMINDER_TABLE + " = \"" + time+"\" and " + STATUS_REMINDER_TABLE + " = 1 and " + TYPE_REMINDER_TABLE + " = 1";
        Log.e("getreminder ", "" + query);
        return db.rawQuery(query, null);
    }

    public SMSBean getMessageOnTime(int Rem_id)
    {
        SMSBean bean=null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + SMS_TABLE+ " WHERE " + REM_ID_SMS_TABLE+ " = "+Rem_id;
        Log.e("getreminder ", "" + query);
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do{
                bean=new SMSBean(cursor.getShort(1),cursor.getString(2),cursor.getShort(3),cursor.getString(4));
            }while (cursor.moveToNext());
        }

        return bean;
    }

    public List<ReminderBean> getSimpleReminder(int deleteSatatus) {
        List<ReminderBean> list = new ArrayList<ReminderBean>();
        SQLiteDatabase db = this.getWritableDatabase();
       /*Cursor cursor = sqLiteDatabase.query(
                tableName, tableColumns, whereClause, whereArgs, groupBy, having, orderBy);
                String[] columns = new String[]{KEY_ID, KEY_CONTENT};
       Gen dob

       */
        Log.e("details", "asc");
        try {
            Cursor cursor = db.query(REMINDER_TABLE, new String[]{ID_REMINDER_TABLE, TITLE_REMINDER_TABLE, TEXT_REMINDER_TABLE, TYPE_REMINDER_TABLE, REPEAT_REMINDER_TABLE, DATE_REMINDER_TABLE, TIME_REMINDER_TABLE, STATUS_REMINDER_TABLE, DELETE_REMINDER_TABLE, TONE_REMINDER_TABLE}, DELETE_REMINDER_TABLE + "=? and " + TYPE_REMINDER_TABLE + "=?", new String[]{Integer.toString(deleteSatatus), "2"}, null, null, null);
            Log.e("cursor", "" + cursor);
            Log.e("2details", "asc");
            if (cursor.moveToFirst()) {
                do {
                    Log.e("details2", "" + cursor.getString(0));

                    ReminderBean reminder = new ReminderBean(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6), cursor.getString(9), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
                    list.add(reminder);
                    Log.e("details", "" + cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception in reminder", "" + e);
        }
        return list;

    }

    public List<ReminderBean> getMessageReminder(int deleteStatus) {
        List<ReminderBean> list = new ArrayList<ReminderBean>();
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("details", "asc");
        try {
            Cursor cursor = db.query(REMINDER_TABLE, new String[]{ID_REMINDER_TABLE, TITLE_REMINDER_TABLE, TEXT_REMINDER_TABLE, TYPE_REMINDER_TABLE, REPEAT_REMINDER_TABLE, DATE_REMINDER_TABLE, TIME_REMINDER_TABLE, STATUS_REMINDER_TABLE, DELETE_REMINDER_TABLE, TONE_REMINDER_TABLE}, DELETE_REMINDER_TABLE + "=? and " + TYPE_REMINDER_TABLE + "=?", new String[]{Integer.toString(deleteStatus), "0"}, null, null, null);
            Log.e("cursor", "" + cursor);
            Log.e("2details", "asc");
            if (cursor.moveToFirst()) {
                do {
                    Log.e("details2", "" + cursor.getString(0));

                    ReminderBean reminder = new ReminderBean(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6), cursor.getString(9), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
                    list.add(reminder);
                    Log.e("details", "" + cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception in reminder", "" + e);
        }
        return list;

    }


    public List<ReminderBean> getEmailReminder(int deleteStatus) {
        List<ReminderBean> list = new ArrayList<ReminderBean>();
        SQLiteDatabase db = this.getWritableDatabase();


        Log.e("details", "asc");
        try {
            Cursor cursor = db.query(REMINDER_TABLE, new String[]{ID_REMINDER_TABLE, TITLE_REMINDER_TABLE, TEXT_REMINDER_TABLE, TYPE_REMINDER_TABLE, REPEAT_REMINDER_TABLE, DATE_REMINDER_TABLE, TIME_REMINDER_TABLE, STATUS_REMINDER_TABLE, DELETE_REMINDER_TABLE, TONE_REMINDER_TABLE}, DELETE_REMINDER_TABLE + "=? and " + TYPE_REMINDER_TABLE + "=?", new String[]{Integer.toString(deleteStatus), "1"}, null, null, null);
            Log.e("cursor", "" + cursor);
            Log.e("2details", "asc");
            if (cursor.moveToFirst()) {
                do {
                    Log.e("details2", "" + cursor.getString(0));

                    ReminderBean reminder = new ReminderBean(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6), cursor.getString(9), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
                    list.add(reminder);
                    Log.e("details", "" + cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception in reminder", "" + e);
        }
        return list;

    }


    public int deleteReminder(String  id,int deleteStatus)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DELETE_REMINDER_TABLE,""+deleteStatus);
        return db.update(REMINDER_TABLE, values, ID_REMINDER_TABLE+"=?",
                new String[]{String.valueOf(id)});
    }

    public void pemanentdeleteReminder(String  id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(REMINDER_TABLE,ID_REMINDER_TABLE+"=?",
                new String[]{String.valueOf(id)});
    }


    public void createMessage(SMSBean sms)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REM_ID_SMS_TABLE, sms.getRem_id());
        values.put(TO_SMS_TABLE, sms.getReciever());
        values.put(FROM_SMS_TABLE, sms.getSender());
        values.put(TEXT_SMS_TABLE, sms.getMessage());
        Log.e("Values: ", "" + values);
        db.insert(SMS_TABLE, null, values);
        showMessage();
        //db.close();

    }

    public void deleteAll()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(REMINDER_TABLE,DELETE_REMINDER_TABLE+"=?",
                new String[]{String.valueOf(1)});
        db.delete(TODOLIST_TABLE,DELETE_TODOLIST_TABLE+"=?",new String[]{String.valueOf(1)});
        Log.e("queryreminder",""+db.delete(REMINDER_TABLE,DELETE_REMINDER_TABLE+"=?",
                new String[]{String.valueOf(1)}));
        Log.e("querytodolist",""+db.delete(REMINDER_TABLE,DELETE_REMINDER_TABLE+"=?",
                new String[]{String.valueOf(1)}));
    }

    public void showMessage() {
        Log.e("reminder1", "");
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + SMS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("reminder", "" + cursor.getString(0));
                Log.e("reminder", "" + cursor.getString(1));
            } while (cursor.moveToNext());
        }
    }


    public void createToDoList(String title)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_TODOLIST_TABLE, title);
        values.put(DELETE_TODOLIST_TABLE, 0);

        Log.e("Values: ", "" + values);
        db.insert(TODOLIST_TABLE, null, values);

        String query = "SELECT * FROM " + SMS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("reminder", "" + cursor.getString(0));
                Log.e("reminder", "" + cursor.getString(1));
            } while (cursor.moveToNext());
        }       //db.close();

    }

    public int getLastTodolistId() {
        int id=0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT MAX(" + ID_TODOLIST_TABLE+ ") FROM " + TODOLIST_TABLE;
        Log.e("getid", "" + query);
        Cursor cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            do {
                id = Integer.parseInt(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return id;
        //return db.query(REMINDER_TABLE, new String[] { MAX(ID_REMINDER_TABLE) }, null, null, null, null, null, null);
    }

    public void AddItems(int todolistid,String title)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_LISTITEM_TABLE, title);
        values.put(ID_TODOLIST_LISTITEM_TABLE, todolistid);
        values.put(STATUS_LISTITEM_TABLE, 0);

        Log.e("Values: ", "" + values);
        db.insert(LISTITEM_TABLE, null, values);

        String query = "SELECT * FROM " + LISTITEM_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("Itemid", "" + cursor.getString(0));
                Log.e("todolist", "" + cursor.getString(1));
                Log.e("item name",""+cursor.getString(2));
            } while (cursor.moveToNext());
        }       //db.close();

    }

    public List<ItemBean> showAllItems(int idtodolist)
    {
        List<ItemBean> list=new ArrayList<ItemBean>();
        SQLiteDatabase db=this.getWritableDatabase();
       /*Cursor cursor = sqLiteDatabase.query(
                tableName, tableColumns, whereClause, whereArgs, groupBy, having, orderBy);
                String[] columns = new String[]{KEY_ID, KEY_CONTENT};
       Gen dob */
        Cursor cursor=db.query(LISTITEM_TABLE,new String[]{ID_LISTITEM_TABLE,ID_TODOLIST_LISTITEM_TABLE,ITEM_LISTITEM_TABLE,STATUS_LISTITEM_TABLE}, ID_TODOLIST_LISTITEM_TABLE + "=?", new String[]{Integer.toString(idtodolist)},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                ItemBean emp = new ItemBean(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), cursor.getString(2),Integer.parseInt(cursor.getString(3)));
                list.add(emp);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<TodoListBean> showTodoList(int deleteStatus) {
        List<TodoListBean> list = new ArrayList<TodoListBean>();
        SQLiteDatabase db = this.getWritableDatabase();
       /*Cursor cursor = sqLiteDatabase.query(
                tableName, tableColumns, whereClause, whereArgs, groupBy, having, orderBy);
                String[] columns = new String[]{KEY_ID, KEY_CONTENT};
       Gen dob

       */
        Log.e("details", "asc");
        try {
            Cursor cursor = db.query(TODOLIST_TABLE, new String[]{ID_TODOLIST_TABLE, TITLE_TODOLIST_TABLE, DELETE_TODOLIST_TABLE}, DELETE_TODOLIST_TABLE+ "=? ", new String[]{Integer.toString(deleteStatus)}, null, null, null);
            Log.e("cursor", "" + cursor);
            Log.e("2details", "asc");
            if (cursor.moveToFirst()) {
                do {
                    Log.e("details2", "" + cursor.getString(0));

                    TodoListBean todolist= new TodoListBean(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                    list.add(todolist);
                    Log.e("details", "" + cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception in todolist", "" + e);
        }
        return list;

    }

    public String getListTitle(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor = db.query(TODOLIST_TABLE, new String[]{ID_TODOLIST_TABLE, TITLE_TODOLIST_TABLE, DELETE_TODOLIST_TABLE}, ID_TODOLIST_TABLE+ "=? ", new String[]{Integer.toString(id)}, null, null, null);
        if(cursor.moveToFirst())
        {
            Log.e("title:",""+cursor.getString(1));
            return cursor.getString(1);
        }
        else
        {
            return null;
        }
    }

    public int deleteTodolist(int id, int deleteStatus)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DELETE_TODOLIST_TABLE,""+deleteStatus);
        return db.update(TODOLIST_TABLE, values, ID_TODOLIST_TABLE+"=?",
                new String[]{String.valueOf(id)});

    }

    public void pemanentdeleteTodoList(String  id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TODOLIST_TABLE,ID_TODOLIST_TABLE+"=?",
                new String[]{String.valueOf(id)});
    }



    public void createEmail(EmailBean emailBean)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(REM_ID_EMAIL_TABLE, emailBean.getRem_id());
        values.put(EMAIL_EMAIL_TABLE, emailBean.getEmail_email());
        values.put(NAME_EMAIL_TABLE, emailBean.getName_email());
        values.put(TYPE_EMAIL_TABLE, emailBean.getType_email());
        values.put(NUMBER_EMAIL_TABLE, emailBean.getNumber_email());
        values.put(SUBJECT_EMAIL_TABLE, emailBean.getSubject_email());
        values.put(MESSAGE_EMAIL_TABLE, emailBean.getMessage_email());

        db.insert(EMAIL_TABLE,null,values);

        String query = "SELECT * FROM " + EMAIL_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("Emailid", "" + cursor.getString(0));
                Log.e("Reminderid", "" + cursor.getString(1));
                Log.e("Email name",""+cursor.getString(2));
            } while (cursor.moveToNext());
        }

        //db.close();
    }

    public void createEmailInternet(EmailBean emailBean)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(REM_ID_EMAIL_TABLE, emailBean.getRem_id());
        values.put(EMAIL_EMAIL_TABLE, emailBean.getEmail_email());
        values.put(NAME_EMAIL_TABLE, emailBean.getName_email());
        values.put(TYPE_EMAIL_TABLE, emailBean.getType_email());
        values.put(NUMBER_EMAIL_TABLE, emailBean.getNumber_email());
        values.put(SUBJECT_EMAIL_TABLE, emailBean.getSubject_email());
        values.put(MESSAGE_EMAIL_TABLE, emailBean.getMessage_email());

        db.insert(EMAIL_TABLE_INTERNET,null,values);

        Log.e("value into internet",""+values);

    }

    public Cursor getEmailInternet()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + EMAIL_TABLE_INTERNET;
        Log.e("getreminder ", "" + query);
        return db.rawQuery(query,null);

    }

    public void deleteEmailInternet(int mailid)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(EMAIL_TABLE_INTERNET,ID_EMAIL_TABLE+ "=? ", new String[]{Integer.toString(mailid)});
        Log.e("Deleted mail","");

    }





    public EmailBean getEmailOnTime(int Rem_id)
    {
        EmailBean bean=null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + EMAIL_TABLE+ " WHERE " + REM_ID_EMAIL_TABLE+ " = "+Rem_id;
        Log.e("getreminder ", "" + query);
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do{
                bean=new EmailBean(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
            }while (cursor.moveToNext());
        }

        return bean;
    }




}


