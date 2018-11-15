package com.example.yarden.moonlight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import javax.xml.validation.Validator;

public class MainActivity extends AppCompatActivity {
    private Button buttonSubmit;
    private Button buttonPhoto;
    private EditText editText_name;
    private EditText editText_email;
    private EditText editText_phone;
    private EditText editText_password;
    private EditText editText_date;
    private RadioButton radioButton_famle;
    private RadioButton radioButton_male;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String date;
    private String gender = null;
    private Uri imageUri;
    private String message;
    private static final int OPEN_DOCUMENT_CODE = 1;
    private static final String VALID = "true" ;
    private InputVerifications inputVerifications= new InputVerifications();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        buttonSubmit = findViewById(R.id.button_submit);
        buttonPhoto = findViewById(R.id.button_photo);
        editText_name = findViewById(R.id.editText_name);
        editText_email = findViewById(R.id.editText_email);
        editText_phone = findViewById(R.id.editText_phone);
        editText_password = findViewById(R.id.editText_password);
        editText_date = findViewById(R.id.editText_date);
        radioButton_famle = findViewById(R.id.radioButton_famle);
        radioButton_male = findViewById(R.id.radioButton_male);

        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, OPEN_DOCUMENT_CODE);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name= editText_name.getText().toString();
               //todo check
                 editText_name.setText(name);
                 email = editText_email.getText().toString();
                 editText_email.setText(email);
                 phone = editText_phone.getText().toString();
                 editText_phone.setText(phone);
                 password = editText_password.getText().toString();
                 editText_password.setText(password);
                 date = editText_date.getText().toString();
                genderSelect();
                if(checkSetting())
                {
                    startUserInfoActivity();
                }
                else
                {
                    showAlart();
                }
            }
        });
    }

    private void showAlart() {

        AlertDialog.Builder hotspotAlert = new AlertDialog.Builder(this);
        hotspotAlert.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    message = null;
                    }
                })
                .create();
        hotspotAlert.show();
    }

    private void startUserInfoActivity() {
        Intent intent = new Intent(getBaseContext() ,UserInfoActivity.class);
        intent.putExtra("name" , name);
        intent.putExtra("email" , email);
        intent.putExtra("phone" , phone);
        intent.putExtra("password" , password);
        intent.putExtra("date" , date);
        intent.putExtra("gender" , gender);
        intent.putExtra("photo" , imageUri.toString());
        startActivity(intent);
    }

    private boolean checkSetting() {
         message = null;

        String nameVerification = inputVerifications.CheckNameValiduty(name);
        String dateVerification = inputVerifications.CheckBirthDate(date);
        String passwordVerification = inputVerifications.CheckPassword(password);
        String phoneVerification = inputVerifications.CheckPhone(phone);
        String emailVerification = inputVerifications.CheckEmailAddress(email);

        if (nameVerification != VALID)
            message += nameVerification + "\n";
        if (dateVerification != VALID)
            message += dateVerification + "\n";
        if (passwordVerification != VALID)
            message += passwordVerification + "\n";
        if (phoneVerification != VALID)
            message += phoneVerification + "\n";
        if (emailVerification != VALID)
            message += emailVerification + "\n";
        if (message != null)
            return false;
        else
            return true;
    }

    private void genderSelect(){
        if(radioButton_male.isChecked())
            gender = "Male";
        else if(radioButton_famle.isChecked())
            gender="Famle";

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == OPEN_DOCUMENT_CODE && resultCode == RESULT_OK) {
            if (resultData != null) {
                // this is the image selected by the user

                imageUri = resultData.getData();
            }
        }
    }



}
