package org.netention.curiosume;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TagActivity extends Activity {

	public final int TIMEOUT_MS = 15 * 1000;
	private WebView webview;
	Parser p = Parser.htmlParser();
	
	public void loadWikiPage(String urlOrTag) {
		String url = urlOrTag;
		try {
			Document d = Jsoup.parse(new URL(url), TIMEOUT_MS);
			d.select(".header").remove();
			d.select("#page-actions").remove();
			d.select("#contentSub").remove();
			d.select("#jump-to-nav").remove();
			d.select(".IPA").remove();
			d.select("script").remove();
			
			
			Elements links = d.select("a");
			for (Element e : links) {
				String href = e.attr("href");
				if (href.startsWith("/wiki")) {
					e.attributes().put("href", "#");
					//e.after("<button>+</button>");
					e.after("XXXX");
				}
			}
			
			/*
			 * 
			 *                if (search) {
                    currentTag = $('.WIKIPAGEREDIRECTOR').html();
               }
               
               br.find('#top').remove();
               br.find('#siteSub').remove();
               br.find('#contentSub').remove();
               br.find('#jump-to-nav').remove();
               br.find('.IPA').remove();
               br.find('script').remove();
               
               br.find('a').each(function(){
                   var t = $(this);
                   var h = t.attr('href');
                   t.attr('href', '#');
                   if (h) {
                    if (h.indexOf('/wiki') == 0) {
                        var target = h.substring(6);
                        
                        t.click(function() {
                             gotoTag(target); 
                        });
                         
                        if ((target.indexOf('Portal:')!=0) && (target.indexOf('Special:')!=0)) {
                            t.after(newPopupButton(target));
                        }
                    }
                   }
               });
               var lt = newPopupButton(currentTag);
               
               if (currentTag.indexOf('Portal:')!=0)
                    br.find('#firstHeading').append(lt);

			 */
			
			webview.loadDataWithBaseURL(url, d.html(), "text/html", null, null);
			//System.err.println(d.html());
		} catch (Exception e) {
			//System.err.println(e);
		}
		
		//webview.loadUrl("http://en.wikipedia.org");				
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		
		//http://developer.android.com/guide/webapps/webview.html
		
		webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new WebViewClient());
		
		WebSettings webSettings = webview.getSettings();
		//webSettings.setJavaScriptEnabled(true);
	
		loadWikiPage("http://en.m.wikipedia.org/wiki/God");		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
		return true;
	}

}
