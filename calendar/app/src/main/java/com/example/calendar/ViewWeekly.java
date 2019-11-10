package com.example.calendar;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.calenderview.R;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class ViewWeekly extends AppCompatActivity implements WeekView.EventClickListener, WeekView.EmptyViewClickListener,
        WeekView.AddEventClickListener, WeekView.WeekViewLoader, WeekView.EmptyViewLongPressListener, WeekView.DropListener,
        WeekView.EventLongPressListener{
    // TAG for debugging
    private static final String TAG = "ViewWeekly";
    // WeekView instantiation for WeekView
    private WeekView mWeekView;
    // Random class for number gen - for random color gen
    private static final Random random = new Random();

    // Generate random color - For Events
    private static @ColorInt int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    protected String getEventTitle(DayTime time) {
        return String.format(Locale.getDefault(), "Event of %s %02d:%02d", time.getDay().getDisplayName(TextStyle.SHORT,
                Locale.getDefault()), time.getHour(), time.getMinute());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreateBundle(called)");
        setContentView(R.layout.activity_view_weekly);

        // Ger reference for xml layout
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set a WeekViewLoader to draw the events on load
        mWeekView.setWeekViewLoader(new WeekView.WeekViewLoader() {

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

        // Can choose other Listeners
    }

    @Override
    public void onDrop(View view, DayTime day) {
        Toast.makeText(this, "View dropped to " + day.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked" + event.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewClicked(DayTime day) {
        Toast.makeText(this, "Empty view" + " clicked: " + getEventTitle(day), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddEventClicked(DayTime startTime, DayTime endTime) {
        Toast.makeText(this, "Add event clicked.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

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
        return events;
    }

    @Override
    public void onEmptyViewLongPress(DayTime time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    private final class DragTapListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
    }
}
