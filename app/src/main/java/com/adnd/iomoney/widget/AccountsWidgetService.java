package com.adnd.iomoney.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.repositories.AccountsRepository;

import java.util.List;

public class AccountsWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGETS = "com.adnd.iomoney.widget.action_update_widgets";

    private static final String EXTRA_APP_WIDGET_IDS = "com.adnd.iomoney.widget.extra.app_widget_ids";


    public AccountsWidgetService() {
        super("AccountsWidgetService");
    }

    public static void startActionUpdateWidgets(Context context, int[] appWidgetsIds) {
        Intent intent = new Intent(context, AccountsWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        intent.putExtra(EXTRA_APP_WIDGET_IDS, appWidgetsIds);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                final int[] appWidgetIds = intent.getIntArrayExtra(EXTRA_APP_WIDGET_IDS);
                handleActionUpdateWidgets(appWidgetIds);
            }
        }
    }

    private void handleActionUpdateWidgets(int[] appWidgetIds) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        AccountsRepository accountsRepository = new AccountsRepository(getApplication());
        List<Account> accounts = accountsRepository.loadAccountsBlock();

        float totalBalance = 0.0f;

        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }

        for (int i : appWidgetIds) {
            AccountsWidget.updateAppWidget(getApplicationContext(), appWidgetManager, i, totalBalance, accounts);
        }

    }
}
