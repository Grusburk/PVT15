package grupp2.satansdemocracy;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    private ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    private ListView twitterView;
    private TextView username, message, testView;
    private final String TAG = MainActivity.class.getSimpleName();

    public NyheterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NyheterFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




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

//        twitterView = (ListView) getView().findViewById(R.id.twitter_view);
        username = (TextView) getView().findViewById(R.id.text1);
        message = (TextView) getView().findViewById(R.id.text2);
        testView = new TextView(getContext());
        testView.setTextColor(Color.CYAN);

        /**
         * Set up for the authentication with twitter
         */
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey("GDG916Hf5d7RfYH1DdoLSvRjI")
                .setOAuthConsumerSecret("kdK7efW38xOMURyDtlqHoZzFVV2Z8v2j0rQzj9lRMWuAeXsEHW")
                .setOAuthAccessToken("1014739988-RKSvYmCAanERT98Wz0iKf5oCcZ9xQ0169YcegKJ")
                .setOAuthAccessTokenSecret("nSrkfuYXMA2Cyk0dCH4tzgoyDmK46EMI5baWxDYNyU402");
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        try {
            Query query = new Query("satansdemokrati");
            query.setCount(100);
            query.setSince("2014-05-25");
            QueryResult result;
            result = twitter.search(query);
            List<Status> users = result.getTweets();
            System.out.println(users);
            for (Status tweet : users) {
//                twitterView.addHeaderView(testView);
//                twitterView.addFooterView(testView);
                username.setText(tweet.getUser().getName());
//                testView.setTextSize(25);
                message.setText(tweet.getText());
            }

        } catch (TwitterException te) {
            te.printStackTrace();
        }

    }
}
