package com.example.lab_12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button Delete,Edit,View,Add;
    EditText Id,Name,Age;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DB(this);

        Delete=findViewById(R.id.delete);
        Edit=findViewById(R.id.edit);
        View=findViewById(R.id.view);
        Add=findViewById(R.id.add);
        Id=findViewById(R.id.id);
        Name=findViewById(R.id.name);
        Age=findViewById(R.id.age);

        AddData();
        View();
        setEditing();
        Deleting();
    }

    private void Deleting() {
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer Del_row=db.DeleteData(Id.getText().toString());
                if(Del_row > 0){
                    Toast.makeText(MainActivity.this,"Yes deleting",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"No deleting",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AddData(){
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insert(Name.getText().toString(),Age.getText().toString());
                if(isInserted == true){
                    Toast.makeText(MainActivity.this,"Yes adding",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"No adding",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void setEditing(){
        boolean isUpdate=db.updateData(
                Id.getText().toString(),
                Name.getText().toString(),
                Age.getText().toString()
        );
        if(isUpdate==true){
            Toast.makeText(MainActivity.this,"Yes updating",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"No updating",Toast.LENGTH_SHORT).show();
        }
    }

    private void View(){
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=db.getData();
                if(res.getCount()==0){
                    all("Error","Nothing found!");
                    return;
                }
                StringBuffer sb=new StringBuffer();
                while(res.moveToNext())
                {
                    sb.append("ID:"+res.getString(0)+" ,");
                    sb.append("Name:"+res.getString(1)+" ,");
                    sb.append("Age:"+res.getString(2)+"\n");
                }
                all("Data",sb.toString());
            }
        });
    }

    void all(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}