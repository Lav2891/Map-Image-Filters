package lav.wru1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ashwin on 3/13/2018.
 */

public class PicFrag extends Fragment {
    View view;
    ImageView chooseimge_iv;
    Button blur_b, undo_b, mirror_b, red_b, green_b, blue_b, colors_b, save_b, original_b;
    LinearLayout colors_ll, edit_ll;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bmp, obmp;
    Bitmap bmOut;
    int value;
    int count=0;
    int mcount=0;
    int ctype=0;
    String type="x";

    public PicFrag(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.picfrag, container, false);

        chooseimge_iv=(ImageView)view.findViewById(R.id.iv_imagechoose);
            chooseimge_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TYPE FIND",type);
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    count=0;
                    mcount=0;
                }
            });


        colors_ll = (LinearLayout)view.findViewById(R.id.ll_colors);
        edit_ll = (LinearLayout)view.findViewById(R.id.ll_edit);

        blur_b=(Button)view.findViewById(R.id.b_blur);
        blur_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    type = "blur";
                    count = count + 1;
                    Log.e("COUNT", String.valueOf(count));
                    value = 50;
                    Log.e("VALUE", String.valueOf(value));
                    BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
                    bmp = abmp.getBitmap();
                    doBrightness(bmp, value);
                    chooseimge_iv.setImageBitmap(bmOut);
                }
            }
        });

        mirror_b = (Button)view.findViewById(R.id.b_mirror);
        mirror_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    type = "mirror";
                    mcount = mcount + 1;
                    BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
                    bmp = abmp.getBitmap();
                    flip(bmp);
                    Log.e("FLIP", "flip");
                    chooseimge_iv.setImageBitmap(bmOut);
                }
            }
        });

        colors_b = (Button)view.findViewById(R.id.b_colors);
        colors_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    edit_ll.setVisibility(View.GONE);
                    colors_ll.setVisibility(View.VISIBLE);
                    BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
                    bmp = abmp.getBitmap();
                    Log.e("COLORS BMP", String.valueOf(bmp));
                }
            }
        });

        red_b = (Button)view.findViewById(R.id.b_red);
        red_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    ctype = 1;
                    int percent = 35;
                    boost(bmp, ctype, percent);
                    chooseimge_iv.setImageBitmap(bmOut);
                    Log.e("COLORS BMP", String.valueOf(bmOut));
                }
            }
        });

        green_b = (Button)view.findViewById(R.id.b_green);
        green_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    ctype = 2;
                    int percent = 10;
                    boost(bmp, ctype, percent);
                    chooseimge_iv.setImageBitmap(bmOut);
                }
            }
        });

        blue_b = (Button)view.findViewById(R.id.b_blue);
        blue_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    ctype = 3;
                    int percent = 30;
                    boost(bmp, ctype, percent);
                    chooseimge_iv.setImageBitmap(bmOut);
                }
            }
        });

        undo_b = (Button)view.findViewById(R.id.b_undo);
        undo_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {

                    if (colors_ll.getVisibility() == View.VISIBLE) {
                        edit_ll.setVisibility(View.VISIBLE);
                        colors_ll.setVisibility(View.GONE);
                    } else {
                        if (type.equals("mirror")) {
                            mcount = mcount - 1;
                            if (mcount >= 0) {
                                BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
                                bmp = abmp.getBitmap();
                                flip(bmp);
                                chooseimge_iv.setImageBitmap(bmOut);
                            }
                            type = "blur";
                        } else if (type.equals("blur")) {
                            count = count - 1;
                            if (count >= 0) {
                                Log.e("DROP COUNT", String.valueOf(count));
                                value = -50;
                                Log.e("DROP VALUE", String.valueOf(value));
                                BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
                                bmp = abmp.getBitmap();
                                doBrightness(bmp, value);
                                chooseimge_iv.setImageBitmap(bmOut);
                            }
                            type = "mirror";
                        } else if(type.equals("x")){

                        }
                    }
                }
            }
        });

        save_b = (Button)view.findViewById(R.id.b_save);
        save_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    saveImageToGallery();
                }
            }
        });

        original_b=(Button)view.findViewById(R.id.b_original);
        original_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseimge_iv.getDrawable()==null){
                    toast();
                }else {
                    chooseimge_iv.setImageBitmap(obmp);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            chooseimge_iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            original();

        }


    }

    public void original(){
        BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
        obmp = abmp.getBitmap();
    }


    private Bitmap doBrightness(Bitmap bmp, int value) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int A, R, G, B;
        int pixel;

        bmOut = Bitmap.createBitmap(width, height, bmp.getConfig());
        for(int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = bmp.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }

    private Bitmap flip(Bitmap bmp){
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        bmOut = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        return bmOut;

    }

    public Bitmap boost(Bitmap src, int type, int percent) {
        int width = src.getWidth();
        int height = src.getHeight();

        bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int pixel;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                if(type == 1) {
                    R = (int)(R * (1 + percent));
                    if(R > 255) R = 255;
                }
                else if(type == 2) {
                    G = (int)(G * (1 + percent));
                    if(G > 255) G = 255;
                }
                else if(type == 3) {
                    B = (int)(B * (1 + percent));
                    if(B > 255) B = 255;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }

    private void saveImageToGallery(){
        BitmapDrawable abmp = (BitmapDrawable) chooseimge_iv.getDrawable();
        bmp = abmp.getBitmap();
        String savedImageURL = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bmp, "Bird","Image of bird");
        Toast.makeText(getContext(),"Saved Image",Toast.LENGTH_SHORT).show();
    }

    public void toast(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_img,
                (ViewGroup) view.findViewById(R.id.ll_toast));
        TextView toastTextView = (TextView) layout.findViewById(R.id.tv_toast);
        ImageView toastImageView = (ImageView) layout.findViewById(R.id.iv_toast);
        toastTextView.setText("No Image Found");
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
    
}
