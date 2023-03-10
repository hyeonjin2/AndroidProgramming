package com.example.neck_is_turtle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class GraphFragment extends Fragment {

    private static final String TAG = "GraphFragment";

    private LinearLayout btn_photo;
    private ImageView img_photo;
    private byte[] imageBytes;
    String month,day;
    TextView tv_month,tv_day;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GraphFragment() {
        // Required empty public constructor
    }

    public static GraphFragment newInstance(String param1, String param2) {
        GraphFragment fragment = new GraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        img_photo = view.findViewById(R.id.img_photo);
        if(getArguments()!=null){
            imageBytes = getArguments().getByteArray("image");
            Bitmap mBitmap = byteArrayToBitmap(imageBytes);

            Matrix rotateMatrix = new Matrix();
            rotateMatrix.postRotate(90); //-360~360

            if(mBitmap!=null) {
                Bitmap sideInversionImg = Bitmap.createBitmap(mBitmap, 0, 0,
                        mBitmap.getWidth(), mBitmap.getHeight(), rotateMatrix, false);
                img_photo.setImageBitmap(sideInversionImg);
            }
            Log.e(TAG,"bytes not null");
        }
        btn_photo = view.findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetCamera bottomSheetCamera = new BottomSheetCamera();
                bottomSheetCamera.show(requireActivity().getSupportFragmentManager(), "BottomSheet");
            }
        });
        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        month = monthFormat.format(date);
        day = dayFormat.format(date);

        tv_month = view.findViewById(R.id.tv_month);
        tv_day = view.findViewById(R.id.tv_day);

        tv_month.setText(month+"월");
        tv_day.setText(day+"일");

        Log.e(TAG,"load");
        return view;
    }
    // Byte를 Bitmap으로 변환
    public Bitmap byteArrayToBitmap(byte[] byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length ) ;
        return bitmap ;
    }
}