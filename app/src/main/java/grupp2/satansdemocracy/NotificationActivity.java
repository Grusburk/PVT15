package grupp2.satansdemocracy;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NotificationActivity extends AppCompatActivity {
    private String snackBarText, dialogText;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        FrameLayout voteFrame = (FrameLayout) this.findViewById(R.id.vote_frame);
        FrameLayout newspaperFrame = (FrameLayout) this.findViewById(R.id.newspaper_frame);
        Button voteButton = (Button) findViewById(R.id.vote_button);
        TextView voteText = (TextView) findViewById(R.id.vote_text);
        TextView candidate1Name = (TextView) findViewById(R.id.woland_text);
        TextView candidate2Name = (TextView) findViewById(R.id.pilatus_text);
        ImageButton candidate1 = (ImageButton) findViewById(R.id.button_woland);
        ImageButton candidate2 = (ImageButton) findViewById(R.id.button_pilatus);

        voteFrame.setVisibility(View.INVISIBLE);
        newspaperFrame.setVisibility(View.INVISIBLE);

        Bundle intentPerformer = getIntent().getExtras();
        message = getIntent().getStringExtra("special");
        if (intentPerformer != null) {

            /**
             * Omröstningar
             */
            if (message != null) {
                dialogText = message;
                setVoteDialog();

            } else if (intentPerformer.getString("key").equals("1")){
                voteText.setText(R.string.vote_text);
                candidate1.setImageResource(R.drawable.woland_selector);
                candidate2.setImageResource(R.drawable.pontius_selector);
                candidate1Name.setText("WOLAND");
                candidate2Name.setText("PONTIUS");
                voteFrameClickListener(voteFrame, voteButton);

            }else if (intentPerformer.get("key").equals("2")){
                voteText.setText(R.string.vote_text2);
                candidate1.setImageResource(R.drawable.aklagaren_selector);
                candidate2.setImageResource(R.drawable.doc_selector);
                candidate1Name.setText("ÅKLAGAREN");
                candidate2Name.setText("DOKTORN");
                voteFrameClickListener(voteFrame, voteButton);

            /**
            *Frågor
             */

            }else if (intentPerformer.getString("key").equals("3")){
                dialogText = "RÄCK UPP HANDEN OCH FRÅGA WOLAND: \r\nVARFÖR MÖRDADE DU JESUS?";
                setVoteDialog();

            }else if (intentPerformer.getString("key").equals("4")){
                dialogText = "Kan du spå in i framtiden? Vet du vad som ska hända innan det händer? Pirrar det i magen? Illamående? I wouldn’t know, " +
                        "I drink too much.\n" + "\n" + "Om du kan se in i framtiden, erkänn det nu.";
                setVoteDialog();

            }else if (intentPerformer.getString("key").equals("5")){
                dialogText = "Börja bråka med Woland. Inled en konversation och försök att bli irriterad på henne tills ni hamnat i en argumentation.";
                setVoteDialog();

            /**
            *Beacons
             */

            }else if (intentPerformer.getString("key").equals("6")){
                newspaperFrame.setVisibility(View.VISIBLE);

            }else if (intentPerformer.getString("key").equals("7")){

            }else if (intentPerformer.getString("key").equals("8")){

            }else if (intentPerformer.getString("key").equals("9")){

            }else if (message == null){

            }else {

            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void voteFrameClickListener(FrameLayout voteFrame, Button voteButton) {
        voteFrame.setVisibility(View.VISIBLE);
        if (voteButton != null) {
            voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBarText = "Din röst är registrerad";
                    getSnackBar();
                }
            });
        }
    }

    private void getSnackBar () {
        final View parentLayout = findViewById(R.id.notificationFrame);
        Snackbar.make(parentLayout, snackBarText, Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (event == DISMISS_EVENT_TIMEOUT){
                    finish();
                }
            }
        }).show();
    }

        private void setVoteDialog () {
        final AlertDialog.Builder questionDialog = new AlertDialog.Builder(NotificationActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        questionDialog.setMessage(dialogText).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        questionDialog.show();
    }
}
