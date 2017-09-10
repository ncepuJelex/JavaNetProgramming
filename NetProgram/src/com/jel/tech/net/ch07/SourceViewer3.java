package com.jel.tech.net.ch07;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * 增加了获取 response message
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class SourceViewer3 {

	public static void main(String[] args) {

		System.out.println("输入url:");
		Scanner sc = new Scanner(System.in);
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			int code = conn.getResponseCode();
			String responseMsg = conn.getResponseMessage();
			System.out.println("HTTP/1.x " + code + " " + responseMsg);
			//要从1开始哦，因为0表示path，它对应的key 为 null
			for(int i=1; ; i++) {
				String header = conn.getHeaderField(i);
				String key = conn.getHeaderFieldKey(i);
				if(header == null || key == null) break;
				System.out.println(key + "==>" + header);
			}
			System.out.println();

			try (InputStream in = new BufferedInputStream(conn.getInputStream())) { // chain
				Reader r = new InputStreamReader(in);
				int c;
				while ((c = r.read()) != -1) {
					System.out.print((char) c);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * running result:我竟然得到了这个结果 301，难得啊！哈哈~因为现在变成https协议了，
		 * 你输入https://www.oreilly.com/就不会出现301了
		 * 输入：http://www.oreilly.com
		   输出：HTTP/1.x 301 Moved Permanently
			Server==>Apache
			Location==>https://www.oreilly.com/
			Content-Length==>232
			Content-Type==>text/html; charset=iso-8859-1
			Cache-Control==>max-age=10276
			Expires==>Sun, 10 Sep 2017 12:39:49 GMT
			Date==>Sun, 10 Sep 2017 09:48:33 GMT
			Connection==>keep-alive

			<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
			<html><head>
			<title>301 Moved Permanently</title>
			</head><body>
			<h1>Moved Permanently</h1>
			<p>The document has moved <a href="https://www.oreilly.com/">here</a>.</p>
			</body></html>
		 */

		/*
		 * 输入：https://www.oreilly.com/
		   输出：HTTP/1.x 200 OK
			Server==>Apache
			Last-Modified==>Sun, 10 Sep 2017 08:30:01 GMT
			Content-Type==>text/html; charset=utf-8
			Cache-Control==>max-age=81864
			Expires==>Mon, 11 Sep 2017 08:35:09 GMT
			Date==>Sun, 10 Sep 2017 09:50:45 GMT
			Content-Length==>39802
			Connection==>keep-alive

			<!DOCTYPE html>

			<html lang="en">
			<head>

			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1">




			  	<title>O’Reilly Media - Technology and business training, knowledge, and insight, delivered by experts and innovators</title>
				<meta name="keywords" content="Home,O'Reilly Media" />
				<meta name="description" content="Gain technology and business knowledge and hone your skills with learning resources created and curated by O'Reilly's experts: live online training, video, books, conferences, and the Safari online platform, with content from 200+ of the world’s best publishers." />
				<meta name="date" content="2017-09-10" />
				<meta name="search_date" content="2017-01-31" />
				<meta name="search-title" content="O’Reilly Media - Technology and business training, knowledge, and insight, delivered by experts and innovators" />
				<meta name="pagename" content="O’Reilly Media - Technology and business training, knowledge, and insight, delivered by experts and innovators" />
				<meta name="site" content="O'Reilly" />
			<meta name="target" content="" />
				<meta name="twitter:title" content="O’Reilly Media - Technology and business training, knowledge, and insight, delivered by experts and innovators" />
				<meta name="twitter:description" content="Gain technology and business knowledge and hone your skills with learning resources created and curated by O'Reilly's experts: live online training, video, books, conferences, and the Safari online platform, with content from 200+ of the world’s best publishers." />
				<meta name="twitter:site" content="@OReillyMedia" />
				<meta property="og:title" content="O’Reilly Media - Technology and business training, knowledge, and insight, delivered by experts and innovators" />
				<meta property="og:type" content="article" />
				<meta name="twitter:card" content="summary" />
				<meta name="twitter:image" content="http://cdn.oreillystatic.com/oreilly/images/oreilly-social-icon-120.png" />
				<meta property="og:image" content="http://cdn.oreillystatic.com/oreilly/images/oreilly-social-icon-200.png" />



			<meta name="language_name" content="English"/>
			<meta name="native_language_name" content="English"/>
			<meta name="format-detection" content="telephone=no">

			<link rel="icon" type="image/png" href="//www.oreilly.com/favicon.ico">



			<!--[if lte IE 9]>
			<script>
			  'article aside footer header main nav section time'.replace(/\w+/g,function(n){document.createElement(n)})
			</script>
			<![endif]-->

			<script>
			(function(d) {
			  var config = {
			    kitId: 'gap0qyx',
			    scriptTimeout: 3000,
			    async: true
			  },
			  h=d.documentElement,t=setTimeout(function(){h.className=h.className.replace(/\bwf-loading\b/g,"")+" wf-inactive";},config.scriptTimeout),tk=d.createElement("script"),f=false,s=d.getElementsByTagName("script")[0],a;h.className+=" wf-loading";tk.src='https://fonts.oreillystatic.com/'+config.kitId+'.js';tk.async=true;tk.onload=tk.onreadystatechange=function(){a=this.readyState;if(f||a&&a!="complete"&&a!="loaded")return;f=true;clearTimeout(t);try{Typekit.load(config)}catch(e){}};s.parentNode.insertBefore(tk,s)
			})(document);
			</script>

			<!-- start style sheet section -->

			<link rel="stylesheet" type="text/css" href="//cdn.oreillystatic.com/assets/css/norm-layout-170601.css" />
			<!--[if lte IE 8]><link rel="stylesheet" href="//cdn.oreillystatic.com/assets/css/norm-layout-170313-ie8.css" type="text/css" /><![endif]-->

			  <link rel="stylesheet" type="text/css" href="https://cdn.oreillystatic.com/assets/css/norm-home-170525.css" />
			  <link href="https://www.oreilly.com" rel="canonical" />













			<!--[if IE 9]>
			<style type="text/css">
			  #menu-toggle:checked ~ .mobile-nav {
			    display:block;
			  }
			  .mobile-nav {
			    display: none;
			  }
			</style>
			<![endif]-->

			<!-- END style sheet section -->


			  <script src="//cdn.oreillystatic.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>


			<!-- Start Visual Website Optimizer Asynchronous Code -->
			<script type='text/javascript'>
			var _vwo_code=(function(){
			var account_id=27087,
			settings_tolerance=2000,
			library_tolerance=2500,
			use_existing_jquery=false,
			// DO NOT EDIT BELOW THIS LINE
			f=false,d=document;return{use_existing_jquery:function(){return use_existing_jquery;},library_tolerance:function(){return library_tolerance;},finish:function(){if(!f){f=true;var a=d.getElementById('_vis_opt_path_hides');if(a)a.parentNode.removeChild(a);}},finished:function(){return f;},load:function(a){var b=d.createElement('script');b.src=a;b.type='text/javascript';b.innerText;b.onerror=function(){_vwo_code.finish();};d.getElementsByTagName('head')[0].appendChild(b);},init:function(){settings_timer=setTimeout('_vwo_code.finish()',settings_tolerance);var a=d.createElement('style'),b='body{opacity:0 !important;filter:alpha(opacity=0) !important;background:none !important;}',h=d.getElementsByTagName('head')[0];a.setAttribute('id','_vis_opt_path_hides');a.setAttribute('type','text/css');if(a.styleSheet)a.styleSheet.cssText=b;else a.appendChild(d.createTextNode(b));h.appendChild(a);this.load('//dev.visualwebsiteoptimizer.com/j.php?a='+account_id+'&u='+encodeURIComponent(d.URL)+'&r='+Math.random());return settings_timer;}};}());_vwo_settings_timer=_vwo_code.init();
			</script>
			<!-- End Visual Website Optimizer Asynchronous Code -->

			<!--chartbeat-->
			<script type="text/javascript">var _sf_startpt=(new Date()).getTime()</script>

			<!-- SiteCatalyst tracking -->

			  <script language="JavaScript" type="text/javascript">
			  <!--
			  var s_account="ororeilly,orglobal"
			  //-->
			  </script>

			  <script language="JavaScript" type="text/javascript" src="//cdn.oreillystatic.com/assets/js/s_code.js"></script>
			  <script language="JavaScript" type="text/javascript"><!--
			  s.pageName="oreilly:oreilly:oreilly.com home page"
			  s.channel="oreilly"
			  s.prop1="oreilly:oreilly"
			  s.prop2="oreilly:oreilly"
			  s.prop3="oreilly:oreilly"
			  s.prop4=""
			  s.prop5="oreilly.com home page"
			  s.prop6="n/csp/92"
			  s.prop24="01/31/2017"
			  s.prop21=""
			  s.hier1="oreilly,oreilly"
			  var s_code=s.t();if(s_code)document.write(s_code)//--></script>
			  <!-- End SiteCatalyst code version: H.20.2. -->
			  <!-- analytics.pl: 2017/2/1 11:37:34 -->



			<!--Facebook Insights -->
			<meta property="fb:page_id" content="15137500430" />



			<!-- Google Analytics -->
			<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-4591498-1']);
			_gaq.push(['_setDomainName', 'oreilly.com']);
			_gaq.push(['_addIgnoredRef', 'oreilly.com']);
			_gaq.push(['_setSiteSpeedSampleRate', 50]);
			_gaq.push(['_trackPageview']);

			(function() { var ga = document.createElement('script'); ga.type =
			'text/javascript'; ga.async = true; ga.src = ('https:' ==
			document.location.protocol ? 'https://ssl' : 'http://www') +
			'.google-analytics.com/ga.js'; var s =
			document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s); })();
			</script>

			</head>

			<body class="radar ">


			<!-- Google Tag Manager code required directly after opening body tag -->
			<noscript> {# Google Tag Manager #}
			  <iframe src="https://www.googletagmanager.com/ns.html?id=GTM-TKWK6H"
			          height="0" width="0"
			          style="display:none;visibility:hidden">
			  </iframe>
			</noscript>
			<script>
			  (function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
			  new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
			  j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
			  'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
			  })(window,document,'script','dataLayer','GTM-TKWK6H');
			</script>


			  <div id="skiptocontent"><a href="#maincontent"><span class="skiplink">skip to main content</span></a></div>

			  <header role="banner">
			    <div class="branding-bar pa-bg" role="navigation" aria-label="site sections">
			      <a href="https://www.oreilly.com" class="logo" title="home page" ><img src="//cdn.oreillystatic.com/images/sitewide-headers/oreilly-logo-norm.svg" onerror="this.src='//cdn.oreillystatic.com/images/sitewide-headers/oreilly-logo-reversed-130x23.png'; this.onerror=null;" alt="O'Reilly home"></a>
			      <ul class="top-nav" role="menu">
			        <li role="menuitem"><a href="http://www.oreilly.com/ideas">Ideas</a></li>
			        <li role="menuitem"><a href="https://www.safaribooksonline.com/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170601+nav">Learning Platform</a></li>
			        <li role="menuitem"><a href="http://www.oreilly.com/conferences/">Conferences</a></li>
			        <li role="menuitem"><a href="http://shop.oreilly.com/">Shop</a></li>
			      </ul>
			      <div class="right">
			        <div class="search-nav">
			          <form class="search-form" action="https://ssearch.oreilly.com" method="GET" role="search">
			            <label for="qd"><span class="screen-reader-text">Search terms</span></label>
			            <input class="transparent" name="q" id="qd" type="text" placeholder="Search...">
			            <a class="search-cta">
			              <span class="icon-search icon--white">
			                <svg viewBox="0 0 20 20" id="search" height="24px" width="19px">
			                  <title>Search</title>
			                  <path d="M17.545 15.467l-3.78-3.78a6.15 6.15 0 0 0 .9-3.21c0-3.416-2.962-6.376-6.38-6.376S2.1 4.87 2.1 8.288c0 3.416 2.96 6.377 6.377 6.377 1.137 0 2.2-.31 3.115-.844l3.8 3.8c.37.372.974.372 1.345 0l.943-.942c.37-.37.236-.84-.135-1.21zm-13.54-7.18c0-2.366 1.916-4.283 4.28-4.283S12.76 6.11 12.76 8.478c0 2.365-1.918 4.283-4.283 4.283s-4.473-2.108-4.473-4.473z"/>
			                </svg>
			              </span>
			            </a>
			            <input class="hide" type="submit" value="Submit">
			          </form>
			        </div>

			        <div id="user">
			          <a href="http://members.oreilly.com" class="t-c--d" id="log-out">Your Account</a>
			        </div>
			      </div> <!-- div.right -->

			    </div><!-- div.branding_bar -->
			    <div id="mobile-menu">

			      <input type="checkbox" id="menu-toggle" name="menu-toggle" value="unchecked" />
			      <label for="menu-toggle">
			         <svg viewBox="-449 428 20 20" id="menu" height="34px" width="34px">
			            <title>Open menu</title>
			            <path d="M-431 433.5c0 .3-.2.5-.5.5h-15c-.3 0-.5-.2-.5-.5v-1c0-.3.2-.5.5-.5h15c.3 0 .5.2.5.5v1zM-431 438.5c0 .3-.2.5-.5.5h-15c-.3 0-.5-.2-.5-.5v-1c0-.3.2-.5.5-.5h15c.3 0 .5.2.5.5v1zM-431 443.5c0 .3-.2.5-.5.5h-15c-.3 0-.5-.2-.5-.5v-1c0-.3.2-.5.5-.5h15c.3 0 .5.2.5.5v1z"/>
			          </svg>
			          <svg viewBox="-489 391 20 20" id="close" height="34px" width="34px">
			            <title>Close menu</title>
			            <path d="M-477.6 401l4.6-4.6c.2-.2.2-.5 0-.7l-.7-.7c-.2-.2-.5-.2-.7 0l-4.6 4.6-4.6-4.6c-.2-.2-.5-.2-.7 0l-.7.7c-.2.2-.2.5 0 .7l4.6 4.6-4.6 4.6c-.2.2-.2.5 0 .7l.7.7c.2.2.5.2.7 0l4.6-4.6 4.6 4.6c.2.2.5.2.7 0l.7-.7c.2-.2.2-.5 0-.7l-4.6-4.6z"/>
			          </svg>
			      </label>

			      <div class="mobile-nav" role="navigation" aria-label="site sections">
			        <ul class="mobile-top-nav">
			          <li><a href="http://www.oreilly.com/ideas">Ideas</a></li>
			          <li><a href="https://www.safaribooksonline.com/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170601+nav">Learning Platform</a></li>
			          <li><a href="http://www.oreilly.com/conferences/">Conferences</a></li>
			          <li><a href="http://shop.oreilly.com/">Shop</a></li>
			        </ul>

			        <div class="search-nav">
			          <form class="search-form--m" action="https://ssearch.oreilly.com" method="GET" role="search">
			            <label for="qm"><span class="screen-reader-text">Search terms</span></label>
			            <input class="transparent" name="q" id="qm" type="text" placeholder="Search..." />
			            <a class="search-cta--m" >
			              <span class="icon-search icon--black">
			                <svg viewBox="0 0 20 20" id="search" height="24px" width="19px">
			                  <title>Search</title>
			                  <path d="M17.545 15.467l-3.78-3.78a6.15 6.15 0 0 0 .9-3.21c0-3.416-2.962-6.376-6.38-6.376S2.1 4.87 2.1 8.288c0 3.416 2.96 6.377 6.377 6.377 1.137 0 2.2-.31 3.115-.844l3.8 3.8c.37.372.974.372 1.345 0l.943-.942c.37-.37.236-.84-.135-1.21zm-13.54-7.18c0-2.366 1.916-4.283 4.28-4.283S12.76 6.11 12.76 8.478c0 2.365-1.918 4.283-4.283 4.283s-4.473-2.108-4.473-4.473z"/>
			                </svg>
			              </span>
			            </a>
			            <input class="hide" type="submit" value="Submit" />
			          </form>
			        </div>


			        <div class="mobile-topics" role="navigation" aria-label="site topics">
			            <h3>On Our Radar</h3>

			            <ul class="mobile-topics-list" role="menu">

			              <li role="menuitem" class="flag ai-bg--before">
			                <a href="/topics/ai" >AI</a>
			              </li>
			              <li role="menuitem" class="flag business-bg--before">
			                <a href="/topics/business" >Business</a>
			              </li>
			              <li role="menuitem" class="flag data-bg--before">
			                <a href="/topics/data" >Data</a>
			              </li>
			              <li role="menuitem" class="flag design-bg--before">
			                <a href="/topics/design" >Design</a>
			              </li>
			              <li role="menuitem" class="flag economy-bg--before">
			                <a href="/topics/economy" >Economy</a>
			              </li>
			              <li role="menuitem" class="flag operations-bg--before">
			                <a href="/topics/operations" >Operations</a>
			              </li>
			              <li role="menuitem" class="flag security-bg--before">
			                <a href="/topics/security" >Security</a>
			              </li>
			              <li role="menuitem" class="flag software-engineering-bg--before">
			                <a href="/topics/software-architecture" >Software Architecture</a>
			              </li>
			              <li role="menuitem" class="flag software-engineering-bg--before">
			                <a href="/topics/software-engineering" >Software Engineering</a>
			              </li>
			              <li role="menuitem" class="flag web-programming-bg--before">
			                <a href="/topics/web-programming" >Web Programming</a>
			              </li>
			            </ul>

			          <a href="https://www.oreilly.com/topics" class="mobile-sa">See all</a>
			        </div><!-- div.mobile-topics -->

			      </div><!-- div.mobile-nav -->


			      <div class="topics" role="navigation" aria-label="site topics">
			        <div class="scrollable">
			          <h3>On Our Radar</h3>

			          <ul role="menu" class="topics-list">
			            <li role="menuitem" class="flag ai-bg--before">
			              <a href="/topics/ai" >AI</a>
			            </li>
			            <li role="menuitem" class="flag business-bg--before">
			              <a href="/topics/business" >Business</a>
			            </li>
			            <li role="menuitem" class="flag data-bg--before">
			              <a href="/topics/data" >Data</a>
			            </li>
			            <li role="menuitem" class="flag design-bg--before">
			              <a href="/topics/design" >Design</a>
			            </li>
			            <li role="menuitem" class="flag economy-bg--before">
			              <a href="/topics/economy" >Economy</a>
			            </li>
			            <li role="menuitem" class="flag operations-bg--before">
			              <a href="/topics/operations" >Operations</a>
			            </li>
			            <li role="menuitem" class="flag security-bg--before">
			              <a href="/topics/security" >Security</a>
			            </li>
			            <li role="menuitem" class="flag software-engineering-bg--before">
			              <a href="/topics/software-architecture" >Software Architecture</a>
			            </li>
			            <li role="menuitem" class="flag software-engineering-bg--before">
			              <a href="/topics/software-engineering" >Software Engineering</a>
			            </li>
			            <li role="menuitem" class="flag web-programming-bg--before">
			              <a href="/topics/web-programming" >Web Programming</a>
			            </li>
			          </ul>

			        </div><!-- div.scrollable -->
			        <a href="https://www.oreilly.com/topics" class="sa">See all</a>
			      </div><!-- div.topics -->

			    </div><!-- div.mobile-menu -->
			  </header>

			  <main role="main" id="maincontent">


			<!-- Begin:Body -->

			<style type="text/css">
			#fd_text_this { display:none; }
			#intro.row h1 .subhead { display: none; font-size: 1rem; letter-spacing: normal; color: #444; line-height: 1.4em; margin-top: 5px; }
			#intro.row .button-group.nocaption #safari-sign-in {
			  margin-left: 0;
			}
			#intro.row .button-group.nocaption #safari-free-trial-nocaption  { display: inline-block; margin-left: 15px; font-weight: bold; }
			#intro.row #safari-free-trial-nocaption:hover { text-decoration: underline; }
			#intro.row .button-group.nocaption #safari-sign-in-nocaption  { display: inline-block; font-weight: bold; }
			#intro.row #safari-sign-in-nocaption:hover { text-decoration: underline; }

			@media screen and (min-width:500px) {
			  #intro.row h1 .subhead { font-size:1.125rem; }
			}
			@media screen and (min-width:600px) {
			  #intro.row h1 .subhead { font-size:1.25rem; }
			}
			@media screen and (min-width:680px) {
			  #intro.row h1 .subhead { font-size:1.5rem; }
			}

			.message-test #intro.row h1 .subhead { display: block; animation: fade_in_subhead 3.25s ease; }

			@keyframes fade_in_subhead {
			  0% { opacity: 0; }
			  66% { opacity: 0; }
			  100% { opacity: 1; }
			}

			.message-test #fd_text_safari { display:none; }
			.message-test #fd_text_this { display:inline; }
			.message-test #intro.row .button-group p.caption { display: none; }

			#intro.row .button-group.nocaption { display:none; }
			.message-test #intro.row .button-group.nocaption { display: inline-block; }
			.message-test #intro.row .button-group.withcaption { display: none; }
			</style>


			<script>
			  $(function() {
			    var rHeading = $('#rotating_text');
			    rHeading.css('width', rHeading.children('.rt_item').first().css('width'));
			    $('#fading_text').addClass('active');

			    function startRText() {
			      rHeading.children('.rt_item').first().addClass('active1');
			      window.setTimeout(updateRText, 3000);
			    }

			    function updateRText() {
			      rHeading.find('.rt_item.not_active').removeClass('not_active');

			      var curRText = rHeading.find('.rt_item.active1, .rt_item.active2');
			      var nextRText = curRText.next('.rt_item');

			      if (!nextRText.length) {
			        //return false;
			        nextRText = rHeading.children('.rt_item').first();
			      }

			      curRText.removeClass('active1 active2').addClass('not_active');
			      nextRText.addClass('active2');
			      rHeading.css('width', rHeading.find('.active2').css('width'));

			      //End loop on second time first text is shown
			      if (!rHeading.children('.rt_item').first().hasClass('active2')) {
			        window.setTimeout(updateRText, 3000);
			      }
			      return rHeading;
			    }
			    function setHeadingWidth() {
			      rHeading.css('width', rHeading.find('.rt_item.active1, .rt_item.active2').css('width'));
			      return true;
			    }

			    window.setTimeout(startRText, 2000);
			    window.addEventListener('resize', setHeadingWidth);
			  });
			</script>

			<div class="new-home-main how-you-learn" >
			  <section class="row" id="intro" role="region">

			    <div class="pane-inset">
			      <div class="main-copy">
			        <h1>
			          <span id="fading_text" class="fd_text"><span class="fd_text_item"><span id="fd_text_safari">Safari</span><span id="fd_text_this">This</span></span> <span class="fd_text_item">is</span> <span class="fd_text_item">how</span> <span class="fd_text_item">you</span></span>

			          <span id="rotating_text" class="rt_list">
			            <span class="rt_item">learn.</span>
			            <span class="rt_item">transform.</span>
			            <span class="rt_item">future-proof.</span>
			          </span>

			          <span class="subhead">O&rsquo;Reilly Safari <span class="nowrap">Learning Platform</span></span>
			        </h1>

			        <div id="devices-wrapper"><div id="devices"></div></div>
			      </div>


			      <div class="button-group withcaption">
			        <p class="caption">Safari, O&rsquo;Reilly&rsquo;s technology and business <span class="nowrap">learning platform.</span></p>
			        <a id="get-started-now" href="https://www.safaribooksonline.com/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170505+homepage+get+started+now"><div class="cta_button"><span class="screen-reader-text">Safari: </span>Get started now</div></a>

			        <p>Already a Safari member? <a id="safari-sign-in" href="https://www.safaribooksonline.com/accounts/login/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170203+homepage+sign+in">Sign in.</a></p>
			      </div>

			      <div class="button-group nocaption">
			        <a id="get-started-now-nocaption" href="https://www.safaribooksonline.com/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170710+homepage+get+started+now"><div class="cta_button"><span class="screen-reader-text">Safari: </span>Find out more</div></a>

			        <p>Ready to learn? <a id="safari-free-trial-nocaption" href="https://www.safaribooksonline.com/public/free-trial/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170710+homepage+start+free+trial">Start a free trial</a> or <a id="safari-sign-in-nocaption" href="https://www.safaribooksonline.com/accounts/login/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170710+homepage+sign+in">Sign in.</a></p>
			      </div>

			    </div>

			  </section>


			  <section class="row" id="paths" role="region">
			    <div class="pane-inset">
			      <div class="channels">
			        <div class="take-class">
			          <h2><a href="https://www.safaribooksonline.com/live-training/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170201+homepage+take+a+live+online+course">Take a live online course</a></h2>
			          <p>Our live, instructor-led online courses get you up to speed quickly on whatever you need to know.</p>
			        </div>

			        <div class="follow-paths">
			          <h2><a href="https://www.safaribooksonline.com/learning-paths/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170201+homepage+follow+a+path">Follow a path</a></h2>
			          <p>Expert-curated learning paths help you master specific topics with text, video, audio, and interactive coding tutorials.</p>
			        </div>

			        <div class="explore">
			          <h2><a href="https://www.safaribooksonline.com/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170505+homepage+unlimited+access">Get unlimited access to</a></h2>
			          <ul>
			            <li>Unique interactive tutorials</li>
			            <li>O&rsquo;Reilly conference recordings</li>
			            <li>Books and video training from 200+ publishers</li>
			            <li>Early access to content as it&rsquo;s being created</li>
			            <li>Transformational playlists</li>
			            <li>Much more</li>
			          </ul>
			        </div>
			      </div>

			    </div>
			  </section>




			  <section class="row dark" id="training" role="region">

			    <div class="pane-inset">
			      <div class="main-ideas">

			        <div class="main-copy">
			          <h1>Learn faster.</h1>

			          <div class="rotating-images">
			            <div class="rotation-container current">
			              <img src="https://cdn.oreillystatic.com/oreilly/home/assets/deeper01.jpg" alt="Training attendees"/>
			            </div>

			            <div class="rotation-container">
			              <img src="https://cdn.oreillystatic.com/oreilly/home/assets/deeper02.jpg" alt="Focused attendee"/>
			            </div>

			            <div class="rotation-container">
			              <img src="https://cdn.oreillystatic.com/oreilly/home/assets/deeper03.jpg" alt="Training attendees"/>
			            </div>
			          </div>

			          <div class="rotating-text">
			            <div class="rotation-container current">
			              <h2>Learn directly from expert practitioners at <span class="nowrap">in-person</span> training courses.</h2>

			              <div class="button-group">
			                <div>
			                  <a href="http://www.oreilly.com/live-training/?view=grid"><div class="cta_button">Learn more<span class="screen-reader-text"> about O'Reilly live training</span></div></a>
			                </div>
			              </div>
			            </div>

			            <div class="rotation-container">
			              <h2>With Safari, quickly find the answers you need, or drill down to learn more.</h2>

			              <div class="button-group">
			                <div>
			                  <a href="https://www.safaribooksonline.com/your-experience/?utm_medium=content&utm_source=oreilly.com&utm_campaign=lgen&utm_content=20170201+homepage+safari+platform"><div class="cta_button">Learn more<span class="screen-reader-text"> about Safari</span></div></a>
			                </div>
			              </div>
			            </div>

			            <div class="rotation-container">
			              <h2>Connect with people and build your own expert network at O&rsquo;Reilly events.</h2>

			              <div class="button-group">
			                <div>
			                  <a href="http://www.oreilly.com/conferences/"><div class="cta_button">Learn more<span class="screen-reader-text"> about O'Reilly conferences</span></div></a>
			                </div>
			              </div>
			            </div>
			          </div>

			        </div>

			      </div>

			      <div class="rotation-indicator">
			        <div class="circle current"></div>
			        <div class="circle"></div>
			        <div class="circle"></div>
			      </div>
			    </div>
			  </section>


			  <section class="row dark" id="reports" role="region">
			    <div class="pane-inset">
			      <div>

			        <div class="left-text">
			          <h2>Today, transformation is business <span class="nowrap">as usual</span>.</h2>

			          <p>Are you doing all you can to meet the challenge?</p>
			          <p><a href="https://cdn.oreillystatic.com/pdf/oreilly_high_performance_organizations_whitepaper.pdf" onclick="var s=s_gi(s_account); s.linkTrackVars='eVar23,eVar52,eVar53,events'; s.linkTrackEvents='event5,event42'; s.events='event5,event42'; s.eVar23='orm-home-successful-transformation-whitepaper'; s.eVar52='pdf'; s.eVar53='successful transformation whitepaper'; s.tl(this,'d','download-pdf-successful transformation whitepaper');">Download “High Performance Organizations” white paper</a></p>
			        </div>

			        <div class="cover-overlay">
			          <div class="cover"><a href="https://cdn.oreillystatic.com/pdf/oreilly_high_performance_organizations_whitepaper.pdf" onclick="var s=s_gi(s_account); s.linkTrackVars='eVar23,eVar52,eVar53,events'; s.linkTrackEvents='event5,event42'; s.events='event5,event42'; s.eVar23='orm-home-successful-transformation-whitepaper'; s.eVar52='pdf'; s.eVar53='successful transformation whitepaper'; s.tl(this,'d','download-pdf-successful transformation whitepaper');"><img src="https://cdn.oreillystatic.com/oreilly/home/assets/cover1.jpg" alt="High Performance Organizations cover" /></a></div>
			        </div>

			      </div>
			    </div>
			  </section>

			  <section class="row dark" id="events" role="region">
			    <div class="pane-inset">
			      <div class="main-ideas">
			        <div class="main-copy">
			          <h1>See farther.</h1>

			          <div class="rotating-images">
			            <div class="rotation-container current">
			              <img src="https://cdn.oreillystatic.com/oreilly/home/assets/farther1.jpg" alt="Cory Doctorow keynote"/>
			              <p><strong>Cory Doctorow</strong> keynoting at OSCON Austin 2016</p>
			            </div>

			            <div class="rotation-container">
			              <img src="https://cdn.oreillystatic.com/oreilly/home/assets/farther3.jpg" alt="On our radar"/>
			              <p><a href="https://www.oreilly.com/ideas/8-data-trends-on-our-radar-for-2017?utm_medium=referral&utm_source=oreilly.com&utm_campaign=lgen&utm_content=link+2017+trends">What’s on our radar: 8 data trends for 2017</a></p>
			            </div>
			          </div>

			          <div class="rotating-text">
			            <div class="rotation-container current">
			              <h2>Find out what’s new and what’s next at O’Reilly events.</h2>

			              <div class="button-group">
			                <div>
			                  <a href="http://www.oreilly.com/conferences/"><div class="cta_button">See upcoming events</div></a>
			                </div>
			              </div>
			            </div>

			            <div class="rotation-container">
			              <h2>Learn about emerging technologies and trends at oreilly.com/ideas.</h2>

			              <div class="button-group">
			                <div>
			                  <a href="https://www.oreilly.com/ideas?utm_medium=referral&utm_source=oreilly.com&utm_campaign=lgen&utm_content=link+read+latest+articles"><div class="cta_button">Read the latest articles</div></a>
			                </div>
			              </div>
			            </div>

			          </div>
			        </div>
			      </div>

			      <div class="rotation-indicator">
			        <div class="circle current"></div>
			        <div class="circle"></div>
			      </div>
			    </div>
			  </section>

			</div>

			<!--end body-->

			<!-- load script to detect mobile touch events -->
			<!-- used for carousel swiping -->
			<script src="https://cdn.oreillystatic.com/assets/js/jquery.mobile.touch_only.min.js"></script>
			<script src="https://cdn.oreillystatic.com/assets/js/norm-home-170224.js"></script>



			  </main>

			  <footer role="contentinfo">

			    <div class="footer-top">
			      <div class="footer-inner">

			        <div class="about-us" role="navigation" aria-label="company info">
			          <h3 class="title">About Us</h3>
			          <ul role="menu">
			            <li role="menuitem"><a href="http://www.oreilly.com/about/">Our Company</a></li>
			            <li role="menuitem"><a href="http://www.oreilly.com/work-with-us.html">Teach/Speak/Write</a></li>
			            <li role="menuitem"><a href="http://www.oreilly.com/careers/"> Careers </a></li>
			            <li role="menuitem"><a href="http://shop.oreilly.com/category/customer-service.do">Customer Service</a></li>
			            <li role="menuitem"><a href="http://www.oreilly.com/about/contact.html">Contact Us</a></li>
			          </ul>
			        </div>

			        <div class="social" role="navigation" aria-label="social media links">
			          <ul role="menu">
			            <li role="menuitem">
			              <a class="icon-link" href="http://twitter.com/oreillymedia">
			                <span class="icon icon-twitter t-fill icon--white">
			                  <svg id="twitter" xmlns="http://www.w3.org/2000/svg" width="24.61" height="20" viewBox="0 0 24.61 20"><title>Twitter</title><path d="M22.09,5c0,.22,0,.44,0,.65A14.27,14.27,0,0,1,7.74,20,14.28,14.28,0,0,1,0,17.73a10.54,10.54,0,0,0,1.2.07,10.13,10.13,0,0,0,6.27-2.16,5.06,5.06,0,0,1-4.72-3.5A5.16,5.16,0,0,0,5,12,5.06,5.06,0,0,1,1,7.1V7a5,5,0,0,0,2.29.63A5,5,0,0,1,1.71.92,14.33,14.33,0,0,0,12.12,6.2,5.1,5.1,0,0,1,12,5a5,5,0,0,1,5-5h0a5,5,0,0,1,3.69,1.59A10.17,10.17,0,0,0,23.93.37a5.07,5.07,0,0,1-2.22,2.79,10.1,10.1,0,0,0,2.9-.79A10.2,10.2,0,0,1,22.09,5Z"/></svg>
			                </span>
			              </a>
			            </li><li role="menuitem">
			              <a class="icon-link" href="http://fb.co/OReilly">
			                <span class="icon icon-facebook t-fill icon--white">
			                  <svg id="facebook" xmlns="http://www.w3.org/2000/svg" width="11.11" height="20" viewBox="0 0 11.11 20"><title>Facebook</title><path d="M11.11,3.48H7.94c-.38,0-.79.49-.79,1.15V6.92h4l-.6,3.27H7.14V20H3.4V10.19H0V6.92H3.4V5A4.71,4.71,0,0,1,7.94,0h3.17V3.48Z"/></svg>
			                </span>
			              </a>
			            </li><li role="menuitem">
			              <a class="icon-link" href="https://www.linkedin.com/company/oreilly-media">
			                <span class="icon icon-linkedin t-fill icon--white">
			                  <svg id="linkedin" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20"><title>LinkedIn</title><path d="M4.46,2.29A2.07,2.07,0,0,1,2.29,4.46,2.07,2.07,0,0,1,.13,2.29,2.26,2.26,0,0,1,2.29,0,2.26,2.26,0,0,1,4.46,2.29ZM0,20V5.61H4.46V20ZM6.5,6.11h3.95L10.7,8h.13A4.79,4.79,0,0,1,15,5.86c2.8,0,5,1.91,5,5.86V20H15.54V12.36c0-1.78-.64-3.06-2.17-3.06a2.18,2.18,0,0,0-2,1.78,2.48,2.48,0,0,0-.13,1V20H6.75V10.45S6.62,7.26,6.5,6.11Z"/></svg>
			                </span>
			              </a>
			            </li><li role="menuitem">
			              <a class="icon-link" href="https://www.youtube.com/user/OreillyMedia">
			                <span class="icon icon-youtube-large t-fill icon--white">
			                  <svg id="youtube-large" xmlns="http://www.w3.org/2000/svg" width="47.87" height="20" viewBox="0 0 47.87 20"><title>YouTube</title><path d="M16.06,13.89c-.4.54-.69.86-1.06.86s-.34-.09-.4-.37V6.51H12.8v7.66a3.92,3.92,0,0,0,.2,1.4,1.09,1.09,0,0,0,1.14.71c.63,0,1.2-.4,1.91-1.17v1.06h1.83V6.51H16.06v7.37ZM8.91,6.46a2.27,2.27,0,0,0-2,.94,3.88,3.88,0,0,0-.57,2.31v3.43a3.51,3.51,0,0,0,.54,2.2,2.56,2.56,0,0,0,2,1,2.66,2.66,0,0,0,2.06-1,3.51,3.51,0,0,0,.54-2.2V9.66a3.88,3.88,0,0,0-.57-2.31,2.57,2.57,0,0,0-2-.89Zm0,8.31c-.57,0-.86-.49-.77-1.43V9.49c-.09-1,.2-1.4.77-1.4s.86.46.77,1.4v3.83C9.74,14.29,9.46,14.77,8.91,14.77ZM4.69,3.29l-1.31,5L2,3.29H0c.4,1.2,2.4,7.66,2.4,7.66v5.29H4.31V10.94l2.4-7.66ZM36.57,8.06a1.41,1.41,0,0,0-.91.43v5.8a1.24,1.24,0,0,0,.91.43c.49,0,.71-.37.71-1.34V9.31c0-1-.2-1.26-.71-1.26ZM43.71,9.2c0-.89-.23-1.14-.8-1.14s-.83.23-.83,1.14v1.06h1.63Zm3.91-4.89a6.35,6.35,0,0,0-1.11-2.83A4.14,4.14,0,0,0,43.66.29c-4-.29-10-.29-10-.29s-6,0-10,.29a4,4,0,0,0-2.86,1.2,6,6,0,0,0-1.11,2.83,43.12,43.12,0,0,0-.29,4.6v2.14a42.42,42.42,0,0,0,.29,4.6,6.35,6.35,0,0,0,1.11,2.83,4.9,4.9,0,0,0,3.14,1.2c2.23.23,9.66.31,9.66.31s6,0,10-.31a4,4,0,0,0,2.86-1.2,6,6,0,0,0,1.11-2.83,43.12,43.12,0,0,0,.29-4.6V8.91A30.34,30.34,0,0,0,47.63,4.31Zm-22,11.86H23.71v-11h-2V3.34h6V5.17H25.66v11Zm6.89,0H30.69V15.11a2.64,2.64,0,0,1-1.91,1.17,1.21,1.21,0,0,1-1.14-.71,3.92,3.92,0,0,1-.2-1.4V6.6h1.83v7.74c0,.29.14.37.4.37s.66-.31,1.06-.86V6.6h1.83v9.57h0Zm6.6-2.86a7.6,7.6,0,0,1-.17,1.91,1.44,1.44,0,0,1-1.43,1.09,2.53,2.53,0,0,1-1.83-1.09v1H34V3.37h1.71V7.54a2.43,2.43,0,0,1,1.83-1.09,1.45,1.45,0,0,1,1.4,1.11,6.87,6.87,0,0,1,.23,1.91v3.83h0Zm3.83,1.4c.43,0,.66-.23.8-.69,0-.09,0-.51,0-1.2h1.83v.29c0,.54-.06.94-.06,1.11a2.16,2.16,0,0,1-.4,1,2.39,2.39,0,0,1-2.09,1,2.49,2.49,0,0,1-2.09-1,3.49,3.49,0,0,1-.6-2.2V9.8a3.88,3.88,0,0,1,.57-2.31,2.46,2.46,0,0,1,2.06-1,2.37,2.37,0,0,1,2,1,3.65,3.65,0,0,1,.57,2.23v2H42.11V13.4c0,.91.23,1.31.83,1.31Z"/></svg>
			                </span>
			              </a>
			            </li><li role="menuitem">
			              <a class="icon-link" href="http://www.oreilly.com/emails/newsletters/">
			                <span class="icon icon-google t-fill icon--white">
			                  <svg id="email" xmlns="http://www.w3.org/2000/svg" width="25" height="20" viewBox="0 0 25 20"><title>Newsletters</title><path d="M22.5,0H2.5A2.5,2.5,0,0,0,0,2.5v15A2.51,2.51,0,0,0,2.5,20h20A2.51,2.51,0,0,0,25,17.5V2.5A2.51,2.51,0,0,0,22.5,0Zm0,5-10,6.25L2.5,5V2.5l10,6.25,10-6.25Z"/></svg>
			                </span>
			              </a>
			            </li>
			          </ul>
			        </div>
			      </div>
			    </div>

			    <div class="footer-bottom">
			      <div class="legal">
			        <a href="https://www.oreilly.com" class="logo pa-bg" title="O'Reilly home page" ><img src="//cdn.oreillystatic.com/images/sitewide-headers/oreilly-logo-norm.svg" onerror="this.src='//cdn.oreillystatic.com/images/sitewide-headers/oreilly-logo-reversed-130x23.png'; this.onerror=null;" alt="O'Reilly home"></a>
			        <p class="copyright">&copy; 2017 O&rsquo;Reilly Media, Inc. All trademarks and registered trademarks appearing on oreilly.com are the property of their respective owners.</p><br />
			        <p class="t-c pa-text"><a href="http://www.oreilly.com/terms/">Terms of Service</a> <span class="bullet">&bull;</span> <a href="http://www.oreilly.com/privacy.html">Privacy Policy</a> <span class="bullet">&bull;</span> <a href="http://www.oreilly.com/about/editorial_independence.html">Editorial Independence</a></p>
			      </div>

			      <figure class="animal">
			        <img class="tarsier" src="//cdn.oreillystatic.com/images/sitewide-headers/tarsier-footer.png" alt="Tarsier">
			      </figure>
			    </div>

			  </footer>

			<!-- search bar - desktop behavior -->
			<script src="//cdn.oreillystatic.com/assets/js/norm-search-bar-170221.js" type="text/javascript"></script>



			<div style="height: 0 !important; width: 0 !important; line-height: 0 !important; font-size: 0 !important; margin-top: -13px; float: left;">





			<script type="text/javascript">
			    adroll_adv_id = "ZMY7MBBFIFDUBAVNOE7XUJ";
			    adroll_pix_id = "JPUOHWQVQZBGBBTRCVCUVP";
			    (function () {
			        var _onload = function(){
			            if (document.readyState && !/loaded|complete/.test(document.readyState)){setTimeout(_onload, 10);return}
			            if (!window.__adroll_loaded){__adroll_loaded=true;setTimeout(_onload, 50);return}
			            var scr = document.createElement("script");
			            var host = (("https:" == document.location.protocol) ? "https://s.adroll.com" : "http://a.adroll.com");
			            scr.setAttribute('async', 'true');
			            scr.type = "text/javascript";
			            scr.src = host + "/j/roundtrip.js";
			            ((document.getElementsByTagName('head') || [null])[0] ||
			                document.getElementsByTagName('script')[0].parentNode).appendChild(scr);
			        };
			        if (window.addEventListener) {window.addEventListener('load', _onload, false);}
			        else {window.attachEvent('onload', _onload)}
			    }());
			</script>



			</div>



			<script type='text/javascript'>
			    var _sf_async_config = _sf_async_config || {};
			    _sf_async_config.uid = 1632;
			    _sf_async_config.domain = 'oreilly.com';
			    _sf_async_config.useCanonical = true;
			    (function() {
			        function loadChartbeat() {
			            var e = document.createElement('script');
			            e.setAttribute('language', 'javascript');
			            e.setAttribute('type', 'text/javascript');
			            e.setAttribute('src', '//static.chartbeat.com/js/chartbeat.js');
			            document.body.appendChild(e);
			        }
			        var oldonload = window.onload;
			        window.onload = (typeof window.onload != 'function') ?
			            loadChartbeat : function() {
			                oldonload();
			                loadChartbeat();
			            };
			    })();
			</script>

			<!-- Crazyegg async script -->
			<script type="text/javascript">
			setTimeout(function(){var a=document.createElement("script");
			var b=document.getElementsByTagName("script")[0];
			a.src=document.location.protocol+"//script.crazyegg.com/pages/scripts/0011/6381.js?"+Math.floor(new Date().getTime()/3600000);
			a.async=true;a.type="text/javascript";b.parentNode.insertBefore(a,b)}, 1);
			</script></body>


			</html>
		 */
	}
}
