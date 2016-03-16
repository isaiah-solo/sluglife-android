package slugapp.com.ucscstudentapp.dining;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.DiningHallHttpRequest;
import slugapp.com.ucscstudentapp.http.HttpCallback;
import slugapp.com.ucscstudentapp.http.TestDiningHallHttpRequest;
import slugapp.com.ucscstudentapp.main.BaseFragment;
import slugapp.com.ucscstudentapp.map.MapFragment;

/**
 * Created by isayyuhh_s on 8/8/2015.
 */
public class DiningHallDetail extends BaseFragment {
    private String diningHallName;
    private DiningHall diningHall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle b = getArguments();
        this.diningHallName = b.getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dining_hall, container, false);
        this.setLayout(diningHallName, R.id.dining_button);
        this.setView(view);

        return view;
    }

    @Override
    protected void setView(View view) {
        final View view_ = view;
        final TextView date = (TextView) view.findViewById(R.id.date);
        new DiningHallHttpRequest(diningHallName).execute(new HttpCallback<DiningHall>() {
            @Override
            public void onSuccess(DiningHall val) {
                diningHall = val;

                date.setText(ac.getToday().getMonth() + " " + ac.getToday().getDay());
                TableLayout layout = (TableLayout) view_.findViewById(R.id.meal);
                int currentTime = today.getStartTime();
                String currentTOD = today.getStartTOD().toLowerCase();
                TextView mealTitle = (TextView) view_.findViewById(R.id.meal_title);
                FoodMenu meal = null;

                if ((currentTime == 12 || currentTime < 11) && currentTOD.compareTo("am") == 0) {
                    meal = diningHall.getBreakfast();
                    mealTitle.setText("Breakfast");
                } else if (currentTime == 11 && currentTOD.compareTo("am") == 0 ||
                        currentTime == 12 && currentTOD.compareTo("pm") == 0 ||
                        currentTime > 0 && currentTime < 5 && currentTOD.compareTo("pm") == 0) {
                    meal = diningHall.getLunch();
                    mealTitle.setText("Lunch");
                } else if (currentTime >= 5 && currentTime < 12 && currentTOD.compareTo("pm") == 0) {
                    meal = diningHall.getDinner();
                    mealTitle.setText("Dinner");
                }
                setMenu(meal, layout);
                setLegendDialog(view_);
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.find_on_map_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // find on map
        if (id == R.id.map_find) {
            /*
            Bundle b = new Bundle();
            b.putString("name", diningHall.getCollege());
            MapFragment fragment = new MapFragment();
            fragment.setArguments(b);
            this.ac.setFragment(fragment);
            */
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMenu(FoodMenu menu, TableLayout table) {
        // for each food item
        for (Food food: menu.getItems()) {
            TableRow row = new TableRow(getActivity());
            TextView name = new TextView(getActivity());
            LinearLayout attributes = new LinearLayout(getActivity());

            // params
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(75, 75);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1);
            rowParams.setMargins(12, 24, 5, 0);

            // food name
            name.setText(food.name());
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
            for (FoodAttribute attribute: food.attributes()) {
                ImageView icon = new ImageView(getActivity());
                icon.setLayoutParams(iconParams);
                icon.setImageResource(attribute.getIcon());
                attributes.addView(icon);
            }

            // set params
            row.addView(name, 0, rowParams);
            row.addView(attributes, 1, rowParams);
            table.addView(row);
        }
    }

    private void setLegendDialog(View view) {
        // set legend OnClickListener
        TextView legend = (TextView) view.findViewById(R.id.legend);
        legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) ft.remove(prev);
                ft.addToBackStack(null);

                // Create and show the dialog.
                DiningLegendDialog dialog = new DiningLegendDialog();
                dialog.show(ft, "dialog");
            }
        });

    }
}
