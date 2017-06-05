<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=Generator content="Microsoft Word 12 (filtered)">
<title>后向流量平台接口文档V1.1</title>
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:Helvetica;
	panose-1:2 11 5 4 2 2 2 2 2 4;}
@font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;}
@font-face
	{font-family:微软雅黑;
	panose-1:2 11 5 3 2 2 4 2 2 4;}
@font-face
	{font-family:"Calibri Light";}
@font-face
	{font-family:Meiryo;
	panose-1:2 11 6 4 3 5 4 4 2 4;}
@font-face
	{font-family:"Source Code Pro";}
@font-face
	{font-family:Monaco;}
@font-face
	{font-family:"Helvetica Neue";}
@font-face
	{font-family:Consolas;
	panose-1:2 11 6 9 2 2 4 3 2 4;}
@font-face
	{font-family:"\@Meiryo";
	panose-1:2 11 6 4 3 5 4 4 2 4;}
@font-face
	{font-family:"\@微软雅黑";
	panose-1:2 11 5 3 2 2 4 2 2 4;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";}
h1
	{mso-style-link:"标题 1 Char";
	margin-top:17.0pt;
	margin-right:0cm;
	margin-bottom:16.5pt;
	margin-left:21.6pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-21.6pt;
	line-height:240%;
	page-break-after:avoid;
	font-size:22.0pt;
	font-family:"Calibri","sans-serif";}
h2
	{mso-style-link:"标题 2 Char";
	margin-top:13.0pt;
	margin-right:0cm;
	margin-bottom:13.0pt;
	margin-left:28.8pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-28.8pt;
	line-height:173%;
	page-break-after:avoid;
	font-size:16.0pt;
	font-family:"Calibri Light";}
h3
	{mso-style-link:"标题 3 Char";
	margin-top:13.0pt;
	margin-right:0cm;
	margin-bottom:13.0pt;
	margin-left:36.0pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-36.0pt;
	line-height:173%;
	page-break-after:avoid;
	font-size:16.0pt;
	font-family:"Calibri","sans-serif";}
h4
	{mso-style-link:"标题 4 Char";
	margin-top:14.0pt;
	margin-right:0cm;
	margin-bottom:14.5pt;
	margin-left:43.2pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-43.2pt;
	line-height:156%;
	page-break-after:avoid;
	font-size:14.0pt;
	font-family:"Cambria","serif";}
h5
	{mso-style-link:"标题 5 Char";
	margin-top:14.0pt;
	margin-right:0cm;
	margin-bottom:14.5pt;
	margin-left:50.4pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-50.4pt;
	line-height:156%;
	page-break-after:avoid;
	font-size:14.0pt;
	font-family:"Calibri","sans-serif";}
h6
	{mso-style-link:"标题 6 Char";
	margin-top:12.0pt;
	margin-right:0cm;
	margin-bottom:3.2pt;
	margin-left:57.6pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-57.6pt;
	line-height:133%;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Cambria","serif";}
p.MsoHeading7, li.MsoHeading7, div.MsoHeading7
	{mso-style-link:"标题 7 Char";
	margin-top:12.0pt;
	margin-right:0cm;
	margin-bottom:3.2pt;
	margin-left:64.8pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-64.8pt;
	line-height:133%;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoHeading8, li.MsoHeading8, div.MsoHeading8
	{mso-style-link:"标题 8 Char";
	margin-top:12.0pt;
	margin-right:0cm;
	margin-bottom:3.2pt;
	margin-left:72.0pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-72.0pt;
	line-height:133%;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Cambria","serif";}
p.MsoHeading9, li.MsoHeading9, div.MsoHeading9
	{mso-style-link:"标题 9 Char";
	margin-top:12.0pt;
	margin-right:0cm;
	margin-bottom:3.2pt;
	margin-left:79.2pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:-79.2pt;
	line-height:133%;
	page-break-after:avoid;
	font-size:10.5pt;
	font-family:"Cambria","serif";}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-link:"页眉 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	layout-grid-mode:char;
	border:none;
	padding:0cm;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-link:"页脚 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	layout-grid-mode:char;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
a:link, span.MsoHyperlink
	{color:#0563C1;
	text-decoration:underline;}
a:visited, span.MsoHyperlinkFollowed
	{color:#954F72;
	text-decoration:underline;}
p.MsoDocumentMap, li.MsoDocumentMap, div.MsoDocumentMap
	{mso-style-link:"文档结构图 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:12.0pt;
	font-family:"Helvetica","sans-serif";}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:21.0pt;
	font-size:10.5pt;
	font-family:"Calibri","sans-serif";}
p.1, li.1, div.1
	{mso-style-name:列出段落1;
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:21.0pt;
	font-size:10.5pt;
	font-family:"Calibri","sans-serif";}
span.Char
	{mso-style-name:"页眉 Char";
	mso-style-link:页眉;}
span.Char0
	{mso-style-name:"页脚 Char";
	mso-style-link:页脚;}
span.1Char
	{mso-style-name:"标题 1 Char";
	mso-style-link:"标题 1";
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
span.2Char
	{mso-style-name:"标题 2 Char";
	mso-style-link:"标题 2";
	font-family:"Calibri Light";
	font-weight:bold;}
span.3Char
	{mso-style-name:"标题 3 Char";
	mso-style-link:"标题 3";
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.2, li.2, div.2
	{mso-style-name:列出段落2;
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:21.0pt;
	font-size:10.5pt;
	font-family:"Calibri","sans-serif";}
span.4Char
	{mso-style-name:"标题 4 Char";
	mso-style-link:"标题 4";
	font-family:"Cambria","serif";
	font-weight:bold;}
span.5Char
	{mso-style-name:"标题 5 Char";
	mso-style-link:"标题 5";
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
span.6Char
	{mso-style-name:"标题 6 Char";
	mso-style-link:"标题 6";
	font-family:"Cambria","serif";
	font-weight:bold;}
span.7Char
	{mso-style-name:"标题 7 Char";
	mso-style-link:"标题 7";
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
span.8Char
	{mso-style-name:"标题 8 Char";
	mso-style-link:"标题 8";
	font-family:"Cambria","serif";}
span.9Char
	{mso-style-name:"标题 9 Char";
	mso-style-link:"标题 9";
	font-family:"Cambria","serif";}
span.Char1
	{mso-style-name:"文档结构图 Char";
	mso-style-link:文档结构图;
	font-family:"Helvetica","sans-serif";}
.MsoChpDefault
	{font-size:10.0pt;}
 /* Page Definitions */
 @page Section1
	{size:595.3pt 841.9pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
 /* List Definitions */
 ol
	{margin-bottom:0cm;}
ul
	{margin-bottom:0cm;}
-->
</style>

</head>

<body lang=ZH-CN link="#0563C1" vlink="#954F72" style='text-justify-trim:punctuation'>

<div class=Section1 style='layout-grid:15.6pt'>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:18.0pt;font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:18.0pt;font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:18.0pt;font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:18.0pt;font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span
style='font-size:24.0pt;font-family:"微软雅黑","sans-serif"'>可当加油卡平台接口文档<span
lang=EN-US>V2.0.1</span></span></b></p>

<p class=MsoNormal><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<h1><span lang=EN-US>1<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>文档说明</span></h1>

<h2><span lang=EN-US>1.1<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>接口说明</span></h2>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-family:"微软雅黑","sans-serif"'>1</span><span style='font-family:"微软雅黑","sans-serif"'>，本文档是合作商和平台对接的<span
lang=EN-US>API</span>文档，用于做加油卡订购，订单查询，订单回调等业务。</span></p>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-family:"微软雅黑","sans-serif"'>2</span><span style='font-family:"微软雅黑","sans-serif"'>，本接口是采用<span
lang=EN-US>http</span>的传参数返回<span lang=EN-US>json</span>的方式进行交互。</span></p>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-family:"微软雅黑","sans-serif"'>3</span><span style='font-family:"微软雅黑","sans-serif"'>，订购加油卡业务是异步的方式，调订购接口时候，后向平台会返回提交成功<span
lang=EN-US>(</span>并不是订购加油卡<a name="_GoBack"></a>成功<span lang=EN-US>)</span>，在订购成功之后，会回调合作商提供的回调地址，通知订单结果<span
lang=EN-US>(</span>成功<span lang=EN-US>/</span>失败<span lang=EN-US>)</span>，如此订购流程结束。订购接口每天允许错误的验证次数最多为<span
lang=EN-US>3</span>次。</span></p>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<h2><span lang=EN-US>1.2<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>版本信息</span></h2>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>版本号</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>修订人</span></b></p>
  </td>
  <td width=111 valign=top style='width:83.6pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>修订日期</span></b></p>
  </td>
  <td width=265 valign=top style='width:7.0cm;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>备注</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>V2.0.1</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>葛贡献</span></p>
  </td>
  <td width=111 valign=top style='width:83.6pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2017-04-01</span></p>
  </td>
  <td width=265 valign=top style='width:7.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>创建文档</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<h1><span lang=EN-US>2<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>接口定义</span></h1>

<h2><span lang=EN-US>2.1<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>加油卡订购</span></h2>

<p class=MsoNormal><span style='font-family:"微软雅黑","sans-serif"'>正式地址：</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black;background:
white'>http://interface.kedang.net:8080</span><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>/fenxiao-if/General/gascard</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>http</span><span
style='font-family:"微软雅黑","sans-serif"'>方法：<span lang=EN-US>post</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>请求参数列表</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>mid</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>商户号</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>data</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>应用参数<span lang=EN-US>DES</span>加密字符串</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>data</span><span
style='font-family:"微软雅黑","sans-serif"'>参数值<span lang=EN-US>= DES(“</span>应用参数列表所有字段<span
lang=EN-US>JSON</span>字符串<span lang=EN-US>”,Secretkey );</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><a
name="OLE_LINK3"><span style='font-family:"微软雅黑","sans-serif";color:red'>注：<span
lang=EN-US>Secretkey </span></span></a><span lang=EN-US style='font-family:
"微软雅黑","sans-serif";color:red'>, mid </span><span style='font-family:"微软雅黑","sans-serif";
color:red'>由供应商提供</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif";color:red'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>应用参数列表（组装<span lang=EN-US>json</span>字符串后<span
lang=EN-US>DES</span>加密）</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>mid</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>商户号</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>date</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>时间戳<span lang=EN-US>(</span>格式为<span
  lang=EN-US>yyyyMMddHHmmss)</span></span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>phone</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>中石油卡号、中石化卡号</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>snum</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>流水号<span lang=EN-US>(</span>商户号加</span></p>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>＋<span lang=EN-US>14</span>位时间戳<span
  lang=EN-US>(</span>格式为<span lang=EN-US>yyyyMMddHHmmss)</span></span></p>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>＋<span lang=EN-US>5</span>位随机数</span></p>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>)</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>size</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>大小，面值 （单位：元）</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>notifyUrl</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>回调地址</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>demo</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>手机号（号码用于接收充值结果，可任意）</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>响应参数列表</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>result</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>办理结果<span lang=EN-US>(</span>详情见</span><span
  lang=EN-US><a href="#_错误码_1"><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'><span
  lang=EN-US>错误</span></span><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'><span
  lang=EN-US>码</span></span></a></span><span lang=EN-US style='font-family:
  "微软雅黑","sans-serif"'>)</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>code</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>错误编码</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>msg</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>错误信息</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
style='font-family:"微软雅黑","sans-serif"'>响应示例：</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>{</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;code&quot;:&quot;0004&quot;,</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;msg&quot;:&quot;</span><span
style='font-family:"微软雅黑","sans-serif"'>该产品不存在或已下架<span lang=EN-US>&quot;,</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;result&quot;:&quot;0001&quot;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>}</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><a name=安全><b><span
style='font-family:"微软雅黑","sans-serif"'>安全信息：</span></b></a></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>data</span><span
style='font-family:"微软雅黑","sans-serif"'>参数加密<span lang=EN-US>:</span>即将所有应用参数组装<span
lang=EN-US>json</span>字符串，使用供应商提供的密钥<span lang=EN-US>Secretkey</span>对<span
lang=EN-US>json</span>字符串进行<span lang=EN-US>DES</span>加密，作为<span lang=EN-US>data</span>值传过来。</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>只有通过合作商提供的<span lang=EN-US>ip</span>地址才能进行调用（需绑定鉴权<span
lang=EN-US>IP</span>）。</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>按传过来的时间戳进行判断<span lang=EN-US>,</span>最大误差不能超过<span
lang=EN-US>10</span>分钟<span lang=EN-US>(</span>后向平台时间<span lang=EN-US>+-10</span>分钟<span
lang=EN-US>)</span>。</span></p>

<h2><span lang=EN-US>2.2<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>异步回调</span></h2>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>该回调接口由合作商提供，下单时<span lang=EN-US>notifyUrl</span>填写回调地址，不可为空，供应商根据回调地址返回给合作商充值结果。</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>传入参数</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=553
 style='width:415.0pt;margin-left:5.9pt;border-collapse:collapse'>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;background:
  #00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><span
  style='font-size:11.0pt;line-height:120%;font-family:"微软雅黑","sans-serif";
  color:black'>参数</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>说明</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>类型</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>可否为空</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>serial_number</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>流水号</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>Varchar(30)</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>不能为空</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>order_id</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>加油卡平台的订单<span
  lang=EN-US>ID</span></span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>Varchar(30)</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>不能为空</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_code</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>代号</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>Varchar(30)</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>不能为空</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_message</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>代号描述</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>Varchar(30)</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>不能为空</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-size:11.0pt;line-height:120%;font-family:"微软雅黑","sans-serif";
color:black'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-size:11.0pt;line-height:120%;font-family:"微软雅黑","sans-serif";
color:black'>fs_code</span><span style='font-size:11.0pt;line-height:120%;
font-family:"微软雅黑","sans-serif";color:black'>可能的值</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=412
 style='width:309.0pt;margin-left:5.9pt;border-collapse:collapse'>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;background:
  #00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_code</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>值</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>说明</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_message</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>的值</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>0000</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>成功</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>成功</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=129 style='width:97.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>9999</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>失败</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>失败的原因</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>传入范例</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=553
 style='width:415.0pt;margin-left:5.9pt;border-collapse:collapse'>
 <tr style='height:31.2pt'>
  <td width=553 rowspan=9 valign=top style='width:415.0pt;border-top:windowtext;
  border-left:windowtext;border-bottom:black;border-right:black;border-style:
  solid;border-width:1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>&lt;?xml version=&quot;1.0&quot;
  encoding=&quot;UTF-8&quot;?&gt;&lt;soap:Envelope xmlns:soap=&quot;http://schemas.xmlsoap.org/soap/envelope/&quot;&gt;<br>
  &nbsp;&lt;soap:Body&gt;<br>
  &nbsp; &lt;ns2:notice
  xmlns:ns2=&quot;http://callback.webservice.wujun.com/&quot;&gt;<br>
  &nbsp;&nbsp; &lt;ORDER&gt;<br>
  &nbsp;&nbsp;&nbsp; &lt;SERIAL_NUMBER&gt;73175072200&lt;/SERIAL_NUMBER&gt;<br>
  &nbsp;&nbsp;&nbsp; &lt;ORDER_ID&gt;N2014050418063489011248&lt;/ORDER_ID&gt;<br>
  &nbsp;&nbsp;&nbsp; &lt;FS_CODE&gt;0000&lt;/FS_CODE&gt;<br>
  &nbsp;&nbsp;&nbsp; &lt;FS_MESSAGE&gt;</span><span style='font-size:11.0pt;
  font-family:"微软雅黑","sans-serif";color:black'>成功<span lang=EN-US>&lt;/FS_MESSAGE&gt;<br>
  &nbsp;&nbsp; &lt;/ORDER&gt;<br>
  &nbsp; &lt;/ns2:notice&gt;<br>
  &nbsp;&lt;/soap:Body&gt;<br>
  &lt;/soap:Envelope&gt;</span></span></p>
  </td>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>返回参数</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=565
 style='width:424.0pt;margin-left:5.9pt;border-collapse:collapse'>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;background:
  #00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>参数</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>说明</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>类型</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>可否为空</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_code</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>返回代号</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>Varchar(30)</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>不能为空</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_message</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>代号描述</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>Varchar(30)</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>不能为空</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>&nbsp;</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>fs_code</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>可能的值</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=424
 style='width:318.0pt;margin-left:5.9pt;border-collapse:collapse'>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;background:
  #00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_code</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>值</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>说明</span></p>
  </td>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-left:
  none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>fs_message</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>的值</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>0000</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>成功</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>成功</span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>9999</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>找不到订单</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>(</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>自定<span lang=EN-US>)</span></span></p>
  </td>
 </tr>
 <tr style='height:24.0pt'>
  <td width=141 style='width:106.0pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>1111</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>系统异常</span></p>
  </td>
  <td width=141 style='width:106.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt'>
  <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph'><span
  lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>(</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>自定<span lang=EN-US>)</span></span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>返回范例</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=565
 style='width:424.0pt;margin-left:5.9pt;border-collapse:collapse'>
 <tr style='height:31.2pt'>
  <td width=565 rowspan=5 valign=top style='width:424.0pt;border-top:windowtext;
  border-left:windowtext;border-bottom:black;border-right:black;border-style:
  solid;border-width:1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
  color:black'>&lt;?xml version=&quot;1.0&quot;
  encoding=&quot;UTF-8&quot;?&gt;&lt;soap:Envelope
  xmlns:soap=&quot;http://schemas.xmlsoap.org/soap/envelope/&quot;&gt;<br>
  &nbsp;&lt;soap:Body&gt;<br>
  &nbsp; &lt;ns2:noticeResponse
  xmlns:ns2=&quot;http://callback.webservice.wujun.com/&quot;&gt;<br>
  &nbsp;&nbsp; &lt;return&gt;<br>
  &nbsp;&nbsp;&nbsp; &lt;FS_CODE&gt;1111&lt;/FS_CODE&gt;<br>
  &nbsp;&nbsp;&nbsp; &lt;FS_MESSAGE&gt;</span><span style='font-size:11.0pt;
  font-family:"微软雅黑","sans-serif";color:black'>系统异常<span lang=EN-US>&lt;/FS_MESSAGE&gt;<br>
  &nbsp;&nbsp; &lt;/return&gt;<br>
  &nbsp; &lt;/ns2:noticeResponse&gt;<br>
  &nbsp;&lt;/soap:Body&gt;<br>
  &lt;/soap:Envelope&gt;</span></span></p>
  </td>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
 <tr style='height:31.2pt'>
  <td style='height:31.2pt;border:none' width=0 height=42></td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<h2><span lang=EN-US style='color:black'>2.3<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体;color:black'>订单查询</span></h2>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>请求地址：</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-size:11.0pt;line-height:120%;font-family:Monaco;
color:black;background:white'>http://interface.kedang.net:8080</span><span
lang=EN-US style='font-size:11.0pt;line-height:120%;font-family:Monaco;
color:black'>/fenxiao-if/api/merchant/query/queryOrder</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>http</span><span
style='font-family:"微软雅黑","sans-serif"'>方法：<span lang=EN-US>post</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>请求参数列表</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>mid</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>商户号</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>snum</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>合作商订单号（下单接口中<span lang=EN-US>snum</span>）</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>响应参数列表</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>result</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>办理结果<span lang=EN-US>(</span>详情见</span><span
  lang=EN-US><a href="#_错误码_1"><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'><span
  lang=EN-US>错误</span></span><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'><span
  lang=EN-US>码</span></span></a></span><span lang=EN-US style='font-family:
  "微软雅黑","sans-serif"'>)</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>code</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>错误编码</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>msg</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>错误信息</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>snum</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>订单号</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>status</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>订单状态，查询成功时，<span lang=EN-US>status</span>不为空</span></p>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>（<span lang=EN-US>0</span>成功，<span
  lang=EN-US>1</span>失败，<span lang=EN-US>2</span>充值中，<span lang=EN-US>3</span>提交成功）</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
style='font-family:"微软雅黑","sans-serif"'>响应示例：</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>{</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;code&quot;:&quot;1000&quot;,</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;msg&quot;:&quot;</span><span
style='font-family:"微软雅黑","sans-serif"'>查询成功<span lang=EN-US>&quot;,</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;snum&quot;:&quot;20123456789123&quot;,</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;status&quot;:&quot;3&quot;,</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;result&quot;:&quot;0000&quot;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>}</span></p>

<h2><span lang=EN-US>2.4<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>余额查询</span></h2>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>请求地址：</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-size:11.0pt;line-height:120%;font-family:Monaco;
color:black;background:white'>http://interface.kedang.net:8080</span><span
lang=EN-US style='font-size:11.0pt;line-height:120%;font-family:Monaco;
color:black'>/fenxiao-if/api/merchant/query/balance</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>http</span><span
style='font-family:"微软雅黑","sans-serif"'>方法：<span lang=EN-US>post</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>请求参数列表</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>mid</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>商户号</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
style='font-family:"微软雅黑","sans-serif"'>响应参数列表</span></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数名</span></b></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>参数类型</span></b></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>result</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>办理结果<span lang=EN-US>(</span>详情见</span><span
  lang=EN-US><a href="#错误码"><span lang=EN-US style='font-family:"微软雅黑","sans-serif";
  color:windowtext;text-decoration:none'><span lang=EN-US>错误</span></span><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif";color:windowtext;
  text-decoration:none'><span lang=EN-US>码</span></span></a></span><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>)</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>code</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>错误编码</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>msg</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>错误信息</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>balance</span></p>
  </td>
  <td width=87 valign=top style='width:65.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>String</span></p>
  </td>
  <td width=376 valign=top style='width:282.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>资金金额</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
style='font-family:"微软雅黑","sans-serif"'>响应示例：</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>{</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;result&quot;:&quot;0000&quot;,</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;code&quot;:&quot;1000&quot;,</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;msg&quot;:&quot;</span><span
style='font-family:"微软雅黑","sans-serif"'>查询成功<span lang=EN-US>&quot;,</span></span></p>

<p class=MsoNormal style='margin-right:-43.9pt;text-indent:21.0pt;line-height:
120%'><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&quot;balance&quot;:&quot;5&quot;</span></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>}</span></p>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

<h1><span lang=EN-US>3<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>附录</span></h1>

<h2><a name="_错误码"></a><a name="_错误码_1"></a><span lang=EN-US>3.1<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
style='font-family:宋体'>错误码</span></h2>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><a
name=result></a><b><span lang=EN-US style='font-family:"微软雅黑","sans-serif"'>result</span></b><b><span
style='font-family:"微软雅黑","sans-serif"'>错误码定义</span></b></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>错误码</span></b></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>0000</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>成功</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>0001</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>失败</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>&nbsp;</span></b></p>

<p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><b><span
lang=EN-US style='font-family:"微软雅黑","sans-serif"'>code</span></b><b><span
style='font-family:"微软雅黑","sans-serif"'>错误码定义</span></b></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=593
 style='border-collapse:collapse;border:none'>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>错误码</span></b></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border:solid windowtext 1.0pt;
  border-left:none;background:#00B0F0;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:120%'><b><span
  style='font-family:"微软雅黑","sans-serif"'>说明</span></b></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>0000</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>受理成功</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1000</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>查询成功</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1001</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>合作商<span lang=EN-US>ID</span>不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1002</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>合作商不存在</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1003</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>时间戳不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1004</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>时间戳格式错误</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1005</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>请求时间相隔超过<span lang=EN-US>10</span>分钟</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1006</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>密钥不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1007</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>密钥错误</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1008</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>手机号不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1009</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>手机号格式不正确</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1010</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>未知归属地</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1011</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>size</span><span
  style='font-family:"微软雅黑","sans-serif"'>不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1012</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>订单号<span lang=EN-US>snum</span>不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1013</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>订单号格式错误</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1016</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>商户余额不足</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1017</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>商户当前不可用</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1018</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>IP</span><span
  style='font-family:"微软雅黑","sans-serif"'>鉴权失败</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1019</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>data</span><span
  style='font-family:"微软雅黑","sans-serif"'>值不能为空</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1020</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>data</span><span
  style='font-family:"微软雅黑","sans-serif"'>密文加密异常</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1021</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>产品不存在</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1022</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>该业务尚未开通</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1023</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>该产品已下架</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1024</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>该产品未配置</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>1025</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>接口账号不存在</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2001</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>查询失败<span lang=EN-US>,</span>请稍后重试<span
  lang=EN-US>!</span></span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2002</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>订单不存在</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2003</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>订单已存在</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2004</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>参数错误</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2005</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>系统错误<span lang=EN-US>(</span>请联系供应商<span
  lang=EN-US>)</span></span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>2006</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>通道异常<span lang=EN-US>(</span>请联系供应商<span
  lang=EN-US>)</span></span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>3000</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>保存入库异常，请联系平台管理员</span></p>
  </td>
 </tr>
 <tr>
  <td width=130 valign=top style='width:97.55pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  lang=EN-US style='font-family:"微软雅黑","sans-serif"'>30001</span></p>
  </td>
  <td width=463 valign=top style='width:347.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='margin-right:-43.9pt;line-height:120%'><span
  style='font-family:"微软雅黑","sans-serif"'>该号码已列为黑名单</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

<h2><span lang=EN-US>3.2<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>加油卡订购</span><span lang=EN-US>DEMO</span><span
style='font-family:宋体'>（</span><span lang=EN-US>java</span><span
style='font-family:宋体'>）</span></h2>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>public</span><span
lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>
static String submitOrder() {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //
</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>下单接口地址</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
url = &quot;http://localhost:8080/fenxiao-if/General/gascard &quot;;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
secretkey = &quot;ijnsk9ok1wsdu4hbijnsk9ok1wsdu411&quot;;// </span><span
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>密钥</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SimpleDateFormat
sdf = new SimpleDateFormat(&quot;yyyyMMddHHmmss&quot;);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
mid = &quot;10000&quot;;// </span><span style='font-size:11.0pt;font-family:
"微软雅黑","sans-serif";color:black'>商户<span lang=EN-US>ID</span></span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
date = sdf.format(new Date());// </span><span style='font-size:11.0pt;
font-family:"微软雅黑","sans-serif";color:black'>日期</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //phone
</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>中石化、中石油卡号</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
phone = &quot;</span><span lang=EN-US style='font-size:9.0pt;font-family:"Helvetica Neue";
color:black;background:#FFE48D'>100011xxxxxxxxxxxxx</span><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&quot;;// </span><span
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>中石化</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
phone = &nbsp;&quot;</span><span lang=EN-US style='font-size:9.0pt;font-family:
"Helvetica Neue";color:black;background:#FFE48D'>90300600xxxxxxxx</span><span
lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&quot;;//
</span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>中石化</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
size = &quot;100&quot;;// (</span><span style='font-size:11.0pt;font-family:
"微软雅黑","sans-serif";color:black'>元<span lang=EN-US>) </span>面值</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
snum = &quot;20123456789123&quot;;// </span><span style='font-size:11.0pt;
font-family:"微软雅黑","sans-serif";color:black'>下游订单号</span></p>

<p class=MsoNormal style='margin-left:21.0pt;text-indent:21.0pt;text-autospace:
none'><span lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>// </span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>下游回调地址</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
notifyUrl = &quot;http://121.41.66.66:8009/notice/kedang&quot;;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; JSONObject
js = new JSONObject();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;mid&quot;,
mid);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;date&quot;,
date);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;phone&quot;,
phone);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;size&quot;,
size);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;snum&quot;,
snum);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;notifyUrl&quot;,
notifyUrl);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; js.put(&quot;demo&quot;,&quot;13100000000&quot;);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; System.<i>out</i>.println(js.toJSONString());</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
dataDes = null;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; try
{</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; dataDes
= DesUtils.<i>encrypt</i>(js.toJSONString(), secretkey);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }
catch (Exception e1) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e1.printStackTrace();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HttpClient
client = new HttpClient();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; client.getParams().setContentCharset(&quot;UTF-8&quot;);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; PostMethod
postMethod = new PostMethod(url);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; postMethod.addParameter(&quot;mid&quot;,
mid); // </span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>商户号</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; postMethod.addParameter(&quot;data&quot;,
dataDes); // </span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>密文</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; try
{</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; client.executeMethod(postMethod);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
result = postMethod.getResponseBodyAsString();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; postMethod.releaseConnection();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return
result;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }
catch (HttpException e) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }
catch (IOException e) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return
null;</span></p>

<p class=MsoNormal style='line-height:20.0pt'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

<h2><span lang=EN-US>3.3<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>订单查询</span><span lang=EN-US>DEMO</span><span
style='font-family:宋体'>（</span><span lang=EN-US>java</span><span
style='font-family:宋体'>）</span></h2>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>public
static String queryOrder() {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
url = &quot;</span><span lang=EN-US><a
href="http://localhost:8080/fenxiao-if/api/merchant/query/queryOrder"><span
style='font-family:"微软雅黑","sans-serif";color:black;text-decoration:none'>http://localhost:8080/fenxiao-if/api/merchant/query/queryOrder</span></a></span><span
lang=EN-US style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&quot;;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
mid =&quot;10000&quot;;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
snum =&quot;1000020160808121212000001&quot;;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HttpClient
client = new HttpClient();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; client.getParams().setContentCharset(&quot;UTF-8&quot;);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; PostMethod
postMethod = new PostMethod(url);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; postMethod.addParameter(&quot;mid&quot;,
mid); // </span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>商户号</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; postMethod.addParameter(&quot;snum&quot;,
snum); // </span><span style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";
color:black'>订单号</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; try
{</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; client.executeMethod(postMethod);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
result = postMethod.getResponseBodyAsString();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; postMethod.releaseConnection();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return
result;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }
catch (HttpException e) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }
catch (IOException e) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return
null;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:"微软雅黑","sans-serif";color:black'>}</span></p>

<h2><span lang=EN-US>3.4<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>回调</span><span lang=EN-US>demo</span></h2>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:#3F5FBF'>/**</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:#3F5FBF'>&nbsp;&nbsp; &nbsp;* </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F9FBF'>@see</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#3F5FBF'> HttpServlet#doPost(HttpServletRequest
request, HttpServletResponse response)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:#3F5FBF'>&nbsp;&nbsp; &nbsp;*/</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#646464'>@SuppressWarnings</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;unchecked&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>protected</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>void</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>
doPost(HttpServletRequest </span><span lang=EN-US style='font-size:11.0pt;
font-family:Monaco;color:#6A3E3E'>request</span><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>, HttpServletResponse </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>response</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>) </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>throws</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>
ServletException,</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IOException</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_code</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;9999&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_message</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>serial_number</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>try</span></b></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>request</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.setCharacterEncoding(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;UTF-8&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>int</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>size</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>request</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.getContentLength();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; InputStream
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>is</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>request</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.getInputStream();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>byte</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>[] </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>reqBodyBytes</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> =
CallbackUtils.<i>readBytes</i>(</span><span lang=EN-US style='font-size:11.0pt;
font-family:Monaco;color:#6A3E3E'>is</span><span lang=EN-US style='font-size:
11.0pt;font-family:Monaco;color:black'>, </span><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>size</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>res</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> String(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>reqBodyBytes</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>, </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;UTF-8&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#0000C0'>logger</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.info(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;====</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>接收到回调信息</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>:&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>res</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>msg</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>order_id</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SAXReader
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>reader</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>
SAXReader();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Document
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>document</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>reader</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.read(</span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>
StringReader(</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;
color:#6A3E3E'>res</span><span lang=EN-US style='font-size:11.0pt;font-family:
Monaco;color:black'>));</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Element
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>root</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>document</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.getRootElement();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; List&lt;Element&gt;
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>childElements</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>root</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elements();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>for</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> (Element </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>child</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> : </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>childElements</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; List&lt;Element&gt;
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result2</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>child</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elements();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>for</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> (Element </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>c</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> : </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result2</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; List&lt;Element&gt;
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>c2</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>c</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elements();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>for</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> (Element </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>ret</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> : </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>c2</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>ret</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elementText(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;FS_CODE&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>msg</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>ret</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elementText(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;FS_MESSAGE&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>serial_number</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>ret</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elementText(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;SERIAL_NUMBER&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);//</span><span
style='font-size:11.0pt;font-family:宋体;color:black'>即下单时提交的</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>snum</span><span
style='font-size:11.0pt;font-family:宋体;color:black'>值</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>order_id</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>ret</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.elementText(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;ORDER_ID&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);//</span><span
style='font-size:11.0pt;font-family:宋体;color:black'>无需关注</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#0000C0'>logger</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.info(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>回调信息：订单</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>SERIAL_NUMBER:&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>serial_number</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;,</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>返回代码</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>:&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;,</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>信息</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>:&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>msg</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#3F7F5F'>//</span><span
style='font-size:11.0pt;font-family:宋体;color:#3F7F5F'>订单状态码</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>if</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> (</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;0000&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.equals(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>))</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#3F7F5F'>//</span><span
style='font-size:11.0pt;font-family:宋体;color:#3F7F5F'>成功</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_code</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;0000&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_message</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>成功</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; …..</span><span
style='font-size:11.0pt;font-family:宋体;color:black'>成功业务逻辑</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#0000C0'>logger</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.info(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;=====</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>回调订单状态</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>[</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>成功</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>]</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>，订单编号</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>[&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>serial_number</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;]=====&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>else</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>if</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> (</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;9999&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.equals(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>result</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>))</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#3F7F5F'>//</span><span
style='font-size:11.0pt;font-family:宋体;color:#3F7F5F'>失败</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_code</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;0000&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_message</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>成功</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; …..</span><span
style='font-size:11.0pt;font-family:宋体;color:black'>失败业务逻辑</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#0000C0'>logger</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.info(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;=====</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>回调订单状态</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>[</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>失败</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>]</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>，订单编号</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>[&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>serial_number</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;]=====&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>catch</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> (Exception </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>e</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_code</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;1111&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_message</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>系统异常</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#0000C0'>logger</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.info(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;=====</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>回调订单状态</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>[</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>异常</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>]</span><span
style='font-size:11.0pt;font-family:宋体;color:#2A00FF'>，订单编号</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>[&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>serial_number</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;]=====&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>finally</span></b></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; StringBuilder
</span><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> = </span><b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>
StringBuilder();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.append(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;?xml
version=\&quot;1.0\&quot; encoding=\&quot;UTF-8\&quot;?&gt;&lt;soap:Envelope
xmlns:soap=\&quot;http://schemas.xmlsoap.org/soap/envelope/\&quot;&gt;&lt;soap:Body&gt;&lt;ns2:noticeResponse
xmlns:ns2=\&quot;http://callback.webservice.wujun.com/\&quot;&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.append(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;return&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.append(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;FS_CODE&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_code</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;/FS_CODE&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.append(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;FS_MESSAGE&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>fs_message</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'> + </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;/FS_MESSAGE&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.append(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;/return&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.append(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;&lt;/ns2:noticeResponse&gt;&lt;/soap:Body&gt;&lt;/soap:Envelope&gt;&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>response</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.setHeader(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;Content-type&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>, </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#2A00FF'>&quot;text/html;charset=UTF-8&quot;</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:11.0pt;font-family:Monaco;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>response</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.getWriter().write(</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:#6A3E3E'>sb</span><span
lang=EN-US style='font-size:11.0pt;font-family:Monaco;color:black'>.toString());</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;font-family:Monaco;
color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<h2><span lang=EN-US>3.5<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:宋体'>加密</span><span lang=EN-US>Demo</span></h2>

<p class=MsoNormal style='text-autospace:none'><b><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>public</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>final</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>static</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> String
encrypt(String </span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:#6A3E3E'>data</span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:black'>, String </span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>pwd</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>throws</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> Exception
{</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>return</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> <i>byte2hex</i>(<i><span
style='background:silver'>encrypt</span></i>(</span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>data</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>.getBytes(),
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>pwd</span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:black'>.getBytes()));</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp; }</span></p>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>public</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>static</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> String <span
style='background:silver'>byte2hex</span>(</span><b><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>byte</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>[] </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>b</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>hs</span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:black'> = </span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:#2A00FF'>&quot;&quot;</span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; String
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>stmp</span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:black'> = </span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:#2A00FF'>&quot;&quot;</span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>for</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> (</span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>int</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>n</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> = 0; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>n</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> &lt; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>b</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>.</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#0000C0'>length</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>n</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>++) {</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>stmp</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> =
(java.lang.Integer.<i>toHexString</i>(</span><span lang=EN-US style='font-size:
16.0pt;font-family:Consolas;color:#6A3E3E'>b</span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>[</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>n</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>] &amp;
0XFF));</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>if</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> (</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>stmp</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>.length()
== 1)</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>hs</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> = </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>hs</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> + </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#2A00FF'>&quot;0&quot;</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> + </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>stmp</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>else</span></b></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>hs</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> = </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>hs</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> + </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>stmp</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>return</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>hs</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>.toUpperCase();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp; }</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>/**</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>用指定的</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>key</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>对数据进行</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>DES</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>加密</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>.</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F9FBF'>@param</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'> data</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><span style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>待加密的数据</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F9FBF'>@param</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'> key</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
DES</span><span style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>加密的</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>key</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F9FBF'>@return</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'> </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>返回</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>DES</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>加密后的数据</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F9FBF'>@throws</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>
Exception</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;* </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F9FBF'>@author</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'> &lt;a <u>href</u>=&quot;mailto:xiexingxing1121@126.<u>com</u>&quot;</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
mce_href=&quot;mailto:xiexingxing1121@126.<u>com</u>&quot;&gt;AmigoXie</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F7F9F'>&lt;/a&gt;</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>
Creation</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
date: 2007</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#7F7F9F'>-</span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:#3F5FBF'>7</span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:#7F7F9F'>-</span><span lang=EN-US style='font-size:
16.0pt;font-family:Consolas;color:#3F5FBF'>31 </span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#7F7F9F'>-</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'> </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F5FBF'>下午</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>12:09:03</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#3F5FBF'>&nbsp; &nbsp;*/</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>private</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>static</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>byte</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>[] <span
style='background:silver'>encrypt</span>(</span><b><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>byte</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>[] </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>data</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>, </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>byte</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>[] </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>key</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>) </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>throws</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> Exception
{</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// DES</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>算法要求有一个可信任的随机数源</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; SecureRandom
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>sr</span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:black'> = </span><b><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:#7F0055'>new</span></b><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'> SecureRandom();</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>从原始密匙数据创建</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>DESKeySpec</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>对象</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; DESKeySpec
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>dks</span><span lang=EN-US style='font-size:16.0pt;font-family:
Consolas;color:black'> = </span><b><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:#7F0055'>new</span></b><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'> DESKeySpec(</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>key</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>创建一个密匙工厂，然后用它把</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>DESKeySpec</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>转换成</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>一个</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>SecretKey</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>对象</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; SecretKeyFactory
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>keyFactory</span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:black'> = SecretKeyFactory.<i>getInstance</i>(</span><b><i><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#0000C0'>ALGORITHM</span></i></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; SecretKey
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>securekey</span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:black'> = </span><span lang=EN-US style='font-size:
16.0pt;font-family:Consolas;color:#6A3E3E'>keyFactory</span><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>.generateSecret(</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>dks</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>//
Cipher</span><span style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>对象实际完成加密操作</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; Cipher
</span><span lang=EN-US style='font-size:16.0pt;font-family:Consolas;
color:#6A3E3E'>cipher</span><span lang=EN-US style='font-size:16.0pt;
font-family:Consolas;color:black'> = Cipher.<i>getInstance</i>(</span><b><i><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#0000C0'>ALGORITHM</span></i></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>用密匙初始化</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>Cipher</span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>对象</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>cipher</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>.init(Cipher.</span><b><i><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#0000C0'>ENCRYPT_MODE</span></i></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>, </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>securekey</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>, </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>sr</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>现在，获取数据并加密</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#3F7F5F'>// </span><span
style='font-size:16.0pt;font-family:宋体;color:#3F7F5F'>正式执行加密操作</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#7F0055'>return</span></b><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'> </span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>cipher</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>.doFinal(</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:#6A3E3E'>data</span><span
lang=EN-US style='font-size:16.0pt;font-family:Consolas;color:black'>);</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-US
style='font-size:16.0pt;font-family:Consolas;color:black'>&nbsp; }</span></p>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

</div>

</body>

</html>
