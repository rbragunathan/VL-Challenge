package com.example.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNumber extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText txtName, txtNumber;
    TextView txtResult;
    Button btnClick,btnClick2,btnClick3,btnClick4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        myDb =new DataBaseHelper(this);
        txtName = (EditText) findViewById(R.id.editText);
        txtNumber =(EditText) findViewById(R.id.editText3);
        txtResult = (TextView) findViewById(R.id.textView);
        btnClick2 = (Button) findViewById(R.id.list);
        btnClick3 = (Button) findViewById(R.id.update);
        btnClick4 = (Button) findViewById(R.id.delete);
        btnClick = (Button) findViewById(R.id.addNumber);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickMe();
            }
        });

        btnClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickMe2();
            }
        });

        btnClick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickMe3();
            }
        });

        btnClick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickMe4();
            }
        });

    }

    private void  ClickMe()
    {
        String name= txtName.getText().toString();
        String number = txtNumber.getText().toString();
        if(name==null || name.equals("") || number == null || number.equals(""))
        {
            Toast.makeText(this, "Field cannot be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
        Boolean result = myDb.insertData(name,number);
        if(result == true)
        {
            Toast.makeText(this, "Data Inserted Successfully",Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(this, "Data Inserted Failed",Toast.LENGTH_LONG).show();
        }}
        txtName.getText().clear();
        txtNumber.getText().clear();
    }
    private void  ClickMe2()
    {
        Cursor res2 = myDb.getAllData();
        StringBuffer stringBuffer =new StringBuffer();

        if(res2 != null && res2.getCount()>0)
        {
            while (res2.moveToNext())
            {
                stringBuffer.append("Name:"+res2.getString(0)+"\n");
                stringBuffer.append("Number:"+res2.getString(1)+"\n");
            }
            txtResult.setText(stringBuffer.toString());
            Toast.makeText(this,"Data Retrieved Successfully",Toast.LENGTH_LONG).show();
        }
        else
            {
                Toast.makeText(this,"No Data to Retrieve ",Toast.LENGTH_LONG).show();
                txtResult.setText("_");
        }
        txtName.getText().clear();
        txtNumber.getText().clear();
    }

    private void  ClickMe3()
    {

        String name= txtName.getText().toString();
        String number = txtNumber.getText().toString();
        if(name==null || name.equals("") || number == null || number.equals(""))
        {
            Toast.makeText(this, "Field cannot be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            Boolean result = myDb.updateData(name,number);
        if(result == true)
        {
            Toast.makeText(this, "Data Updated Successfully",Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(this, "Data Updated Failed",Toast.LENGTH_LONG).show();
        }}
        txtName.getText().clear();
        txtNumber.getText().clear();

    }

    private void  ClickMe4()
    {
        String name= txtName.getText().toString();
        int result =myDb.deleteData(name);
        Toast.makeText(this,result+ ":Row Deleted ",Toast.LENGTH_LONG).show();
        txtName.getText().clear();
        txtNumber.getText().clear();

    }


}
