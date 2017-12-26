package com.ervic.mac.gobvalle.Principal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.ervic.mac.gobvalle.WebView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

public class TestCancelar extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ProgressDialog progress;
    private boolean one_time;
    private int _firsttime = 0;
    private AdvancedWebView webview;
    BottomNavigationView bottomNavigationView;
    private AdvancedWebView web_cancelar;
    private String TAG_LOGGED = "LOGGED";
    private PreferenciasGobvalle my_preferences;
    private String url = "http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=consult&tipo=1";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar);
        progress = new ProgressDialog(this);
        web_cancelar = (AdvancedWebView) findViewById(R.id.web_cancelar);
        my_preferences = new PreferenciasGobvalle(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
        ImageView btn_back = (ImageView)findViewById(R.id.btn_back);
        web_cancelar.setWebViewClient(getWebViewClient());
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestCancelar.this, MainActivity.class);
                startActivity(intent);
            }
        });
        web_cancelar.addHttpHeader("nx-bodycss", "app-design");
        WebSettings settings = web_cancelar.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDisplayZoomControls(false);
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBuiltInZoomControls(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        web_cancelar.setWebChromeClient(new MyWebChromeClient(this));
        web_cancelar.setWebViewClient(getWebViewClient());

        settings.setUserAgentString("user-agent-string");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        web_cancelar.loadUrl(url,true);
        isLogged();

        final Menu menu = bottomNavigationView.getMenu();
        int i = 0;

        for(i = 0; i < MyApplication.getData().bottomMenu.size(); i++) {
            final int aux = i;
            Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
            Picasso.with(this).load(MyApplication.getData().bottomMenu.get(i).icon).placeholder(getResources().getDrawable(R.drawable.ic_launcher_background)).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(drawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    //menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(placeHolderDrawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    //Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
                }
            });
        }
        bottomNavigationView.invalidate();
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        MenuItem menuItem = bottomNavigationView.getMenu().getItem(item.getItemId()).setChecked(true);
        switch (item.getItemId()) {
            case 0: {
                Intent intent = new Intent(TestCancelar.this,WebView.class);
                intent.putExtra("item",0);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(TestCancelar.this,WebView.class);
                intent.putExtra("item",1);
                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(TestCancelar.this,WebView.class);
                intent.putExtra("item",2);
                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(TestCancelar.this,WebView.class);
                intent.putExtra("item",3);
                startActivity(intent);
            }
            break;
        }
        return false;
    }
    public void cancelProgress(){
        Thread timerTread = new Thread(){
            public void run(){
                try{


                    sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();
                } finally {

                    progress.cancel();
                }
            }
        };
        timerTread.start();
    }
    public void isLogged(){

        if(my_preferences.readElement(TAG_LOGGED,false)){

            web_cancelar.loadUrl(url,getCustomHeaders());
            cancelProgress();
            Log.e("nx_user_identification", MyApplication.get_dataUser().getIdentificacion());
            Log.e("nx_user_fecha_pago",MyApplication.get_dataUser().getPago());
        }else{

            web_cancelar.setWebViewClient(getWebViewClient());
            web_cancelar.loadUrl(url,getCustomHeaders());
            cancelProgress();

        }

    }
    private Map<String, String> getCustomHeaders()
    {
        Map<String, String> headers = new HashMap<>();
        if(my_preferences.readElement(TAG_LOGGED,false)) {

            headers.put("nx-bodycss", "app-design");
            headers.put("nx-user-identification", MyApplication.get_dataUser().getIdentificacion());
            headers.put("nx-user-fecha-pago", MyApplication.get_dataUser().getPago());
            headers.put("nx-user-token",MyApplication.getTokenFirebase());
            return headers;
        }else{
            headers.put("nx-bodycss", "app-design");
            headers.put("nx-user-token",MyApplication.getTokenFirebase());


            return headers;
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(TestCancelar.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
    private Map<String,String> getHeader()
    {
        Map<String,String> header = new HashMap<>();
        header.put("nx-bodycss", "app-design");
        return  header;

    }
    private WebViewClient getWebViewClient()
    {
        return new WebViewClient()
        {



            @Override
            @SuppressWarnings("deprecation")  // Deprecated until api 21
            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, String url) {
                Uri uri = Uri.parse(url);

                if (uri.getHost().equalsIgnoreCase("qa-pidame.nexura.com")) {
                    if(uri.toString().contains("http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=infoAppointment") && one_time)
                        return loadRequestWithHeaders(url);
                } else {
                    return super.shouldInterceptRequest(view, url);
                }
                return null;
            }

            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, WebResourceRequest request) {
                Uri uri = null;
                String myuri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    myuri = request.getUrl().toString();
                    uri = Uri.parse(myuri);
                }

                if (uri.getHost().equalsIgnoreCase("qa-pidame.nexura.com")) {
                    Log.e("DOBLE-RAYOS", myuri);
                    if((uri.toString().contains("http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=consult&tipo=1") && one_time || uri.toString().contains("http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=infoAppointment") && one_time )) {
                        one_time = false;
                        return loadRequestWithHeaders(myuri);

                    }
                } else {
                    return super.shouldInterceptRequest(view, myuri);
                }
                return null;
            }


            private WebResourceResponse loadRequestWithHeaders(String url) {
                try {

                    URL urlObject = new URL(url);
                    web_cancelar.loadUrl(url,getHeader());
                    Log.e("MyURL",url);
                    HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();

                    con.setRequestProperty("nx-bodycss", "app-design");
                    con.setRequestMethod("HEAD");
                    Map<String, List<String>> hashmap = con.getHeaderFields();
                    Log.e("NUMEROHEADER",String.valueOf(hashmap.size()));
                    Log.e("HEADER0",con.getHeaderField(0));
                    Log.e("HEADER1",con.getHeaderField(1));
                    Log.e("HEADER2",con.getHeaderField(2));
                    Log.e("HEADER19",con.getHeaderField(19).toString());

                    String[] types = parseContentHeader(con.getContentType());
                    return new WebResourceResponse(types[0], types[1], con.getInputStream());
                } catch (Exception ex) {
                    return loadRequestWithHeaders(url);

                }
            }


            /**
            @Override
            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, String url) {

                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setHeader("nx-bodycss", "app-design");
                    HttpResponse httpReponse = client.execute(httpGet);

                    Header contentType = httpReponse.getEntity().getContentType();
                    Header encoding = httpReponse.getEntity().getContentEncoding();
                    InputStream responseInputStream = httpReponse.getEntity().getContent();

                    String contentTypeValue = null;
                    String encodingValue = null;
                    if (contentType != null) {
                        contentTypeValue = contentType.getValue();
                    }
                    if (encoding != null) {
                        encodingValue = encoding.getValue();
                    }
                    return new WebResourceResponse(contentTypeValue, encodingValue, responseInputStream);
                } catch (ClientProtocolException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                } catch (IOException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                }
            }
            **/

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request)
            {
                view.loadUrl(request.getUrl().toString(), getHeader());
                Log.e("Cancelar",request.getUrl().toString());
                return true;
            }

            /**

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, WebResourceRequest request) {

                String mimetype = null;
                String encoding = null;
                Map<String, String> headers = new HashMap<>();
                headers.putAll(getCustomHeaders());
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");


                URL urll = null;
                try {
                    String urlentry = request.getUrl().toString();
                    Log.e("PASAPORTESENTRA",urlentry);
                    urll = new URL(request.getUrl().toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) urll.openConnection();
                    //conn.setInstanceFollowRedirects(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String key : headers.keySet()) {
                    conn.setRequestProperty(key, headers.get(key));
                    // TODO look for the mimetype and encoding header information and set mimetype and encoding
                }

                try {
                    if(request.getUrl().getHost().equalsIgnoreCase("http://qa-pidame.nexura.com/"))
                        Log.e("RAYOS",request.getUrl().toString());

                    return new WebResourceResponse(mimetype, encoding, conn.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // return null here if you decide to let the webview load the resource
                return null;
            }
**/
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Log.v("TEST", url);
                    Toast.makeText(getApplicationContext(), "SKIP", Toast.LENGTH_SHORT).show();

                    view.loadUrl(url,getHeader());

                return true;
            }
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                if(url == "http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=infoAppointment")
                    one_time = false;

                super.onPageFinished(view, url);
            }
        };


    }


    private String[] parseContentHeader(String contentType) {
        String[] types = contentType.split(";");

        if (types[1].startsWith("charset=")) {
            types[1] = types[1].substring("charset=".length());
        }

        types[0] = types[0].trim();
        types[1] = types[1].trim();

        return types;
    }



    public WebViewClient getWebViewClientWithCustomHeader(){
        return new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, String url) {
                try {
                    OkHttpClient httpClient = new OkHttpClient();
                    com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                            .url(url.trim())
                            .addHeader("nx-bodycss", "app-design")
                            .build();
                    com.squareup.okhttp.Response response = httpClient.newCall(request).execute();

                    return new WebResourceResponse(
                            response.header("content-type", response.body().contentType().type()), // You can set something other as default content-type
                            response.header("content-encoding", "utf-8"),  // Again, you can set another encoding as default
                            response.body().byteStream()
                    );
                } catch (ClientProtocolException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                } catch (IOException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                }
            }
        };

    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }
}
