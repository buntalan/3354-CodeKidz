package com.example.calendar;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.view.Menu;

import com.example.calendar.R;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Calendar;
import java.util.GregorianCalendar;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class ViewWeekly extends AppCompatActivity implements WeekView.EventClickListener, WeekView.EmptyViewClickListener,
        WeekView.AddEventClickListener, WeekView.WeekViewLoader, WeekView.EmptyViewLongPressListener, WeekView.DropListener,
        WeekView.EventLongPressListener{
    // TAG for debugging
    private static final String TAG = "ViewWeekly";
    // WeekView reference for WeekView object
    private WeekView mWeekView;

    // WeekViewType
    private int mWeekViewType;

    // Random class for number gen - for random color gen
    private static final Random random = new Random();

    // Titles for intents
    private static final String HOUR_OF_DAY = "com.example.calendar.hour_of_day";
    private static final String DAY_OF_WEEK = "com.example.calendar.day_of_week";
    private static final String NUMBER_OF_WEEK = "com.example.calendar.number_of_week";
    private static final String MONTH = "com.example.calendar.month";
    private static final String YEAR = "com.example.calendar.year";

    // Type of View
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_3WEEK_VIEW = 3;
    private static final int TYPE_WEEK_VIEW = 7;

    // Attributes
    private int hour;
    private int day;
    private int weekNumber;
    private int month;
    private int year;

    // Generate random color - For Events
    private static @ColorInt int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    // Get event title; used for onClickEvent to get title
    protected String getEventTitle(DayTime time) {
        return String.format(Locale.getDefault(), "Event of %s %02d:%02d", time.getDay().getDisplayName(TextStyle.SHORT,
                Locale.getDefault()), time.getHour(), time.getMinute());
    }

    // Override onCreate class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreateBundle(called)");
        setContentView(R.layout.activity_view_weekly);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.weeklyToolBar);
        setSupportActionBar(myToolbar);

        mWeekViewType = TYPE_3WEEK_VIEW;

        hour = getIntent().getIntExtra(HOUR_OF_DAY, 0);
        day = getIntent().getIntExtra(DAY_OF_WEEK, 0);
        weekNumber = getIntent().getIntExtra(NUMBER_OF_WEEK, 0);
        month = getIntent().getIntExtra(MONTH, 0);
        year = getIntent().getIntExtra(YEAR, 1970);

        // Ger reference for xml layout
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set a WeekViewLoader to draw the events on load
        mWeekView.setWeekViewLoader(new WeekView.WeekViewLoader() {

            // For loading events during scroll
            @Override
            public List<? extends WeekViewEvent> onWeekViewLoad() {
                List<WeekViewEvent> events = new ArrayList<>();
                // Add Week Events
                return events;
            }
        });

        // Set EventClicker for this class
        mWeekView.setOnEventClickListener(this);

        // Set WeekLoader for this class for infinite horizontal scrolling
        mWeekView.setWeekViewLoader(this);

        // Set long press listener for this class
        mWeekView.setEventLongPressListener(this);

        // Set empty view click listener
        mWeekView.setEmptyViewClickListener(this);

        // Set empty view click listener
        mWeekView.setAddEventClickListener(this);

        // Set drag and drop listener
        mWeekView.setDropListener(this);

        // Can choose other Listeners, but probably wont

    }

    // Override drag and drop
    @Override
    public void onDrop(View view, DayTime day) {
        //Toast.makeText(this, "View dropped to " + day.toString(), Toast.LENGTH_SHORT).show();
    }

    // Override onEventClick
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(this, "Clicked" + event.toString(), Toast.LENGTH_SHORT).show();
    }

    // Override onEmptyViewClicked
    // Implement event add
    @Override
    public void onEmptyViewClicked(DayTime day) {
        //Toast.makeText(this, "Empty view" + " clicked: " + getEventTitle(day), Toast.LENGTH_SHORT).show();
    }

    // Override addEventClicked
    @Override
    public void onAddEventClicked(DayTime startTime, DayTime endTime) {
        //Toast.makeText(this, "Add event clicked.", Toast.LENGTH_SHORT).show();
    }

    // Override onEventLongPress... may or may not implement
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    // Will need to change this method to put actual events.
    // TODO: Will use this method to load in actual events from file.
    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DayTime startTime = new DayTime(LocalDateTime.now().plusHours(i * (random.nextInt(3) + 1)));
            DayTime endTime = new DayTime(startTime);
            endTime.addMinutes(random.nextInt(30) + 30);
            WeekViewEvent event = new WeekViewEvent("ID" + i, "Event " + i, startTime, endTime);
            event.setColor(randomColor());
            events.add(event);
        }

        // Day Hour Minute
        // How to set new events
        DayTime startTime = new DayTime(day % 7, hour, 0);
        DayTime endTime = new DayTime(day % 7, hour + 1, 0);
        WeekViewEvent event = new WeekViewEvent("ID" + 0, "Event Experiment", startTime, endTime);
        events.add(event);

        return events;
    }

    // Override long press on empty
    // For either day view or add event
    @Override
    public void onEmptyViewLongPress(DayTime time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    // For dragging events
    private final class DragTapListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
    }

    // For retrieving the date from MonthlyView activity
    public static Intent newIntent(Context packageContext,long date) {
        // Create calendar object to get date
        Calendar calendar = new GregorianCalendar();

        // Set date and timezone of calendar
        calendar.setTimeInMillis(date);

        // Extract date from calendar
        Intent intent = new Intent(packageContext, ViewWeekly.class);
        intent.putExtra(HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        intent.putExtra(DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK));
        intent.putExtra(NUMBER_OF_WEEK, calendar.get(Calendar.WEEK_OF_YEAR));
        intent.putExtra(MONTH, calendar.get(Calendar.MONTH));
        intent.putExtra(YEAR, calendar.get(Calendar.YEAR));

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_week, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // TODO: Change Add Event Page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_to_month:
                // Go to main activity
                startActivity(new Intent(ViewWeekly.this, MainActivity.class));
                return true;
            case R.id.menu_to_7week:
                // Switch to 7 day
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Change dimensions
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                            getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                            getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                            getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.menu_to_3week: // This aint needed here
                // Go to weekly
                if (mWeekViewType != TYPE_3WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_3WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Change dimensions
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                            getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.menu_to_day:
                // Go to day view
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Change dimensions
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                            getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.menu_to_year:
                // Go to year view
                return true;
            case R.id.menu_to_event:
                // Go to event
                return true;
            default:
                // Action not recognized, invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }
}
