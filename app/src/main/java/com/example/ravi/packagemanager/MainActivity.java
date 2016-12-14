package com.example.ravi.packagemanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView apps;
    PackageManager packageManager;
    ArrayList<String> checkedValue;
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.button1);
        apps = (ListView) findViewById(R.id.listView1);
        packageManager = getPackageManager();
        final List <PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_META_DATA); // all apps in the phone
        final List<PackageInfo> packageList1 = packageManager
                .getInstalledPackages(0);


        try {
            packageList1.clear();
            for (int n = 0; n < packageList.size(); n++)
            {

                PackageInfo PackInfo = packageList.get(n);
                if (((PackInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true)
                //check weather it is system app or user installed app
                {
                    try
                    {

                        packageList1.add(packageList.get(n)); // add in 2nd list if it is user installed app
                        Collections.sort(packageList1,new Comparator<PackageInfo>()
                                // this will sort App list on the basis of app name
                        {
                            public int compare(PackageInfo o1,PackageInfo o2)
                            {
                                return o1.applicationInfo.loadLabel(getPackageManager()).toString()
                                        .compareToIgnoreCase(o2.applicationInfo.loadLabel(getPackageManager())
                                                .toString());// compare and return sorted packagelist.
                            }
                        });


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Listadapter Adapter = new Listadapter(this,packageList1, packageManager);
        apps.setAdapter(Adapter);
        apps.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this,"" + checkedValue,Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox1);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        cb.performClick();
        if (cb.isChecked()) {

            checkedValue.add(tv.getText().toString());
        } else if (!cb.isChecked()) {
            checkedValue.remove(tv.getText().toString());
        }
    }
}
















