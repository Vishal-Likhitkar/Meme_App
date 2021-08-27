package com.appy.meme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class meme1 extends AppCompatActivity {

    ImageView m1;
    ProgressBar pb;
    String  MemeUrl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme1);
        m1 = (ImageView) findViewById(R.id.image);
        pb = (ProgressBar) findViewById(R.id.p1);
        loadurl();
    }
    private void loadurl() {
        pb.setVisibility(View.VISIBLE);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";
// Request a string response from the provided URL.
        JsonObjectRequest jsonobjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MemeUrl = response.getString("url");

                            Glide.with(meme1.this).load(MemeUrl).listener(new RequestListener<Drawable>() {
                                                                                     @Override
                                                                                     public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                                                         pb.setVisibility(View.GONE);
                                                                                         return false;
                                                                                     }

                                                                                     @Override
                                                                                     public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                                                         pb.setVisibility(View.GONE);
                                                                                         return false;
                                                                                     }
                                                                                 }

                            ).into(m1);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(meme1.this,"Something is wrong  " + error, Toast.LENGTH_LONG).show();

            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonobjectRequest);

    }
    public void sharememe(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(intent.EXTRA_TEXT,"Hii checkout this meme...  "+ MemeUrl);
        startActivity(Intent.createChooser(intent,"Share this meme with"));
    }

    public void nextmeme(View view) {

        loadurl();
    }
}