package contentprovider;

import cc.rep.CollectionOpenHelper;
import cc.rep.ItemOpenHelper;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MainContentProvider extends ContentProvider {
	private CollectionOpenHelper collectionDB;
	private ItemOpenHelper itemDB;
	
	public static final int C = 100;
	public static final int C_ID = 110;
	
	public static final int I = 200;
	public static final int I_ID = 220;
	
	private static final String AUTHORITY = "com.contentprovider.cc.maincontentprovider";
	
	private static final String COLLECTION_BASE_PATH = CollectionOpenHelper.COLLECTION_TABLE_NAME;
	public static final Uri CONTENT_URI_C = Uri.parse("content://" + AUTHORITY + "/" + COLLECTION_BASE_PATH);
	
	private static final String ITEM_BASE_PATH = ItemOpenHelper.ITEM_TABLE_NAME;
	public static final Uri CONTENT_URI_I = Uri.parse("content://" + AUTHORITY + "/" + ITEM_BASE_PATH);
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, COLLECTION_BASE_PATH, C);
		sURIMatcher.addURI(AUTHORITY, COLLECTION_BASE_PATH +"/#", C_ID);
		
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH, I);
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH + "/#", I_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		collectionDB = new CollectionOpenHelper(getContext());
		itemDB = new ItemOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qB = new SQLiteQueryBuilder();
		Cursor cursor;
		switch (sURIMatcher.match(uri)){
		case C:
			qB.setTables(CollectionOpenHelper.COLLECTION_TABLE_NAME);
			cursor = qB.query(collectionDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case C_ID:
			qB.setTables(CollectionOpenHelper.COLLECTION_TABLE_NAME);
			qB.appendWhere(CollectionOpenHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
			cursor = qB.query(collectionDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case I:
			qB.setTables(ItemOpenHelper.ITEM_TABLE_NAME);
			cursor = qB.query(itemDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case I_ID:
			qB.setTables(ItemOpenHelper.ITEM_TABLE_NAME);
			qB.appendWhere(ItemOpenHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
			cursor = qB.query(itemDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri.toString());
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}