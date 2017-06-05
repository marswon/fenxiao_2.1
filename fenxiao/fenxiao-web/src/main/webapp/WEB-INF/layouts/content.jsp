<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<!-- 引入bootstrap样式 -->  
<link rel="stylesheet" type="text/css"   href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" />
<!-- 引入easyUi默认的CSS格式--蓝色 -->  
<link rel="stylesheet" type="text/css"   href="${ctx}/static/easyui/themes/bootstrap/easyui.css" />  
<!-- 引入easyUi小图标 -->  
<link rel="stylesheet" type="text/css"   href="${ctx}/static/easyui/themes/icon.css" />  
<link rel="stylesheet" type="text/css"   href="${ctx}/static/styles/layoutxx.css" />
<!-- 引入Jquery -->  
<script type="text/javascript"   src="${ctx}/static/easyui/jquery-1.8.3.js" ></script>  
<!-- 引入Jquery_easyui -->  
<script type="text/javascript"   src="${ctx}/static/easyui/jquery.easyui.min.js" ></script>  
<!-- 引入easyUi国际化--中文 -->  
<script type="text/javascript"   src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script> 

<script type="text/javascript" src="${ctx}/static/chosen/common.js"></script>
<script type="text/javascript"
	src="${ctx}/static/chosen/chosen.jquery.min.js"></script>
<link rel="stylesheet" href="${ctx}/static/chosen/chosen.min.css"
	rel="stylesheet">
<!-- 引入iCheck --> 
<link href="${ctx}/static/iCheck/minimal/grey.css" rel="stylesheet">
<script src="${ctx}/static/iCheck/icheck.js"></script>
	
<sitemesh:head/>
<style scoped="scoped">
       .textbox{
           height:20px;
           margin:0;
           padding:0 2px;
           box-sizing:content-box;
       }
</style>
</head>
<script type="text/javascript">
var provinceMap = {
		'010' : '北京BJ',
		'020' : '广东GD',
		'021' : '上海SH',
		'022' : '天津TJ',
		'023' : '重庆CQ',
		'025' : '江苏JS',
		'026' : '青海QH',
		'027' : '海南HN',
		'028' : '四川SC',
		'029' : '陕西SX',
		'030' : '山西SX',
		'035' : '河北HB',
		'039' : '河南HN',
		'040' : '内蒙古NMG',
		'041' : '辽宁LN',
		'045' : '吉林JL',
		'046' : '黑龙江HLJ',
		'050' : '安徽AH',
		'055' : '浙江ZJ',
		'059' : '福建FJ',
		'060' : '山东SD',
		'070' : '广西GX',
		'071' : '湖北HB',
		'073' : '湖南HN',
		'075' : '江西JX',
		'080' : '云南YN',
		'085' : '贵州GZ',
		'089' : '西藏XZ',
		'090' : '宁夏NX',
		'093' : '甘肃GS',
		'095' : '新疆XJ'
	};

var areaCodeMap = {
		'000':'未知',
		'010':'北京',
		'020':'广州',
		'021':'上海',
		'022':'天津',
		'023':'重庆',
		'024':'沈阳',
		'025':'南京',
		'027':'武汉',
		'028':'眉山',
		'029':'西安',
		'0310':'邯郸',
		'0311':'石家庄',
		'0312':'保定',
		'0313':'张家口',
		'0314':'承德',
		'0315':'唐山',
		'0316':'廊坊',
		'0317':'沧州',
		'0318':'衡水',
		'0319':'邢台',
		'0335':'秦皇岛',
		'0349':'朔州',
		'0350':'忻州',
		'0351':'太原',
		'0352':'大同',
		'0353':'阳泉',
		'0354':'晋中',
		'0355':'长治',
		'0356':'晋城',
		'0357':'临汾',
		'0358':'吕梁',
		'0359':'运城',
		'0370':'商丘',
		'0371':'郑州',
		'0372':'安阳',
		'0373':'新乡',
		'0374':'许昌',
		'0375':'平顶山',
		'0376':'信阳',
		'0377':'南阳',
		'0378':'开封',
		'0379':'洛阳',
		'0391':'焦作',
		'0392':'鹤壁',
		'0393':'濮阳',
		'0394':'周口',
		'0395':'漯河',
		'0396':'驻马店',
		'0398':'三门峡',
		'0410':'铁岭',
		'0411':'大连',
		'0412':'鞍山',
		'0413':'抚顺',
		'0414':'本溪',
		'0415':'丹东',
		'0416':'锦州',
		'0417':'营口',
		'0418':'阜新',
		'0419':'辽阳',
		'0421':'朝阳',
		'0427':'盘锦',
		'0429':'葫芦岛',
		'0431':'长春',
		'0432':'吉林',
		'0433':'延边',
		'0434':'四平',
		'0435':'通化',
		'0436':'白城',
		'0437':'辽源',
		'0438':'松原',
		'0439':'白山',
		'0451':'哈尔滨',
		'0452':'齐齐哈尔',
		'0453':'牡丹江',
		'0454':'佳木斯',
		'0455':'绥化',
		'0456':'黑河',
		'0457':'大兴安岭',
		'0458':'伊春',
		'0459':'大庆',
		'0464':'七台河',
		'0467':'鸡西',
		'0468':'鹤岗',
		'0469':'双鸭山',
		'0470':'呼伦贝尔',
		'0471':'呼和浩特',
		'0472':'包头',
		'0473':'乌海',
		'0474':'乌兰察布',
		'0475':'通辽',
		'0476':'赤峰',
		'0477':'鄂尔多斯',
		'0478':'巴彦淖尔',
		'0479':'锡林郭勒盟',
		'0482':'兴安盟',
		'0483':'阿拉善盟',
		'0510':'无锡',
		'0511':'镇江',
		'0512':'苏州',
		'0513':'南通',
		'0514':'扬州',
		'0515':'盐城',
		'0516':'徐州',
		'0517':'淮安',
		'0518':'连云港',
		'0519':'常州',
		'0523':'泰州',
		'0527':'宿迁',
		'0530':'菏泽',
		'0531':'济南',
		'0532':'青岛',
		'0533':'淄博',
		'0534':'德州',
		'0535':'烟台',
		'0536':'潍坊',
		'0537':'济宁',
		'0538':'泰安',
		'0539':'临沂',
		'0543':'滨州',
		'0546':'东营',
		'0550':'滁州',
		'0551':'合肥',
		'0552':'蚌埠',
		'0553':'芜湖',
		'0554':'淮南',
		'0555':'马鞍山',
		'0556':'安庆',
		'0557':'宿州',
		'0558':'阜阳',
		'0559':'黄山',
		'0561':'淮北',
		'0562':'铜陵',
		'0563':'宣城',
		'0564':'六安',
		'0565':'巢湖',
		'0566':'池州',
		'0570':'衢州',
		'0571':'杭州',
		'0572':'湖州',
		'0573':'嘉兴',
		'0574':'宁波',
		'0575':'绍兴',
		'0576':'台州',
		'0577':'温州',
		'0578':'丽水',
		'0579':'金华',
		'0580':'舟山',
		'0591':'福州',
		'0592':'厦门',
		'0593':'宁德',
		'0594':'莆田',
		'0595':'泉州',
		'0596':'漳州',
		'0597':'龙岩',
		'0598':'三明',
		'0599':'南平',
		'0631':'威海',
		'0632':'枣庄',
		'0633':'日照',
		'0634':'莱芜',
		'0635':'聊城',
		'0660':'汕尾',
		'0662':'阳江',
		'0663':'揭阳',
		'0668':'茂名',
		'0691':'西双版纳',
		'0692':'德宏',
		'0701':'鹰潭',
		'0710':'襄阳',
		'0711':'鄂州',
		'0712':'孝感',
		'0713':'黄冈',
		'0714':'黄石',
		'0715':'咸宁',
		'0716':'荆州',
		'0717':'宜昌',
		'0718':'恩施',
		'0719':'十堰',
		'0722':'随州',
		'0724':'荆门',
		'0728':'仙桃',
		'0730':'岳阳',
		'0731':'长沙',
		'0734':'衡阳',
		'0735':'郴州',
		'0736':'常德',
		'0737':'益阳',
		'0738':'娄底',
		'0739':'邵阳',
		'0743':'吉首',
		'0744':'张家界',
		'0745':'怀化',
		'0746':'永州',
		'0750':'江门',
		'0751':'韶关',
		'0752':'惠州',
		'0753':'梅州',
		'0754':'汕头',
		'0755':'深圳',
		'0756':'珠海',
		'0757':'佛山',
		'0758':'肇庆',
		'0759':'湛江',
		'0760':'中山',
		'0762':'河源',
		'0763':'清远',
		'0766':'云浮',
		'0768':'潮州',
		'0769':'东莞',
		'0770':'防城港',
		'0771':'南宁',
		'0772':'柳州',
		'0773':'桂林',
		'0774':'梧州',
		'0775':'玉林',
		'0776':'百色',
		'0777':'钦州',
		'0778':'河池',
		'0779':'北海',
		'0790':'新余',
		'0791':'南昌',
		'0792':'九江',
		'0793':'上饶',
		'0794':'抚州',
		'0795':'宜春',
		'0796':'吉安',
		'0797':'赣州',
		'0798':'景德镇',
		'0799':'萍乡',
		'0812':'攀枝花',
		'0813':'自贡',
		'0816':'绵阳',
		'0817':'南充',
		'0818':'达州',
		'0825':'遂宁',
		'0826':'广安',
		'0827':'巴中',
		'0830':'泸州',
		'0831':'宜宾',
		'0832':'内江',
		'0833':'乐山',
		'0834':'西昌',
		'0835':'雅安',
		'0836':'甘孜',
		'0837':'阿坝',
		'0838':'德阳',
		'0839':'广元',
		'0851':'贵阳',
		'0852':'遵义',
		'0853':'安顺',
		'0854':'黔南',
		'0855':'黔东南',
		'0856':'铜仁',
		'0857':'毕节',
		'0858':'六盘水',
		'0859':'黔西南',
		'0870':'昭通',
		'0871':'昆明',
		'0872':'大理',
		'0873':'红河',
		'0874':'曲靖',
		'0875':'保山',
		'0876':'文山',
		'0877':'玉溪',
		'0878':'楚雄',
		'0879':'普洱',
		'0883':'临沧',
		'0886':'怒江',
		'0887':'迪庆',
		'0888':'丽江',
		'0891':'拉萨',
		'0892':'日喀则',
		'0893':'山南',
		'0894':'林芝',
		'0895':'昌都',
		'0896':'那曲',
		'0897':'阿里',
		'0898':'海口',
		'0899':'三亚',
		'0901':'塔城',
		'0902':'哈密',
		'0903':'和田',
		'0906':'阿勒泰',
		'0908':'克州',
		'0909':'博乐',
		'0910':'咸阳',
		'0911':'延安',
		'0912':'榆林',
		'0913':'渭南',
		'0914':'商洛',
		'0915':'安康',
		'0916':'汉中',
		'0917':'宝鸡',
		'0919':'铜川',
		'0930':'临夏',
		'0931':'兰州',
		'0932':'定西',
		'0933':'平凉',
		'0934':'庆阳',
		'0935':'武威',
		'0936':'张掖',
		'0937':'嘉峪关',
		'0938':'天水',
		'0939':'陇南',
		'0941':'甘南',
		'0943':'白银',
		'0951':'银川',
		'0952':'石嘴山',
		'0953':'吴忠',
		'0954':'固原',
		'0955':'中卫',
		'0970':'海北',
		'0971':'西宁',
		'0972':'海东',
		'0973':'黄南',
		'0974':'共和',
		'0975':'果洛',
		'0976':'玉树',
		'0977':'德令哈',
		'0979':'格尔木',
		'0990':'克拉玛依',
		'0991':'乌鲁木齐',
		'0992':'奎屯',
		'0993':'石河子',
		'0994':'昌吉',
		'0995':'吐鲁番',
		'0996':'库尔勒',
		'0997':'阿克苏',
		'0998':'喀什',
		'0999':'伊犁'};
</script>
<body>
<sitemesh:body/>
</body>
</html>