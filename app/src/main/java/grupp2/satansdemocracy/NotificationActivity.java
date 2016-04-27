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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NotificationActivity extends AppCompatActivity {
    private String snackBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        FrameLayout voteFrame = (FrameLayout) this.findViewById(R.id.vote_frame);
        FrameLayout newspaperFrame = (FrameLayout) this.findViewById(R.id.newspaper_frame);
        Button voteButton = (Button) findViewById(R.id.vote_button);
        voteFrame.setVisibility(View.INVISIBLE);
        newspaperFrame.setVisibility(View.INVISIBLE);

        Bundle intentPerformer = getIntent().getExtras();
        if (intentPerformer != null) {
            if (intentPerformer.getString("key").equals("1")){
                newspaperFrame.setVisibility(View.VISIBLE);
            }else if (intentPerformer.get("key").equals("2")){
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
//                setVoteDialog();

            }else if (intentPerformer.equals("3")){

            }else if (intentPerformer.equals("4")){

            }else if (intentPerformer.equals("5")){

            }else if (intentPerformer.equals("6")){

            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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


    //    private void setVoteDialog () {
//        final AlertDialog.Builder voteDialog = new AlertDialog.Builder(NotificationActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
//        CharSequence[] array = getResources().getStringArray(R.array.voteoptions);
//        final View parentLayout = findViewById(R.id.notificationFrame);
//        voteDialog.setTitle("Vem tycker du skulle bli bäst stadsminisiter");
//        voteDialog.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Snackbar.make(parentLayout, "Din röst är registrerad", Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
//                    @Override
//                    public void onDismissed(Snackbar snackbar, int event) {
//                        super.onDismissed(snackbar, event);
//                        if (event == DISMISS_EVENT_TIMEOUT){
//                            finish();
//                        }
//                    }
//                }).show();
//            }
//        });
//        voteDialog.show();
//    }


}
