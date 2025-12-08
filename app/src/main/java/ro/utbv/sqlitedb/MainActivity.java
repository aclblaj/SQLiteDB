package ro.utbv.sqlitedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnGetAll, btnExit;
    RecyclerView recyclerView;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnGetAll = findViewById(R.id.btnGetAll);
        btnExit = findViewById(R.id.btnExit);
        recyclerView = findViewById(R.id.recyclerView);

        btnAdd.setOnClickListener(this);
        btnGetAll.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        loadStudentData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudentData();
    }

    private void loadStudentData() {
        StudentRepo repo = new StudentRepo(this);
        ArrayList<HashMap<String, String>> studentList = repo.getStudentList();

        if (!studentList.isEmpty()) {
            adapter = new StudentAdapter(studentList, new StudentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id) {
                    Intent intent = new Intent(MainActivity.this, StudentDetail.class);
                    intent.putExtra("student_Id", Integer.parseInt(id));
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setAdapter(null);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            Intent intent = new Intent(this, StudentDetail.class);
            intent.putExtra("student_Id", 0);
            startActivity(intent);
        } else if (view.getId() == R.id.btnExit) {
            finishAffinity();
        } else if (view.getId() == R.id.btnGetAll) {
            loadStudentData();
            if (adapter == null || adapter.getItemCount() == 0) {
                 Toast.makeText(this, "No student!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
