package coen268.hw3.photonotes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddPhotoActivity extends Activity implements View.OnClickListener {

	private Button takePicture,save;
	private EditText caption;
	String filePath;
	ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_photo_activity);
		initialize();	

	}
	
	public void initialize(){
		takePicture=(Button)findViewById(R.id.button1);
		save=(Button)findViewById(R.id.button2);
		caption=(EditText)findViewById(R.id.editText1);
		takePicture.setOnClickListener(this);
		save.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
			case R.id.button1:
				dispatchTakePictureIntent();
				img=(ImageView)findViewById(R.id.imageView1);
	            File imgFile = new  File(filePath);
	    		if(imgFile.exists()){
	    			BitmapFactory.Options options= new BitmapFactory.Options();
	    			options.inSampleSize=8;
	    		    Bitmap b = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	    		    img.setImageBitmap(b);
	    		}
				break;
		    
		    case R.id.button2:
		    	String caption1=caption.getText().toString();
		    	insertPhoto(caption1, filePath);
		    	this.finish();
		    	
		    	break;
		}
	}
	
	private void insertPhoto(String name, String filePath) {
		SQLiteDatabase db = new PhotoDBHelper(this).getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(PhotoDBHelper.CAPTION_COLUMN, name);
		newValues.put(PhotoDBHelper.FILE_PATH_COLUMN, filePath);
		db.insert(PhotoDBHelper.DATABASE_TABLE, null, newValues);
		db.close();
	}
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, 5);
	        }
	    }
	    
	}

	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    filePath = "" + image.getAbsolutePath();
	    return image;
	}
}