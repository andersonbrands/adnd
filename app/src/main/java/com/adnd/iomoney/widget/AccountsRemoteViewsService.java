package com.adnd.iomoney.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adnd.iomoney.R;
import com.adnd.iomoney.models.Account;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class AccountsRemoteViewsService extends RemoteViewsService {

    public static final String EXTRA_ACCOUNTS_LIST_JSON = "extra_accounts_list_json";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AccountsWidgetRemoteViewsFactory(getApplicationContext(), intent);
    }

    class AccountsWidgetRemoteViewsFactory implements RemoteViewsFactory {

        private Context context;
        private List<Account> accountList;

        public AccountsWidgetRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            String accountsListJson = intent.getStringExtra(EXTRA_ACCOUNTS_LIST_JSON);
            accountList = getAccountListFromJson(accountsListJson);
        }

        private List<Account> getAccountListFromJson(String json) {
            Moshi moshi = new Moshi.Builder().build();
            Type accountListType = Types.newParameterizedType(List.class, Account.class);
            JsonAdapter<List<Account>> jsonAdapter = moshi.adapter(accountListType);

            List<Account> accounts = null;
            try {
                accounts = jsonAdapter.fromJson(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return accounts;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.account_widget_list_item);
            Account account = accountList.get(position);
            views.setTextViewText(R.id.tv_account_name, account.getName());
            views.setTextViewText(R.id.tv_account_balance, context.getResources().getString(R.string.currency_format, account.getBalance()));
            return views;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return accountList.size();
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
