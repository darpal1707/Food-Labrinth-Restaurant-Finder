package com.foodlabrinth.darpal.demo5.Login_Signup;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.MainActivity;
import com.foodlabrinth.darpal.demo5.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantLoginRegistrationFragment extends Fragment implements View.OnClickListener {

    private Button btnLinkToLogin;
    private Button register;
    EditText resname;
    EditText fullname;
    EditText area, address, contact, email, cost, password;
    Button meal, cuisine, ambience, time;
    TextView mealview, cuisineview, ambienceview, timeview;
    int thour, tminute;
    String s10, s7, s9;
    static LinearLayout mygallery;

    //upload image
    Button selectphotos, uploadphotos;
    public static String img1[];
    public static LinearLayout layout;
    public static ImageView imageView;
    MainActivity mainActivity;
    static Context context;
    public String upLoadServerUri ="http://foodlabrinth.com/upload_img.php";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_login_registration, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        context = getActivity();
        btnLinkToLogin = (Button) view.findViewById(R.id.btnLinkToLoginScreen);
        resname = (EditText) view.findViewById(R.id.restaurantname);
        fullname = (EditText) view.findViewById(R.id.fullname);
        area = (EditText) view.findViewById(R.id.area);
        address = (EditText) view.findViewById(R.id.address);
        contact = (EditText) view.findViewById(R.id.contact);
        email = (EditText) view.findViewById(R.id.email);
        cost = (EditText) view.findViewById(R.id.cost);
        password = (EditText) view.findViewById(R.id.password);
        //mygallery = (LinearLayout) view.findViewById(R.id.mygallery);

        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantLoginFragment restaurantLoginFragment = new RestaurantLoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.loginlayout, restaurantLoginFragment).commit();
            }
        });


        register = (Button) view.findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resname.getText().toString().equals("") && fullname.getText().toString().equals("") &&
                        area.getText().toString().equals("") && address.getText().toString().equals("") &&
                        contact.getText().toString().equals("") && email.getText().toString().equals("") &&
                        cost.getText().toString().equals("") && password.getText().toString().equals("")) {
                    resname.setError("This Field cannot be blank");
                    fullname.setError("This Field cannot be blank");
                    area.setError("This Field cannot be blank");
                    address.setError("This Field cannot be blank");
                    contact.setError("This Field cannot be blank");
                    email.setError("This Field cannot be blank");
                    cost.setError("This Field cannot be blank");
                    password.setError("This Field cannot be blank");
                    Toast.makeText(getActivity(), "Please Complete above required fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String s1 = resname.getText().toString();
                        String s2 = fullname.getText().toString();
                        String s3 = area.getText().toString();
                        String s4 = address.getText().toString();
                        String s5 = contact.getText().toString();
                        String s6 = email.getText().toString();
                        s7 = cuisineview.getText().toString();
                        String s8 = cost.getText().toString();
                        s9 = ambienceview.getText().toString();
                        s10 = mealview.getText().toString();
                        String s11 = timeview.getText().toString();
                        String s12 = password.getText().toString();

                        s10 = s10.replace("\n", ",");
                        s7 = s7.replace("\n", ",");
                        s9 = s9.replace("\n", ",");

                        String url = "http://foodlabrinth.com/restaurant_reg.php?RestaurantName=" + s1 +
                                "&FullName=" + s2 + "&Area=" + s3 + "&Address=" + s4 + "&ContactNo=" + s5 +
                                "&EmailId=" + s6 + "&Photos=" + s6 + "&Cost=" + s8 + "&Cuisine=" + s7 +
                                "&Ambience=" + s9 + "&Meal=" + s10 + "&OpenTill=" + s11 + "&Password=" + s12 + "";
                        url = url.replaceAll(" ", "%20");
                        Log.e("urll==", url);
                        URL sourceUrl = new URL(url);
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).build();
                        Response response = client.newCall(request).execute();
                        Toast.makeText(getActivity(), "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                        Snackbar.make(getActivity().findViewById(R.id.container), "Please Log in to upload photos.", Snackbar.LENGTH_LONG)
                                .show();


                    } catch (Exception e) {

                        isOnline();
                        Log.i("Error", e.toString());
                    }
                }

            }
        });


        mealview = (TextView) view.findViewById(R.id.mealview);
        meal = (Button) view.findViewById(R.id.meal);
        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                final String[] items = {"Breakfast", "Lunch", "Dinner", "Cafe", "Late Night"};
                final boolean isSelected[] = {false, false, false, false, false};
                final ArrayList<Integer> itemsSelected = new ArrayList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the Meal you will provide:");

                builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            itemsSelected.add(which);
                        } else if (itemsSelected.contains(which)) {
                            itemsSelected.remove(Integer.valueOf(which));
                        }
                    }
                }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder sb = new StringBuilder();
                        if (itemsSelected.size() != 0) {
                            for (int i = 0; i < itemsSelected.size(); i++) {
                                String mealtype = items[itemsSelected.get(i)];
                                sb = sb.append(mealtype + "\n");
                            }
                            mealview.setText(sb.toString());
                            //Toast.makeText(getActivity(), "" + sb.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        cuisineview = (TextView) view.findViewById(R.id.cuisineview);
        cuisine = (Button) view.findViewById(R.id.cuisine);
        cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                final String[] items = {"Chinese", "Italian", "Asian", "Street Food", "Lebanese", "Mediterranean", "Indian", "Punjabi", "South Indian", "Gujarati", "Mexican", "Thai", "European", "Cafe", "Desert"};
                boolean isSelected[] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
                final ArrayList<Integer> itemsSelected = new ArrayList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Cuisines you provide: ");

                builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            itemsSelected.add(which);
                        } else if (itemsSelected.contains(which)) {
                            itemsSelected.remove(Integer.valueOf(which));
                        }
                    }
                }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder sb = new StringBuilder();
                        if (itemsSelected.size() != 0) {
                            for (int i = 0; i < itemsSelected.size(); i++) {
                                String cuisinetype = items[itemsSelected.get(i)];
                                sb = sb.append(cuisinetype + "\n");
                            }

                            cuisineview.setText(sb.toString());
                            //Toast.makeText(getActivity(), "" + sb.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        ambienceview = (TextView) view.findViewById(R.id.ambienceview);
        ambience = (Button) view.findViewById(R.id.ambience);
        ambience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                final String[] items = {"Luxury", "Romantic", "Roof Top", "Hangout", "Unique Dining", "Street/Open Area", "Buffet/Thali", "Late Night Meals", "Live Sports"};
                boolean isSelected[] = {false, false, false, false, false, false, false,false,false};
                final ArrayList<Integer> itemsSelected = new ArrayList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose your Ambience:  ");

                builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            itemsSelected.add(which);
                        } else if (itemsSelected.contains(which)) {
                            itemsSelected.remove(Integer.valueOf(which));
                        }
                    }
                }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder sb = new StringBuilder();
                        if (itemsSelected.size() != 0) {
                            for (int i = 0; i < itemsSelected.size(); i++) {
                                String ambiencetype = items[itemsSelected.get(i)];
                                sb = sb.append(ambiencetype + "\n");
                            }
                            ambienceview.setText(sb.toString());

                            //Toast.makeText(getActivity(), "" + sb.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        final java.util.Calendar c = java.util.Calendar.getInstance();
        thour = c.get(Calendar.HOUR_OF_DAY);
        tminute = c.get(Calendar.MINUTE);

        timeview = (TextView) view.findViewById(R.id.timeview);
        time = (Button) view.findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), setTime, thour, tminute, true).show();
            }

            TimePickerDialog.OnTimeSetListener setTime = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    thour = hourOfDay;
                    tminute = minute;
                    String time = thour + ":" + tminute;
                    timeview.setText(time);
                }
            };
        });


        //selectphotos = (Button) view.findViewById(R.id.selectphotos);
        //selectphotos.setOnClickListener(this);

        /*uploadphotos = (Button) view.findViewById(R.id.uploadButton);
        uploadphotos.setOnClickListener(this);*/

        return view;
    }


    public boolean isOnline() {
        ConnectivityManager connec =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Snackbar.make(getActivity().findViewById(R.id.container), "Not Connected To Internet", Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }

        return connec.getActiveNetworkInfo() != null &&
                connec.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.selectphotos:
                Intent intent = new Intent(getActivity(), GalleryIntent.class);
                startActivity(intent);
                break;*/

            /*case R.id.uploadButton:
                break;*/

        }
    }

    public static void GetImage(String img[]) {
        try {
            img1 = img;
            String data = null;

            for (int i = 0; i < img1.length; i++) {
                Log.e("data", img1[i]);
                data = img1[i];
                mygallery.addView(insertPhoto(data));
            }
        } catch (Exception e) {
            Log.e("Error", "ViewError");
        }

    }

    static View insertPhoto(String path) {
        Bitmap bm = decodeSampledBitmapFromUri(path, 400, 400);
        layout = new LinearLayout(context);

        layout.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
        layout.setGravity(Gravity.CENTER);
        imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(450, 450));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bm);
        if (imageView.getParent() != null)
            ((ViewGroup) imageView.getParent()).removeView(imageView);
        layout.addView(imageView);
        return layout;
    }

    public static Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
        Bitmap bm = null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);
        return bm;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}
