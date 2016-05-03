package grupp2.satansdemocracy;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.ArrayList;
import java.util.List;


public class NyheterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Tweet> tweetList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Tweetadapter mAdapter;
    private ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    private ListView twitterView;
    private final String TAG = MainActivity.class.getSimpleName();

    public NyheterFragment() {
        // Required empty public constructor
    }

    public static NyheterFragment newInstance(String param1, String param2) {
        NyheterFragment fragment = new NyheterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nyheter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new Tweetadapter(tweetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareTweetData();
    }

    /**
     * Set up for the authentication with twitter
     */
    private void prepareTweetData() {
        configurationBuilder.setPrettyDebugEnabled(true)
                .setOAuthConsumerKey("GDG916Hf5d7RfYH1DdoLSvRjI")
                .setOAuthConsumerSecret("kdK7efW38xOMURyDtlqHoZzFVV2Z8v2j0rQzj9lRMWuAeXsEHW")
                .setOAuthAccessToken("1014739988-RKSvYmCAanERT98Wz0iKf5oCcZ9xQ0169YcegKJ")
                .setOAuthAccessTokenSecret("nSrkfuYXMA2Cyk0dCH4tzgoyDmK46EMI5baWxDYNyU402");
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        Query query = new Query("#obama");

        query.setResultType(Query.ResultType.recent);
        query.setCount(80);
        query.setSince("2015-01-01");
        try {
            QueryResult result = twitter.search(query);
            Log.i("TAG", "Size: " + result.getCount());
            Log.i("TAG", "Limit: " + result.getTweets().size());
            List<Status> users = result.getTweets();
            System.out.println(users.size());
            System.out.println(query.getSince() + " " + query.getCount());
            System.out.println(users);
            for (Status tweet : users) {
                Tweet tweetData = new Tweet(tweet.getUser().getName(), tweet.getText());
                tweetList.add(tweetData);
                mAdapter.notifyDataSetChanged();
            }
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
