package coen268.hw3.photonotes;

import java.io.File;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhotoActivity extends Activity {
	String name,filepath;
	int list_id;
	TextView caption;
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_photo);
		
		list_id=getIntent().getExtras().getInt("Id");
		queryDB();
		caption= (TextView)findViewById(R.id.caption);
		image=(ImageView)findViewById(R.id.image);
		caption.setText(name);
		File imgFile = new  File(filepath);
		if(imgFile.exists()){
			BitmapFactory.Options options= new BitmapFactory.Options();
			options.inSampleSize=8;
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    image.setImageBitmap(myBitmap);
		}
	}


	private void queryDB() {
		SQLiteDatabase db = new PhotoDBHelper(this).getWritableDatabase();
		String where = null;
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		String[] resultColumns = {PhotoDBHelper.ID_COLUMN, PhotoDBHelper.CAPTION_COLUMN, PhotoDBHelper.FILE_PATH_COLUMN};
		Cursor cursor = db.query(PhotoDBHelper.DATABASE_TABLE, resultColumns, where, whereArgs, 
				groupBy, having, order);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(PhotoDBHelper.ID_COLUMN));
			if(list_id+1==id){
			name=cursor.getString(1);
			filepath = cursor.getString(2);
			Log.d("PHOTO", String.format("%s,%s,%s", id, name, filepath));
			break;
			}
		}
		db.close();
	}

}
