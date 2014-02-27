package com.example.tesseractpic_2_text;




import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Button cameraButton;
	private Button galleryButton;
	private ImageView photoDisplay;
	private Uri outputFileUri;
	private int REQUEST_GALLERY = 2;
	private int REQUEST_CAMERA = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final View contentView = findViewById(R.id.fullscreen_content);		

		cameraButton = (Button) findViewById(R.id.camera_button);
		galleryButton = (Button) findViewById(R.id.gallery_button);
		
		

		galleryButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View x) {
				
				Intent galleryIntent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
				outputFileUri = galleryIntent.getData();				
				galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);				
				startActivityForResult(galleryIntent, REQUEST_GALLERY);				
			}
		});
		

		cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, REQUEST_CAMERA);	
              	}
        });
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_CAMERA){
			
		}
		else if(requestCode == REQUEST_GALLERY){
		}
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
