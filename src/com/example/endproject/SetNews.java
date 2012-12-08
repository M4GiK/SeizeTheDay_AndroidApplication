package com.example.endproject;

import java.util.ArrayList;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This class is responsible for displaying and choosing category.
 * 
 * @author Seize the Day
 * 
 */
public class SetNews extends Activity {

	/**
	 * Static field to define name in table
	 */
	private static final String TABLE = "component";

	/**
	 * Object need to connect with database
	 */
	private SQLiteDatabase db;

	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor;

		
	
	/**
	 * Constructor. Initialize elements with database.
	 */
	public SetNews() {}

		
	
	/**
	 * This method initialize all part to save selected category. Called when
	 * the activity is starting. This is where most initialization should go:
	 * calling setContentView(int) to inflate the activity's UI, using
	 * findViewById(int) to programmatically interact with widgets in the UI,
	 * calling managedQuery(android.net.Uri, String[], String, String[], String)
	 * to retrieve cursors for data being displayed, etc.
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.set_news);

		db = (new DatabaseHelper(this)).getWritableDatabase();
		
		String[] resultColumns = new String[] { "_id", "item", "data" };
		
		cursor = db.query(TABLE, resultColumns, "item=?" , new String[]{"news"}, null, null, null);
		
		ListView list;

		list = (ListView) findViewById(R.id.list_news);

		ArrayList<String> newsList = new ArrayList<String>();

		// Add categories to list
		newsList.add("World");
		newsList.add("Future");
		newsList.add("Travel");
		newsList.add("History");
		newsList.add("Nature");
		newsList.add("Health");

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, newsList);

		list.setAdapter(arrayAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String category = ((TextView) view).getText().toString();

				// Write information to data base.
				ContentValues values = new ContentValues(2);
				
				if (cursor != null) {
					// Removing from database 
					db.delete(TABLE, "item=?", new String[]{"news"});
				}
				
				if (category.equals("World")) {			
					
					values.put("item", "news");
					values.put("data", "http://feeds.bbci.co.uk/news/world/rss.xml");	
					
				} else if (category.equals("Future")) {
					
					values.put("item", "news");
					values.put("data", "http://www.bbc.com/future/feed.rss");	
					
				} else if (category.equals("Travel")) {
					
					values.put("item", "news");
					values.put("data", "http://www.bbc.com/travel/feed.rss");	
					
				} else if (category.equals("History")) {
					
					values.put("item", "news");
					values.put("data", "http://www.bbc.co.uk/history/0/rss.xml");	
					
				} else if (category.equals("Nature")) {

					values.put("item", "news");
					values.put("data", "http://feeds.bbci.co.uk/nature/rss.xml");	
					
				} else if (category.equals("Health")) {
					
					values.put("item", "news");
					values.put("data", "http://www.bbc.co.uk/health/0/rss.xml");	
					
				}
				
				db.insert(TABLE, null, values);
				cursor.requery();

				// Starting new intent.
				Intent intent = new Intent(SetNews.this, MainActivity.class);

				// Back to main page.
				startActivity(intent);

			}
		});
	}

	
	
	/**
	 * Perform any final cleanup before an activity is destroyed. This can
	 * happen either because the activity is finishing (someone called finish()
	 * on it, or because the system is temporarily destroying this instance of
	 * the activity to save space. You can distinguish between these two
	 * scenarios with the isFinishing() method.
	 */
	@Override
	public void onDestroy() {
		
		super.onDestroy();
		cursor.close();
		db.close();
		
	}
}
