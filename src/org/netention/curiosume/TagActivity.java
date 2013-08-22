package org.netention.curiosume;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class TagActivity extends Activity {

	public final int TIMEOUT_MS = 15 * 1000;
	private WebView webview;
	final Parser p = Parser.htmlParser();

	protected class MyWebViewClient extends WebViewClient {
		
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			/*
			 * if (Uri.parse(url).getHost().equals("www.example.com")) { // This
			 * is my web site, so do not override; let my WebView load the page
			 * return false; } // Otherwise, the link is not for a page on my
			 * site, so launch another Activity that handles URLs Intent intent
			 * = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			 * startActivity(intent); return true;
			 */

			final String originalURL = url;
			String urlPrefix = "http://en.m.wikipedia.org/";
			if (url.startsWith(urlPrefix)) {
				url = url.substring(urlPrefix.length());
				final String turl = urlPrefix + "wiki/" + url;
				
				AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this);
			    builder.setTitle(url);
			    CharSequence[] items = new CharSequence[8];
			    items[0] = "Go...";
			    items[1] = "Learner";
			    items[2] = "Learner Collaborator";
			    items[3] = "Collaborator Learner";
			    items[4] = "Collaborator Teacher";
			    items[5] = "Teacher Collaborator";
			    items[6] = "Teacher";
			    items[7] = "Cancel";
			    
			    
			    builder.setItems(items, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   if (which == 0) {
			        		   runOnUiThread(new Runnable() {
			        			  @Override public void run() {
					        		  loadWikiPage(turl);			        				
			        			  } 
			        		   });
			        	   }
			        	   else if (which < 7) {
			        		   Toast.makeText(TagActivity.this, "Tagged.", Toast.LENGTH_SHORT).show();
			        	   }
			        	   
		        		   dialog.dismiss();
			           }
			    });

				AlertDialog dialog = builder.create();
				dialog.show();

				return true;
			}

			return false;

		}
	}

	public void loadWikiPage(String urlOrTag) {
		webview.loadData("Loading...", "text/html", null);
		
		String url = urlOrTag;
		
		String[] sects = url.split("/");
		String tag = sects[sects.length-1];
		
		try {
			Document d = Jsoup.parse(new URL(url), TIMEOUT_MS);

			d.select("head").after(
					"<style>.crb { border: 1px solid gray; }</style>");

			d.select(".header").remove();
			d.select("#page-actions").remove();
			//d.select("#contentSub").remove();
			d.select("#jump-to-nav").remove();
			//d.select(".IPA").remove();
			//d.select("script").remove();

			Elements links = d.select("a");
			for (Element e : links) {
				String href = e.attr("href");
				if (href.startsWith("/wiki")) {
					String target = href.substring(5);
					e.attributes().put("href", target);
					e.attributes().put("class", "crb");
				}
			}
			Elements headings = d.select("#section_0");
			for (Element e : headings) {
				e.html("<a href='/" + tag + "' class='crb'>" + e.text() + "</a>");
			}

			webview.loadDataWithBaseURL(url, d.html(), "text/html", null, null);
			// System.err.println(d.html());
		} catch (Exception e) {
			System.err.println(e);
		}

		// webview.loadUrl("http://en.wikipedia.org");
	}

	@Override
	protected void onStart() {
		super.onStart();
		   runOnUiThread(new Runnable() {
			  @Override public void run() {
				  loadWikiPage("http://en.m.wikipedia.org/wiki/Human");
			  }
		   });
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);

		// http://developer.android.com/guide/webapps/webview.html

		webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new MyWebViewClient());

		//WebSettings webSettings = webview.getSettings();
		// webSettings.setJavaScriptEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
		return true;
	}

}
