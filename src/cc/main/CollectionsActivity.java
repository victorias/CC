package cc.main;

import java.util.ArrayList;
import java.util.List;

import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class CollectionsActivity extends Activity {
	
	static final int NEW_COLLECTION_REQUEST = 0;
	//List<Collection> collections;
	GridView gridColls;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collections);
        
        CCActivity.ALERT.setMessage("Alert Browsing");
        CCActivity.ALERT.show();
        
        //collections = new ArrayList<Collection>();
        gridColls = (GridView) findViewById(R.id.gridView1);
        
        CCActivity.ALERT.setMessage("Alert Browsing 1");
        CCActivity.ALERT.show();
        //gridColls.setAdapter(new ImageAdapter(this,getLayoutInflater()));
    }
    //hi
    
    public void onNewCollectionButtonClick(View view) {
    	Intent intent = new Intent(this, NewCollectionActivity.class);
    	startActivityForResult(intent, NEW_COLLECTION_REQUEST);
    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case NEW_COLLECTION_REQUEST:
    		switch (resultCode) {
    		case RESULT_OK:
    	        Collection newColl = (Collection) data.getParcelableExtra("collection");
    			break;
    		case RESULT_CANCELED:
    			break;
    		}
    		break;
    	}
    }
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.collections, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share:
            	
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}