package ro.utbv.sqlitedb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final ArrayList<HashMap<String, String>> studentList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    public StudentAdapter(ArrayList<HashMap<String, String>> studentList, OnItemClickListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> student = studentList.get(position);
        holder.studentName.setText(student.get("name"));
        holder.studentEmail.setText(student.get("email"));
        holder.studentId.setText(student.get("id"));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(student.get("id")));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;
        public TextView studentEmail;
        public TextView studentId;

        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            studentEmail = itemView.findViewById(R.id.student_email);
            studentId = itemView.findViewById(R.id.student_Id);
        }
    }
}
