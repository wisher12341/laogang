function getTitle(title) {
    var a;
    if (title.constructor === Object){
        a= {
            text: title.text,
            link:title.link,
            textStyle: {
                color: '#fff',
                fontSize: 14,
                fontWeight: 'normal'
            },
            x: '0%',
            y: '0%'
        };
    }else{
        a= {
            text: title,
            textStyle: {
                color: '#fff',
                fontSize: 14,
                fontWeight: 'normal'
            },
            x: '0%',
            y: '0%'
        };
    }
    return a;
}

function getLegend(data) {
    return {
        data: data,
        itemWidth: 10,  // 设置大小
        itemHeight: 10,
        itemGap: 5, // 设置间距
        icon: "circle", //设置形状
        right: 10,
        top: 3,
        bottom: 20,
        orient: 'vertical',
        textStyle: { //图例文字的样式
            color: '#fff',
            fontSize: 10
        }
    };
}

/**
 * 圆角环形图
 * @param title
 * @param data
 * @param obj
 * @param callback
 * @param lengend
 */
function pie1(title, data, target, callback, customLegend) {
    var obj = document.getElementById(target);
    var legend_data = [];
    var series_data = [];
    var i = 0;
    for (var key in data) {
        legend_data[i] = key;
        series_data[i] = {name: key, value: data[key]};
        i++;
    }
    var pie = echarts.init(obj);
    var legend;
    if (customLegend === null) {
        legend = getLegend(legend_data)
    } else {
        legend = customLegend;
        legend.data = legend_data;
    }
    var option = {
        title: getTitle(title),
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: legend,
        series: [
            {
                name: '人数',
                type: 'pie',
                radius: ['40%', '70%'],
                center: ['47%', '60%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 5,
                    borderColor: 'rgb(30,55,70)',
                    borderWidth: 1
                },
                data: series_data,
                //去掉指示线
                label: {
                    normal: {
                        position: 'inner',
                        show: false
                    }
                },
                labelLine: {
                    show: false
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    if (callback !== null) {
        pie.off('click');
        pie.on('click', function (params) {
            var name = params.name;
            callback(title, name);

        });
    }

    pie.setOption(option, true);
}

/**
 * 饼图
 * @param title
 * @param data
 * @param obj
 * @param callback
 * @param lengend
 */
function pie2(title, data, target, callback, customLegend) {
    var obj = document.getElementById(target);
    var legend_data = [];
    var series_data = [];
    var i = 0;
    for (var key in data) {
        legend_data[i] = key;
        series_data[i] = {name: key, value: data[key]};
        i++;
    }
    var pie = echarts.init(obj);
    var legend;
    if (customLegend === null) {
        legend = getLegend(legend_data)
    } else {
        legend =customLegend
    }
    var option = {
        title: getTitle(title),
        tooltip: {
            trigger: 'item'
        },
        legend:legend,
        series: [
            {
                center: ['43%', '60%'],
                name: '访问来源',
                type: 'pie',
                radius: '60%',
                data: series_data,
                label: {
                    normal: {
                        position: 'inner',
                        show : false
                    }
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    if (callback !== null) {
        pie.off('click');
        pie.on('click', function (params) {
            var name = params.name;
            callback(title, name);

        });
    }

    pie.setOption(option, true);
}


/**
 * 极坐标柱图
 * @param title
 * @param data
 * @param obj
 * @param callback
 * @param lengend
 */
function bar4(title, data, target, callback, customLegend) {
    var obj = document.getElementById(target);
    var series_data = [];
    var xAxisData = [];
    var i = 0;
    for (var key in data) {
        series_data[i] = data[key];
        xAxisData[i] = key;
        i++;
    }
    var bar = echarts.init(obj);
    var option = {
        title:getTitle(title),
        angleAxis: {
            type: 'category',
            data: xAxisData,
            axisLabel: {
                textStyle: {
                    color: '#fff',
                    fontSize: '10',
                    itemSize: ''

                }
            }
        },
        radiusAxis: {
            axisLabel: {
                textStyle: {
                    color: '#fff',
                    fontSize: '14',
                    itemSize: ''

                }
            }
        },
        polar: {},
        series: [{
            type: 'bar',
            data: series_data,
            coordinateSystem: 'polar',
            name: 'A',
            stack: 'a',
            emphasis: {
                focus: 'series'
            }
        }]
    };


    if (callback !== null) {
        bar.off('click');
        bar.on('click', function (params) {
            if (params.componentType == "yAxis" || params.componentType == "xAxis") {
                var name = params.value;
                callback(name);
            }

        });
    }
    bar.setOption(option, true);
}

/**
 * 嵌套饼图
 * @param title
 * @param data
 * @param obj
 * @param callback
 * @param lengend
 */
function pie3(title, data, target, callback, legendData) {
    var obj = document.getElementById(target);
    var pie = echarts.init(obj);
    var option = {
        title: getTitle(title),
        tooltip: {
            trigger: 'item'
        },
        legend: getLegend(legendData),
        series: [
            {
                name: '',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    normal: {
                        formatter: '{d}%',
                        position: 'inner',
                        fontSize: 20,
                        textStyle: {
                            color: '#fff',
                            fontSize: '20'
                        }

                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: data[0]
            },
            {
                name: '',
                type: 'pie',
                radius: ['40%', '55%'],
                label: {
                    normal: {
                        position: 'inner',
                        show : false
                    }
                },
                data: data[1]
            }
        ]
    };
    console.info(JSON.stringify(option));
    if (callback !== null) {
        pie.off('click');
        pie.on('click', function (params) {
            var name = params.name;
            callback(title, name);

        });
    }

    pie.setOption(option, true);
}

/**
 * 仪表盘
 * @param title
 * @param data
 * @param target
 * @param callback
 * @param customLegend
 */
function gauge1(title, data, target, callback, customLegend) {
    var obj = document.getElementById(target);
    var gauge = echarts.init(obj);
    var option = {
        title: getTitle(title),
        series: [{
            type: 'gauge',
            progress: {
                show: true,
                width: 18
            },
            axisLine: {
                lineStyle: {
                    width: 18
                }
            },
            axisTick: {
                show: false
            },
            splitLine: {
                length: 15,
                lineStyle: {
                    width: 1,
                    color: '#fff'
                }
            },
            axisLabel: {
                distance: 25,
                color: '#fff',
                fontSize: 12
            },
            anchor: {
                show: true,
                showAbove: true,
                size: 12,
                itemStyle: {
                    borderWidth: 10
                }
            },
            title: {
                show: false
            },
            detail: {
                valueAnimation: true,
                fontSize: 14,
                offsetCenter: [0, '70%'],
                color: "#fff"
            },
            data: [{
                value: data
            }]
        }]
    };

    gauge.setOption(option, true);
}


/**
 * 柱状图
 * @param title
 * @param data
 * @param obj
 * @param callback
 */
function bar1(title, data, target, callback) {
    var obj = document.getElementById(target);
    var series_data = [];
    var xAxisData = [];
    var i = 0;
    for (var key in data) {
        series_data[i] = data[key];
        xAxisData[i] = key;
        i++;
    }
    var bar = echarts.init(obj);

    var option = {
        title: getTitle(title),
        xAxis: {
            type: 'category',
            data: xAxisData,
            axisLabel: {
                textStyle: {
                    color: '#fff',
                    fontSize: '10',
                    itemSize: ''

                }
            }
        },
        yAxis: {
            type: 'value',
            axisTick: {
                show: false
            },
            // y 轴线
            axisLine: {
                show: false,

            },
            // 分割线设置
            splitLine: {
                show: false,  //显示分割线
            },
            axisLabel: {
                textStyle: {
                    color: '#fff',
                    fontSize: '10',
                    itemSize: ''

                }
            }
        },
        series: {
            data: series_data,
            type: 'bar'
        }
    };

    if (callback !== null) {
        bar.off('click');
        bar.on('click', function (params) {
            if (params.componentType == "yAxis" || params.componentType == "xAxis") {
                var name = params.value;
                callback(name);
            }

        });
    }
    bar.setOption(option, true);
}


/**
 * 柱状图 特性示例：渐变色 阴影 点击缩放
 * @param title
 * @param data
 * @param obj
 * @param callback
 */
function bar3(title, data, target, callback) {
    var obj = document.getElementById(target);
    var series_data = [];
    var xAxisData = [];
    var i = 0;
    for (var key in data) {
        series_data[i] = data[key];
        xAxisData[i] = key;
        i++;
    }
    var bar = echarts.init(obj);
    var option = {
        title: getTitle(title),
        xAxis: {
            data: xAxisData,
            axisLabel: {
                inside: true,
                textStyle: {
                    color: '#fff'
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            z: 10
        },
        yAxis: {
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            }
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            {
                type: 'bar',
                showBackground: true,
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#83bff6'},
                            {offset: 0.5, color: '#188df0'},
                            {offset: 1, color: '#188df0'}
                        ]
                    )
                },
                emphasis: {
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2378f7'},
                                {offset: 0.7, color: '#2378f7'},
                                {offset: 1, color: '#83bff6'}
                            ]
                        )
                    }
                },
                data: series_data
            }
        ]
    };


    if (callback !== null) {
        bar.off('click');
        bar.on('click', function (params) {
            if (params.componentType == "yAxis" || params.componentType == "xAxis") {
                var name = params.value;
                callback(name);
            }

        });
    }
    bar.setOption(option, true);
}

/**
 * 堆叠条形图
 * @param title
 * @param data
 * @param target
 * @param callback
 */
function bar2(title, data, target, callback) {
    var obj = document.getElementById(target);
    var bar = echarts.init(obj);

    var yAxis_data = [];
    var series_data = [];
    var legend_data = [];
    var i = 0;
    for (var key1 in data) {
        yAxis_data[i] = key1;
        var j = 0;
        for (var key2 in data[key1]) {
            if (series_data[j] === null || series_data[j] === undefined) {
                series_data[j] = [];
            }
            series_data[j][i] = data[key1][key2];
            if (i === 0) {
                legend_data[j] = key2;
            }
            j++;
        }
        i++
    }
    var series = [];
    for (var k = 0; k < series_data.length; k++) {
        series[k] = {
            name: legend_data[k],
            type: 'bar',
            stack: '总量',
            label: {
                show: true
            },
            emphasis: {
                focus: 'series'
            },
            data: series_data[k]
        }
    }
    var option = {
        title: getTitle(title),
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // Use axis to trigger tooltip
                type: 'shadow'        // 'shadow' as default; can also be 'line' or 'shadow'
            }
        },
        legend: getLegend(legend_data),
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: yAxis_data,
            axisLabel: {
                textStyle: {
                    color: '#fff',
                    fontSize: '14',
                    itemSize: ''

                }
            }
        },
        series: series
    };

    if (callback !== null) {
        bar.off('click');
        bar.on('click', function (params) {
            if (params.componentType == "yAxis" || params.componentType == "xAxis") {
                var name = params.value;
                callback(name);
            }

        });
    }
    bar.setOption(option, true);
}