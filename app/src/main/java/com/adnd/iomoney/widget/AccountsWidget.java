package com.adnd.iomoney.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.adnd.iomoney.MainActivity;
import com.adnd.iomoney.R;
import com.adnd.iomoney.models.Account;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class AccountsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, float totalBalance, List<Account> accountList) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.accounts_widget);
        views.setTextViewText(R.id.tv_total_balance, context.getResources().getString(R.string.currency_format, totalBalance));

        views.setOnClickPendingIntent(R.id.accounts_widget_root, pendingIntent);

        Intent remoteViewServiceIntent = new Intent(context, AccountsRemoteViewsService.class);
        remoteViewServiceIntent.putExtra(AccountsRemoteViewsService.EXTRA_ACCOUNTS_LIST_JSON, parseAccountsListToJson(accountList));
        views.setRemoteAdapter(R.id.lv_accounts_list, remoteViewServiceIntent);
        views.setEmptyView(R.id.lv_accounts_list, R.id.empty_widget_accounts_list);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String parseAccountsListToJson(List<Account> accountList) {
        Moshi moshi = new Moshi.Builder().build();
        Type accountListType = Types.newParameterizedType(List.class, Account.class);
        JsonAdapter<List<Account>> jsonAdapter = moshi.adapter(accountListType);
        return jsonAdapter.toJson(accountList);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if (appWidgetIds.length > 0) {
            AccountsWidgetService.startActionUpdateWidgets(context, appWidgetIds);
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
}

