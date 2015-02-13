package coen268.hw3.photonotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotoDBHelper extends SQLiteOpenHelper {
	public static final String ID_COLUMN = "_id";
	public static final String CAPTION_COLUMN = "CAPTION_COLUMN";
	public static final String DESCRIPTION_COLUMN = "DESCRIPTION_COLUMN";
	public static final String FILE_PATH_COLUMN = "FILE_PATH_COLUMN";
	
	public static final String DATABASE_TABLE = "Zoo";
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" +
			"  %s integer primary key autoincrement, " +
			"  %s text," +
			"  %s text)",
			DATABASE_TABLE, ID_COLUMN, CAPTION_COLUMN, FILE_PATH_COLUMN);

	public PhotoDBHelper(Context context) {
		super(context, DATABASE_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}
}