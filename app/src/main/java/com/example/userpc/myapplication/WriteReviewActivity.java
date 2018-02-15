package com.example.userpc.myapplication;


import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class WriteReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Button reviewItButton = (Button) findViewById(R.id.reviewitbutton);

        reviewItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog myDialog;
                myDialog = new Dialog(view.getContext());
                myDialog.setContentView(R.layout.submit_review);
                myDialog.setCancelable(true);
                myDialog.setCanceledOnTouchOutside(true);
                Button submit = (Button) myDialog.findViewById(R.id.submitreview);

                EditText movieID = (EditText) myDialog.findViewById(R.id.movieidedit);
                EditText shortReview = (EditText) myDialog.findViewById(R.id.shortreviewedit);
                EditText fullReview = (EditText) myDialog.findViewById(R.id.fullreviewedit);
                ImageButton close = (ImageButton) myDialog.findViewById(R.id.closeimageButton);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();

                submit.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        //your login calculation goes here
                    }
                });
            }
        });
    }
}
