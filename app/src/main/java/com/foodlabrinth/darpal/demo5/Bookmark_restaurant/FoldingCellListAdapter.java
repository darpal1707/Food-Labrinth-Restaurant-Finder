package com.foodlabrinth.darpal.demo5.Bookmark_restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.Upload_Image.ShowImagesActivity;
import com.ramotion.foldingcell.FoldingCell;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Item> {


    Context context;
    boolean status = true;
    //String bookmarkUrl;
    String rid;

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    boolean val = false;
    //ArrayList<HashMap<String, Boolean>> arrayList;
    //private static final int REQUEST_FOR_STORAGE_PERMISSION = 123;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    public String upLoadServerUri;
    BookmarkRestaurants mainActivity;

    static ViewHolder viewHolder;
    static Context context1;
    String rate11;
    static String rate;
    ShineButton shineButton;

    String stars;
    String reviews;
    String resid, uid;
    public static int id1;

    public FoldingCellListAdapter(Context context, List<Item> objects, String rate) {
        super(context, 0, objects);
        this.context = context;
        this.mainActivity = (BookmarkRestaurants) context;
        context1 = context;
        this.rate11 = rate;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // get item for selected view
        SharedPreferences pref = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        rid = pref.getString("Res_id", null);

        final Item item = getItem(position);
        // if cell exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        SharedPreferences preferences = context1.getSharedPreferences("rav", Context.MODE_PRIVATE);
        String data = preferences.getString("key1", rate);
        //final ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(com.foodlabrinth.darpal.demo5.R.layout.cell, parent, false);

            viewHolder.cuisine = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.cuisineDisp);
            viewHolder.ambience = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.ambience_title);
            //viewHolder.date = (TextView) cell.findViewById(R.id.title_date_label);
            viewHolder.resname = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.resDispName);
            viewHolder.toAddress = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.title_to_address);
            //viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
            viewHolder.avgcost = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.title_pledge);
            viewHolder.avgrate = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.avgrate);
            viewHolder.avgdisp = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.title_price);


            viewHolder.RESNAME = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.restaurant_name);
            viewHolder.RESADD = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.restaurant_address);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.content_request_btn);
            viewHolder.contact = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.contact_display_one);
            viewHolder.timeDisp = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.content_delivery_time);
            viewHolder.email = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.email_display);
            viewHolder.RESCOST = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.cost_value);
            viewHolder.RESCUISINE = (TextView) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.cuisine_name);

            viewHolder.photosdisp = (Button) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.photosActivity);
            //viewHolder.rate = (TextView) cell.findViewById(R.id.title_price);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        SharedPreferences pref1 = context.getSharedPreferences("userpref", Context.MODE_PRIVATE);
        uid = pref1.getString("uid", null);


        // bind data from selected element to view through view holder
        String s1, s2;
        s1 = item.getCuisine();
        s1 = s1.replace(",", "\n");

        s2 = item.getAmbience();
        s2 = s2.replace(",", "\n");

        viewHolder.cuisine.setText(s1);
        viewHolder.ambience.setText(s2);
        viewHolder.RESCUISINE.setText(s1);
        viewHolder.RESCOST.setText(item.getPledgePrice());
        viewHolder.RESNAME.setText(item.getFromAddress());
        viewHolder.RESADD.setText(item.getToAddress());
        //viewHolder.date.setText(item.getDate());
        viewHolder.resname.setText(item.getFromAddress());
        viewHolder.toAddress.setText(item.getToAddress());
        viewHolder.avgcost.setText(item.getPledgePrice());
        viewHolder.timeDisp.setText(item.getDate());
        viewHolder.contact.setText(item.getContact());
        viewHolder.email.setText(item.getEmail());
        viewHolder.avgrate.setText(item.getAvgrate());
        viewHolder.avgdisp.setText(item.getAvgrate());
        //viewHolder.avgrate.setText(rate + "");
        //viewHolder.avgdisp.setText(rate);

        Log.e("ResId---> ", item.getResid());
        shineButton = (ShineButton) cell.findViewById(com.foodlabrinth.darpal.demo5.R.id.po_image0);

        viewHolder.photosdisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImagesActivity.class);
                intent.putExtra("Res_id", item.getResid());
                Log.e("Image Resid:", item.getResid());
                context.startActivity(intent);

            }
        });


        shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (uid.equals("")) {
                        Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            upLoadServerUri = "http://foodlabrinth.com/bookmark_remove.php?Bookmark_Status=" + status + "&Res_id=" + item.getResid() + "&Uid=" + uid;
                            new Thread(new Runnable() {
                                public void run() {
                                    uploadFile(status, rid, uid);
                                    //shineButton.setChecked(true);
                                }
                            }).start();
                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                    shineButton.setChecked(false);
                }
                //Toast.makeText(context, "" + item.getResid(), Toast.LENGTH_SHORT).show();
            }


        });


        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FoldingCell) v).toggle(false);
                // register in adapter that state for selected cell is toggled
                registerToggle(position);
                int id = position;
                final Item item = getItem(id);
                item.getResid();
                id1 = position;
                Log.e("Cell----id", String.valueOf(id1));

            }
            });


        // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }

        return cell;
    }


    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    public static class ViewHolder {
        TextView cuisine;
        public static TextView contentRequestBtn;
        TextView avgcost;
        TextView resname;
        TextView avgrate;
        TextView toAddress;
        //TextView date;
        TextView ambience;
        TextView contact;
        TextView timeDisp;
        TextView email;


        Button photosdisp;
        TextView RESNAME;
        TextView RESADD;
        TextView RESCOST;
        TextView RESCUISINE;
        TextView avgdisp;
    }

    /*public static void RatingSend(String rate1) {
        rate = rate1;
        Log.e("GetRate", rate);
        viewHolder.avgrate.setText(rate + "");
        SharedPreferences preferences = context1.getSharedPreferences("rav", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("key1", rate);
        ed.apply();

    }*/
      /*  int green = Color.parseColor("#00e500");
        int yellow = Color.parseColor("#ffff00");
        int red = Color.parseColor("#ff0000");
        if (rate.equals("5")) {
            viewHolder.avgrate.setBackgroundColor(green);
            viewHolder.avgrate.setText(rate + "");

            //viewHolder.avgdisp.setBackgroundColor(green);
            //viewHolder.avgdisp.setText(rate + "");
        }
        if (rate.equals("3")) {
            viewHolder.avgrate.setBackgroundColor(yellow);
            viewHolder.avgrate.setText(rate + "");

            //viewHolder.avgdisp.setBackgroundColor(yellow);
            //viewHolder.avgdisp.setText(rate + "");
        }
        if (rate.equals("1.5")) {
            viewHolder.avgrate.setBackgroundColor(red);
            viewHolder.avgrate.setText(rate + "");
        }*/

    //}
    /*public static String Getvalue(String data) {
        return data;
    }*/

    public boolean uploadFile(boolean status, String rid, String uid) {

        String fileName = upLoadServerUri;   // new line added

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
//        File sourceFile = new File(sourceFileUri);


        {
            try {

                // open a URL connection to the Servlet
                // upLoadServerUri = upLoadServerUri + "&res_id="+rid;

                URL url = new URL(upLoadServerUri + rid);
                Log.e("url", upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                //conn.setRequestProperty("uploaded_file", fileName);
                conn.setRequestProperty("Bookmark_Status", String.valueOf(status));
                conn.setRequestProperty("Res_id", rid);
                conn.setRequestProperty("Uid", uid);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"&Res_id="+rid +"\""+ lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size


                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                //dos.writeBytes(rid);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    mainActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            //String msg = "File Upload Completed.";
                            // messageText.setText(msg);
                            shineButton.setChecked(true);
                            Toast.makeText(context, "Bookmark Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    try {

                        StringBuffer sb = new StringBuffer();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            sb.append(line);
                        }
                        rd.close();
                        if (!sb.toString().contains("fail")) {
                            return true;
                        } else {
                            return false;
                        }

                    } catch (Exception e) {
                        Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                    } finally {
                        //close the streams //
                        dialog.dismiss();
                        dos.flush();
                        dos.close();
                    }
                }
            } catch (Exception e) {

            }
            return false;
        } // End else block
    }
}
