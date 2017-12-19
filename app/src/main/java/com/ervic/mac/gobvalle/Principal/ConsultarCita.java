package com.ervic.mac.gobvalle.Principal;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

public class ConsultarCita extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ImageView btn_back;
    ProgressDialog progress;
    BottomNavigationView bottomNavigationView;
    android.webkit.WebView web_consultar;
    private String TAG_LOGGED = "LOGGED";
    //private AdvancedWebView web_consultar;
    private PreferenciasGobvalle my_preferences;
    private WebView myWebview;
    private String url = "http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=consult&tipo=2";
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_cita);
        my_preferences = new PreferenciasGobvalle(this);
        web_consultar = (WebView) findViewById(R.id._webview);
        progress = new ProgressDialog(this);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultarCita.this,Consultar.class);
                startActivity(intent);
                finish();
            }
        });
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere ...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        web_consultar.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 [FB_IAB/FB4A;FBAV/28.0.0.20.16;]");
        progress.show();
        //web_consultar.addHttpHeader("nx-bodycss", "app-design");
        isLogged();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
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
                Intent intent = new Intent(ConsultarCita.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",0);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(0).link);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(ConsultarCita.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",1);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(1).link);

                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(ConsultarCita.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",2);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(2).link);

                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(ConsultarCita.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",3);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(3).link);

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
            WebSettings webSettings = web_consultar.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            web_consultar.setWebViewClient(getWebViewClient());
            //web_consultar.addHttpHeader("nx-bodycss", "app-design");
            //web_consultar.addHttpHeader("nx-user-identification", MyApplication.get_dataUser().getIdentificacion());
            //web_consultar.addHttpHeader("nx-user-fecha-pago", MyApplication.get_dataUser().getPago());
            web_consultar.loadUrl(url,getCustomHeaders());
            cancelProgress();



        }else{
            WebSettings webSettings = web_consultar.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            web_consultar.setWebViewClient(getWebViewClient());
           //web_consultar.addHttpHeader("nx-bodycss", "app-design");
            web_consultar.loadUrl(url,getCustomHeaders());
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
            return headers;
        }else{
            headers.put("nx-bodycss", "app-design");



            return headers;
        }
    }
    @Override
    public void onBackPressed(){

       finish();

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

                if (uri.toString().equalsIgnoreCase("http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=consult&tipo=2")) {
                    Log.e("RAYOS",url);
                    return loadRequestWithHeaders(url);
                } else {
                    return super.shouldInterceptRequest(view, url);
                }
            }

            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, WebResourceRequest request) {
                Uri uri = null;
                String myuri = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    myuri = request.getUrl().toString();
                    uri = Uri.parse(myuri);
                }
                Log.e("VALOR",uri.getHost());
                if (uri.getHost().equalsIgnoreCase("qa-pidame.nexura.com")) {

                    //Log.e("RAYOS",myuri);
                    return loadRequestWithHeaders(url);
                } else {
                    //Log.e("RAYOS",url);

                    return super.shouldInterceptRequest(view, url);
                }
            }


            private WebResourceResponse loadRequestWithHeaders(String url) {
                try {
                    URL urlObject = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();

                    con.addRequestProperty("nx-bodycss", "app-design");

                    String[] types = parseContentHeader(con.getContentType());
                    return new WebResourceResponse(types[0], types[1], con.getInputStream());
                } catch (Exception ex) {
                    return null;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("URLRECIBIDO",url);
                //view.loadUrl(url,getCustomHeaders());
                super.onPageFinished(view, url);
            }
            /**
            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(final android.webkit.WebView view, WebResourceRequest request)
            {

                        view.loadUrl(url,getHeader());
                        Log.e("URLRECIBIDO",url);


                //view.loadUrl(request.getUrl().toString(), getCustomHeaders());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(final android.webkit.WebView view, final String url)
            {


                        view.loadUrl(url,getHeader());
                        Log.e("",url);

                return false;
            }
            **/



        };
    }
/**
    public WebViewClient WebViewClient() {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            try {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("MY-CUSTOM-HEADER", "header value");
                httpGet.setHeader(HttpHeaders.USER_AGENT, "custom user-agent");
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
    }
*/
private boolean handleUri(final Uri uri) {
    Log.i("URI", "Uri =" + uri);
    final String host = uri.getHost();
    final String scheme = uri.getScheme();
    // Based on some condition you need to determine if you are going to load the url
    // in your web view itself or in a browser.
    // You can use `host` or `scheme` or any part of the `uri` to decide.
    if (true) {
        // Returning false means that you are going to load this url in the webView itself
        return false;
    } else {
        // Returning true means that you need to handle what to do with the url
        // e.g. open web page in a Browser
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        return true;
    }
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

}
