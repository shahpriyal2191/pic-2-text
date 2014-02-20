package com.example.pic_2_text;

import java.io.File;

import com.example.pic_2_text.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Application_Layout extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	
	
	
	 private Camera mCamera;
	 public final int cameraId = 0;
	 public Activity activity = null;
	 
	private static final boolean AUTO_HIDE = true;
	
	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	private static final int photoResultView = 0;
	
	private static final int CAMERA_PIC_REQUEST = 1337;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_application__layout);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI. 
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
		
		

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		//findViewById(R.id.button1).setOnTouchListener(mDelayHideTouchListener);
		final Button button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Context context = getApplicationContext();
            	//CharSequence text = "Hello toast!";
            	//int duration = Toast.LENGTH_SHORT;
            	//Toast toast = Toast.makeText(context, text, duration);
            	//toast.show();
            	//Camera.open();
            	//Camera.Parameters.
              	//Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
               	//startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            	saveFullImage();
            	
            	
            	
            	
            
            	}
        });
	

	}

	
	private void saveFullImage() {
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
	Uri outputFileUri = Uri.fromFile(file);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	startActivityForResult(intent, CAMERA_PIC_REQUEST);
}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAMERA_PIC_REQUEST) {
	    	if ((requestCode == CAMERA_PIC_REQUEST) && (resultCode == Activity.RESULT_OK)) {
	    		
	    	}
	    	if (data == null) {
	    		
	    	}
	    	//Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        	//ImageView image = (ImageView) findViewById(R.id.photoResultView);
        	//image.setImageBitmap(thumbnail);
	    }
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};
	
	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}




/*public class Camera extends Application_Layout
{
    private Camera mCamera;
    private CameraPreview mPreview;
    public final int cameraId = 0;
    public Activity activity = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        activity = this; 

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    public void setCameraDisplayOrientation(Activity activity,
                        int cameraId, android.hardware.Camera camera) {

    }

    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    ...
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        ...
        setCameraDisplayOrientation(activity, cameraId, mCamera);
        ....
    }
    }
}*/ 
