package zoomkart.paykart.models;

/**
 * Created by sudhanvahuruli on 3/13/17.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import zoomkart.paykart.R;

import static android.graphics.Typeface.BOLD;
import static zoomkart.paykart.models.Constants.FIRST_COLUMN;
import static zoomkart.paykart.models.Constants.SECOND_COLUMN;

public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.column_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.name);
            txtSecond=(TextView) convertView.findViewById(R.id.price);

            HashMap<String, String> map=list.get(position);

            if (map.get("First").equalsIgnoreCase("Total:")
                    || map.get("First").equalsIgnoreCase("Sub Total:")
                    || map.get("First").equalsIgnoreCase("HST:")){
                txtFirst.setTypeface(null, BOLD);
                txtSecond.setTypeface(null, BOLD);
                txtFirst.setTextSize(TypedValue.COMPLEX_UNIT_PT,13);
                txtSecond.setTextSize(TypedValue.COMPLEX_UNIT_PT,11);
            }

            txtFirst.setText(map.get(FIRST_COLUMN));
            txtSecond.setText(map.get(SECOND_COLUMN));
        }


        return convertView;
    }

}
