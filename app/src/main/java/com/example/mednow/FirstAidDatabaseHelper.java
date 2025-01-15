package com.example.mednow;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FirstAidDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "firstAidGuidelines.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "guidelines";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KEYWORD = "keyword";
    private static final String COLUMN_GUIDELINE = "guideline";

    public FirstAidDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_KEYWORD + " TEXT, "
                + COLUMN_GUIDELINE + " TEXT)";
        db.execSQL(CREATE_TABLE);

        // Insert initial data
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('bleeding', 'Apply direct pressure to the wound using a clean cloth.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('burn', 'Cool the burn under cool (not cold) running water for 10-15 minutes.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('choking', 'If the person is able to cough, encourage them to continue coughing. If they cannot, perform the Heimlich maneuver.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('fracture', 'Immobilize the injured area and avoid movement. Apply a splint if trained to do so.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('heart attack', 'Call emergency services immediately. Keep the person calm and have them sit down.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('stroke', 'Use the FAST method to identify stroke symptoms and call emergency services if present.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('CPR', 'Perform chest compressions at 100-120 per minute and give 2 rescue breaths after every 30 compressions if trained.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('asthma', 'Sit the person upright and keep them calm. Assist with their inhaler if available.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('seizure', 'Protect the person from injury and place them in the recovery position once the seizure ends.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('anaphylaxis', 'Administer an epinephrine auto-injector if available and call emergency services.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('nosebleed', 'Sit upright, lean forward, and pinch the soft part of the nose.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('heat exhaustion', 'Move the person to a cool area and offer cool water to drink. Apply a cool cloth to the neck.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('hypothermia', 'Move the person to a warm place and cover them with blankets. Offer warm drinks if conscious.')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (keyword, guideline) VALUES ('poisoning', 'Call emergency services or poison control. Do not induce vomiting unless instructed.')");
        // Add more guidelines as needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to retrieve guidelines by keywords
    public String getGuidelinesByKeywords(String[] keywords) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder guidelines = new StringBuilder();
        StringBuilder query = new StringBuilder("SELECT " + COLUMN_GUIDELINE + " FROM " + TABLE_NAME + " WHERE ");

        for (int i = 0; i < keywords.length; i++) {
            query.append(COLUMN_KEYWORD + " LIKE ?");
            if (i < keywords.length - 1) {
                query.append(" OR ");
            }
        }

        String[] keywordArgs = new String[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            keywordArgs[i] = "%" + keywords[i] + "%";
        }

        Cursor cursor = db.rawQuery(query.toString(), keywordArgs);
        if (cursor.moveToFirst()) {
            do {
                guidelines.append("• ").append(cursor.getString(0)).append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return guidelines.toString();
    }
}
