package huji.ac.il.todoapplistex3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.view.ContextMenu;
import 	android.widget.AdapterView;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TodoAppList extends ActionBarActivity {
    public static final String EXTRA_KEY_TODO_CONTENT ="title";
    public static final String EXTRA_KEY_EXPIRATION_DATE ="dueDate";
    private final int MENU_CONTEXT_CONTINUE_ID = 2;
    private final int MENU_CONTEXT_CALL_ID = 3;
    private final int ITEM_ADDITION_REQUEST_CODE = 0x0b1;
    private final String REGEX_CALL_TASK = "(?i:^call\\s+([0-9][-0-9]*)$)";
    private ListView lstvwTodoListElement;
    private TodoAppAdapter adptDataToControlConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_app_list);
        lstvwTodoListElement = (ListView) findViewById(R.id.lstTodoItems);

        adptDataToControlConnector = new TodoAppAdapter(this,new ArrayList<TodoItem>());

        lstvwTodoListElement.setAdapter(adptDataToControlConnector);
        registerForContextMenu(lstvwTodoListElement);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String title =  adptDataToControlConnector.getItem(info.position).m_strTodoContent;
        menu.setHeaderTitle(title);
        Pattern ptrnCallRecognizer = Pattern.compile(REGEX_CALL_TASK);
        Matcher mtchTitleToRegexComp = ptrnCallRecognizer.matcher(title);
        if(mtchTitleToRegexComp.matches())
        {
            menu.add(Menu.NONE, MENU_CONTEXT_CALL_ID, Menu.NONE, title);
        }
        menu.add(Menu.NONE, MENU_CONTEXT_CONTINUE_ID, Menu.NONE, "ok");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_CONTEXT_CALL_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                return DialItemInList(info.position);
            case MENU_CONTEXT_CONTINUE_ID:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_app_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean blnActionRes = false;
        switch(item.getItemId())
        {
            case R.id.menuitemAdd:
                blnActionRes =  AddItemToList();
                break;
            case R.id.action_settings:
                blnActionRes = true;
        }
        return blnActionRes ;
    }

    private boolean AddItemToList() {
        //TODO insert changes here - call dialog activity ,await response and react to it
        Intent intntItemAdditionAction = new Intent();
        intntItemAdditionAction.setClass(this,AddNewTodoItemActivity.class);
        startActivityForResult(intntItemAdditionAction,ITEM_ADDITION_REQUEST_CODE);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ITEM_ADDITION_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                adptDataToControlConnector.add(new TodoItem(data.getStringExtra(EXTRA_KEY_TODO_CONTENT),
                                               new Date(Long.valueOf(data.getStringExtra(EXTRA_KEY_EXPIRATION_DATE)))));
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    private boolean DialItemInList(int p_TaskId) {
        int nIndexOfItem = 0;
        if(p_TaskId >= 0) {
            nIndexOfItem = p_TaskId;
        }
        else
        {
            nIndexOfItem = 0;
        }
        TodoItem tditmTodoToHandle = adptDataToControlConnector.getItem(nIndexOfItem);
        Pattern ptrnCallRecognizer = Pattern.compile(REGEX_CALL_TASK);
        Matcher mtchTitleToRegexComp = ptrnCallRecognizer.matcher(tditmTodoToHandle.m_strTodoContent);
        mtchTitleToRegexComp.matches();
        String strPhoneNumber = mtchTitleToRegexComp.group(1);
        Intent intntDialNumber = new Intent(Intent.ACTION_DIAL,
                                            Uri.parse(String.format("tel:%s",strPhoneNumber)));
        startActivity(intntDialNumber);

        return true;
    }
}
