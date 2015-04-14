package huji.ac.il.todoapplistex3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




/**
 * Created by  on 10/2/13.
 */
public class TodoAppAdapter extends ArrayAdapter<TodoItem> {
    private Context mycontext;
    SimpleDateFormat m_sdfSimplifiedExpDate;
    private ArrayList<TodoItem> m_arrTodoItems;
    private final String EXP_DATE_PATTERN = "dd/MM/yyyy";
    public TodoAppAdapter(Context context,ArrayList<TodoItem> p_arrlstTodoAppData)
    {
        super(context,R.layout.todo_list_item,p_arrlstTodoAppData);
        m_sdfSimplifiedExpDate = new SimpleDateFormat(EXP_DATE_PATTERN);
        m_arrTodoItems = p_arrlstTodoAppData;
        this.mycontext=context;
    }

    @Override
    public void add(TodoItem p_NewTask)
    {
        m_arrTodoItems.add(p_NewTask);
        notifyDataSetChanged();
    }
    @Override
    public void remove(TodoItem p_tditmTodoTaskIndexAsStr)
    {
        m_arrTodoItems.remove(p_tditmTodoTaskIndexAsStr);
        notifyDataSetChanged();
    }
    @Override
    public int getCount()
    {
        return m_arrTodoItems.size();
    }


    @Override
    public TodoItem getItem(int position)
    {
        return m_arrTodoItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        View vwTodoTaskVisual = convertView;

        if (vwTodoTaskVisual ==null)
        {
            LayoutInflater inflater=((Activity)mycontext).getLayoutInflater();
            vwTodoTaskVisual =inflater.inflate(R.layout.todo_list_item,parent,false);
        }
        TodoItem tditmCurrItemToInsert = m_arrTodoItems.get(position);
        TextView txtvwItemContent = (TextView) vwTodoTaskVisual.findViewById(R.id.txtvwItemContent);
        txtvwItemContent.setText((CharSequence) tditmCurrItemToInsert.m_strTodoContent);
        String strParsedExpiration = m_sdfSimplifiedExpDate.format(tditmCurrItemToInsert.m_dtExpirationDate);
        TextView txtvwExpiration = (TextView) vwTodoTaskVisual.findViewById(R.id.txtvwItemDueDate);
        txtvwExpiration.setText((CharSequence)strParsedExpiration);
        if(m_arrTodoItems.get(position).m_dtExpirationDate.before(new Date()))
        {
            txtvwItemContent.setTextColor(android.graphics.Color.RED);
            txtvwItemContent.setBackgroundColor(android.graphics.Color.BLUE);
            txtvwExpiration.setTextColor(android.graphics.Color.RED);
            txtvwExpiration.setBackgroundColor(android.graphics.Color.BLUE);
        }
        return  vwTodoTaskVisual;
    }

}
