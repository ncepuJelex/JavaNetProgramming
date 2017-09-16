package com.jel.tech.net.ch10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
/**
 * 使用安全的socket示例
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class HTTPSClient {

	public static void main(String[] args) {

		int port = 443; //default https port
		System.out.println("输入host:");
		Scanner sc = new Scanner(System.in);
		String host = sc.next();
		sc.close();
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try {
			socket = (SSLSocket) factory.createSocket(host, port);
			//enable all the suites
			String[] supported = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supported);

			Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			// https requires the full url in the GET line
			out.write("GET http://" + host + "/ HTTP/1.1\r\n");
			out.write("Host: " + host + "\r\n");
			out.write("\r\n");
			out.flush();

			//读取server response
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s;
			//读取response header
			while(!(s=br.readLine()).equals("")) {
				System.out.println(s);
			}
			//read the length
			String contentLength = br.readLine();
			int len = Integer.MAX_VALUE;
			try {
				len = Integer.parseInt(contentLength, 16);
			} catch (NumberFormatException e) {
				// This server doesn't send the content-length
		        // in the first line of the response body
			}
			System.out.println(len);

			//读取其它body
			int c;
			int i=0;
			while((c=br.read()) != -1 && i++ < len) {
				System.out.write(c);
			}
			System.out.println();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
	/*
	 * running result:
	 * 输入：www.usps.com
	   输出：HTTP/1.1 200 OK
		Cache-Control: max-age=0
		Expires: Sat, 16 Sep 2017 08:50:29 GMT
		Content-Type: text/html
		Date: Sat, 16 Sep 2017 09:30:14 GMT
		Transfer-Encoding:  chunked
		Connection: keep-alive
		Connection: Transfer-Encoding
		24576
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		    <title>Welcome | USPS</title>
		    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		    <!-- PULLING IN EXISTING USPS CSS -->
		    <link href="/assets/css/home/usps.css" rel="stylesheet" type="text/css">
		    <link type="text/css" href="/assets/css/home/customer.css" rel="stylesheet">
		    <link href="/assets/css/home/elements.css" rel="stylesheet" type="text/css">
		    <link rel="stylesheet" type="text/css" href="/assets/css/home/stylesheet.css" />
		    <link rel="stylesheet" href="/assets/css/home/homepage-blades.css" type="text/css" />
		    <link rel="stylesheet" href="/assets/css/home/navigation-sb.css" type="text/css" />
		    <link rel="icon" type="image/x-icon" href="/favicon.ico">
		    <meta name="keywords" content="Quick Tools, Shipping Services, Mailing Services, Village Post Office, Ship Online, Flat Rate, Postal Store, Ship a Package, Send Mail, Manage Your Mail,  Business Solutions, Find Locations, Calculate a Price, Look Up a Zip Code, Track Packages, Print a Label, Stamps">
		    <meta name="description" content="Welcome to USPS.com. Find information on our most convenient and affordable shipping and mailing services. Use our quick tools to find locations, calculate prices, look up a Zip Code, and get Track &amp; Confirm info.">
			<style>

			.mobile-hero-image-text, .hero-image-text {
				margin-bottom: 25px !important;
			}
			.primary--button {
				border: 1px solid #333366;
				background-color: #333366;
				padding: 10px 20px;
				color: #FFFFFF !important;
				font-size: 14px;
				font-family: "HelveticaNeueW02-75Bold", "Helvetica Neue", Helvetica, Arial, sans-serif !important;
				border-radius: 3px;
				display: inline-block;
				max-width: 150px;
				white-space: nowrap;
			}
			@media only screen and (min-width: 959px) {
				.hero-wrapper {
					background-image: url(/assets/images/home/premium/id-bg-hand.jpg) !important;
				}
			.mobile-image-premium {
				display:none;
			}

			span.homepage-left-image {
				display: inline-block;
				margin-right: 0px;
				    margin-top: 0%;
			}

			span.homepage-right-button {
				display: inline-block;
				vertical-align: top;
			}
			.homepage-text-section {
				width: 340px;
				text-align: left;
				display: inline-block;
				vertical-align: top;
				margin-right: 15px;
			}
			.hero-image-text {
				font-family: "HelveticaNeueW02-55Roma", "Helvetica Neue", Helvetica, Arial, sans-serif;
				color: #000000;
				font-size: 16px;
				text-align: left;

			}

			.hero-image-header {
				color: #333366;
				width: 100%;
				margin-bottom: 0px;
				float: left;
				text-transform: initial;
			}
			.homepage-text-section {
				width: 340px;
				text-align: left;
				margin-left: 0 !important;
			}
			.image-content {
				text-align: center;
				width: 100%;
				display: inline-block;
				margin-top: 0;
			}
			}
			@media only screen and (max-width: 958px) {
			.mobile-hero-image-text {
				width: 100%;
			}
			.homepage-hero-image {
				background-image: url(/assets/images/home/premium/id-bg-hand.jpg) !important;
			}


			}
			@media only screen and (max-width: 958px) {
		    .mobile-image-content {
		        margin-left: 50%;
		        display:inline-block;
		    }
		    .mobile-image-premium {
		        position:absolute;
		        bottom:70px;
		        right:55%;
		    }
			.mobile-hero-image-header {
				text-transform: initial !important;
				margin-bottom: 0 !important;
				z-index: 0;
				display: block;
				position: relative;

			}
			.premium-text-link {
				font-size: 18px;
				font-family: "HelveticaNeueW02-75Bold", "Helvetica Neue", Helvetica, Arial, sans-serif !important;
				color:#333366;
			}
			.premium-text-link:hover {
				color: #d8d8d8;
			}
			.mobile-image-premium img {
				display: none;
			}
				.mobile-image-premium {
					width: 50%;
					display: inline-block;
					height:100%;
					position: absolute;
					top: 0;
					left: 0;
				}

				.mobile-image-premium:after {
					content:'';
					display:block;
					position:absolute;
					right: 0;
					background: url('/assets/images/home/premium/id-fg-hand.png') no-repeat;
					background-position: 110%;
					top: 0;
					width: 100%;
					height: 100%;
				}
		}




		@media only screen and (max-width:530px) {
		    .mobile-image-premium {
		        position:absolute;
		        bottom:0px;
		        right:55%;
		    }
				.mobile-hero-image-header {
			}
		    .mobile-hero-image-header {

				margin-top:5px;
		    }
			.mobile-image-content {
		        margin-left: 40%;
		        display:inline-block;
				padding-right:10px;
		    }
		    .mobile-hero-image-text {

				width:100%;
			}
		}

		@media only screen and (max-width:330px) {
		    .mobile-hero-image-header {
		        font-size: 16px;
		        line-height: 18px;
		    }

		    .mobile-hero-image-text {
		        font-size: 12px;
		        line-height: 14px;
				width:100%;
		    }
		}

		@media only screen and (min-width: 500px) and (max-width: 1200px){
		    .homepage-left-image {
		        float:left;
		        margin-right: 0px !important;
		        margin-left: -12%;
		        margin-top: -3%;
		    }
		    .homepage-left-image img{
		        height:300px;
		    }

		    .homepage-text-section {
		        float:right;
		    }
		    .image-content, .homepage-hero-image, .hero-wrapper-link, .hero-wrapper {
		        height: 300px !important;
		        position:relative;
		        overflow:hidden;
		        margin-top: 0;
		    }
		    .homepage-text-section {
		        margin-top: 3%;
		    }
		}
		@media only screen and (min-width: 1200px){

		.homepage-left-image img {
		    height: 350px;
		}
		}
		@media only screen and (min-width:959px){
			.hero-wrapper {
				height:300px;
			}
			.homepage-hero-image {
				overflow: hidden;
				height: 335px;
			}
			span.homepage-left-image {
				height: 370px;
			}
			span.homepage-text-section h1 {
		    font-size: 32px;
		    margin-top: 3%;
			}
			span.homepage-text-section p {
				font-size:18px;
			}
			 homepage-text-section {
		        margin-top: 0;
		    }
		}
		@media only screen and (min-width: 959px) and (max-width: 1200px) {
		    .homepage-left-image img {
		        height: 335px;
		    }
			span.homepage-text-section h1 {
		    font-size: 25px;
		}
		span.homepage-text-section p {
		    font-size:15px;
		}
		}
		@media only screen and (max-width: 1250px) and (min-width: 1200px)
		{
		.homepage-left-image img {
		    height: 325px;
		}

		}
		@media only screen and (max-width: 1060px) and (min-width: 959px)
		{
		.homepage-left-image img {
		    height: 335px;
		}
		.homepage-left-image {
		    margin-left:-25%;
		}

		}


		p.mobile-hero-image-text {
		    font-size: 15px;
		}

		.mobile-hero-image-header {
		    font-size:22px;
		}
		@media only screen and (max-width: 350px){
		.mobile-hero-image-header {
			margin-top:0;
		}
		}

		@media only screen and (max-width: 350px){
		    p.mobile-hero-image-text {
		        font-size: 13px;
		    }
		    .mobile-hero-image-header {
		        margin-top:10px;
		    }
		}
			</style>
		</head>

		<body>

			     <div id="utility-header">
		        <style type="text/css">
		            #utility-bar {
		                display: none;
		            }

				@media only screen and (min-width: 959px) {
				  .alert-bar {
					position: relative !important;
				  }
				}
				.alert-bar p {
				  margin: 0 !important;
				  width: 100% !important;
				  line-height: 20px;
				  max-width: 1400px;
				  text-align: center;
				  font-size: 12px;
				  color: #FFFFFF;
				  font-family: "HelveticaNeueW02-75Bold", "Helvetica Neue", Helvetica, Arial, sans-serif;
					font-weight: normal;
				  padding-top: 5px;
				  padding-bottom: 10px;
				  text-transform: uppercase;
				}

				.alert-bar a {
				  color: white;
				  text-decoration: none;
				  font-size: 12px;
				  color: #FFFFFF;
				  font-family: "HelveticaNeueW02-75Bold", "Helvetica Neue", Helvetica, Arial, sans-serif;
					font-weight: normal;
				  white-space: normal !important;
				}

				.mobile-quicktools .quicktools-full .shortcut.sc-pobox {
					border-left: 0px !important;
				}
				#utility-header a#link-myusps:before {
					background-image: url(/global-elements/header/images/utility-header/mailman.svg) !important;
				}
				@media only screen and (max-width:958px){
					.alert-bar{}
					.menu.active .menu--tier-one-link span:first-of-type {
						width: 80%;
						display: block;
						left: 0;
						position: absolute;
						padding-left: 22px;
						height: 80px;
						top: 0;
						padding-top: 28px;
						box-sizing: border-box;
					}
					.menu.active .menu--tier-one li {
						border-top: 1px solid #333366;
						padding-top: 0 !important;
						padding-bottom:  0 !important;
					}

					.menu.active .menu--tier-one li.ge_parent ol li {
						padding-top:0 !important;
						padding-bottom:0 !important;

					}

					a.menu--tier-one-link.menu--item {
						padding-top: 28px !important;
						display: block;
						padding-bottom: 27px !important;
					}

					.menu ol li ol li a {
						line-height: 20px !important;
						margin-top: 0px !important;
						padding: 20px 22px !important;
					}
					.menu.active ol li.touchscreen-only {
							display: none !important;
						}
				}
		        </style>
		        <div id="skip-content2" style="height:0px;overflow:hidden;">
		            <a href="#maincontent" id="skip-nav2" style="height: 0.1rem;">Skip to Main Content</a>
		        </div>
				<script type="text/javascript" src="https://www.usps.com/ContentTemplates/common/scripts/OneLinkUsps.js"></script>

		        <div class="utility-links">
		            <div id="nav-tool-multilingual-header" class="nav-tool-header" style="display:inline !important;">
		                <a href="#" class="anchor" tabindex="0" name="anchor-login" id="multiling-anchor"
		                 style="display:inline !important;">
		                    English
		                    </a>
		                <div id="lang_select" class="nav-window-header" style="height: 0px; overflow: hidden;">
		                    <div class="content">
		                        <div class="multi-option">
		                            <a href="javascript:OneLink('en');" class="multi-lang-link">English</a>
		                        </div>
		                        <div class="multi-option odd">
		                            <a href="javascript:OneLink('es');" class="multi-lang-link">Espa&ntilde;ol</a>
		                        </div>
		                        <div class="multi-option last">
		                            <a href="javascript:OneLink('zh');" class="multi-lang-link chinese">&nbsp;</a>
		                        </div>
		                    </div>
		                </div>
		            </div>

		            <a id="link-customer" href="https://www.usps.com/help/welcome.htm">Customer &nbsp;Service</a>
		            <a id="link-myusps" href="https://informeddelivery.usps.com">Informed Delivery</a>
		            <a id="login-register-header" class="link-reg" href="https://reg.usps.com/entreg/LoginAction_input?app=Phoenix&amp;appURL=https://www.usps.com/"><span>Register / Sign in</span></a>
		            <div id="link-cart" style="display: inline-block;"></div>
		        </div>
			</div>
		<script>var appID = "Phoenix";</script>


		    <div class="global-navigation">
		<!-- Not necessary for Home
		       <link href="/global-elements/header/css/usps-elements.css" rel="stylesheet" type="text/css" />
			   <link type="text/css" rel="stylesheet" href="/global-elements/header/css/utility-header.css">
		-->

		        <div class="mobile-header mobile-title">
		            <div class="mobile-header search-trigger">
		                <a href="#"></a>
		            </div>
		            <div class="mobile-header usps_logo">
		                <a href="https://www.usps.com/welcome.htm"></a>
		            </div>
		            <div class="mobile-header mobile-menu-trigger">
		                <a href="#"></a>
		            </div>
		        </div>

		        <div id="global-menu" class="menu" style="top: 0px;">

		            <div class="menu-wrap">
		                <div id="msign" class="mobile-utility">
		                    <div class="mobile-sign">
		                        <a href="https://reg.usps.com/entreg/LoginAction_input?app=Phoenix&amp;appURL=">Sign In</a>
		                    </div>
		                </div>
		                <nav class="site-nav">
		                    <ol class="menu--tier-one">
		                        <li class="usps-logo">
		                            <a href="https://www.usps.com/welcome.htm">
		                                <img src="/global-elements/header/images/utility-header/logo-sb.svg">
		                            </a>
		                        </li>
		                        <li class="menu--tier-one-category--quick-tools">
		                            <a href="#" class="quick-tools--trigger menu--tier-one-link menu--item"><span>Quick Tools</span></a>
		                        </li>
		                        <li class="menu--tier-one-category--mail-ship">
		                            <a href="https://www.usps.com/ship/welcome.htm" class="menu--tier-one-link menu--item"><span>Mail &amp; Ship</span><span class="tier_one_chevron">Open/Close Menu</span></a>
		                            <ol class="menu--tier-two">
		                                <li class="touchscreen-only">
		                                    <a href="https://www.usps.com/ship/welcome.htm" class="menu--tier-two-link">Mail &amp; Ship</a>
		                                </li>
		                                <li>
		                                    <a href="https://cns.usps.com/" class="menu--tier-two-link">Click-N-Ship</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/" class="menu--tier-two-link">Order Stamps &amp; Supplies</a>
		                                </li>
		                                <li>
		                                    <a href="https://postcalc.usps.com/" class="menu--tier-two-link">Calculate a Price</a>
		                                </li>
		                                <li>
		                                    <a href="https://tools.usps.com/go/ScheduleAPickupAction!input.action" class="menu--tier-two-link">Schedule a Pickup</a>
		                                </li>
		                                <li>
		                                    <a href="https://tools.usps.com/go/ZipLookupAction_input" class="menu--tier-two-link">Look Up a ZIP Code</a>
		                                </li>
		                                <li>
		                                    <a href="https://tools.usps.com/go/POLocatorAction!input.action" class="menu--tier-two-link">Find USPS Locations</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/ship/welcome.htm" class="menu--tier-two-link">Learn about Mail &amp; Shipping</a>
		                                </li>
		                            </ol>
		                        </li>
		                        <li class="menu--tier-one-category--track-manage">
		                            <a href="https://www.usps.com/manage/welcome.htm" class="menu--tier-one-link menu--item"><span>Track &amp; Manage</span><span class="tier_one_chevron">Open/Close Menu</span></a>
		                            <ol class="menu--tier-two">
		                                <li class="touchscreen-only">
		                                    <a href="https://www.usps.com/manage/welcome.htm" class="menu--tier-two-link">Track &amp; Manage</a>
		                                </li>
		                                <li>
		                                    <a href="https://tools.usps.com/go/TrackConfirmAction_input" class="menu--tier-two-link">Tracking</a>
		                                </li>
		                                <li>
		                                    <a href="https://informeddelivery.usps.com" class="menu--tier-two-link">Informed Delivery</a>
		                                </li>
		                                <li>
		                                    <a href="https://retail-pi.usps.com/retailpi/actions/index.action" class="menu--tier-two-link">Intercept a Package</a>
		                                </li>
		                                <li>
		                                    <a href="https://redelivery.usps.com/redelivery/" class="menu--tier-two-link">Schedule a Redelivery</a>
		                                </li>
		                                <li>
		                                    <a href="https://holdmail.usps.com/holdmail/" class="menu--tier-two-link">Hold Mail</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/manage/forward.htm" class="menu--tier-two-link">Forward Mail</a>
		                                </li>
		                                <li>
		                                    <a href="https://moversguide.usps.com/?referral=MG80" class="menu--tier-two-link">Change of Address</a>
		                                </li>
		                                <li>
		                                    <a href="https://poboxes.usps.com/" class="menu--tier-two-link">Rent or Renew PO Box</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/manage/welcome.htm" class="menu--tier-two-link">Learn about Managing Mail</a>
		                                </li>
		                            </ol>
		                        </li>
		                        <li class="menu--tier-one-category--postal-store">
		                            <a href="https://store.usps.com/store/" class="menu--tier-one-link menu--item"><span>Postal Store</span><span class="tier_one_chevron">Open/Close Menu</span></a>
		                            <ol class="menu--tier-two">
		                                <li class="touchscreen-only">
		                                    <a href="https://store.usps.com/store/" class="menu--tier-two-link">Postal Store</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/browse/category.jsp?categoryId=buy-stamps"
		                                     class="menu--tier-two-link">Stamps</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/browse/category.jsp?categoryId=shipping-supplies"
		                                     class="menu--tier-two-link">Shipping Supplies</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/browse/category.jsp?categoryId=cards-envelopes"
		                                     class="menu--tier-two-link">Cards &amp; Envelopes</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/browse/category.jsp?categoryId=stamp-collectors"
		                                     class="menu--tier-two-link">Collectors</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/pse/" class="menu--tier-two-link">Personalized Stamped Envelopes</a>
		                                </li>
		                                <li>
		                                    <a href="https://store.usps.com/store/browse/category.jsp?categoryId=stamp-gifts"
		                                     class="menu--tier-two-link">Gifts</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/shop/money-orders.htm" class="menu--tier-two-link">Money Orders</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/shop/returns-exchanges.htm" class="menu--tier-two-link">Returns &amp;  Exchanges</a>
		                                </li>
		                            </ol>
		                        </li>
		                        <li class="menu--tier-one-category--business">
		                            <a href="https://www.usps.com/business/welcome.htm" class="menu--tier-one-link menu--item"><span>Business</span><span class="tier_one_chevron">Open/Close Menu</span></a>
		                            <ol class="menu--tier-two">
		                                <li class="touchscreen-only">
		                                    <a href="https://www.usps.com/business/welcome.htm" class="menu--tier-two-link">Business</a>
		                                </li>
		                                <li>
		                                    <a href="https://dbcalc.usps.com/" class="menu--tier-two-link">Calculate a  Business Price</a>
		                                </li>
		                                <li>
		                                    <a href="https://eddm.usps.com/eddm/customer/routeSearch.action" class="menu--tier-two-link">Every Door Direct Mail</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/business/business-shipping.htm" class="menu--tier-two-link">Shipping For Business</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/business/advertise-with-mail.htm" class="menu--tier-two-link">Advertise with Mail</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/business/postage-options.htm" class="menu--tier-two-link">Postage Options</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/business/return-services.htm" class="menu--tier-two-link">Returns Services</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/business/international-shipping.htm" class="menu--tier-two-link">Take Your Business Global</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/business/manage-mail.htm" class="menu--tier-two-link">Manage Business Mail</a>
		                                </li>
		                            </ol>
		                        </li>
		                        <li class="menu--tier-one-category--international">
		                            <a href="https://www.usps.com/international/welcome.htm" class="menu--tier-one-link menu--item"><span>International</span><span class="tier_one_chevron">Open/Close Menu</span></a>
		                            <ol class="menu--tier-two">
		                                <li class="touchscreen-only">
		                                    <a href="https://www.usps.com/international/welcome.htm" class="menu--tier-two-link">International</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/international/international-how-to.htm" class="menu--tier-two-link">Print &amp; Ship International</a>
		                                </li>
		                                <li>
		                                    <a href="https://ircalc.usps.com/" class="menu--tier-two-link">Calculate International Prices</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/international/mail-shipping-services.htm" class="menu--tier-two-link">International Mail Services</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/international/preparing-international-shipments.htm"
		                                     class="menu--tier-two-link">Preparing International Shipments</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/international/customs-forms.htm" class="menu--tier-two-link">Complete Customs Forms</a>
		                                </li>
		                                <li>
		                                    <a href="https://www.usps.com/ship/apo-fpo-dpo.htm?pov=
	 */
}
