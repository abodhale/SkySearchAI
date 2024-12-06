package com.example.skysearchai;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText editText;
    TextView wlcmtxt;
    ImageView sendBtn;
    MessagesList messagesList;
    User us,chatgpt;
    MessagesListAdapter<Message> adapter;
    FirebaseAuth auth;
    Button logout_btn;
    FirebaseUser user;
    ProgressBar progressBar;

    TextToSpeech tts;
    boolean istts=false;


    //  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
// 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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextText);
        sendBtn = findViewById(R.id.imageButton);
        messagesList=findViewById(R.id.messagesList);
        wlcmtxt = findViewById(R.id.welocmetxt);
        auth= FirebaseAuth.getInstance();
        logout_btn=findViewById(R.id.logout);
        user=auth.getCurrentUser();

        logout_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Log.d("logout","inside logout");
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),FeedbackActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload)
            {
                Picasso.get().load(url).into(imageView);
            }
        };
        adapter=new MessagesListAdapter<Message>("1",imageLoader);

        us = new User("1","akash", "");
        chatgpt=new User("2","Chatgpt","");

        messagesList.setAdapter(adapter);
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Message message=new Message("m1", editText.getText().toString(),us, Calendar.getInstance().getTime(),null);
                adapter.addToStart(message,true);
                if(editText.getText().toString().toLowerCase().startsWith("generate image"))
                {
                    generateImageAction(editText.getText().toString());
                }
                else
                {
                    performAction(editText.getText().toString());
                }
                editText.setText("");
                wlcmtxt.setText("");
            }
        });

        tts =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.US);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.voice && istts == false)
        {

            item.setIcon(R.drawable.baseline_record_voice_over_24);
            istts=true;
        }
        else
        {
            item.setIcon(R.drawable.baseline_voice_over_off_24);
            istts=false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void performAction(String inputText) {
        if (inputText.isEmpty()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.openai.com/v1/chat/completions";

        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("model", "gpt-4o-mini-2024-07-18");
            jsonObject.put("max_tokens", 1500); // Limit response length
            jsonObject.put("temperature", 0.7); // Adjust creativity


            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", inputText);
            messages.put(message);
            jsonObject.put("messages", messages);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    response -> {
                        try {
                            JSONArray choices = response.getJSONArray("choices");
                            JSONObject choice = choices.getJSONObject(0);
                            JSONObject messageObject = choice.getJSONObject("message");
                            String result = messageObject.getString("content");
                            Message message1= new Message("M2",result.trim(),chatgpt,Calendar.getInstance().getTime(),null);
                            adapter.addToStart(message1,true);
                            if(istts)
                            {
                             tts.speak(result,TextToSpeech.QUEUE_FLUSH,null,null);
                            }
//                            resultTV.setText(result);
                        } catch (JSONException e) {
//                            resultTV.setText("Response parsing error.");
                        }
                    },
                    error -> Log.e("ChatGPT", "Network error: " + error.getMessage())
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Content-Type", "application/json");
                    map.put("Authorization", "Bearer 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");
                    return map;
                }
            };
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 60000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 15;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            queue.add(jsonObjectRequest);
        }
        catch (JSONException e)
        {
            // resultTV.setText("Request setup error.");
        }
    }

    public void generateImageAction(String inputText)
    {
        if (inputText.isEmpty()) {
            // resultTV.setText("Please provide input.");
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.openai.com/v1/images/generations";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "dall-e-2");
            jsonObject.put("prompt", editText.getText().toString());
            jsonObject.put("n",1);
            jsonObject.put("size", "1024x1024");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    response -> {
                        try {
                            JSONArray datas = response.getJSONArray("data");
                            JSONObject urlObj = datas.getJSONObject(0);
                            String result = urlObj.getString("url");
                            Log.i("Imgurl",result);
                            Message message1= new Message("M2","Image",chatgpt,Calendar.getInstance().getTime(),result.trim());
                            adapter.addToStart(message1,true);
                            Log.i("result",result);
                            //resultTV.setText(result);
                        } catch (JSONException e) {
                            Log.e("ChatGPT", "Parsing error: " + e.getMessage());
                        }
                    },
                    error -> Log.e("ChatGPT", "Network error: " + error.getMessage())
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Content-Type", "application/json");
                    map.put("Authorization", "Bearer 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");
                    return map;
                }
            };
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 60000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 15;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {
                }
            });
            queue.add(jsonObjectRequest);
        }
        catch (JSONException e)
        {
            Log.e("ChatGPT", "Request setup error: " + e.getMessage());
        }
    }
}

