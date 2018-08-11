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
    private static final String PREFS = "PREFS";
    private static final String INGREDIENTS = "INGREDIENTS";
    private static String mRecipeName;
    private static String mRecipeIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        mRecipeName = preferences.getString(RECIPE_NAME,null);
        mRecipeIngredients = preferences.getString(INGREDIENTS, null);
        Log.v(INGREDIENTS, mRecipeIngredients);
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        //TODO: I can successfully display the ingredients, however I do not know how to organize into a ListView
        views.setTextViewText(R.id.selected_recipe, mRecipeIngredients);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.chef, pIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

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
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(ACTION_TEXT_CHANGED)) {
//            // handle intent here
//            mRecipeName = intent.getStringExtra(RECIPE_NAME);
//        }
//        super.onReceive(context, intent);
//
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

