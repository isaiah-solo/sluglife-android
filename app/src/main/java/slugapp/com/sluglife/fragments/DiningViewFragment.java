package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewDiningBinding;
import slugapp.com.sluglife.enums.AttributeEnum;
import slugapp.com.sluglife.models.FoodObject;
import slugapp.com.sluglife.models.FoodMenuObject;

/**
 * Created by isaiah on 8/8/2015
 * <p/>
 * This file contains a view fragment that displays dining hall food information for the given
 * time of day.
 */
public class DiningViewFragment extends BaseViewFragment {
    private static final int LINEAR_LAYOUT_PARAMS = 75;

    private static final int TABLE_ROW_INIT_WIDTH = 0;
    private static final int TABLE_ROW_INIT_WEIGHT = 1;

    private static final int TABLE_ROW_LEFT = 12;
    private static final int TABLE_ROW_TOP = 24;
    private static final int TABLE_ROW_RIGHT = 5;
    private static final int TABLE_ROW_BOTTOM = 0;

    private static final int TABLE_COLUMN_FOOD = 0;
    private static final int TABLE_COLUMN_ATTRIBUTES = 1;

    private static final float TEXT_SIZE = 14.0f;

    private ViewDiningBinding mBinding;
    private FoodMenuObject mFoodMenu;

    /**
     * Gets a new instance of fragment
     *
     * @param context Activity context
     * @param name    Fragment name
     * @param menu    Food menu
     * @return New instance of fragment
     */
    public static DiningViewFragment newInstance(Context context, String name, FoodMenuObject menu) {
        DiningViewFragment fragment = new DiningViewFragment();

        Bundle b = new Bundle();
        b.putString(context.getString(R.string.bundle_name), name);
        b.putSerializable(context.getString(R.string.bundle_json), menu);
        fragment.setArguments(b);

        return fragment;
    }

    /**
     * Fragment's onCreateView method
     *
     * @param inflater           Layout inflater
     * @param container          Container of fragment
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.view_dining, container, false);

        this.setViewFragment();

        return this.mBinding.getRoot();
    }

    /**
     * Sets fields from fragment arguments
     *
     * @param b Bundle from fragment arguments
     */
    @Override
    protected void setArgumentFields(Bundle b) {
        this.mFoodMenu = (FoodMenuObject) b.getSerializable(this.mContext.getString(R.string.bundle_json));
        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
    }

    /**
     * Sets fragment view
     */
    @Override
    protected void setView() {
        if (this.mFoodMenu.isEmpty()) {
            this.hideViews(this.mBinding.meal);
            this.showViews(this.mBinding.failed);
            return;
        }

        // for each food item
        for (final FoodObject food : this.mFoodMenu.getItems()) {
            TableRow row = new TableRow(this.mContext);
            TextView name = new TextView(this.mContext);
            LinearLayout attributes = new LinearLayout(this.mContext);

            // Params
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    LINEAR_LAYOUT_PARAMS, LINEAR_LAYOUT_PARAMS);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TABLE_ROW_INIT_WIDTH,
                    TableRow.LayoutParams.WRAP_CONTENT, TABLE_ROW_INIT_WEIGHT);
            rowParams.setMargins(TABLE_ROW_LEFT, TABLE_ROW_TOP, TABLE_ROW_RIGHT, TABLE_ROW_BOTTOM);

            // Food name
            name.setText(food.name);
            name.setTextColor(Color.BLACK);
            name.setTextSize(TEXT_SIZE);

            // Food attributes
            attributes.setOrientation(LinearLayout.HORIZONTAL);
            for (AttributeEnum attribute : food.attributes) {
                ImageView icon = new ImageView(this.mContext);

                icon.setLayoutParams(iconParams);
                icon.setImageResource(attribute.icon);

                attributes.addView(icon);
            }

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDialogFragment(DiningFoodDialogFragment.newInstance(mContext,
                            food));
                }
            });

            // Set params
            row.addView(name, TABLE_COLUMN_FOOD, rowParams);
            row.addView(attributes, TABLE_COLUMN_ATTRIBUTES, rowParams);
            this.mBinding.meal.addView(row);
        }
    }
}