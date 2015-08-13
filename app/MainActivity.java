package com.model.rosesagency;
import java.util.Calendar;
import java.util.Date;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class MainActivity extends ActionBarActivity {
    //fields
    private final String mViewUrl = "http://www.thecoolagency.com/webapp";
    private final String mViewQs = "?d=android";
    private final String mNochaceQs = "&nocache=";
    
    protected WebView mWebView;
    protected WebView mExternalView;
    protected android.support.v7.app.ActionBar mActionBar;
    //properties

    public String getViewUrl(){return mViewUrl;}
    @Override
    public void onCreate(Bundle savedInstanceState) {
     webview.setWebViewClient(new MyWebViewClient());
 webview.getSettings().setJavaScriptEnabled(true);
 webview.getSettings().setPluginsEnabled(true);
     }


     private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            view.loadUrl(url);
            return true;
        }

        @Override
        public void onLoadResource(WebView  view, String  url){

        }
    }    
    public void createAlarm(String message, int hour, int minutes) {
    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
            .putExtra(AlarmClock.EXTRA_MESSAGE, message)
            .putExtra(AlarmClock.EXTRA_HOUR, hour)
            .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
    }
}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the action bar at the top of the screen
        mActionBar = getSupportActionBar();
        mActionBar.hide();
        mActionBar.setHomeButtonEnabled(true); 
        //define action bar color
        mActionBar.setBackgroundDrawable(new ColorDrawable(0xff2d5b8e)); //2d5b8e
        //define action bar icon
        mActionBar.setIcon(R.drawable.ic_launcher);
        //set the main view
        setContentView(R.layout.activity_main);
        //build the main view
        buildView(savedInstanceState);

                     ProgressDialog _dialog ;                   
               my_web_view.setWebViewClient(new WebViewClient(){


       @Override
       public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // TODO Auto-generated method stub
        _dialog =ProgressDialog.show(Store_locator.this, "", "Please wait...");
        super.onPageStarted(view, url, favicon);
       }
       @Override
       public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
        _dialog.dismiss();
       }

       @Override
       public void onReceivedError(WebView view, int errorCode,
         String description, String failingUrl) {
        // TODO Auto-generated method stub
        super.onReceivedError(view, errorCode, description, failingUrl);
        try{
         _dialog.dismiss();
        }catch (Exception e) {
         // TODO: handle exception
        }
       }

      });

    }
    //find out if this URL is a link to a file (eg: PDF) by checking the extension at the end of the url
    private boolean isFileUrl(String url, String extension){
        boolean isFile=false;
        url=url.toLowerCase();extension=extension.toLowerCase();
        //if contains extension
        if(url.contains(extension)){
            //if also contains ? query string
            if(url.contains("?")){
                //strip off query string
                url=url.substring(0,url.indexOf("?"));
            }
            //if ends in file extension
            if(url.lastIndexOf(extension)==url.length()-extension.length()){
                //then yep. This is a file
                isFile=true;
            }
        }
        return isFile;
    }
    private class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;
    }
}
    //pass any url to this function to auto-handle certain activities (eg: opening PDF documents or making phone calls)
    protected void handleExternalDeviceActivity(String url){
        //pass this special URL to an external activity (outside of the app)
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    //verify that an internet connection is available
    protected boolean hasInternetConnection(Context context) {
        boolean isConnected=false;
        //if connectivity object is available
        ConnectivityManager con_manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con_manager.getActiveNetworkInfo() != null){
            //if network is available
            if(con_manager.getActiveNetworkInfo().isAvailable()){
                //if connected
                if(con_manager.getActiveNetworkInfo().isConnected()){
                    //yep... there is connectivity
                    isConnected=true;
                }
            }
        }
        return isConnected;
    }
    //show something if there is no internet connectivity
    protected void noInternetMessage(){
        //build an alert box
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        // Set the message to display
        alertbox.setMessage(R.string.err_connection_summary);
        // Add a neutral button to the alert box and assign a click listener
        alertbox.setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //quit the app
                finish();
            }
        });
        alertbox.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                //quit the app
                finish();
            }
        });
         // show the alert box
        alertbox.show();
    }
    protected void buildView(final Bundle savedInstanceState){
        //if there is still internet connectivity
        if(hasInternetConnection(this)){
            //get the webview object
            mWebView = ((WebView)findViewById(R.id.webview));
            //if web view is NOT already initialized (don't re-init just because orientation changed)
            if(savedInstanceState==null){
                //ALLOW JAVASCRIPT TO RUN INSIDE THE WEBVIEW
                //==========================================
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                //INIT LOADING INDICATOR OBJECT
                //=============================
                final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...",true);
                //KEY WEBVIEW EVENTS (OVERRIDES)
                //==============================
                mWebView.setWebViewClient(new WebViewClient() {
                    /*@Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    }*/
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //override webview url loading (distinguish between internal and external urls)
                        boolean ret = true;
                        //if there is still internet connectivity
                        if(hasInternetConnection(view.getContext())){
                            //if the new url is on the same webview page
                            if (url.indexOf(mViewUrl)==0) {
                                //load the new page inside the webview NOT another browser activity
                                ret = false;
                            }else{
                                //the new url is NOT on a webview page...
                                
                                //if the url ends in .pdf... (PDF files don't open inside webviews)
                                if(isFileUrl(url,".pdf")){
                                    //launch an external Activity that handles URLs, such as a browser application
                                    handleExternalDeviceActivity(url);
                                }else{
                                    //not a PDF url...
                                    
                                    //if this is a telephone number url (starts with "tel:")
                                    if(url.indexOf("tel:")==0){
                                        //launch an external Activity that handles telephone numbers to make a call
                                        handleExternalDeviceActivity(url);
                                    }else{
                                        //if this is a mailto link
                                        if(url.indexOf("mailto:")==0){
                                            //launch an external Activity that handles email mailto links
                                            handleExternalDeviceActivity(url);
                                        }else{
                                            //try to load the external url in the alternate webview
                                            buildExternalView(url);
                                        }
                                    }
                                }
                            }
                        }else{
                            //no internet connectivity...
                            noInternetMessage();
                        }
                        return ret;
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //hide the loading indicator when the webview is fully loaded
                        if(pd.isShowing()&&pd!=null){
                            pd.dismiss();
                            //clear the webview cache
                            super.onPageFinished(view, url);
                            view.clearCache(true);
                        }
                    }
                });
                //LOAD THE APP PAGE INTO THE WEB VIEW
                //===================================
                Calendar now = Calendar.getInstance();  
                Date date = new Date();
                now.setTime(date);
                String nocache = now.get(Calendar.YEAR)  + "_" + now.get(Calendar.MONTH) + "_" + now.get(Calendar.DAY_OF_MONTH) + "_"
                        + now.get(Calendar.HOUR_OF_DAY) + "_" + now.get(Calendar.MINUTE) + "_" + now.get(Calendar.SECOND);
                mWebView.loadUrl(mViewUrl + mViewQs + mNochaceQs + nocache);
            }else{
                //RESTORE THE INSTANCE STATE
                //==========================
                mWebView.restoreState(savedInstanceState);
            }
        }else{
            //no internet connection...
            noInternetMessage();
        }
    }
    protected void buildExternalView(String externalUrl){
        //if web view is NOT already initialized (don't re-init just because orientation changed)
        if(mExternalView==null){
            //get the webview object
            mExternalView = ((WebView)findViewById(R.id.externalView));
            //ALLOW JAVASCRIPT TO RUN INSIDE THE WEBVIEW
            //==========================================
            WebSettings webSettings = mExternalView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //INIT LOADING INDICATOR OBJECT
            //=============================
            final ProgressDialog pd = ProgressDialog.show(this, "", "Loading More...",true);
            //KEY WEBVIEW EVENTS (OVERRIDES)
            //==============================
            mExternalView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //override webview url loading (distinguish between internal and external urls)
                    boolean ret = true;
                    //if the url ends in .pdf... (PDF files don't open inside webviews)
                    if(isFileUrl(url,".pdf")){
                        //launch an external Activity that handles URLs, such as a browser application
                        handleExternalDeviceActivity(url);
                    }else{
                        
                        //if this is a telephone number url (starts with "tel:")
                        if(url.indexOf("tel:")==0){
                            //launch an external Activity that handles telephone numbers to make a call
                            handleExternalDeviceActivity(url);
                        }else{
                            //if this is a mailto link
                            if(url.indexOf("mailto:")==0){
                                //launch an external Activity that handles email mailto links
                                handleExternalDeviceActivity(url);
                            }else{
                                //just handle this url inside this same web view
                                ret=false;
                            }
                        }
                    }
                    return ret;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    //hide the loading indicator when the webview is fully loaded
                    if(pd.isShowing()&&pd!=null){
                        pd.dismiss();
                        //clear the webview cache
                        super.onPageFinished(view, url);
                        view.clearCache(true);
                        //try to make this webview visible
                        view.setVisibility(View.VISIBLE);
                        view.bringToFront();
                        //show the action bar at the top of the screen
                        mActionBar.show();
                    }
                    //set the new page title
                    setTitle(view.getTitle());
                }
            });
        }else{
            //mExternalView already created...
        }
        //LOAD THE EXTERNAL PAGE INTO THE WEB VIEW
        //========================================
        mExternalView.loadUrl(externalUrl);
    }
    //close the external webview and go back to the main app webview
    private void closeExternalView(){
        //show the main webview
        mWebView.bringToFront();
        //clear browse history from external web view
        mExternalView.loadUrl("about:blank");
        mExternalView.clearHistory();
        mExternalView.clearCache(true);
        //hide the externalView and the action bar
        mExternalView.setVisibility(View.GONE);
        mExternalView = null;
        mActionBar.hide();
    }
    @Override
    public void onBackPressed()
    {
        //if NOT in the external view mode
        if(mExternalView==null){
            if(mWebView.canGoBack()){
                //the back button should go to previous webview page instead of close the app
                mWebView.goBack();
            }
        }else{
            //in the external view mode...
            
            //if there is a previous external page
            if(mExternalView.canGoBack()){
                //get the last url
                WebBackForwardList backForwardList = mExternalView.copyBackForwardList();
                String historyUrl = backForwardList.getItemAtIndex(backForwardList.getCurrentIndex()-1).getUrl();
                //if NOT going back to about:blank (about:blank was inserted into the list at the point at which the external view was last closed)
                if(!historyUrl.equals("about:blank")){
                    //the back button should go to previous page instead of doing nothing
                    mExternalView.goBack();
                }else{
                    //instead of going back to about:blank, go back to the main view
                    closeExternalView();
                }
            }else{
                //there is no previous external page...
                
                //close the external view and return to the main app view
                closeExternalView();
            }
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
      //save the state of the WebView
      mWebView.saveState(outState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle action bar event options
        switch (item.getItemId()) 
        {        
            //if the main app icon was clicked in the action bar (while viewing an external webview page)
            case android.R.id.home:            
                //switch back to the main view and exit the external view...
                closeExternalView();
                return true;  
            //if the drop down "Return" item was clicked
            case R.id.action_returnapp:            
                //switch back to the main view and exit the external view...
                closeExternalView();
                return true; 
            //if the user clicked "Open in Browser"
            case R.id.action_openinbrowser:  
                //open the external view in a real browser outside of the app
                handleExternalDeviceActivity(mExternalView.getUrl());
                //close the external view and return to the main app view
                closeExternalView();
                return true; 
            default:            
                return super.onOptionsItemSelected(item);    
        }
    }
}