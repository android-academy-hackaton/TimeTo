package hackaton.academy.timeto;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import hackaton.academy.timeto.rest.Contact;
import hackaton.academy.timeto.rest.RestManager;
import hackaton.academy.timeto.rest.TimetoApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindContactsActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String TAG ="mo" ;
    ListView listview;
    Button inviteButton;
    List<String> data_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contacts_dialogue);
        inviteButton = findViewById(R.id.inviteButtonClick);

        inviteButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                TimetoApiClient timeslotApiClient = RestManager.getTimeToApiClientInstance();
                                                final Contact c1 = new Contact();
                                                c1.name = "moria";
                                                c1.id = "afdafdafad";
                                                c1.phone = "+972542325333";
                                                ArrayList a = new ArrayList<Contact>();
                                                a.add(c1);

//                                                JSONObject json = new JSONObject();
//                                                Contact[] contacts = {c1};

                                                    //json.put("contacts",contacts);
                                                    timeslotApiClient.iniviteContacts(a).enqueue(new Callback<Boolean>() {
                                                        @Override
                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Boolean> call, Throwable t) {

                                                        }
                                                    });



                                            }
                                        });

        showContacts();



//        Button loginBtn = findViewById(R.id.btn_login);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
////                myIntent.putExtra("key", value); //Optional parameters
//                startActivity(myIntent);
//            }
//        });

    }


    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            data_array = contacts;
            listview = (ListView) findViewById(R.id.listview);
            CustomAdapter cus = new CustomAdapter();
            listview.setAdapter(cus);
           // listview.setAdapter( new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts));
        }
    }

    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts



        String[] projection1 =
                new String[]{ ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            projection1,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                        contacts.add(name);
                    }
//                    contacts.add(name );

                }}
            while (cursor.moveToNext()) ;

        }
        // Close the curosor
        cursor.close();

        return contacts;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


class CustomAdapter extends BaseAdapter {
    LayoutInflater mInflater;


    public CustomAdapter() {
        mInflater = (LayoutInflater) FindContactsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data_array.size();//listview item count.
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder vh;
        vh = new ViewHolder();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row, parent, false);
            //inflate custom layour
            vh.tv2 = (TextView) convertView.findViewById(R.id.textView2);

        } else {
            convertView.setTag(vh);
        }
        //vh.tv2.setText("Position = "+position);
        vh.tv2.setText(data_array.get(position));
        //set text of second textview based on position

        return convertView;
    }

    class ViewHolder {
        TextView tv1, tv2;
    }
}
}
