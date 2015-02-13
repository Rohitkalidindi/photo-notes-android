package coen268.hw3.photonotes;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity {

	ListView list;
	String name;
	String caption;
	ArrayList<String> PhotoCaptionList =  new ArrayList<String>();
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    list = (ListView) findViewById(R.id.listView1);
		query();
	  	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                 this, 
	                 android.R.layout.simple_list_item_1,PhotoCaptionList );	  	
        list.setAdapter(arrayAdapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i = new Intent(ListActivity.this, ViewPhotoActivity.class);
				i.putExtra("Id", position);
		    	startActivity(i);
				
			}
		});
	    }

	@Override
	protected void onResume() {
		 super.onResume();
		list = (ListView) findViewById(R.id.listView1);
		query();
	  	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                 this, 
	                 android.R.layout.simple_list_item_1,PhotoCaptionList );	  	
        list.setAdapter(arrayAdapter);
	}
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     getMenuInflater().inflate(R.menu.main, menu);
	     return true;
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
        case R.id.ADD:
        	Intent newActivity = new Intent(ListActivity.this, AddPhotoActivity.class);
        	startActivity(newActivity);
            return true;
		} 
		return super.onOptionsItemSelected(menu);
	}

	private void query() {
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
			String name=cursor.getString(1);
			if(PhotoCaptionList.contains(name)){
				continue;
			}
			else{
				PhotoCaptionList.add(name);
			}
			String filepath = cursor.getString(2);
			Log.d("PHOTO", String.format("%s,%s,%s", id, name, filepath));
		}
		db.close();
	}

	
}
