package com.example.orderyourfood.databasemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.orderyourfood.cartmanagement.OrderCart;

import java.util.ArrayList;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="UsersData.db";

    private static final String TABLE1="Profile";
    private static final String NAME_COLUMN="name";
    private static final String EMAIL_COLUMN="email";
    private static final String PHONE_COLUMN="phone";
    private static final String IMAGE_COLUMN="image";
    private static final String ADDRESS_COLUMN="address";
    private static final String ID_COLUMN="id";
    private static final String PASSWORD_COLUMN="password";

    private static final String TABLE2="Cart";
    private static final String FOOD_NAME_COLUMN="Food";
    private static final String FOOD_COUNT="Count";
    private static final String PRICE="Price";


    public Dbhelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  Profile (id text,email text,password text,name text,image BLOB,address text ,phone text)");
        db.execSQL("create table  Cart(id Integer ,Food text,Count Integer,Price Integer,email text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE1 );
        db.execSQL("drop table if exists "+TABLE2 );
        onCreate(db);
    }
    public void setImage(String email,byte[] id) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(IMAGE_COLUMN,id);
        db.update(TABLE1,contentValues,EMAIL_COLUMN+" = ? ",new String[]{email});
    }
    public void setName(String email,String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME_COLUMN,name);
        db.update(TABLE1,contentValues,EMAIL_COLUMN+" = ? ",new String[]{email});
    }
    public void setPhone(String email, String phone)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PHONE_COLUMN,phone);
        db.update(TABLE1,contentValues,EMAIL_COLUMN+" = ? ",new String[]{email});
    }
    public void setBasicInfo(String email, String password,String uniqueId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        int cnt=0;
        Cursor cursor=db.rawQuery("select * from "+TABLE1+" where email='"+email+"'",null);
        while(cursor.moveToNext())
        {
            cnt++;
        }
        cursor.close();
        if(0==cnt)
        {
            contentValues.put(EMAIL_COLUMN,email);
            contentValues.put(ID_COLUMN,uniqueId);
            contentValues.put(PASSWORD_COLUMN,password);
            db.insert(TABLE1,null,contentValues);
        }
    }
    public void setAddress(String email,String address)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ADDRESS_COLUMN,address);
        db.update(TABLE1,contentValues,EMAIL_COLUMN+" = ? ",new String[]{email});
    }
    public String getAddress(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String address = " ";
        Cursor cursor=db.rawQuery("select address from "+TABLE1+" where email='"+email+"'",null);
        while (cursor.moveToNext())
        {
            address=cursor.getString(0);
        }
        cursor.close();
        return address;
    }
    public byte[] getImage(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        byte[] bytes = new byte[0];
        Cursor cursor=db.rawQuery("select image from "+TABLE1+" where email='"+email+"'",null);
        while (cursor.moveToNext())
        {
            bytes= cursor.getBlob(0);
        }
        cursor.close();
        return bytes;

    }
    public String getName(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String name = " ";
        Cursor cursor=db.rawQuery("select name from "+TABLE1+" where email='"+email+"'",null);
        while (cursor.moveToNext())
        {
            name=cursor.getString(0);
        }
        cursor.close();
        return name;
    }
    public String getNumber(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String phone = " ";
        Cursor cursor=db.rawQuery("select phone from "+TABLE1+" where email='"+email+"'",null);
        while (cursor.moveToNext())
        {
            phone=cursor.getString(0);
        }
        cursor.close();
        return phone;
    }

    public void addCart(int id,String name,int price,String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        int itemsCount=get_total_items(id,email);
        if(itemsCount==0)
        {
            contentValues.put(ID_COLUMN,id);
            contentValues.put(FOOD_NAME_COLUMN,name);
            contentValues.put(PRICE,price);
            contentValues.put(FOOD_COUNT,1);
            contentValues.put(EMAIL_COLUMN,email);
            db.insert(TABLE2,null,contentValues);
        }
        else {
            itemsCount++;
            db.execSQL("update Cart set count="+itemsCount+" where id="+id+" and '"+email+"'"+"="+EMAIL_COLUMN);
        }
    }
    private int get_total_items(int image_id,String email) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select count from Cart  where id="+image_id+" and '"+email+"'"+"="+EMAIL_COLUMN,null);
        int counts=0;
        while(cursor.moveToNext())
        {
            counts=cursor.getInt(0);
        }
        cursor.close();
        return counts;
    }
    public int getTotal(String email)
    {
        int total=0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from Cart  where '"+String.valueOf(email)+"'"+"="+EMAIL_COLUMN,null);
        while (cursor.moveToNext())
        {
            total+=(cursor.getInt(2)*cursor.getInt(3));
        }
        cursor.close();
        return total;
    }

    public void remove(int image_id,String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        int itemsCount=get_total_items(image_id,email);
        if(itemsCount<=1)
        {
            db.execSQL("delete from Cart where id="+image_id+" and '"+email+"'"+"="+EMAIL_COLUMN);
        }
        else {
            itemsCount--;
            db.execSQL("update Cart set count="+itemsCount+" where id="+image_id+" and '"+email+"'"+"="+EMAIL_COLUMN);
        }
    }

    public ArrayList<OrderCart> getAllOrders(String userEmail) {
        ArrayList<OrderCart>Orders=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from Cart  where '"+userEmail+"'"+"="+EMAIL_COLUMN,null);
        while (cursor.moveToNext())
        {
            Orders.add(new OrderCart(cursor.getInt(0),cursor.getString(1),cursor.getInt(3),cursor.getInt(2)));
        }
        cursor.close();
        return Orders;
    }

    public boolean isPresent(String userEmail) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from Profile  where '"+userEmail+"'"+"="+EMAIL_COLUMN,null);
        int cnt=0;
        while(cursor.moveToNext())
        {
            cnt++;
        }
        cursor.close();
        return cnt>=1;
    }

    public void setPassword(String userEmail, String password) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(EMAIL_COLUMN,userEmail);
        contentValues.put(PASSWORD_COLUMN,password);
        db.update(TABLE1,contentValues,EMAIL_COLUMN+"=?",new String[]{userEmail});
    }
    public boolean isPresent(String email,String userPassword)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from Profile  where '"+userPassword+"'"+"="+PASSWORD_COLUMN+" and '"+email+"'"+"="+EMAIL_COLUMN,null);
        int cnt=0;
        while(cursor.moveToNext())
        {
            cnt++;
        }
        return cnt>=1;
    }
}
