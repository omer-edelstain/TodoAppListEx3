package huji.ac.il.todoapplistex3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;


public class AddNewTodoItemActivity extends Activity {
    DatePicker dtpkExpirationSelector;
    TextView txtvwTodoContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);
        txtvwTodoContent = (TextView)findViewById(R.id.edtItemContent);
        dtpkExpirationSelector = (DatePicker)findViewById(R.id.dtpSetExpiration);
        ((Button)findViewById(R.id.btnCancelAdd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCancellation();
            }
        });
        ((Button)findViewById(R.id.btnConfirmAdd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleApproval();
            }
        });
    }

    private void HandleApproval() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(TodoAppList.EXTRA_KEY_TODO_CONTENT,txtvwTodoContent.getText().toString());
        returnIntent.putExtra(TodoAppList.EXTRA_KEY_EXPIRATION_DATE,
                              Long.toString(dtpkExpirationSelector.getCalendarView().getDate()));
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void HandleCancellation() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_todo_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
