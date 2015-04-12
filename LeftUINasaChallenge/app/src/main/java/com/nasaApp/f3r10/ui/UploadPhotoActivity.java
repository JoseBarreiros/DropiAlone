package com.nasaApp.f3r10.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nasaApp.f3r10.R;
import com.nasaApp.f3r10.http.request.MultipartEntity;
import com.nasaApp.f3r10.http.request.MultipartRequest;
import com.nasaApp.f3r10.util.GPSTracker;
import com.nasaApp.f3r10.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Matrix;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class UploadPhotoActivity extends BaseActivity {

    Utils utils = new Utils();
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect,choiceButton1,choiceButton2;
    ImageView ivImage;
    EditText namePlant, typeRegion;
    TextView storyTextView;
    private int TAKE_PICTURE = 9000;
    private int serverResponseCode = 0;
    private double latitude;
    private double longitude;
    String urlPost = utils.baseurl +  "WebServAlone/alone/imagen/subir/";
    private MultipartRequest mPR;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    private static final String TAG = "upload";
    FrameLayout progressBarHolder;
    private Bitmap bitmap;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    private String mResponse;

    private String name;

    private String path;
    private GPSTracker gps;

    private Spinner spinnerZone, spinnerDropLeaf;

    private String zoneCrop;

    private String dropLeaft;

    private AlertDialog alertDialog;


    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.setStatusBarBackground(R.color.material_color_primary_dark);

        // Tie the DrawerLayout with the Toolbar.
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                getToolbar(), R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);

        ivImage = (ImageView)findViewById(R.id.storyImageView);
        namePlant = (EditText)findViewById(R.id.nameCrop);
        //typeRegion = (EditText)findViewById(R.id.zoneCrop);
        storyTextView = (TextView)findViewById(R.id.storyTextView);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        spinnerZone = (Spinner)findViewById(R.id.spinnerZone);
        spinnerDropLeaf = (Spinner)findViewById(R.id.spinnerDropLeaf);
        ArrayAdapter adapterDrop  = ArrayAdapter.createFromResource(this, R.array.dropLeaft, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter  = ArrayAdapter.createFromResource(this, R.array.zoneCrop, android.R.layout.simple_spinner_item);
        spinnerZone.setAdapter(adapter);
        spinnerDropLeaf.setAdapter(adapterDrop);
        btnSelect = (Button) findViewById(R.id.buttonTakePhoto);
        gps = new GPSTracker(this);
        initGps();
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
                btnSelect.setEnabled(false);
                choiceButton1.setEnabled(true);
            }
        });

        choiceButton1 = (Button)findViewById(R.id.choiceButton1);
        choiceButton1.setEnabled(false);
        choiceButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refresh();

            }
        });

        spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                zoneCrop = parentView.getItemAtPosition(position).toString();

            }

            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerDropLeaf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                dropLeaft = parentView.getItemAtPosition(position).toString();

            }

            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        choiceButton2 = (Button)findViewById(R.id.choiceButton2);
        choiceButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Picasso.with(getApplication()).load(R.drawable.page0).into(ivImage);
                storyTextView.setText("");
                namePlant.setText("");
                btnSelect.setEnabled(true);
                choiceButton1.setEnabled(true);


            }
        });

        alertDialog = new AlertDialog.Builder(UploadPhotoActivity.this).create();
        info();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_upload_photo;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotoActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    File photoFile = null;
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                if(ivImage != null){
                    ((BitmapDrawable)ivImage.getDrawable()).getBitmap().recycle();
                    Picasso.with(this).load("file://" + mCurrentPhotoPath).resize(350, 350).centerCrop().into(ivImage);
                }
                else {
                    Picasso.with(this).load(mCurrentPhotoPath).resize(450, 450).centerCrop().into(ivImage);
                }
            }
        }
    }


    public void refresh() {
        setPic();
        TareaWSInsertar tarea = new TareaWSInsertar();
        tarea.execute();


    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/picupload";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivImage.getWidth();
        int targetH = ivImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/600, photoH/800);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        Matrix mtx = new Matrix();
        mtx.postRotate(90);
        // Rotating Bitmap
        Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

        if (rotatedBMP != bitmap)
            bitmap.recycle();

        //mImageView.setImageBitmap(rotatedBMP);

        try {
            sendPhoto(rotatedBMP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);
    }

    private class UploadTask extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            choiceButton1.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            choiceButton1.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            if (bitmaps[0] == null)
                return null;
            setProgress(0);
            name = System.currentTimeMillis() + ".jpg";
            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                HttpPost httppost = new HttpPost(
                        utils.baseurl + "WebServAlone/alone/imagen/subir/"); // server

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("uploadedFile",name, in);
                httppost.setEntity(reqEntity);

                Log.i(TAG, "request " + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    response = httpclient.execute(httppost);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    if (response != null)
                        Log.i(TAG, "response " + response.getStatusLine().toString());
                } finally {

                }
            } finally {

            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

    }

    public void initGps(){
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            //cant get location
            //GPS or Network is not enabled
            //Ask user to enable GPS in settings
            gps.showSettingsAlert();
        }

    }

    private class TareaWSInsertar extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
			/*
			 * String nombre=params[0]; int
			 * telefono=Integer.parseInt(params[1]);
			 */

            // String nombre = txtNombre.getText().toString();
            // int telefono =
            // Integer.parseInt(txtTelefono.getText().toString());*/

            String uri =  utils.baseurl + "WebServAlone/alone/insertar/";
            HttpPost post = new HttpPost(uri);
            post.setHeader("content-type", "application/json");
            try {
                JSONObject photo = new JSONObject();
                // dato.put("Id", Integer.parseInt(txtId.getText().toString()));
                photo.put("idphoto", "");
                photo.put("photoDamagePercent", "");
                photo.put("photoLat", Double.toString(latitude));
                photo.put("photoLon", Double.toString(longitude));
                photo.put("photoName", name );
                photo.put("photoNdvi", "");
                photo.put("photoOzonePercent", "");
                photo.put("photoPlantName", namePlant.getText().toString());
                photo.put("photoZone", zoneCrop);
                photo.put("photoDrop", dropLeaft );

				/*
				 * dato.put("Telefono",
				 * Integer.parseInt(txtTelefono.getText().toString()));
				 */

                StringEntity entity = new StringEntity(photo.toString());
                String aux = photo.toString();
                System.out.println(entity);
                System.out.println(aux);// Impresion de las variables

                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());
                if (respStr == null)
                    resul = false;
                else mResponse = respStr;

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);// Error
                resul = false;
            }
            return resul;

        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                if(mResponse !=null)
                {
                    storyTextView.setText(Html.fromHtml(mResponse));
                    choiceButton1.setEnabled(false);
                    alertDialog.show();

                }
                else{
                    Log.d("Upload", "Fail");
                }

            } else {
                System.out.println("Temrino sin ingresar");
            }
        }
    }



    private void info(){
        // Setting Dialog Title
        alertDialog.setTitle("TYPE CLASS");

        // Setting Dialog Message
        alertDialog.setMessage(
                "CLASS 1 = 0% No ozone-induced injury\n" +
                "CLASS 2 = 1 to 6% Light ozone-induced injury\n" +
                "CLASS 3 = 7 to 25% Moderate ozone-induced injury\n" +
                "CLASS 4 = 26 to 50% Moderately severe ozone-induced injury\n" +
                "CLASS 5 = 51 to 75% Severe ozone-induced injury\n" +
                "CLASS 6 = 76 to 100% Extremely severe ozone-induced injury\n" +
                "CLASS 7 = leaf dropped with no prior symptoms or just missing\n" +
                "CLASS 8 = leaf dropped with prior chlorosis (yellowing) only\n" +
                "CLASS 9 = leaf dropped with prior stippling (the tan to brownish dots) only\n" +
                "CLASS 10 = leaf dropped with all or some of the following: stippling (small dark colored dots), chlorosis (yellowing), or necrosis (browning)");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
    }


}
