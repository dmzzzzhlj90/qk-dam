let api = [];
api.push({
    alias: 'debug',
    order: '1',
    desc: '数据标准excel导入导出功能接口',
    link: '数据标准excel导入导出功能接口',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '业务术语excel导出 @Param: response',
});
api[0].list.push({
    order: '2',
    desc: '业务术语excel导入 @Param: file',
});
api[0].list.push({
    order: '3',
    desc: '数据标准基本信息excel导出 @Param: response',
});
api[0].list.push({
    order: '4',
    desc: '数据标准基本信息excel 导入 @Param: file',
});
api[0].list.push({
    order: '5',
    desc: '码表信息excel导出 @Param: response',
});
api[0].list.push({
    order: '6',
    desc: '码表信息excel导入 @Param: file',
});
api.push({
    alias: 'DataStandardTermController',
    order: '2',
    desc: '数据标准业务术语接口',
    link: '数据标准业务术语接口',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '查询业务术语信息',
});
api[1].list.push({
    order: '2',
    desc: '新增业务术语信息',
});
api[1].list.push({
    order: '3',
    desc: '编辑业务术语信息',
});
api[1].list.push({
    order: '4',
    desc: '删除业务术语信息',
});
api.push({
    alias: 'DataStandardBasicInfoController',
    order: '3',
    desc: '数据标准标准信息接口',
    link: '数据标准标准信息接口',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '查询所有标准信息',
});
api[2].list.push({
    order: '2',
    desc: '根据标准分类id查询标准信息',
});
api[2].list.push({
    order: '3',
    desc: '新增标准信息',
});
api[2].list.push({
    order: '4',
    desc: '编辑标准信息',
});
api[2].list.push({
    order: '5',
    desc: '删除标准信息',
});
api.push({
    alias: 'DataStandardDirController',
    order: '4',
    desc: '数据标准目录接口',
    link: '数据标准目录接口',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '获取数据标准分类目录树',
});
api[3].list.push({
    order: '2',
    desc: '新增数据标准分类目录',
});
api[3].list.push({
    order: '3',
    desc: '编辑数据标准分类目录',
});
api[3].list.push({
    order: '4',
    desc: '标准目录单子节点删除方式',
});
api[3].list.push({
    order: '5',
    desc: '标准目录支持根节点关联删除子节点方式',
});
api.push({
    alias: 'DataStandardCodeTermController',
    order: '5',
    desc: '数据标准码表术语接口',
    link: '数据标准码表术语接口',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '查询所有的数据标准码表术语信息',
});
api[4].list.push({
    order: '2',
    desc: '根据码表分类id查询码表术语信息',
});
api[4].list.push({
    order: '3',
    desc: '新增数据标准码表术语信息',
});
api[4].list.push({
    order: '4',
    desc: '编辑数据标准码表术语信息',
});
api[4].list.push({
    order: '5',
    desc: '删除数据标准码表术语信息',
});
api.push({
    alias: 'DataStandardCodeDirController',
    order: '6',
    desc: '数据标准码表目录接口',
    link: '数据标准码表目录接口',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '获取数据标准码表分类目录树',
});
api[5].list.push({
    order: '2',
    desc: '新增码表分类目录',
});
api[5].list.push({
    order: '3',
    desc: '编辑码表分类目录',
});
api[5].list.push({
    order: '4',
    desc: '码表目录单叶子节点删除方式',
});
api[5].list.push({
    order: '5',
    desc: '码表目录支持根节点删除关联删除叶子节点方式',
});
api.push({
    alias: 'dict',
    order: '7',
    desc: '数据字典',
    link: 'dict_list',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}