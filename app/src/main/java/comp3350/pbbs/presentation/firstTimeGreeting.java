package comp3350.pbbs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import comp3350.pbbs.R;

public class firstTimeGreeting extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_greeting);

        Button b = findViewById(R.id.button);
        EditText nameBox = findViewById(R.id.textBox);

        b.setOnClickListener(view ->
        {
            if (nameBox.getText().toString().isEmpty())
            {
                nameBox.setError("This field cannot be empty.");
            }
            else
            {
                startActivity(new Intent(firstTimeGreeting.this, MainActivity.class));
                finish();
            }
        });
    }
}