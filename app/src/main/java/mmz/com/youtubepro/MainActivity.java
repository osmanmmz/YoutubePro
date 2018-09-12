package mmz.com.youtubepro;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mmz.com.youtubepro.Adapter.CustomExpandableListAdapter;
import mmz.com.youtubepro.Helper.FragmentNavigationManager;
import mmz.com.youtubepro.Interface.NavigationManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String [] items;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> listTitle;
    private Map<String,List<String>> listChild;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle=getTitle().toString();
        expandableListView=(ExpandableListView)findViewById(R.id.navList);
        navigationManager= FragmentNavigationManager.getmInstance(this);

        initItems();
        View listHeaderView=getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);
        genData();

        addDrawerItem();
        setupDrawer();

        if(savedInstanceState==null){
            selectFirstItemAsDefault();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("MMz");
    }

    private void selectFirstItemAsDefault() {
        if(navigationManager!=null){
            String firstItem=listTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setupDrawer() {
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("MMz");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItem() {
        adapter=new CustomExpandableListAdapter(this,listTitle,listChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                getSupportActionBar().setTitle(listTitle.get(i).toString());
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                getSupportActionBar().setTitle("MMz");
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selectedItem=((List)(listChild.get(listTitle.get(i)))).get(i1).toString();
                getSupportActionBar().setTitle(selectedItem);
                if(items[0].equals(listTitle.get(i))){
                    navigationManager.showFragment(selectedItem);
                }else{
                    throw new IllegalArgumentException("not supported");
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void genData() {
        List<String> title= Arrays.asList("osman","mamaz","mahmut","askmakfs");
        List<String> childItem= Arrays.asList("asd","fgh","jkl","şi,");

        listChild=new TreeMap<>();
        listChild.put(title.get(0),childItem);
        listChild.put(title.get(1),childItem);
        listChild.put(title.get(2),childItem);
        listChild.put(title.get(3),childItem);

        listTitle=new ArrayList<>(listChild.keySet());

    }

    private void initItems() {
        items=new String[]{"osman","mamaz","mahmut","askmakfs"};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
