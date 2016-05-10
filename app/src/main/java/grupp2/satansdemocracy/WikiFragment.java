package grupp2.satansdemocracy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class WikiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView charName, charInfo;

    public WikiFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WikiFragment newInstance(String param1, String param2) {
        WikiFragment fragment = new WikiFragment();
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

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        charName = (TextView) view.findViewById(R.id.char_name_view);
        charInfo = (TextView) view.findViewById(R.id.char_about_view);

        ImageButton summaryButton = (ImageButton) view.findViewById(R.id.button_summary);
        ImageButton wolandButton = (ImageButton) view.findViewById(R.id.button_woland);
        ImageButton pilatusButton = (ImageButton) view.findViewById(R.id.button_pilatus);
        ImageButton margaritaButton = (ImageButton) view.findViewById(R.id.button_margarita);
        ImageButton aklagarButton = (ImageButton) view.findViewById(R.id.button_aklagaren);
        ImageButton docButton = (ImageButton) view.findViewById(R.id.button_doctor);

        summaryButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                charName.setText("DEL: 1");
                charInfo.setText(R.string.summaryinfo);
            }
        });
        summaryButton.requestFocus();

        wolandButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    charName.setText("WOLAND");
                    charInfo.setText(R.string.wolandinfo);
                }
            }
        });

        pilatusButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                charName.setText("PONTIUS PILATUS");
                charInfo.setText(R.string.pilatusinfo);

            }
        });

        margaritaButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                charName.setText("MARGARITA");
                charInfo.setText(R.string.margaritainfo);

            }
        });

        docButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                charName.setText("DOKTORN");
                charInfo.setText(R.string.docinfo);

            }
        });

        aklagarButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                charName.setText("ÅKLAGAREN");
                charInfo.setText(R.string.aklagarinfo);

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wiki, container, false);
    }

}
