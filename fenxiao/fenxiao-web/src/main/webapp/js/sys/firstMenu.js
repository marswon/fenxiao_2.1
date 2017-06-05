window.onload = function () {
    loadFirstMenu();
}

/**
 * 加载一级菜单
 */
function loadFirstMenu() {
    $.getJSON("sys/menu/firstUser?_" + $.now(), function (r) {
        for (var i = 0; i < r.firstList.length; i++) {
            var name = r.firstList[i].name;
            $("#firstMenu").append(
                '<li id="firstMenu' + i
                + '" menuId="' + r.firstList[i].id + '"><a href="#"> <i class="fa fa-users"></i> &nbsp;'
                + name + '</a></li>');
        }
        $("#firstMenu li").click(function () {
            $(this).siblings('li').removeClass('firstMenuYes');  // 删除其他兄弟元素的样式
            $(this).addClass('firstMenuYes');                   // 添加当前元素的样式
            var menuId = $(this).attr("menuId");
            reloadLastMenu(menuId);
        });
    });
}
/**
 * 加载二级以上菜单，根据一级菜单menuId
 * @param index
 */
function reloadLastMenu(index) {
    //清空组建
    vm.menuList = "";
    //加载组建信息
    $.getJSON("sys/menu/user?menuId=" + index + "&_" + $.now(), function (r) {
        vm.menuList =r.menuList;
    });
}
