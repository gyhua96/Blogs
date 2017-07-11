package cn.gongyuhua.blogs;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class Posts extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String CATEGORY_ID = "category_id";
        private ArrayList<String> mList;
        MyAdapter ma;
        RecyclerView recyclerView;

        public Posts() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Posts newInstance(int category) {
            Posts fragment = new Posts();
            Bundle args = new Bundle();
            args.putInt(CATEGORY_ID , category);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.posts);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                mList = new ArrayList<String>();
                ma = new MyAdapter(mList,getContext());
                //GetPosts gp=new GetPosts(mList,ma,recyclerView);
                String url = "http://gongyuhua.cn/wp-json/wp/v2/posts?category=15&page=1&per_page=1&context=embed";
                switch (getArguments().getInt(CATEGORY_ID)) {
                    case 1:
                        url += "&categories=3";
                        break;
                    case 2:
                        url += "&categories=8";
                        break;
                    case 3:
                        url += "&categories=6";
                        break;
                    case 4:
                        url += "&categories=4";
                        break;
                    case 5:
                        url += "&categories=2";
                        break;
                    default:
                        url += "&categories=15";
                }
                for (int i = 0; i < 8; i++) {
                    GetPosts gp = new GetPosts(mList, ma, recyclerView);
                    String str = url + "&offset=" + i;
                    gp.execute(str);
                }
                recyclerView.setAdapter(ma);


            //recyclerView.setAdapter(ma);

            //TextView textView = (TextView) rootView.findViewById(R.id.textView);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(CATEGORY_ID )));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a Posts (defined as a static inner class below).
            // TODO change the category id for instance hewe
            return Posts.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "技术杂谈";
                case 1:
                    return "编程语言";
                case 2:
                    return "音乐";
                case 3:
                    return "算法基础";
                case 4:
                    return "ACM";
            }
            return null;
        }
    }
    private static class GetPosts extends AsyncTask<String, Integer, ArrayList<String>> {

        MyAdapter adapter;
        ArrayList<String> postsList;
        RecyclerView recyclerView;
        ArrayList<String> posts=new ArrayList<>();
        GetPosts(ArrayList<String> postList,MyAdapter adapter,RecyclerView recyclerView) {
            this.postsList = postList;
            this.adapter = adapter;
            this.recyclerView = recyclerView;
        }

        @Override
        protected ArrayList<String> doInBackground(String... ids) {
            //for (int i = 0; i < 6; i++) {
                HttpURLConnection connection = null;
                String str = null;
                try {
                    URL url = new URL(ids[0]);
                    //Log.d("url",ids[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    str = "";
                    String len;
                    while ((len = br.readLine()) != null) {
                        str = str + len;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }

                str = str.substring(1, str.length() - 1);
                if(!str.equals("")) {
                    PostsBean post = PostsBean.objectFromData(str);
                    posts.add(post.getTitle().getRendered());
                    posts.add(post.getExcerpt().getRendered());
                    posts.add(post.getDate());
                    posts.add(post.getId());
                }
            //}
            return posts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> posts) {
            postsList.addAll(posts);
            adapter.update(postsList);

        }
    }

}
