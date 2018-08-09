package kitchenpal.troychuinard.com.kitchenpal;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String RECIPE_NAME = "RECIPE_NAME";
    //TODO: Another method of getting information from Activity/Fragment/POJO to widget - unclear why I would need to do this but also added this string to my manifest
    public static final String ACTION_TEXT_CHANGED = "kitchenpal.troychuinard.com.kitchenpal.TEXT_CHANGED";
    private static String mRecipeName;
    private SharedPreferences mPrefs;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        Log.v("WIDGET", mRecipeName);
        views.setTextViewText(R.id.selected_recipe, mRecipeName);
        //TODO: How would I set the Textview text size?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            views.setTextViewTextSize(R.id.selected_recipe, 12);
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        //TODO: Why isn't my widget launching my MainActivity onClick?
        //TODO: I am trying to add a ListView of ingredients to my Widget but have no idea where to begin
        views.setOnClickPendingIntent(R.id.chef, pIntent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //TODO: I cannot pass data from MyAdapter to the BakingWidgerProvider. I am simply trying to display the most recently
    //TODO: selected recipe on the widget. I have scoured StackOverflow to no avail
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_TEXT_CHANGED)) {
            // handle intent here
            mRecipeName = intent.getStringExtra(RECIPE_NAME);
        }
        super.onReceive(context, intent);

//        if(intent.getAction().equals("UPDATE_ACTION")) {
//            mRecipeName = intent.getStringExtra(RECIPE_NAME);
//            int[] appWidgetIds = intent.getExtras().getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
//            if (appWidgetIds != null && appWidgetIds.length > 0) {
//                this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
//            }
//        } else {
//            super.onReceive(context, intent);
//        }
//    }
    }
}

