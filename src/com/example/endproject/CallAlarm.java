package com.example.endproject;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.example.endproject.database.DatabaseHelper;
import com.example.endproject.xml.XMLParser;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;

/**
 * This class is responsible for displaying the prepared window.
 * 
 * @author Seize the Day
 * 
 */
public class CallAlarm extends Activity {

	/**
	 * Static field to define name in table
	 */
	private static final String TABLE_COMPONENT = "component";

	/**
	 * Filed need to define item in XML file. Parent node.
	 */
	static final String KEY_ITEM = "item";

	/**
	 * Filed need to define link in XML file
	 */
	static final String KEY_LINK = "link";

	/**
	 * Filed need to define name in XML file
	 */
	static final String KEY_NAME = "name";

	/**
	 * Filed need to define title in XML file
	 */
	static final String KEY_TITLE = "title";

	/**
	 * Filed need to define item in XML file
	 */
	static final String KEY_DESC = "description";

	/**
	 * Static name define name table in database.
	 */
	private static final String TABLE_TIME = "timealarm";

	/**
	 * Variable need to played sounds.
	 */
	private MediaPlayer mp;

	/**
	 * Variable need to get access to database.
	 */
	private SQLiteDatabase db;

	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor;

	/**
	 * Constructor. Initialize database helper.
	 */
	public CallAlarm() {
	}

	/**
	 * Called when the activity is starting. This is where most initialization
	 * should go: calling setContentView(int) to inflate the activity's UI,
	 * using findViewById(int) to programmatically interact with widgets in the
	 * UI, calling managedQuery(android.net.Uri, String[], String, String[],
	 * String) to retrieve cursors for data being displayed, etc.
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.call_alarm);

		db = (new DatabaseHelper(this)).getWritableDatabase();

		// -------------------------------------ALARM--------------------------------------------//
		alarm();
		// -------------------------------------NEWS---------------------------------------------//
		if (checkItem("news")) {
			news();
		} else {
			// Disable list
			ListView list;
			list = (ListView) findViewById(R.id.listNews);
			list.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, 100));
			//list.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, 1));
			//RelativeLayout layout = (RelativeLayout)findViewById(R.id.layout);
			//layout.removeViewInLayout(list);
		}
		// -----------------------------------APHORISM-------------------------------------------//
		if (checkItem("aphorism")) {
			aphorism();
		} else {
			ListView list;
			list = (ListView) findViewById(R.id.listNews);
			list.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, 1));
		}		
	}

	/**
	 * This method check in database existing item.
	 * 
	 * @param string
	 *            - item in database
	 * @return - true if is existing in database, false if isn't
	 */
	private boolean checkItem(String item) {

		String[] resultColumns = new String[] { "_id", "item", "data" };

		cursor = db.query(TABLE_COMPONENT, resultColumns, null , null, null, null, null);
		
		startManagingCursor(cursor);
		
		// loop through cursor 
		while(cursor.moveToNext()) {
		    if( (cursor.getString(cursor.getColumnIndex("item"))).equals(item) ) {
		    	return true;
		    }
		}

		return false;
	}

	/**
	 * This method call aphorism when activity is started.
	 */
	private void aphorism() {

		String[] resultColumns = new String[] { "_id", "item", "data" };

		cursor = db.query(TABLE_COMPONENT, resultColumns, "item=?",
				new String[] { "aphorism" }, null, null, null);

		ListView list;

		list = (ListView) findViewById(R.id.listAphorism);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();

		String xml = new String();

		if (cursor.moveToFirst()) {
			xml = parser.getXmlFromUrl(cursor.getString(cursor
					.getColumnIndex("data"))); // Getting XML.
		}

		Document doc = parser.getDomElement(xml); // Getting DOM element.

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);

		// Looping through all item nodes <item>.
		for (int i = 0; i < 1; i++) {

			// Creating new HashMap.
			HashMap<String, String> map = new HashMap<String, String>();

			Element e = (Element) nl.item(i);

			// Adding each child node to HashMap key => value.
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView.
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item, new String[] { KEY_TITLE, KEY_DESC },
				new int[] { R.id.title, R.id.description });

		list.setAdapter(adapter);

	}

	/**
	 * This method call news when activity is started.
	 */
	private void news() {

		String[] resultColumns = new String[] { "_id", "item", "data" };

		cursor = db.query(TABLE_COMPONENT, resultColumns, "item=?",
				new String[] { "news" }, null, null, null);

		ListView list;

		list = (ListView) findViewById(R.id.listNews);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();

		String xml = new String();

		if (cursor.moveToFirst()) {
			xml = parser.getXmlFromUrl(cursor.getString(cursor
					.getColumnIndex("data"))); // Getting XML.
		}

		Document doc = parser.getDomElement(xml); // Getting DOM element.

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);

		// Looping through all item nodes <item>.
		for (int i = 0; i < nl.getLength(); i++) {

			// Creating new HashMap.
			HashMap<String, String> map = new HashMap<String, String>();

			Element e = (Element) nl.item(i);

			// Adding each child node to HashMap key => value.
			map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView.
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item, new String[] { KEY_TITLE, KEY_DESC,
						KEY_LINK }, new int[] { R.id.title, R.id.description,
						R.id.link });

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Getting values from selected ListItem.
				Uri uriUrl = Uri.parse(((TextView) view.findViewById(R.id.link))
						.getText().toString());

				// Starting new intent.
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);

				// Start link in browser.
				startActivity(launchBrowser);

			}
		});

	}

	/**
	 * This method call alarm when activity is started.
	 */
	private void alarm() {

		// remove from db data
		db.delete(TABLE_TIME, null, null);

		mp = MediaPlayer.create(CallAlarm.this, R.raw.ring);
		mp.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.start();
			}
		});

		mp.start();

	}

	/**
	 * 
	 * @param v
	 *            - View
	 */
	public void bClosePressed(View v) {
		mp.stop();
		finish();
	}

	/**
	 * 
	 */
	protected void onPause() {
		super.onPause();
		mp.stop();
	}

	/**
	 * 
	 */
	@Override
	public void onDestroy() {

		super.onDestroy();
		db.close();
	}
}
