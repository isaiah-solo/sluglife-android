package slugapp.com.ucscstudentapp.dining;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.EventSearchList;
import slugapp.com.ucscstudentapp.http.Callback;
import slugapp.com.ucscstudentapp.http.TestDiningHallHttpRequest;
import slugapp.com.ucscstudentapp.main.ActivityCallback;
import slugapp.com.ucscstudentapp.map.Map;

/**
 * Created by isayyuhh_s on 8/8/2015.
 */
public class DiningHallDetail extends Fragment {
    private String name;
    private DiningHall diningHall;
    private ActivityCallback mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallBack = (ActivityCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle b = getArguments();
        name = b.getString("name");
        new TestDiningHallHttpRequest(name).execute(new Callback<DiningHall>() {
            @Override
            public void onSuccess(DiningHall val) {
                diningHall = val;
            }
            @Override
            public void onError(Exception e) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dining_hall, container, false);
        mCallBack.setTitle(name);

        // set date, breakfast, lunch, and dinner
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(mCallBack.today().month() + " " + mCallBack.today().day());
        setMenu(diningHall.getBreakfast(), (TableLayout) view.findViewById(R.id.breakfast));
        setMenu(diningHall.getLunch(), (TableLayout) view.findViewById(R.id.lunch));
        setMenu(diningHall.getDinner(), (TableLayout) view.findViewById(R.id.dinner));
        setLegendDialog(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCallBack.setButtons(R.id.dining_button);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.find_on_map_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.map_find) {
            Bundle b = new Bundle();
            b.putString("name", mCallBack.title());
            FragmentTransaction ft = mCallBack.fm().beginTransaction();
            Map fragment = new Map();
            fragment.setArguments(b);
            ft.replace(R.id.listFragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMenu(FoodMenu menu, TableLayout table) {
        // for each food item
        for (int i = 0; i < menu.size(); i++) {
            // init objects
            FoodItem food = menu.get(i);
            TableRow row = new TableRow(getActivity());
            TextView name = new TextView(getActivity());
            LinearLayout attributes = new LinearLayout(getActivity());

            // params
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(75, 75);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1);
            rowParams.setMargins(12, 24, 5, 0);

            // food name
            name.setText(menu.get(i).name());
            name.setTextColor(Color.BLACK);
            name.setTextSize(14.0f);

            // food attributes
            attributes.setOrientation(LinearLayout.HORIZONTAL);
            if (food.attributes().size() == 0) {
                ImageView icon = new ImageView(getActivity());
                icon.setLayoutParams(iconParams);
                icon.setImageResource(R.drawable.ic_allergy_free);
                attributes.addView(icon);
            }
            for (int j = 0; j < food.attributes().size(); j++) {
                ImageView icon = new ImageView(getActivity());
                icon.setLayoutParams(iconParams);
                icon.setImageResource(food.attributes().get(j).getIcon());
                attributes.addView(icon);
            }

            // set params
            row.addView(name, 0, rowParams);
            row.addView(attributes, 1, rowParams);
            table.addView(row);
        }
    }

    private void setLegendDialog (View view) {
        // set legend OnClickListener
        for (int i = 0; i < 3; i++) {
            TextView legend;
            switch (i) {
                case 0:default:
                    legend = (TextView) view.findViewById(R.id.blegend);
                    break;
                case 1:
                    legend = (TextView) view.findViewById(R.id.llegend);
                    break;
                case 2:
                    legend = (TextView) view.findViewById(R.id.dlegend);
                    break;
            }
            legend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    LegendDialog dialog = new LegendDialog();
                    dialog.show(ft, "dialog");
                }
            });
        }
    }
}
