package com.example.zuoye;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private Context context;

    private SQLiteDatabase sqLiteDatabase;

    public static String AUTHORITY = "com.example.zuoye.MyContentProvider";
    public static final int URI_CODE = 1;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, DbHelp.TABLE_NAME, URI_CODE);
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        initProviderData();
        return false;
    }

    private void initProviderData() {
        sqLiteDatabase = new DbHelp(context).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DbHelp.TABLE_NAME, new String[]{"_id", "name"}, null, null, null, null, null);
        boolean hasData = false;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if (name.equals("少年") || name.equals("不才")) {
                hasData = true;
                cursor.close();
                break;
            }
        }
        if (!hasData) {
            sqLiteDatabase.beginTransaction();
            ContentValues c = new ContentValues();
            c.put("name", "少年");
            sqLiteDatabase.insert(DbHelp.TABLE_NAME, null, c);
            c.put("name", "不才");
            sqLiteDatabase.insert(DbHelp.TABLE_NAME, null, c);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }
    }

    /**
     * 获取表名
     *
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                tableName = DbHelp.TABLE_NAME;
                break;

        }
        return tableName;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        return sqLiteDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        sqLiteDatabase.insert(tableName, null, values);
        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int count = sqLiteDatabase.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row = sqLiteDatabase.update(tableName, values, selection, selectionArgs);
        if (row > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
