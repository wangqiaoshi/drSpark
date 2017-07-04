<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>spark平台资源统计</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <%--<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->--%>
    <%--<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->--%>
    <%--<!--[if lt IE 9]>--%>
    <%--<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>--%>
    <%--<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>--%>
    <%--<![endif]-->--%>

    <script type="text/javascript" src="js/jquery.js"></script>
    <style type="text/css">
        ${demo.css}
    </style>

    <script src="js/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script type="text/javascript">


        var totalCores = ${totalCores}
        var memory = ${memory}
        var submitCounts = ${submitCounts}
        var submitMemory = ${submitMemory}
        var submitCore = ${submitCore}

        $(function () {
            Highcharts.setOptions({ global: { useUTC: false } });
            Highcharts.chart("container",
                    {chart: {
                        plotShadow: false,
                        margin: [0, 0, 0, 0]
            },
            title: {
                text: '当前资源使用比例',
                style:'{"color": "#333333", "fontSize": "8px"}'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                            cursor: 'pointer',
                    size:100,
                            dataLabels: {
                        enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                name: 'cpu',
                type: 'pie',
                center: [200, 100],
                colorByPoint: true,
                pointWidth: 15,
                data: totalCores
            },
                { name: 'memory',
                    type:'pie',
                    center: [240, 260],
                    colorByPoint: true,
                    pointWidth: 28,
                    data: memory,
                }
            ]})



            var myChart = Highcharts.chart('bar', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '每小时的提交次数'
                },
                xAxis: {
                    title:{text:'时间'},
                    type:'datetime'
                },
                yAxis:{
                    title:{text:'提交量'}
                },
                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                },
                series: submitCounts
            });

            var bar_daily = Highcharts.chart('bar_daily', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '过去一天每小时的内存使用比例'
                },
                xAxis: {
                    title:{text:'时间'},
                    type:'datetime'
                },
                yAxis:{
                    title:{text:'提交量'},
                    labels: {
                        formatter: function() {
                            return this.value +'M';
                        }
                    }
                },
                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                },
                series:submitMemory
            });

            var bar_cpu_hour = Highcharts.chart('bar_cpu_hour', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '过去一天每小时的cpu使用比例'
                },
                xAxis: {
                    title:{text:'时间'},
                    type:'datetime'
                },
                yAxis:{
                    title:{text:'提交量'},
                    labels: {
                        formatter: function() {
                            return this.value +'core';
                        }
                    }
                },
                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                },
                series:submitCore
            });
        });
    </script>

</head>

<body>
<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="applist">spark平台监控</a>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
                <ul class="dropdown-menu message-dropdown">
                    <li class="message-preview">
                        <a href="#">
                            <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                <div class="media-body">
                                    <h5 class="media-heading">
                                        <strong>John Smith</strong>
                                    </h5>
                                    <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                    <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="message-preview">
                        <a href="#">
                            <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                <div class="media-body">
                                    <h5 class="media-heading">
                                        <strong>John Smith</strong>
                                    </h5>
                                    <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                    <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="message-preview">
                        <a href="#">
                            <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                <div class="media-body">
                                    <h5 class="media-heading">
                                        <strong>John Smith</strong>
                                    </h5>
                                    <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                    <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="message-footer">
                        <a href="#">Read All New Messages</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                <ul class="dropdown-menu alert-dropdown">
                    <li>
                        <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
                    </li>
                    <li>
                        <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
                    </li>
                    <li>
                        <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
                    </li>
                    <li>
                        <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
                    </li>
                    <li>
                        <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
                    </li>
                    <li>
                        <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">View All</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                    </li>
                </ul>
            </li>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <li>
                    <a href="applist"><i class="fa fa-fw fa-bar-chart-o"></i>spark程序监控</a>
                </li>
                <li  class="active">
                    <a href="usedStatistics"><i class="fa fa-fw fa-edit"></i>spark使用统计</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        spark平台资源统计
                    </h1>
                    <%--<ol class="breadcrumb">--%>
                        <%--<li>--%>
                            <%--<i class="fa fa-dashboard"></i>  <a href="index.html">Dashboard</a>--%>
                        <%--</li>--%>
                        <%--<li class="active">--%>
                            <%--<i class="fa fa-file"></i>spark使用统计--%>
                        <%--</li>--%>
                    <%--</ol>--%>
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="panel panel-red">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 过去一天内存和cpu使用比例</h3>
                                </div>
                                <div class="panel-body">
                                   <div id="container"></div>
                                    <div class="text-right">
                                        <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i>过去一天每小时的用户提交量</h3>
                                </div>
                                <div class="panel-body">
                                    <div id="bar"></div>
                                    <div class="text-right">
                                        <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i>过去一天每小时的用户内存使用量</h3>
                                </div>
                                <div class="panel-body">
                                    <div id="bar_daily"></div>
                                    <div class="text-right">
                                        <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                                    </div>
                                </div>
                            </div>

                    </div>
                        <div class="col-lg-6">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i>过去一天每小时的用户cpu使用量</h3>
                                </div>
                                <div class="panel-body">
                                    <div id="bar_cpu_hour"></div>
                                    <div class="text-right">
                                        <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>

                </div>
            </div>
            <!-- /.row -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

</body>

</html>