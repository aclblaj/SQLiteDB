package ro.utbv.sqlitedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentDetail extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    Button btnGetAddress;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    EditText editTextZip;
    TextView textViewAddress;
    private int _Student_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnGetAddress = (Button) findViewById(R.id.btnGetAddress);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextZip = (EditText) findViewById(R.id.editTextZip);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnGetAddress.setOnClickListener(this);


        _Student_Id =0;
        Intent intent = getIntent();
        _Student_Id =intent.getIntExtra("student_Id", 0);
        
        if(_Student_Id != 0) {
            StudentRepo repo = new StudentRepo(this);
            Student student = repo.getStudentById(_Student_Id);

            editTextAge.setText(String.valueOf(student.age));
            editTextName.setText(student.name);
            editTextEmail.setText(student.email);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            StudentRepo repo = new StudentRepo(this);
            Student student = new Student();
            
            String ageText = editTextAge.getText().toString();
            String nameText = editTextName.getText().toString();
            String emailText = editTextEmail.getText().toString();
            
            if (nameText.isEmpty() || emailText.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            
            try {
                student.age= Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                 Toast.makeText(this, "Invalid age format", Toast.LENGTH_SHORT).show();
                 return;
            }

            student.email=emailText;
            student.name=nameText;
            student.student_ID=_Student_Id;

            if (_Student_Id==0){
                _Student_Id = repo.insert(student);

                Toast.makeText(this,"New Student Insert",Toast.LENGTH_SHORT).show();
                finish();
            }else{

                repo.update(student);
                Toast.makeText(this,"Student Record updated",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            StudentRepo repo = new StudentRepo(this);
            repo.delete(_Student_Id);
            Toast.makeText(this, "Student Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        } else if (view == findViewById(R.id.btnGetAddress)) {
            String zipCode = editTextZip.getText().toString();
            if (zipCode.isEmpty()) {
                Toast.makeText(this, "Please enter a zip code", Toast.LENGTH_SHORT).show();
                return;
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.102:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StudentApiService service = retrofit.create(StudentApiService.class);
            // http://10.216.100.142:8000/adresa?zc=123456
            Call<Address> call = service.getStudentAddress(zipCode);

            call.enqueue(new Callback<Address>() {
                @Override
                public void onResponse(Call<Address> call, Response<Address> response) {
                    if (response.isSuccessful()) {
                        Address address = response.body();
                        if (address != null) {
                            textViewAddress.setText("Address: " + address.getStreet() + ", " + address.getCity());
                        } else {
                            textViewAddress.setText("Address not found");
                        }
                    } else {
                        textViewAddress.setText("Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Address> call, Throwable t) {
                    textViewAddress.setText("Failure: " + t.getMessage());
                }
            });
        }


    }

}
