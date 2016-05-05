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
import android.widget.ProgressBar;

import com.d4t.getoldtweetslibrary.manager.TweetManager;
import com.d4t.getoldtweetslibrary.model.Tweet;

import java.util.ArrayList;
import java.util.List;


public class NyheterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters
    private RecyclerView recyclerView;
    private Tweetadapter mAdapter;
    private ListView twitterView;
    private final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;

    public NyheterFragment() {
        // Required empty public constructor
    }

    public static NyheterFragment newInstance(String param1, String param2) {
        NyheterFragment fragment = new NyheterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar) ;
        prepareTweetData();
    }

    /**
     * Använder Mattes egengjorda biblotek
     */
    private void prepareTweetData() {
        recyclerView.setVisibility(View.GONE);
        TweetManager tweetManager = new TweetManager();
        tweetManager.executeTwitterQuery("Satansdemokrati", 100, new TweetManager.TwitterCallback() {
            @Override
            public void onResponse(List<Tweet> tweets) {
                mAdapter = new Tweetadapter(tweets);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
